define([
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
                 template) {

            return declare([ _WidgetBase, _TemplatedMixin, main_widget], {

                // template string.
                templateString : template,

                // picture width
                picture_width :"32",

                // picture height
                picture_height : "32",

                // image size
                type : "thumbnail",

                // target
                target : "_self",

                // default username
                username : "",

                /*
                 * Load a default image if the real image source fails.
                 */
                _loadDefaultImage : function (event) {
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