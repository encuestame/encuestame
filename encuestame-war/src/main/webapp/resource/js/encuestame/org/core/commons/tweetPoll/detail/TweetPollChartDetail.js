dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.chart.AbstractChartVoteSupport");

dojo.require('dojox.timing');

dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail",
    [encuestame.org.main.EnmeMainLayoutWidget,
     encuestame.org.core.commons.chart.AbstractChartVoteSupport],{
    	
    	/**
    	 * template path.
    	 */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollChartDetail.html"),
        
        /**
         * TweetPoll Id.
         */
        tweetPollId : null,

        /**
         * Define if is completed.
         */
        completed : false,
        
        /**
         * Owner of the tweetpoll.
         */
        username : "",                

        /**
         * Post create.
         */
        postCreate : function() {
            this._loadVotes();
            this.enableVoteTime( this._live);
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
                            	var votes = data.votes == null ? 0 : data.votes;
                                var answer = [data.question_label, (votes), data.color];
                                results.push(answer);
                                totalVotes += votes;
                                dojo.publish("/encuestame/tweetpoll/detail/answer/reload", [data.id, [votes, data.percent]]);
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
                dojo.publish("/encuestame/tweetpoll/detail/answer/reload");
              });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet( encuestame.service.list.getTweetPollVotes(this.username, this.tweetPollId), {}, response, error);
        }
});