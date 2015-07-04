define([
         "dojo/_base/declare",
         "me/web/widget/tweetpoll/TweetPollPublishItemAbstractStatus",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollPublishItemSUCCESSStatus.html" ],
        function(
                declare,
                TweetPollPublishItemAbstractStatus,
                _ENME,
                template) {
            return declare([TweetPollPublishItemAbstractStatus], {

           /**
            * Template string reference
            * @param templateString
            */
          templateString : template,

        /**
          * i18N Message.
          * @property i18nMessage
          */
          i18nMessage : {
            commons_success : _ENME.getMessage("commons_success", "SUCCESS"),
            pubication_success_status : _ENME.getMessage("pubication_success_status", "Your tweet has been sent"),
            link_to_publication : _ENME.getMessage("link_to_publication", "Link to your Publication")
          }
    });
});