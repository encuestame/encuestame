dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");

dojo.require('dojox.timing');

dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollChartDetail.html"),
        //widget
        widgetsInTemplate: true,

        widgetChart : null,

        tweetPollId : null,

        completed : false,

        username : "",

        enableLiveVotes : true,

        delay : 31000,

        _timer : null,

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

        _noVotes : function(){
            console.info('NO VOTES');
        },

        /*
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
        _loadVotes : function(){
            var response = dojo.hitch(this, function(dataJson) {
                if (dataJson.success.votesResult) {
                    var votes = dataJson.success.votesResult;
                    if(votes.length > 0) {
                    var results = [];
                    dojo.forEach(
                            votes,
                            dojo.hitch(this, function(data, index) {
                                console.info("ANSWER BEAN", data);
                                var answer = [data.question_label, (data.votes == null ? 0 : data.votes), data.color];
                                results.push(answer);
                                dojo.publish("/encuestame/tweetpoll/detail/answer/reload", [data.id, [data.votes, data.percent]]);
                    }));
                    var id = this.id+"_chart";
                    dojo.empty(this._chart);
                    this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(id, results, 110);
                    this.render();
                    } else {
                        this._noVotes();
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