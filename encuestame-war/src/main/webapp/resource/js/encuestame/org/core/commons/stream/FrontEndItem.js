dojo.provide("encuestame.org.core.commons.stream.FrontEndItem");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.commons.stream.HashTagInfo');

dojo.declare(
    "encuestame.org.core.commons.stream.FrontEndItem",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.stream", "templates/frontEndItem.html"),

        widgetsInTemplate: true,
        contextPath : encuestame.contextDefault,
        questionName : "",
        id : 0,
        owner : "dasd",
        votes : 0,
        views : 0,
        relativeTime: '',
        url : "#",

        /*
         * Post create.
         */
        postCreate : function() {
            if (this._hashtag) {
               // this._hashtag.href = encuestame.contextDefault+"/tag/"+this.hashTagName+"/";
            }
        },

        _geTtotalVotes : function(){

        }
});