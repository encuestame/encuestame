define( [
         "dojo/_base/declare",
         "dojo/dom-construct",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollPublishItemSUCCESStatus",
         "me/web/widget/tweetpoll/TweetPollPublishItemSCHEDULEDtatus",
         "me/web/widget/tweetpoll/TweetPollPublishItemFAILUREStatus",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollPublishItemStatus.html" ],
        function(
                declare,
                domConstruct,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TweetPollPublishItemSUCCESStatus,
                TweetPollPublishItemSCHEDULEDtatus,
                TweetPollPublishItemFAILUREStatus,
                _ENME,
                 template ) {
        return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

       /*
        *  Template string.
        */
        templateString: template,

        /**
         * Store all TweetPoll widget data
         * @param tweetPollWidget
         */
        tweetPollWidget: null,

          /**
          *
          */
         data: {},
         /**
          * Social account data.
          */
         socialAccount: {},

         /**
          * Post create lyfe cycle.
          */
         postCreate: function() {
             this.initialize();
         },

         /**
          * Initialize widget.
          */
         initialize: function() {
             var _node;
             if ( this.data.status_tweet === _ENME.STATUS[ 0 ] ) {  //SUCCESS
                 _node = this._showSuccessMessage( this.data );
                 if ( _node ) {
                    domConstruct.place( _node, this._detailStatus );

                    //This._detailStatus.appendChild();
                 }
             } else if ( this.data.status_tweet === _ENME.STATUS[ 1 ] ) { //FAILURE
                 _node = this._showFailureMessage( this.data );
                 if ( _node ) {
                   domConstruct.place( _node, this._detailStatus );
                 }
             } else if ( this.data.status_tweet === _ENME.STATUS[ 3 ] ) { //SCHEDULED
                 _node = this._showScheduldedMessage( this.data );
                 if ( _node ) {
                   domConstruct.place( _node, this._detailStatus );
                 }
             }
             this._accountProviderIcon.src = _ENME.shortPicture( this.socialAccount.type_account );
         },

         /**
          * Convert a failure status to success status
          * @method
          */
         convertToSuccess: function( data, status ) {
            if ( status === "scheduled" ) {
               return this._showScheduldedMessage( data );
            } else {
               return this._showSuccessMessage( data );
            }
         },

         /*
          * Build succes message.
          */
         _showScheduldedMessage: function( data ) {
            data.scheduled_date = moment( data.scheduled_date ).format("MM-DD-YYYY hh:mm:ss");
             var success = new TweetPollPublishItemSCHEDULEDtatus({
                 metadata: data
             });
             return success.domNode;
         },

         /*
          * Build succes message.
          */
         _showSuccessMessage: function( data ) {
             var success = new TweetPollPublishItemSUCCESStatus({
                 metadata: data
             });
             return success.domNode;
         },

         /*
          * Build failure message.
          */
         _showFailureMessage: function( data ) {
             var fail = new TweetPollPublishItemFAILUREStatus({
                 metadata: data,
                 parentStatusWidget: this,
                 tweetPollWidget: this.tweetPollWidget // Important, transfer the main widget reference
             });
             return fail.domNode;
         }

    });
});
