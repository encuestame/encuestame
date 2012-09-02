dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer");

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
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer",
    [encuestame.org.main.EnmeMainLayoutWidget],{
        //template
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollAnswer.html"),
        //widget
        widgetsInTemplate: true,

        //answer id.
        aId : null,

        //label
        label : "",

        completed : false,

        //url
        url : "",

        owner: "",

        color : "#000",

        /**
         * post create.
         */
        postCreate : function() {
            dojo.subscribe("/encuestame/tweetpoll/detail/answer/reload", this, this._reloadAnswerInfo);
//            dojo.connect(this._url, "onclick", dojo.hitch(this, function(event){
//                dojo.stopEvent(event);
//                location.href = this.url;
//            }));
        },

        /**
         * reload answer info.
         */
        _reloadAnswerInfo : function(id, data /*[votes, percent]*/) {
            if (this.aId == id) {
                this._reloadValues(data[0], data[1]);
            }
        },

        /*
         * reload percent and votes values.
         */
        _reloadValues : function(votes, percent){
            if (this._votes) {
                this._votes.innerHTML = votes;
            }
            if (this._percent) {
                this._percent.innerHTML = percent;
            }
        }


});