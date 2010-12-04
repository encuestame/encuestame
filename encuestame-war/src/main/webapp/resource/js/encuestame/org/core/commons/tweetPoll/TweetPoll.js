dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPoll");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.require("encuestame.org.core.commons.tweetPoll.Answers");
dojo.require("encuestame.org.core.commons.tweetPoll.HashTags");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.TweetPoll",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpoll.inc"),

        widgetsInTemplate: true,

        hashTagWidget: null,

        answerWidget : null,

        questionWidget : null,

        previeWidget : null,

        maxTweetText : 140,

        postCreate: function() {
            this.questionWidget = dijit.byId("question");
            this.answerWidget = dijit.byId("answers");
            this.hashTagWidget = dijit.byId("hashtags");
            this.previeWidget = dijit.byId("preview");
            if(this.questionWidget
                    || this.answerWidget
                    || this.hashTagWidget
                    || this.previeWidget){
                this.initialize();
            } else {
                this.errorLoading();
            }
        },

        initialize : function(){
             dojo.subscribe("/encuestame/tweetpoll/updatePreview", this, "updatePreview");
             dojo.connect(this.questionWidget, "onKeyDown", dojo.hitch(this, function(e) {
                  this.updatePreview();
             }));
        },

        updatePreview : function(){
            //console.debug("updatePreview");
            var previousPreview = this.previeWidget.get('value');
            var lastTweetText = previousPreview.length;
            var newPreview = "";
            //question
            newPreview = this.questionWidget.get("value");

            //hash answers
            dojo.forEach(
                this.answerWidget.listItems,
                dojo.hitch(this, function(data, index) {
                    newPreview += " ";
                    newPreview += data.buildDemoUrl();
                    newPreview += " ";
            }));

            //hash tags
            dojo.forEach(
                this.hashTagWidget.listItems,
                dojo.hitch(this, function(data, index) {
                    newPreview += " #";
                    newPreview += data.label;
            }));
            lastTweetText = this.maxTweetText - newPreview.length;
            this._tweetCount.innerHTML = lastTweetText;
            this.previeWidget.set("value", newPreview);
        },

        errorLoading : function(){

        }
    }
);
