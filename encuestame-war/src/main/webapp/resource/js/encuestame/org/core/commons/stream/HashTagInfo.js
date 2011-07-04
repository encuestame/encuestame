dojo.provide("encuestame.org.core.commons.stream.HashTagInfo");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.commons.stream.HashTagInfo",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.stream", "templates/hashTagInfo.html"),

        widgetsInTemplate: true,

        hashTagName : "",

        url : "#",

        /*
         * Post create.
         */
        postCreate : function() {
            if (this._hashtag) {
               // this._hashtag.href = encuestame.contextDefault+"/tag/"+this.hashTagName+"/";
            }
        }
});