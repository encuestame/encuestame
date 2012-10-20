define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollPublishItemStatus",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollPublishItemSUCCESSStatus.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TweetPollPublishItemStatus,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
          templateString : template,

          metadata : null,
          /**
           * i18N Message.
           */
          i18nMessage : {
            commons_success : ENME.getMessage("commons_success", "SUCCESS"),
            pubication_success_status : ENME.getMessage("pubication_success_status", "pubication_success_status")
          }
    });
});