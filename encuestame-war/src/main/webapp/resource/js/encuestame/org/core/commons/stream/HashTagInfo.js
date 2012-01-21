dojo.provide("encuestame.org.core.commons.stream.HashTagInfo");
;
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.commons.stream.HashTagInfo",
    [encuestame.org.main.EnmeMainLayoutWidget],{

        /*
         * template.
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.stream", "templates/hashTagInfo.html"),

        /*
         * the hashtag name.
         */
        hashTagName : "",

        /*
         * customized the size of font.
         */
        size : null,

        /*
         * set true to create url automatically.
         */
        autoCreateUrl : false,

        /*
         * default class of css.
         */
        cssClass : "tag",

        url : "",

        /*
         * initialized before render.
         */
        postMixInProperties : function(){
            if (this.autoCreateUrl) {
                //<%=request.getContextPath()%>/tag/${h.hashTagName}/
                this.url = this.url.concat(config.domain);
                //this.url = this.url.concat(config.contextPath);
                this.url = this.url.concat("/tag/");
                this.url = this.url.concat(this.hashTagName);
            }
        },

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