dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPoll");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.NumberSpinner");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dijit._Widget");
dojo.require("dojo.dnd.Source");
dojo.require("dojo.hash");
dojo.require("dojox.widget.Dialog");

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

        timerAutoSave: null,

        delay: 300000, //every 5 minutes.

        /* stored save tweetPoll. */
        tweetPoll : {
            tweetPollId : null,
            started : false,
            question: {},
            anwers : [
                      { answer : "answer1",
                        answerId : 1,
                        url : "http://www.google.es"
                      },
                      {
                        answer : "answer2",
                        answerId : 2,
                        url : "http://www.google.es"
                       },
                       {
                           answer : "answer3",
                           answerId : 3,
                           url : "http://www.google.es"
                       },
                       {
                           answer : "answer3",
                           answerId : 4,
                           url : "http://www.google.es"
                       }],
            hashtags : [{value : "hash1"}, {value : "hash2"}, { value: "hash3"} ]
          },

        /*
         * enable or disable autosave.
         */
        autosave : true,

        /*
         * post create.
         */
        postCreate: function() {
            this.questionWidget = dijit.byId("question");
            this.answerWidget = dijit.byId("answers");
            this.hashTagWidget = dijit.byId("hashtags");
            this.previeWidget = dijit.byId("preview");
            if(this.questionWidget
                    || this.answerWidget
                    || this.hashTagWidget
                    ){
                this.initialize();
            } else {
                this.errorLoading();
            }
            var node = dojo.byId("wrapper");
            document.addEventListener(!dojo.isMozilla ? "onmousewheel" : "DOMMouseScroll", this.scroll, false);
            window.onscroll = this.scroll;
            //enable auto save.
            if (this.autosave) {
              dojo.subscribe("/encuestame/tweetpoll/autosave", this, this._autoSave);
              dojo.subscribe("/encuestame/tweetpoll/autosave/status", this, this._autoSaveStatus);
              this.loadAutoSaveTimer();
            }
        },

        /*
         * Load AutoSave timer.
         */
        loadAutoSaveTimer : function(){
            console.info("enabled autosave timer");
            var widget = this;
            this.timerAutoSave = new dojox.timing.Timer(this.delay);
            this.timerAutoSave.onTick = function() {
              widget._autoSave();
              console.info("enabled autosave execute");
            };
            this.timerAutoSave.onStart = function() {};
            this.timerAutoSave.start();
        },

        /*
         * Auto save tweetPoll.
         * @param tweetPollId if is null we crete new tweetPoll.
         * @param question { id, question}
         * @param hastags [id,id,id,id]
         * @param anseerws { id, question}
         */
        _autoSave : function(tweetPollId, /** widget **/ question, /** answers. **/ answers, /**hashtags **/ hashtags){
            console.debug("auto save");
            if(this.tweetPoll.tweetPollId == null){
               console.debug("tweet poll is autosaving ...", this.tweetPoll);
            } else {
               console.info("tweetPol exist", this.tweetPoll.tweetPollId) ;
            }
            cometd.publish('/service/tweetpoll/autosave', { tweetPoll: this.tweetPoll});
         },

         /*
          * Get activity services response after save tweetpoll.
          */
         _autoSaveStatus : function(status){
             console.debug("auto save status", status);

             if(this.tweetPoll.tweetPollId == null){
               this.tweetPoll.tweetPollId = status.data.tweetPollId;
               var tweetPoll = {id:this.tweetPoll.tweetPollId};
               dojo.hash(dojo.objectToQuery(tweetPoll));
             }
             //this.tweetPoll.hashtags = status.hashTags;
             //this.tweetPoll.anwsers = status.answers;
             //
             this.tweetPoll.started = true;
             console.debug("_autoSaveStatus FINISH", this.tweetPoll);
         },

        /*
         * auto-scroll publish top bar.
         */
        scroll : function(){
            var node = dojo.byId("defaultMarginWrapper");
            var nodeFixed = dojo.byId("previewWrapper");
            var coords = dojo.coords(node);
            if(coords.y < 0){
              dojo.addClass(nodeFixed, "previewFixed");
              dojo.removeClass(nodeFixed, "previewAbsolute");
            } else {
              dojo.addClass(nodeFixed, "previewAbsolute");
              dojo.removeClass(nodeFixed, "previewFixed");
            }
          },

        /*
         * initialize new tweetpoll create.
         */
        initialize : function(){
           var hash = dojo.queryToObject(dojo.hash());
             if(hash.id){
              //retrieve tweetPoll autosaved information.
              console.debug("url id ", hash.id);
              //this.tweetPoll.tweetPollId = hash.id;
             }
             dojo.subscribe("/encuestame/tweetpoll/updatePreview", this, "updatePreview");
             dojo.connect(this.questionWidget, "onKeyUp", dojo.hitch(this, function(e) {
                  this.previeWidget.updatePreview(this.questionWidget, this.answerWidget, this.hashTagWidget);
             }));
             this.questionWidget.onChange = dojo.hitch(this, function(){
                 this.tweetPoll.question.value = this.questionWidget.get("value");
                 dojo.publish("/encuestame/tweetpoll/autosave");
             });
        },

        /*
         * update widget preview.
         */
        updatePreview : function(){
            this.previeWidget.updatePreview(this.questionWidget, this.answerWidget, this.hashTagWidget);
        },

        /*
         * show exceted tweet.
         */
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

        /*
         * is valid.
         */
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

        /*
         *
         */
        loadPublicationDialogContent : function(){
            //loadTwitterAccounts.
            dijit.byId("accounts")._loadTwitterAccounts();
            this._displayPublishContent();
        },

        /*
         *
         */
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
            console.debug("params", params);
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

/*
 * A preview of tweetpoll.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPreview",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpollPreview.inc"),

            _questionBox : {node:null,initialize:false},
            _answersBox : {node:null,initialize:false},
            _hashTagsBox : {node:null,initialize:false},
            _completeText : "",

            postCreate : function(){
                this.initialize();
                this.showEmtpyContent();
            },

            showEmtpyContent : function(){
              dojo.empty(this._content);
              var emtpy = dojo.doc.createElement("div");
              emtpy.innerHTML = "The tweetPoll is emtpy";
              this._content.appendChild(emtpy);
            },

            initialize : function(){
//              var wishlist = new dojo.dnd.Source(this._content);
//              var arrayItem = [];
//              var span1 = dojo.doc.createElement("span");
//              span1.innerHTML = "Wrist watch1";
//              var span2 = dojo.doc.createElement("span");
//              span2.innerHTML = "Wrist watch2";
//              var span3 = dojo.doc.createElement("span");
//              span3.innerHTML = "Wrist watch3";
//              var span4 = dojo.doc.createElement("span");
//              span4.innerHTML = "Wrist watch4";
//              var span5 = dojo.doc.createElement("span");
//              span5.innerHTML = "Wrist watch5";
//              arrayItem.push(span1);
//              arrayItem.push(span2);
//              arrayItem.push(span3);
//              arrayItem.push(span4);
//              arrayItem.push(span5);
//              wishlist.insertNodes(false, arrayItem);
            },

            /*
             *
             */
            _buildQuestion : function(question){
              dojo.empty(this._content);
              //console.debug("_buildQuestion", this._questionBox);
                if (this._questionBox.node == null) {
                  this._questionBox.node = dojo.doc.createElement("span");
                  dojo.addClass(this._questionBox.node, "previewQuestion");
                  this._questionBox.innerHTML = "";
                  this._questionBox.initialize = true;
                }
                if (question) {
                  var newPreview = question.get("value");
                  //console.debug("_buildQuestion newPreview", newPreview);
                  //question
                  this._questionBox.node.innerHTML = newPreview;
                  //console.debug("_buildQuestion newPreview", this._questionBox.node);
                  this._completeText = newPreview;
                } else {
                   //question widget is null.
                  console.info("question is null");
                }
                this._content.appendChild(this._questionBox.node);
            },

            /*
             *
             */
            _buildAnswers : function(answers){
              if (answers != null) {
                //console.info("answers", answers);
                var arrayItem = [];
                var questionDiv = dojo.doc.createElement("span");
                var wishlist = new dojo.dnd.Source(questionDiv);
                dojo.forEach(answers.getAnswers(),
                        dojo.hitch(this,function(item) {
                        //console.info("item", item);
                          var span1 = dojo.doc.createElement("span");
                          span1.innerHTML = item.label;
                          this._completeText = this._completeText + " "+item.label;
                          dojo.addClass(span1, "previewAnswer");
                          arrayItem.push(span1);
                        }));
                wishlist.insertNodes(false, arrayItem);
                //console.info("questionDiv", questionDiv);
                this._content.appendChild(questionDiv);
              } else {
                console.info("no answers widget");
              }
            },

            _buildHahsTags : function(hashtags){

            },

            _updateCounter : function(textTweet){
              if(this._counter){
                var counter = parseInt(this._counter.innerHTML);
                  if (counter <= 140) {
                    var currentCounter = 140 - textTweet.length;
                    this._counter.innerHTML = currentCounter;
                  } else {
                    //block counter.
                  }
              }
            },

           getCompleteTweet : function(){
               return this._completeText;
           },

            /*
             * Update preview.
             */
            updatePreview : function(question, answers, hashtags){
                 this._buildQuestion(question);
                 this._buildAnswers(answers);
                 this._updateCounter(this.getCompleteTweet());
                //console.debug("updatePreview");
//              var previousPreview = this.previeWidget.getCompleteTweet();
//                var lastTweetText = previousPreview.length;
//                var newPreview = "";
//                //question
//                newPreview = this.questionWidget.get("value").trim();
//                //hash answers
//                dojo.forEach(
//                    this.answerWidget.listItems,
//                    dojo.hitch(this, function(data, index) {
//                        newPreview += " ";
//                        newPreview += data.buildDemoUrl().trim();
//                        //newPreview += " ";
//                }));
//
//                //hash tags
//                dojo.forEach(
//                    this.hashTagWidget.listItems,
//                    dojo.hitch(this, function(data, index) {
//                        newPreview += this.answerWidget.listItems.length == 0 ? " #" : "#";
//                        newPreview += data.data.label.trim();
//                }));
//                lastTweetText = this.maxTweetText - newPreview.trim().length;
//                this._tweetCount.innerHTML = lastTweetText;
//                //this.previeWidget.set("value", newPreview);
//                if(parseInt(lastTweetText) < 0){
//                    this.showExcededTweet("red");
//                } else if(parseInt(lastTweetText) <  30 && parseInt(lastTweetText) > -1){
//                    this.showExcededTweet("#FAF05C");
//                } else {
//                    this.showExcededTweet("#9E9DA4")
//                }
            }

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
                    encuestame.service.list.socialAccounts, {}, load, error);
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

