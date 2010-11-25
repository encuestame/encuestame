dojo.provide("encuestame.org.class.shared.utils.Suggest");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.TextBox");
dojo.require("dojox.data.QueryReadStore");
dojo.require('encuestame.org.class.commons');

dojo.declare(
    "encuestame.org.class.shared.utils.Suggest",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.shared.utils", "template/suggest.inc"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,

        store: null,

        url : encuestame.service.list.hashtags,

        textBoxWidget: null,

        selectedItem : null,

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
        },

        /** Build Row. **/
        buildRow : function(data){
            var widget = new encuestame.org.class.shared.utils.SuggestItem(
                    {
                        data: { id : data.id, label : data.hashTagName},
                        parentWidget : this
                    });
            this._suggestItems.appendChild(widget.domNode);
            widget.processItem = this.processSelectedItem;
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
    },
});
/**
 * Suggested Item.
 */
dojo.declare(
        "encuestame.org.class.shared.utils.SuggestItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.class.shared.utils", "template/suggestItem.inc"),

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
