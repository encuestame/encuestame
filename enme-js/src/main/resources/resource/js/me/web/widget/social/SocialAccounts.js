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
        helpSteps : [
            {
                element: '.listSettings',
                intro: _ENME.getMessage('help_center_search')
            },
            {
                element: '.settingsListDetail',
                intro: _ENME.getMessage('help_menu_search')
            },
            {
                element: '.toolBox .dijitButton',
                intro: _ENME.getMessage('help_menu_search')
            }
        ],

        /**
         *
         */
        postCreate : function() {
            //details
            this._createDetail("twitterDetail", "Twitter");
            this._createDetail("facebookDetail", "Facebook");
            this._createDetail("linkedinDetail", "LinkedIn");
            this._createDetail("tumblrDetail", "Tumblr");
            this._createDetail("plurkDetail", "Plurk");
            //this._createDetail("googleplusDetail", "GooglePlus");
            //this._createDetail("yahooDetail", "Yahoo"); DISABLED
            //buttons
            this._cretateButton("twitter", "Twitter", true);
            this._cretateButton("facebook", "Facebook");
            this._cretateButton("linkedin", "LinkedIn");
            this._cretateButton("tumblr", "Tumblr");
            this._cretateButton("plurk", "Plurk");
            //this._cretateButton("googleplus", "Google +");
            //this._cretateButton("yahoo", "Yahoo"); DISABLED
            //help links
            this.initHelpLinks(dojo.hitch(this, function(){
                this.updateHelpPageStatus(_ENME.config('currentPath'), true);
            }));
        },

        /*
         *
         */
        _createDetail : function(id, provider) {
            var widget = new SocialAccountDetail({
                id : id,
                socialProvider : provider
            });
            dojo.addClass(widget.domNode, "defaultDisplayHide");
            this._detail.appendChild(widget.domNode);
        },

        /*
         *
         */
        _cretateButton : function(id, provider) {
            var widget = new SocialButton({
                id : id,
                label : provider
            });
            this._buttons.appendChild(widget.domNode);
        }

   });
});