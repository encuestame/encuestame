dojo.provide("encuestame.org.core.commons.stream.FrontEnd");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.commons.stream.FrontEndItem');

dojo.declare(
    "encuestame.org.core.commons.stream.FrontEnd",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.stream", "templates/frontEnd.html"),

        widgetsInTemplate: true,

        _maxResults : 10,

        _start : 10,

        /*
         * post create.
         */
        postCreate : function(){
            dojo.connect(this._stream, "onclick", dojo.hitch(this, function(event) {
                this._loadItems();
                this._start = this._start + this._maxResults;
            }));
        },

        /*
         * print items.
         */
        _printItems : function(items){
            dojo.forEach(items,
                dojo.hitch(this,function(item) {
                  var widget = new encuestame.org.core.commons.stream.FrontEndItem(
                          {   questionName : item.questionBean.questionName,
                              id : item.id,
                              owner : item.ownerUsername,
                              votes : 100,
                              views : 440,
                              relativeTime: item.relativeTime,
                              hashtags : item.hashTags
                           });
                  this._list.appendChild(widget.domNode);
                }));
        },

        /*
         *
         */
        _fadeOutTrigger : function(){
            dojo.style(this._pagination, "opacity", "1");
            var fadeArgs = {
                node: this._pagination
            };
            dojo.fadeOut(fadeArgs).play();
        },

        /*
         * load lal items
         */
        _loadItems : function(){
            var load = dojo.hitch(this, function(data) {
                var items = data.success.frontendItems;

                if(items.length > 0) {
                    this._printItems(items);
                } else {
                    this._fadeOutTrigger();
                }
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.stream, {period : "30", maxResults : this._maxResults , start: this._start}, load, error);
        }
});
