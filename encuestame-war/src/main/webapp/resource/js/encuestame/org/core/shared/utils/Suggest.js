dojo.provide("encuestame.org.core.shared.utils.Suggest");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dojox.data.QueryReadStore");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.main.EnmeMainLayoutWidget');

dojo.declare(
    "encuestame.org.core.shared.utils.Suggest",
    [encuestame.org.main.EnmeMainLayoutWidget],{

      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/suggest.html"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,

        store: null,

        label : "Add",

        url : encuestame.service.list.hashtags,

        textBoxWidget : null,

        buttonWidget : null,

        hideLabel : false,

        selectedItem : null,

        addButton : true,

        modeMultiSearch : false,

        multiStores : [],

        modeQuery : "get",

        limit : 10,

        label : "Label",

        query :  {hashTagName : "*"},

        sortFields : [{attribute: 'hashTagName', descending: true}],

        /*
         * Default search parameters.
         */
        searchParam: { limit : 10, keyword : ""},

        /*
         * A filter list to exluce from suggested items.
         */
        exclude : [],

        _itemStored : [],

        _delay : 700,

        _inProcessKey : false,

        /*
         * post create life cylce.
         */
        postCreate: function() {
            this.textBoxWidget = dijit.byId(this._suggest);
            if(this.textBoxWidget){
              //enable keyword events
                dojo.connect(this.textBoxWidget, "onKeyUp", dojo.hitch(this, function(e) {
                    if (!this._inProcessKey) {
                        this._inProcessKey = true;
                        var parent = this;
                        dojo.hitch(this, setTimeout(function () {
                            parent._inProcessKey = false;
                            if (dojo.keys.SPACE == e.keyCode || dojo.keys.ENTER == e.keyCode) {
                                parent.processSpaceAction();
                            } else if (dojo.keys.UP_ARROW == e.keyCode) {
                                //TODO: down by suggestion list.
                            } else if (dojo.keys.DOWN_ARROW == e.keyCode) {
                                //TODO: up by suggestion list.
                            } else {
                                parent._setParams(
                                        { limit: parent.limit,
                                          keyword : parent.textBoxWidget.get("value"),
                                          excludes : parent.exclude});
                                //console.debug("suggest", this.textBoxWidget.get("value"));
                                if (!encuestame.utilities.isEmpty(parent.textBoxWidget.get("value"))) {
                                    //call first time suggest.
                                     parent.callSuggest();
                                 }
                              }
                              // this.textBoxWidget //TODO: this.hide() on lost focus.
                        }, this._delay));
                      }
                }));
                //query read store.
                this.store = new dojox.data.QueryReadStore({
                    url: this.url,
                    sortFields : this.sortFields,
                    requestMethod : this.modeQuery}
                );
                 this.callSuggest();
                //enable add button, if not the default add is click on item.
                if (this.addButton) {
                  //check if node exist.
                  if (this._suggestButton) {
                      dojo.style(this._suggestButton, "display", "block");
                      this.buttonWidget = new dijit.form.Button({
                          label: "Add",
                          onClick: dojo.hitch(this, function(event) {
                              dojo.stopEvent(event);
                              this.processSelectedItemButton();
                          })
                      },
                      this._suggestButton);
                  }
                }
                if (this.hideLabel) {
                   if(this._label) {
                     dojo.addClass(this._label,"defaultDisplayHide");
                   }
                }
            } else {
                console.error("Error");
            }
        },

        block : function(){

        },

        unblock : function(){

        },

        /*
         * if user click up space bar.
         */
        processSpaceAction : function(){
            //overide.
        },

        _setParams: function(value){
            this.searchParam = value;
        },

        /*
         * start suggestion call.
         */
        callSuggest : function(){
            var fetch = {
                    query: this.query,
                    queryOptions: {
                        ignoreCase: this.ignoreCase,
                        deep: true
                    },
                    serverQuery: this.searchParam,
                    onComplete: dojo.hitch(this, function(result, dataObject){
                        this._itemStored = result;
                        this.evaluateItems();
                    }),
                    onError: function(errText){
                        console.error('dijit.form.FilteringSelect: ' + errText);
                    }
                };
            this.store.fetch(fetch);
        },

        /*
         *  Evaluate Items.
         */
        evaluateItems : function(){
            if(this._itemStored.length > 0){
                 dojo.empty(this._suggestItems);
                 var fadeArgs = {
                         node: this._suggestItems
                 };
                 dojo.fadeIn(fadeArgs).play();
                 dojo.forEach(
                        this._itemStored,
                        dojo.hitch(this, function(data, index) {
                            this.buildRow(data.i);
                        }));
            } else {
                var fadeArgs = {
                     node: this._suggestItems
                 };
                 dojo.fadeOut(fadeArgs).play();
            }
        },

        /*
         * hide with fade out the suggest box.
         */
        hide : function(){
            //console.info("HIDE");
            this._itemStored = [];
            var fadeArgs = {
                    node: this._suggestItems
            };
            dojo.fadeOut(fadeArgs).play();
            this.clear();
        },

        /*
         *  Build Row.
         */
        buildRow : function(/** hashtag item. **/ data){
            var widget = new encuestame.org.core.shared.utils.SuggestItem(
                    {
                        data: { id : data.id, label : data.hashTagName},
                        parentWidget : this
                    });
            this._suggestItems.appendChild(widget.domNode);
            widget.processItem = this.processSelectedItem;
        },

        //Process after click add button.
        processSelectedItemButton : function() {
            if (this.textBoxWidget && this.addButton) {
                this.hide();
                var newValue = {id:null, label:"", newValue: true};
                newValue.label = this.textBoxWidget.get("value");
                this.selectedItem = newValue;
                if (!encuestame.utilities.isEmpty(newValue.label)) {
                    this.processSelectedItem(this.selectedItem);
                }
                this.clear();
            }
        },

        clear : function(){
              if(this.textBoxWidget){
                  this.selectedItem = null;
                  this.textBoxWidget.set("value", "");
              }
              dojo.empty(this._suggestItems);
        },

        //Process Selected Item.
        processSelectedItem : function(selectedItem){
            console.info("implemt this method in the parent widget 2", selectedItem);
        }
});

dojo.extend(dojox.data.QueryReadStore, {
    _filterResponse: function(data){
        data = data.success;
        return data;
    }
});

/**
 * Suggested Item.
 */
dojo.declare(
        "encuestame.org.core.shared.utils.SuggestItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/suggestItem.html"),

            /** Allow other widgets in the template. **/
            widgetsInTemplate: true,
            data : null,
            parentWidget  : null,
            selected : false,
            postCreate : function(){
            },
            //Selected Item.
            _selectItem: function(event){
                dojo.stopEvent(event);
                this.selected = !this.selected;
                this.parentWidget.selectedItem = this.data;
                this.parentWidget.hide();
                this.processItem(this.data);
            },
            //Process Item.
            processItem : function(){
                console.info("implemt this method in the parent widget 1");
            }
});
