dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");

dojo.require('dojox.timing');

dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer",
    [dijit._Widget, dijit._Templated],{
        //template
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollAnswer.html"),
        //widget
        widgetsInTemplate: true,

        //answer id.
        aId : null,

        //label
        label : "",

        //url
        url : "",

        owner: "",

        /*
         * post create.
         */
        postCreate : function() {
            dojo.subscribe("/encuestame/tweetpoll/detail/answer/reload", this, this._reloadAnswerInfo);
        },

        /*
         * reload answer info.
         */
        _reloadAnswerInfo : function(){
            var response = dojo.hitch(this, function(dataJson) {
                //TODO: reload info answer.
              });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.list.tweetpoll.answer.getVotes(this.owner, this.aId), response, error);
        }


});