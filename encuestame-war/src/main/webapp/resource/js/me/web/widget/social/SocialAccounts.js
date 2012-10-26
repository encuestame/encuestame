define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/menu/SettingsMenuSwitch",
         "me/web/widget/social/SocialButton",
         "me/web/widget/social/SocialAccountDetail",
         "me/core/enme",
         "dojo/text!me/web/widget/social/templates/socialAccounts.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                SettingsMenuSwitch,
                SocialButton,
                SocialAccountDetail,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, SettingsMenuSwitch, _WidgetsInTemplateMixin], {

        /*
         * template string.
         */
        templateString : template,

         /**
         *
         */
        domain : _ENME.config('contextPath'),

        /**
         *
         */
        postCreate : function() {
            //details
            this._createDetail("twitterDetail", "Twitter");
            this._createDetail("facebookDetail", "Facebook");
            this._createDetail("linkedinDetail", "LinkedIn");
            this._createDetail("googleplusDetail", "GooglePlus");
            this._createDetail("identicaDetail", "Identica");
            //this._createDetail("yahooDetail", "Yahoo"); DISABLED
            //buttons
            this._cretateButton("twitter", "Twitter");
            this._cretateButton("facebook", "Facebook");
            this._cretateButton("linkedin", "LinkedIn");
            this._cretateButton("googleplus", "Google +");
            this._cretateButton("identica", "Identi.ca");
            //this._cretateButton("yahoo", "Yahoo"); DISABLED
        },

        /*
         *
         */
        _createDetail : function(id, provider) {
            //console.debug("_createDetail", provider);
            var widget = new SocialAccountDetail(
                    {
                        id : id,
                        socialProvider : provider
                    }
                    );
            dojo.addClass(widget.domNode, "defaultDisplayHide");
            this._detail.appendChild(widget.domNode);
        },

        /*
         *
         */
        _cretateButton : function(id, provider) {
            var widget = new SocialButton(
                    {
                        id : id,
                        label : provider
                    }
                    );
            this._buttons.appendChild(widget.domNode);
        }

   });
});