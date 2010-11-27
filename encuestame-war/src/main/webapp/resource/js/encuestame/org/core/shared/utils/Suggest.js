dojo.provide("encuestame.org.core.shared.utils.Suggest");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dojox.data.QueryReadStore");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.Suggest",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/suggest.inc"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,

        store: null,

        url : encuestame.service.list.hashtags,

        textBoxWidget : null,

        buttonWidget : null,

        selectedItem : null,

        addButton : true,

        limit : 10,

        label : "Label",

        searchParam: { limit : 10, keyword : ""},

        postCreate: function() {
            this.textBoxWidget = dijit.byId(this._suggest);
            if(this.textBoxWidget){
                dojo.connect(this.textBoxWidget, "onKeyDown", dojo.hitch(this, function(e) {
                    this._setParams({limit:this.limit, keyword : this.textBoxWidget.get("value")});
                    this.callSuggest();
                }));
                this.store = new dojox.data.QueryReadStore({
                    url: this.url,
                    sortFields : [{attribute: 'hashTagName', descending: true}],
                    requestMethod : "get"}
                );
                this.callSuggest();
                if(this.addButton){
                    dojo.style(this._suggestButton, "display", "block");
                    this.buttonWidget = new dijit.form.Button({
                        label: "Add",
                        onClick: dojo.hitch(this, function() {
                            this.processSelectedItemButton();
                        })
                    },
                    this._suggestButton);
                    console.debug(this.buttonWidget);
                }
            } else {
                console.error("Error");
            }
        },

        _setParams: function(value){
            this.searchParam = value;
        },

        callSuggest : function(){
            var fetch = {
                    query: {hashTagName : "*"},
                    queryOptions: {
                        ignoreCase: this.ignoreCase,
                        deep: true
                    },
                    serverQuery: this.searchParam,
                    onComplete: dojo.hitch(this, function(result, dataObject){
                        this.evaluateItems(result);
                    }),
                    onError: function(errText){
                        console.error('dijit.form.FilteringSelect: ' + errText);
                    }
                };
            this.store.fetch(fetch);
        },

        /** Evaluate Items. **/
        evaluateItems : function(data){
            if(data.length > 0){
                 dojo.empty(this._suggestItems);
                 var fadeArgs = {
                         node: this._suggestItems
                 };
                 dojo.fadeIn(fadeArgs).play();
                 dojo.forEach(
                        data,
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

        hide : function(){
            var fadeArgs = {
                    node: this._suggestItems
            };
            dojo.fadeOut(fadeArgs).play();
            this.clear();
        },

        /** Build Row. **/
        buildRow : function(data){
            var widget = new encuestame.org.core.shared.utils.SuggestItem(
                    {
                        data: { id : data.id, label : data.hashTagName},
                        parentWidget : this
                    });
            this._suggestItems.appendChild(widget.domNode);
            widget.processItem = this.processSelectedItem;
        },

        //Process after click add button.
        processSelectedItemButton : function(){
            if(this.textBoxWidget && this.addButton){
                this.hide();
                var newValue = {id:null, label:"", newValue: true};
                newValue.label = this.textBoxWidget.get("value");
                this.selectedItem = newValue;
                this.processSelectedItem(this.selectedItem);
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
            this.clear();
        }
});

dojo.extend(dojox.data.QueryReadStore, {
    _filterResponse: function(data){
        data = data.success;
        return data;
    },
});
/**
 * Suggested Item.
 */
dojo.declare(
        "encuestame.org.core.shared.utils.SuggestItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/suggestItem.inc"),

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
