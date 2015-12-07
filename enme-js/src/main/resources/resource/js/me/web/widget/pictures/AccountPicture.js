define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/pictures/templates/accountPicture.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                main_widget,
                _ENME,
                 template ) {

            return declare( [ _WidgetBase, _TemplatedMixin, main_widget ], {

                // Template string.
                templateString: template,

                // Picture width
                picture_width:"32",

                // Picture height
                picture_height: "32",

                // Image size
                type: "thumbnail",

                // Target
                target: "_self",

                // Default username
                username: "anonymous",

                /*
                 * Load a default image if the real image source fails.
                 */
                _loadDefaultImage: function( event ) {
                     this._img.src = this.contextPath + "/resources/images/default.png";
                 },

                /*
                 *
                 */
                 postMixInProperties: function() {
                    this.contextPath = _ENME.config("contextPath");
                }
            });
        });
