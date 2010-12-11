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
                     }
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
                dojo.destroy(dijit.byId("publishTweetPoll").titleBar);
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

        _publishTweet : function(event){
            dojo.stopEvent(event);
            var valid = true;
            //Question
            var question = this.questionWidget.get("value");
            if(!question || question == ""){
                valid = false;
            }

            //getTwitterAccounts
            var listAccounts = [];
            var accountsWidget = dijit.byId("accounts").arrayAccounts;
            if (accountsWidget.length < 0) { //answer should be 2
                valid = false;
            } else {
                dojo.forEach(
                        accountsWidget,
                        dojo.hitch(this, function(data, index) {
                            listAccounts.push(data.account.accountId);
                }));
            }

            //getAnswers
            var answers = [];
            if (this.answerWidget.listItems.length < 2) { //answer should be 2
                valid = false;
            } else {
                dojo.forEach(
                        this.answerWidget.listItems,
                        dojo.hitch(this, function(data, index) {
                            answers.push(data.answer.label);
                }));
            }
            //getHashTags
            var tags = [];
            dojo.forEach(
                    this.hashTagWidget.listItems,
                    dojo.hitch(this, function(data, index) {
                        tags.push(data.data.label.trim());
            }));

            //getScheduled
            var params = {
                     "question" : question.trim(),
                    "twitterAccounts" : listAccounts,
                    "scheduled" : false,
                    "hashtags" : tags,
                    "answers" : answers
            };

            var load = dojo.hitch(this, function(data){
                console.debug(data);
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.list.publishTweetPoll, params, load, error);
        },

        _cancelPublish : function(event){
            dijit.byId("publishTweetPoll").hide();
            this.resetPublish();
        },

        resetPublish : function(){

        },

        _displayPublishContent : function(){

        },

        _activateScheduled : function(b){
           if (b) {
               dojo.style(dojo.byId("showDate"), "display", "block");
           } else {
               dojo.style(dojo.byId("showDate"), "display", "none");
           }
        },

        _activateSpinner : function(b){
            if (b) {
                dojo.style(dojo.byId("showSpinner"), "display", "block");
            } else {
                dojo.style(dojo.byId("showSpinner"), "display", "none");
            }
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

        widgetsInTemplate: true,

        _listSocialAccounts : null,

        arrayAccounts : [],

        _loadTwitterAccounts : function(){
           var load = dojo.hitch(this, function(data){
                this._listSocialAccounts = data.success.items;
                dojo.empty(this._tweetPublishAccounts);
                dojo.publish("/encuestame/tweetpoll/twitter/accounts");
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.list.twitterAccount, {}, load, error);
       },

       postCreate : function(){
           dojo.subscribe("/encuestame/tweetpoll/twitter/accounts", this, "showAccounts");
       },

       showAccounts : function(){
           dojo.forEach(
                   this._listSocialAccounts,
                   dojo.hitch(this, function(data, index) {
                     if (data.typeAccount == "TWITTER") {
                         this.createTwitterAccount(data);
                     } else if(data.typeAccount == "IDENTICA"){

                     }
               }));
       },

       createTwitterAccount : function(data){
           var widget = new encuestame.org.core.commons.tweetPoll.TwitterAccount({account : data});
           this._tweetPublishAccounts.appendChild(widget.domNode);
           this.arrayAccounts.push(widget);
       },

       createIdentiCaAccount : function(){
          //future.
       }
});

/*
 * Twitter Account.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TwitterAccount",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/twitterAccount.inc"),

        account : {},

        widgetsInTemplate: true,

        selected : false,

        postCreate : function(){
        },

        _select : function(b){
            this.selected = b;
         }
 });

/*
 * Identi.ca account.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.IdentiCaAccount",
        [dijit._Widget, dijit._Templated],{
        //templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPublishTwitterAccount.inc"),
        postCreate : function(){
        }
 });

