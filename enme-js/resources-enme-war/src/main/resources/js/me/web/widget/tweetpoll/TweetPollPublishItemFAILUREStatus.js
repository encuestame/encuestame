define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollPublishItemFAILUREStatus.html" ],
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

          metadata : null,

          parentStatusWidget : null,

             /**
          * i18N Message.
          */
         i18nMessage : {
           commons_failure : _ENME.getMessage("commons_failure", "FAILURE"),
           button_try_later : _ENME.getMessage("button_try_later", ""),
           button_ignore : _ENME.getMessage("button_ignore", ""),
           button_try_again : _ENME.getMessage("button_try_again", ""),
           pubication_failure_status : _ENME.getMessage("pubication_failure_status", "pubication_failure_status")
             },

             /**
          *
          * @param events
          */
         _scheduleTweet : function(events) {}
    });
});