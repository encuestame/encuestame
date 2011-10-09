dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");

dojo.require("dojo.date.locale");

dojo.require("encuestame.org.core.shared.utils.SurveyStatus");
dojo.require("encuestame.org.core.shared.utils.CountDown");
dojo.require("encuestame.org.core.shared.utils.AccountPicture");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail",
    [dijit._Widget, dijit._Templated],{
        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollInfoDetail.html"),
        /*
         * widget
         */
        widgetsInTemplate: true,
        /*
         * date
         */
        date : "",
        /*
         * owner
         */
        owner : "",

        /*
         * question name.
         */
        question : "",

        /*
         * tweetpoll id
         */
        tweetPollid : null,
        /*
         *
         */
        completed : false,

        /*
         *
         */
        votes : 0,

        /*
         *
         */
        hits : 0,

        /*
         *
         */
        contextPath : encuestame.contextDefault,

        /*
         *
         */
        countdownWidget : null,

        /*
         *
         */
        statusWidget : null,

        /*
         *
         */
        postCreate : function() {
            console.debug("this.date", this.date);
            this.statusWidget = dijit.byId("status_"+this.id);
            if (this._hits) {
                console.info("hits dom", this._hits);
                this._hits.innerHTML = encuestame.utilities.shortAmmount(this.hits);
            }
            if (this._question_title) {
                this._question_title.innerHTML = this.question;
            }
            if(!this.completed) {
                if (this.date != null) {
                    this.date = new Date(this.date);
                    console.debug("this.date 2 ", this.date);
                    this.countdownWidget = new encuestame.org.core.shared.utils.CountDown({
                        limitDate : {
                            day : this.date.getDate(),
                            month : this.date.getMonth(),
                            year : this.date.getFullYear(),
                            hour : this.date.getHours(),
                            minutes : this.date.getMinutes(),
                            seconds : this.date.getSeconds()
                        }
                    });
                    //countdown widget initialize.
                    var countdown = dojo.byId("countdown_"+this.id);
                    if (countdown){
                        countdown.appendChild(this.countdownWidget.domNode);
                        this.countdownWidget.countdown();
                        this.countdownWidget.timeOffAction = dojo.hitch(this, function() {
                            this.completed = true;
                            this._completeTweetPoll();
                        });
                    }
                }
            } else {
                this._completeTweetPoll();
            }
        },

        /*
         *
         */
        _completeTweetPoll : function() {
            if(this.countdownWidget){
                this.countdownWidget.hide();
            }
            this.statusWidget.setClose();
        },

        _reloadHits : function() {
            //TODO: reload hits.
        }
});