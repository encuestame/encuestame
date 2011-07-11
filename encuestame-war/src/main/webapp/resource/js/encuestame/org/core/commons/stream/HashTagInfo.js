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

        size : null,

        url : "#",

        /*
         * Post create.
         */
        postCreate : function() {
            if (this.size) {
               //console.debug(this.size);
               dojo.style(this._hashtag, "font-size", this.size+"px");
               //console.debug(this._hashtag);
            }
        }
});