dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

dojo.require('dojox.timing');

dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail",
    [encuestame.org.main.EnmeMainLayoutWidget],{
    	
    	/**
    	 * template path.
    	 */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollChartDetail.html"),

        /**
         * Chart widget.
         */
        widgetChart : null,
        
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
         * Enable live votes.
         */
        enableLiveVotes : true,
        
        /**
         * Default delay.
         */
        delay : 31000,
        
        /**
         * Timer object.
         */
        _timer : null,

        /**
         * Post create.
         */
        postCreate : function() {
            this._loadVotes();
            dojo.addOnLoad(dojo.hitch(this, function() {
                if (this.enableLiveVotes && !this.completed) {
                    this.setTimer();
                    this._live.innerHTML = "ON LIVE: Results refreshed every "+(this.delay/1000)+" seconds";
                    dojo.removeClass(this._live, "defaultDisplayHide");
                } else{
                    dojo.addClass(this._live, "defaultDisplayHide");
                }
            }));
        },
        
        /**
         * 
         */
        _noVotes : function(){
            console.info('NO VOTES');
        },

        /**
         * set timer to reload votes.
         */
        setTimer : function(){
            var father = this;
            this._timer = new dojox.timing.Timer(this.delay);
            this._timer.onTick = function() {
                if (!father.completed) {
                father._loadVotes();
                } else {
                    this._timer.stop();
                }
            };
            this._timer.onStart = function() {
            };
            this._timer.start();
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
	                    this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(
	                    		id, 
	                    		/** array of results **/ results, 
	                    		/** **/ 110);
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
        },

        /**
         * Render.
         */
        render : function(){
            this.widgetChart._buildSeries();
            this.widgetChart.render();
        }

});