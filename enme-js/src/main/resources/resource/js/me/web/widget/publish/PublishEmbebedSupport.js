define( [
         "dojo/_base/declare",
         "dojo/Deferred",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/utils/ContextSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/publish/templates/embebedSupport.html" ],
        function(
                declare,
                Deferred,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                ContextSupport,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, ContextSupport, _WidgetsInTemplateMixin ], {

         /*
          * Template string.
          */
         templateString: template,

         widgetsInTemplate: true,

         itemId: null,

         name: null,

         _domain: null,

         _pollPath: "/poll/",

         postMixInProperties: function() {
             this._domain = _ENME.config( "domain" );
         },

         postCreate: function() {
             this._buildJavascriptEmbebed();
         },

         _buildJsUrl: function() {
             return this._domain + this._pollPath + this.itemId + ".js";
         },

         _buildUrl: function() {
             return this._domain + this._pollPath + this.itemId + "/";
         },

         /*
          * <script type="text/javascript" charset="utf-8" src="http://demo.encuestame.org/poll/5439680.js"></script>
          * <noscript><a href="http://demo.encuestame.org/poll/5439680/">My New Poll</a></noscript>
          */
         _buildJavascriptEmbebed: function() {
             var script = "<script type=\"text/javascript\" charset=\"utf-8\" src=\"" + this._buildJsUrl() + "\"></script>";
             var noscript = "<noscript><a href=\"" + this._buildUrl() + "\">" + this.name + "</a></noscript>";
             this._textarea.value = script + noscript;
         }

    });
});
