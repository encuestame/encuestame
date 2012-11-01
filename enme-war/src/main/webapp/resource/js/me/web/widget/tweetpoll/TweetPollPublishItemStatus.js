define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollPublishItemSUCCESStatus",
         "me/web/widget/tweetpoll/TweetPollPublishItemFAILUREStatus",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollPublishItemStatus.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                TweetPollPublishItemSUCCESStatus,
                TweetPollPublishItemFAILUREStatus,
                main_widget,
                _ENME,
                 template) {
        return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

       /*
        *  template string.
        */
        templateString : template,

          /**
          *
          */
         data : {},
         /**
          * Social account data.
          */
         socialAccount : {},

         /**
          * Post create lyfe cycle.
          */
         postCreate : function() {
             this.initialize();
         },

         /**
          * initialize widget.
          */
         initialize : function() {
             if (this.data.status_tweet == encuestame.status[0]) {  SUCCESS
                 this._detailStatus.appendChild(this._showSuccessMessage());
             } else if (this.data.status_tweet == encuestame.status[1]) { FAILURE
                 this._detailStatus.appendChild(this._showFailureMessage());
             }
             this._accountProviderIcon.src = _ENME.shortPicture(this.socialAccount.type_account);
         },

         /*
          * build succes message.
          */
         _showSuccessMessage : function() {
             var success = new TweetPollPublishItemSUCCESStatus({
                 metadata : this.data
             });
             return success.domNode;
         },

         /*
          * build failure message.
          */
         _showFailureMessage : function() {
             var fail = new TweetPollPublishItemFAILUREStatus({
                 metadata : this.data,
                 parentStatusWidget : this
             });
             return fail.domNode;
         }

    });
});