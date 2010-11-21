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

        limit : 10,

        label : "Label",

        searchParam: { limit : 10, keyword : ""},

        postCreate: function() {
            this.textBoxWidget = dijit.byId(this._suggest);
            if(this.textBoxWidget){
                dojo.connect(this.textBoxWidget, "onKeyDown", dojo.hitch(this, function(e) {
                    console.debug("e ",e);
                    console.debug("this.textBoxWidget ", this.textBoxWidget);
                    console.debug("this.textBoxWidget.getValue() ",this.textBoxWidget.get("value"));
                    this._setParams({limit:this.limit, keyword : this.textBoxWidget.get("value")});
                    console.debug("New Params ",this.searchParam);
                    this.callSuggest();
                }));
                console.debug("url", this.url);
                this.store = new dojox.data.QueryReadStore({
                    url: this.url,
                    sortFields : [{attribute: 'hashTagName', descending: true}],
                    requestMethod : "get"}
                );
                this.callSuggest();
            } else {
                console.debug("Error");
            }
        },

        _setParams: function(value){
            this.searchParam = value;
        },

        callSuggest : function(){
            console.debug("callSuggest");
            var fetch = {
                    query: {hashTagName : "*"},
                    queryOptions: {
                        ignoreCase: this.ignoreCase,
                        deep: true
                    },
                    serverQuery: this.searchParam,
                    onComplete: dojo.hitch(this, function(result, dataObject){
                        console.debug("result", result);
                        console.debug("dataObject", dataObject);
                        this.evaluateItems(result);
                    }),
                    onError: function(errText){
                        console.error('dijit.form.FilteringSelect: ' + errText);
                    }
                };
            this.store.fetch(fetch);
        },

        evaluateItems : function(data){
            if(data.length > 0){
                dojo.forEach(
                        data,
                        dojo.hitch(this, function(data, index) {
                            this.buildRow(data);
                        }));
            }
        },

        buildRow : function(data){
            var widget = new encuestame.org.class.shared.utils.SuggestItem({data: data});
            console.debug(widget);
        }
});

dojo.declare(
        "encuestame.org.class.shared.utils.SuggestItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.class.shared.utils", "template/suggestItem.inc"),

            /** Allow other widgets in the template. **/
            widgetsInTemplate: true

});
