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
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpoll.html"),

        widgetsInTemplate: true,

        hashTagWidget: null,

        answerWidget : null,

        questionWidget : null,

        previeWidget : null,

        maxTweetText : 140,

        timerAutoSave: null,

        _questionTextLastSucessMessage : "",

        delay: 300000, //every 5 minutes.

        /* stored save tweetPoll. */
        tweetPoll : {
            tweetPollId : null,
            started : false,
            question: {},
            anwers : [],
            hashtags : []
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
              this.loadAutoSaveTimer();
              dojo.addOnLoad(dojo.hitch(this, function(){
                    subscriptionAutoSave  = encuestame.activity.cometd.subscribe('/service/tweetpoll/autosave',
                    dojo.hitch(this, function(message) {
                        console.info("notification response", message);
                        this._autoSaveStatus(message);
                    }));
              }));
              dojo.addOnUnload(function() {
                  if(subscriptionAutoSave != null){
                      console.info("un subsrcibe subscriptionAutoSave service");
                      encuestame.activity.cometd.unsubscribe(subscriptionAutoSave);
                  }
              });
            }
            dojo.subscribe("/encuestame/tweetpoll/block", this, this._block);
            dojo.subscribe("/encuestame/tweetpoll/unblock", this, this._unblock);
        },

        /*
         * block widgets.
         */
        _block : function(){
            this.answerWidget.block();
            this.questionWidget.maxLength = 0;
            console.info("block this.questionWidget.maxLength ", this.questionWidget.maxLength);
            this.questionWidget.block = true;
        },

        /*
         * unblock items.
         */
        _unblock : function(){
            console.info("unblock");
            this.answerWidget.unblock();
            this.questionWidget.maxLength = 140;
            this.questionWidget.block = false;
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
         * auto save tweetPoll.
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
            encuestame.activity.cometd.publish('/service/tweetpoll/autosave', { tweetPoll: this.tweetPoll});
         },

         /*
          * get activity services response after save tweetpoll.
          */
         _autoSaveStatus : function(status){
             console.debug("auto save status", status);
             if (this.tweetPoll.tweetPollId == null) {
               this.tweetPoll.tweetPollId = status.data.tweetPollId;
               var tweetPoll = {id:this.tweetPoll.tweetPollId};
               this.hashTagWidget.tweetPollId = this.tweetPoll.tweetPollId;
               this.answerWidget.tweetPollId = this.tweetPoll.tweetPollId;
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
             this.questionWidget.block = false;
             dojo.connect(this.questionWidget, "onKeyPress", dojo.hitch(this, function(event) {
                 if (dojo.keys.DELETE == event.keyCode || dojo.keys.BACKSPACE == event.keyCode) {
                        console.debug("is removing");
                        this.previeWidget.updatePreview(this.questionWidget, this.answerWidget, this.hashTagWidget);
                        if(!this.questionWidget.block){
                            this._questionTextLastSucessMessage = this.questionWidget.get("value");
                        }
                 } else {
                     this.previeWidget.updatePreview(this.questionWidget, this.answerWidget, this.hashTagWidget);
                     if(!this.questionWidget.block){
                         this._questionTextLastSucessMessage = this.questionWidget.get("value");
                     } else {
                         this.questionWidget.set('value', this._questionTextLastSucessMessage);
                         console.info("question is bloked",  this.questionWidget.maxLength);
                     }
                 }
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
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpollPreview.html"),

            _questionBox : {node:null,initialize:false},
            _answersBox : {node:null,initialize:false},
            _hashTagsBox : {node:null,initialize:false},
            _completeText : "",

            /*
             * max length text.
             */
            _counterMax : 140,

            _lastedCounter : 0,

            /*
             * post create.
             */
            postCreate : function(){
                this.initialize();
                this.showEmtpyContent();
            },

            /*
             * show empty content.
             */
            showEmtpyContent : function(){
              dojo.empty(this._content);
              var emtpy = dojo.doc.createElement("div");
              emtpy.innerHTML = "The tweetPoll is emtpy";
              this._content.appendChild(emtpy);
            },

            initialize : function(){

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
              if (answers != null) {;
                var arrayItem = [];
                var questionDiv = dojo.doc.createElement("span");
                var wishlist = new dojo.dnd.Source(questionDiv);
                dojo.forEach(answers.getAnswers(),
                        dojo.hitch(this,function(item) {
                          var span1 = dojo.doc.createElement("span");
                          span1.innerHTML = item.value;
                          this._completeText = this._completeText + " "+item.value;
                          dojo.addClass(span1, "previewAnswer");
                          arrayItem.push(span1);
                        }));
                wishlist.insertNodes(false, arrayItem);
                //console.info("questionDiv", questionDiv);
                dojo.addClass(questionDiv, "inlineBlock")
                this._content.appendChild(questionDiv);
              } else {
                console.info("no answers widget");
              }
            },

            /*
             * build hashtag items on preview.
             */
            _buildHahsTags : function(hashtags) {
                if (hashtags != null) {
                        var arrayItem = [];
                        var questionDiv = dojo.doc.createElement("span");
                        var wishlist = new dojo.dnd.Source(questionDiv);
                        dojo.forEach(hashtags.listItems, dojo.hitch(this, function(item,
                                index) {
                            var span1 = dojo.doc.createElement("span");
                            span1.innerHTML = "#"+item.data.label;
                            this._completeText = this._completeText + " " + "#"+item.data.label.trim();
                            dojo.addClass(span1, "previewHashTag");
                            arrayItem.push(span1);
                        }));
                        wishlist.insertNodes(false, arrayItem);
                        // console.info("questionDiv", questionDiv);
                        dojo.addClass(questionDiv, "inlineBlock")
                        this._content.appendChild(questionDiv);
                      } else {
                          console.info("no hashtags widget");
                      }
            },

            _getCurrentLengthText : function(){
                return this._lastedCounter;
            },

             /*
              * update counter.
              */
            _updateCounter : function(textTweet){
              if(this._counter){
                var counter = this._getCurrentLengthText();
                  console.debug("counter", counter);
                  if (counter >= 0) {
                    var currentCounter = this._counterMax - textTweet.length;
                    this._counter.innerHTML = currentCounter;
                    this._lastedCounter = currentCounter;
                    dojo.publish("/encuestame/tweetpoll/unblock");
                  } else {
                    dojo.publish("/encuestame/tweetpoll/block");
                    //this._lastedCounter = 0;
                    var currentCounter = this._counterMax - textTweet.length;
                    this._counter.innerHTML = currentCounter;
                    this._lastedCounter = currentCounter;
                  }
              }
            },

           /*
            * return complete tweet.
            */
           getCompleteTweet : function(){
               return this._completeText;
           },

            /*
             * Update preview.
             */
            updatePreview : function(question, answers, hashtags){
                 this._buildQuestion(question);
                 this._buildAnswers(answers);
                 this._buildHahsTags(hashtags);
                 this._updateCounter(this.getCompleteTweet());
            }
});

dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPublishTwitterAccount",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPublishTwitterAccount.html"),

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

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/twitterAccount.html"),

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
        //templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPublishTwitterAccount.html"),
        postCreate : function(){
        }
 });

