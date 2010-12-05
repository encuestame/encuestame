dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPoll");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit._Templated");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dojox.widget.Dialog");
dojo.require("dijit.form.NumberSpinner");

dojo.require("dijit.Dialog");

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
            newPreview = this.questionWidget.get("value").trim();
            //hash answers
            dojo.forEach(
                this.answerWidget.listItems,
                dojo.hitch(this, function(data, index) {
                    newPreview += " ";
                    newPreview += data.buildDemoUrl().trim();
                    //newPreview += " ";
            }));

            //hash tags
            dojo.forEach(
                this.hashTagWidget.listItems,
                dojo.hitch(this, function(data, index) {
                    newPreview += this.answerWidget.listItems.length == 0 ? " #" : "#";
                    newPreview += data.data.label.trim();
            }));
            lastTweetText = this.maxTweetText - newPreview.trim().length;
            this._tweetCount.innerHTML = lastTweetText;
            this.previeWidget.set("value", newPreview);
            if(parseInt(lastTweetText) < 0){
                this.showExcededTweet("red");
            } else if(parseInt(lastTweetText) <  30 && parseInt(lastTweetText) > -1){
                this.showExcededTweet("#FAF05C");
            } else {
                this.showExcededTweet("#9E9DA4")
            }
        },

        showExcededTweet : function(endColor){
             dojo.animateProperty({
                 node: dojo.byId("tweetPollPreview"),
                 duration: 500,
                 properties: {
                     backgroundColor: {
                         end: endColor
                     },
                     color: {
                         start: "black",
                         end: "white"
                     },
                 },
                 onEnd: function() {
                     //dojo.byId("tweetPollPreview").innerHTML = "Granted";
                 }
             }).play();
        },

        _isValid : function(){
            return true;
        },

        //publishTweetPoll
        _publish : function(event){
            dojo.stopEvent(event);
            if(this._isValid()){
                dijit.byId("publishTweetPoll").show();
                this.loadPublicationDialogContent();
            } else {
                console.info("publish not valid");
            }
        },

        loadPublicationDialogContent : function(){
            //loadTwitterAccounts.
            dijit.byId("accounts")._loadTwitterAccounts();
            this._displayPublishContent();
        },

        _displayPublishContent : function(){

        },

        errorLoading : function(){

        }
    }
);

dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPreview",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpollPreview.inc")


});

dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPublishTwitterAccount",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPublishTwitterAccount.inc"),

        _listSocialAccounts : null,

        _loadTwitterAccounts : function(){
           var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                this._listSocialAccounts = data.success.items;
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.list.twitterAccount, {}, load, error);
       }
});

