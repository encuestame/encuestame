define( [
         "dojo/_base/declare",
         "me/web/widget/tweetpoll/TweetPollPublishItemAbstractStatus",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollPublishItemSCHEDULEDtatus.html" ],
        function(
                declare,
                TweetPollPublishItemAbstractStatus,
                _ENME,
                template ) {
            return declare( [ TweetPollPublishItemAbstractStatus ], {

           /**
            * Template string reference
            * @param templateString
            */
          templateString: template,

        /**
          * I18N Message.
          * @property i18nMessage
          */
          i18nMessage: {
            commons_success: _ENME.getMessage("commons_scheduled", "SCHEDULED"),
            pubication_success_status: _ENME.getMessage("pubication_scheduled_status", "Your tweet has scheduled and will be published at")
          }
    });
});
