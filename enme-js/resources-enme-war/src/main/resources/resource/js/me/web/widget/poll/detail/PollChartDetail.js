define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/chart/AbstractChartVoteSupport",
     "me/core/enme",
     "dojo/text!me/web/widget/poll/detail/templates/detail_poll.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    abstractChartVoteSupport,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, abstractChartVoteSupport, _WidgetsInTemplateMixin], {

     // template string.
     templateString : template,
     /**
     *
     */
    pollId : null,

    /**
     * Owner of the tweetpoll.
     */
    username : "",

    /**
     * Post create.
     */
    postCreate : function(){
      this._loadVotes();
      this.enableVoteTime(this._live);
    },

    /**
     * Override _loadVotes.
     * Load current votes for a poll.
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
                          var votes = data.answer_votes == null ? 0 : data.answer_votes;
                            var answer = [this.percents ? data.percent : data.answer.answers, (votes), data.color];
                            results.push(answer);
                            totalVotes += votes;
                            //dojo.publish("/encuestame/poll/detail/answer/reload", [data.id, [votes, data.percent]]);
                }));
                //clean chart node.
                dojo.empty(this._chart);
                //check if votes are 0
                if (totalVotes > 0) {
                    var id = this.id+"_chart";
                    //create new chart
                    this.createChart(id, results, null);
                    //render the chart
                    this.render();
                    } else {
                        this._noVotes();
                    }
                } else {
                  console.info("NO VOTES");
                }
              }
            //dojo.publish("/encuestame/poll/detail/answer/reload");
          });
        var error = function(error) {
            console.debug("error", error);
        };
        encuestame.service.xhrGet(
            this.getURLService().service(
            "encuestame.service.list.poll.getVotes",
            [this.username]), { id : this.pollId}, response, error);
    }

  });
});