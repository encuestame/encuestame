dojo.provide("encuestame.org.core.shared.publications.Publications");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require('encuestame.org.core.commons.stream.HashTagInfo');

dojo.declare(
    "encuestame.org.core.shared.publications.Publications",
    [encuestame.org.main.EnmeMainLayoutWidget],{
        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.publications", "templates/publication.html"),

        username : "",

        title : "",

        url : "",

        added : "",

        relativeTime : "",

        itemId : "",

        ht : "",

        type : "",

        _tagsCloned : [],

        postMixInProperties : function(){
            if (this.ht != null) {
                this._tagsCloned = this.ht.split(",");
            }
        },

        /*
         * Post create.
         */
        postCreate : function() {
            var node = this._tags;
            dojo.forEach(this._tagsCloned, function(entry, i){
                var widget = new encuestame.org.core.commons.stream.HashTagInfo(
                        {
                          hashTagName : entry,
                          autoCreateUrl : true, //create url automatically
                          cssClass : "web-pub-hashtag"
                        });
                node.appendChild(widget.domNode);
            });
            var nodeCustom;
            if (dojo.indexOf(encuestame.surveys, dojo.trim(this.type)) == 0) { //tp

            } else if (dojo.indexOf(encuestame.surveys, dojo.trim(this.type)) == 1) { //pll

            } else if (dojo.indexOf(encuestame.surveys, dojo.trim(this.type)) == 0){ //survey

            } else {
                //error
            }
        }

//        _printCloud : function(items){
//            dojo.forEach(items,
//                dojo.hitch(this,function(item) {
//                  var wrapper = dojo.doc.createElement("div");
//                  dojo.style(wrapper, "display", "inline-block");
//                  dojo.style(wrapper, "margin", "5px");
//                  dojo.style(wrapper, "fontSize", "1"+item.size+"%");
//                  var widget = new encuestame.org.core.commons.stream.HashTagInfo(
//                          {hashTagName : item.hashTagName,
//                           url : encuestame.contextDefault+"/tag/"+item.hashTagName+"/",
//                           size : item.size});
//                  wrapper.appendChild(widget.domNode);
//                  this._hashtagCloud.appendChild(wrapper);
//                }));
//        },
//
//        /*
//         *
//         */
//        _loadCloud : function(){
//            var load = dojo.hitch(this, function(data) {
//                this.arrayAccounts = data.success.cloud;
//                dojo.empty(this._hashtagCloud);
//                //console.debug("social", this._hashtagCloud);
//                this._printCloud(data.success.cloud);
//            });
//            var error = function(error) {
//                //console.debug("error", error);
//            };
//            encuestame.service.xhrGet(
//                    encuestame.service.list.cloud, {}, load, error);
//        }
});
