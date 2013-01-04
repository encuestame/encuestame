define([ 
         "dojo/_base/declare",
		 "dijit/_WidgetBase", 
		 "dijit/_TemplatedMixin",
		 "dijit/_WidgetsInTemplateMixin",
		 "me/core/main_widgets/EnmeMainLayoutWidget",
		 "me/core/enme",
		 "dojo/text!me/web/widget/stream/templates/hashTagInfo.html" ],
		function(
		declare,
		_WidgetBase, 
		_TemplatedMixin,
		_WidgetsInTemplateMixin,
		main_widget, 
		_ENME, 
		 template) {

	return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

		// template string.
		templateString : template,

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
                this.url = this.url.concat(_ENME.config('domain'));
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
});