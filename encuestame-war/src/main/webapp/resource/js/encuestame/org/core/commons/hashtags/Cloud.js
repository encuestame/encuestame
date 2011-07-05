dojo.provide("encuestame.org.core.commons.hashtags.Cloud");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.commons.stream.HashTagInfo');

dojo.declare(
    "encuestame.org.core.commons.hashtags.Cloud",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.hashtags", "template/cloud.html"),

        widgetsInTemplate: true,

        _hashtagCloud : null,

        /*
         * Post create.
         */
        postCreate : function() {
            this._loadCloud();
        },

        _printCloud : function(items){
            dojo.forEach(items,
                dojo.hitch(this,function(item) {
                  var wrapper = dojo.doc.createElement("div");
                  dojo.style(wrapper, "display", "inline-block");
                  //dojo.style(wrapper, "float", "left");
                  dojo.style(wrapper, "margin", "5px");
                  dojo.style(wrapper, "fontSize", "1"+item.size+"%");
                  var widget = new encuestame.org.core.commons.stream.HashTagInfo(
                          {hashTagName : item.hashTagName,
                           url : encuestame.contextDefault+"/tag/"+item.hashTagName+"/",
                           size : item.size});
                  wrapper.appendChild(widget.domNode);
                  this._hashtagCloud.appendChild(wrapper);
                }));
        },

        /*
         *
         */
        _loadCloud : function(){
            var load = dojo.hitch(this, function(data) {
                this.arrayAccounts = data.success.cloud;
                dojo.empty(this._hashtagCloud);
                console.debug("social", this._hashtagCloud);
                this._printCloud(data.success.cloud);
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.list.cloud, {}, load, error);
        }
});
