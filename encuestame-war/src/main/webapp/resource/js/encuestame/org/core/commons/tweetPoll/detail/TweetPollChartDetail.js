dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");

dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollChartDetail.html"),
        //widget
        widgetsInTemplate: true,

        widgetChart : null,

        tweetPollId : null,

        username : "",

        postCreate : function() {
            this._loadVotes();
        },

        _noVotes : function(){
            console.info('NO VOTES');
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
                                var answer = [data.answersBean.answers, data.results];
                                console.debug("Re answer", answer);
                                results.push(answer);
                    }));
                    var id = this.id+"_chart";
                    dojo.empty(this._chart);
                    this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(id, results, 110);
                    this.render();
                    } else {
                        this._noVotes();
                    }
                  }
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