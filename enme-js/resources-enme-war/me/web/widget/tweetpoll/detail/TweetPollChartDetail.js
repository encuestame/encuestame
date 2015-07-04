define([
     "dojo/_base/declare",
     "dojo/on",
     "dojo/dom-class",
     "dijit/_WidgetBase",
     "dijit/Dialog",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/options/EmbebedOptions",
     "me/web/widget/chart/AbstractChartVoteSupport",
     "me/core/enme",
     "dojo/text!me/web/widget/tweetpoll/detail/templates/tweetPollChartDetail.html" ],
    function(
    declare,
    on,
    domClass,
    _WidgetBase,
    Dialog,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    EmbebedOptions,
    abstractChartVoteSupport,
    _ENME,
     template) {

  return declare([_WidgetBase,
                   _TemplatedMixin,
                   main_widget,
                   abstractChartVoteSupport,
                   _WidgetsInTemplateMixin], {

      // template string.
      templateString : template,

      /**
      * TweetPoll Id.
      */
      tweetPollId : null,


      /**
      * Define if is completed.
      */
      completed : false,

      /**
       * Render the chart on load.
       * @property
       */
      renderOnLoad : true,

      /**
      * Owner of the tweetpoll.
      */
      username : "",

      /**
      * Post create.
      */
      postCreate : function() {
          if (this.renderOnLoad) {
             this._loadVotes();
          }
         this.enableVoteTime( this._live);
        //console.log("_ENME.get('isMobile')", _ENME.get('isMobile'));
          if (!this.isMobile) {
              on(this._embebed, "click", dojo.hitch(this, function (e) {
                  this.stopEvent(e);
                  this._embebed_options.initialize();
                  this._embebed_options_dialog.show();
              }));
              this._embebed_options.dialogWidget = this._embebed_options_dialog;
          } else {
              domClass.add(this._embebed, 'hidden');
          }
      },

      _closeEmbebedDialog: function(e) {
          this._embebed_options_dialog.hide();
      },

      /**
      * Load votes for tweetpoll.
      * format
      * {
         error: { }
         success: {
             votesResult: [{
                 results: 1
                 -
                 answersBean: {
                     url: null
                     answers: "answer"
                     shortUrlType: null
                     answerHash: null
                     shortUrl: "tinyurl"
                     answerId: 890
                     questionId: 888
                 }
             }]
         }
         }
      */
      _loadVotes : function() {
         var response = dojo.hitch(this, function(dataJson) {
             if ("success" in dataJson) {
                 var votes = dataJson.success.votesResult,
                 totalVotes = 0;
                 if (votes.length > 0) {
                 var results = [];
                 dojo.forEach(
                         votes,
                         dojo.hitch(this, function(data, index) {
                           var votes = data.votes === null ? 0 : data.votes;
                             var answer = [data.question_label, votes, data.color];
                             results.push(answer);
                             totalVotes += votes;
                             dojo.publish("/encuestame/tweetpoll/detail/answer/reload", [data.id, [votes, data.percent]]);
                 }));
                 //clean chart node.
                 dojo.empty(this._chart);
                 //check if votes are 0
                 if (totalVotes > 0) {
                     this.createChart(this._chart, results, this._defaultChart);
                     } else {
                         this._noVotes();
                     }
                 }
               }
               dojo.publish("/encuestame/tweetpoll/detail/answer/reload");
           });
         var error = function(error) {
             console.error("error", error);
         };
         this.getURLService().get(["encuestame.service.list.getTweetPollVotes", [this.username, this.tweetPollId]], {
             id : this.pollId
         }, response, error , dojo.hitch(this, function() {

         }));
      }

      });
});