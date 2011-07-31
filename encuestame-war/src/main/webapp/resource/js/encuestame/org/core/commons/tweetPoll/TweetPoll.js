dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPoll");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.TimeTextBox");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.NumberSpinner");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dijit._Widget");
dojo.require("dojo.dnd.Source");
dojo.require("dojo.hash");
dojo.require("dojox.widget.Dialog");

dojo.require("encuestame.org.core.commons.tweetPoll.Answers");
dojo.require("encuestame.org.core.commons.tweetPoll.HashTags");
dojo.require("encuestame.org.core.commons.social.SocialAccountPicker");
dojo.require("encuestame.org.core.commons.dialog.Dialog");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.TweetPoll",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpoll.html"),

        widgetsInTemplate: true,

        hashTagWidget: null,

        answerWidget : null,

        questionWidget : null,

        previeWidget : null,

        socialWidget : null,

        dialogWidget : null,

        scheduleWidget : null,

        scheduledDateWidget  : null,

        scheduledTimeWidget  : null,

        captchaWidget : null,
        //Limit Votes
        limitVotesWidget  : null,
        //
        limitNumbersWidget  : null,

        //Allow Repeated Votes.
        ipWidget  : null,
        //
        repeatedNumbersWidget  : null,

        //report widget.
        resumeWidget  : null,

        _isValidMessage : null,

        //dashboard widget.
        dashboardWidget  : null,

        timerAutoSave: null,

        _questionTextLastSucessMessage : "",

        delay: 300000, //every 5 minutes.

        /* stored save tweetPoll. */
        tweetPoll : {
            tweetPollId : null,
            started : false,
            question: {},
            answers : [],
            hashtags : [],
            options : {scheduled : false,
                       scheduledTime : null,
                       scheduledDate : null,
                       liveResults: false,
                       captcha : false,
                       limitVotes: false,
                       maxLimitVotes : 100,
                       repeatedVotes : false,
                       maxRepeatedVotes : 2,
                       resumeLiveResults : false,
                       followDashBoard : false
                       }
          },

        /*
         * enable or disable autosave.
         */
        autosave : true,

        /*
         * tweet poll publish widget reference.
         */
        tweetPollPublishWidget : null,

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
              //publish suport.
              dojo.subscribe("/encuestame/tweetpoll/autosave", this, this._autoSave);
              dojo.subscribe("/encuestame/tweetpoll/block", this, this._block);
              dojo.subscribe("/encuestame/tweetpoll/unblock", this, this._unblock);
              //active auto save.
              this.loadAutoSaveTimer();
              //add comet support on load.
              dojo.addOnLoad(dojo.hitch(this, function(){
                    subscriptionAutoSave  = encuestame.activity.cometd.subscribe('/service/tweetpoll/autosave',
                    dojo.hitch(this, function(message) {
                        console.info("tweetpoll autosave response", message);
                        this._autoSaveStatus(message);
                    }));
              }));
              //remove comet support on unload.
              dojo.addOnUnload(function() {
                  if(subscriptionAutoSave != null){
                      console.info("un subsrcibe subscriptionAutoSave service");
                      encuestame.activity.cometd.unsubscribe(subscriptionAutoSave);
                  }
              });
            }

            //tweet poll options

            //live results.
            this.liveResultsWidget = dijit.byId("liveResults");
            this.liveResultsWidget.onChange = dojo.hitch(this, function(event){
              this.tweetPoll.options.liveResults = event;
              dojo.publish("/encuestame/tweetpoll/autosave");
            });

            //scheduled
            this.scheduleWidget = dijit.byId("schedule");
            this.scheduleWidget.onChange = dojo.hitch(this, function(event){
                console.debug("shecduled", event);
                if (event) {
                    dojo.removeClass(this._scheduledTime, "defaultDisplayHide");
                    dojo.removeClass(this._scheduledDate, "defaultDisplayHide");
                } else {
                    dojo.addClass(this._scheduledTime, "defaultDisplayHide");
                    dojo.addClass(this._scheduledDate, "defaultDisplayHide");
                }
                this.tweetPoll.options.scheduledTime = encuestame.date.getFormatTime(new Date(),
                        encuestame.date.timeFormat);
                this.scheduledDateWidget.set("value", new Date());
                this.scheduledTimeWidget.set("value", new Date());
                this.tweetPoll.options.scheduledTime = encuestame.date.getFormatTime(new Date(),
                        encuestame.date.timeFormat);
                this.tweetPoll.options.scheduledDate = encuestame.date.getFormatTime(new Date(),
                        encuestame.date.dateFormat);
                this.tweetPoll.options.scheduled = event;
                dojo.publish("/encuestame/tweetpoll/autosave");
            });
            //date widget.
            this.scheduledDateWidget = dijit.byId("scheduledDate");
            this.scheduledDateWidget.onChange = dojo.hitch(this, function(event){
                console.debug("Scheduled Date", this.scheduledDateWidget.get("value"));
              this.tweetPoll.options.scheduledDate = encuestame.date.getFormatTime(this.scheduledDateWidget.get("value"),
                      encuestame.date.dateFormat);
              dojo.publish("/encuestame/tweetpoll/autosave");
            });
            //time widget.
            this.scheduledTimeWidget = dijit.byId("scheduledTime");
            this.scheduledTimeWidget.onChange = dojo.hitch(this, function(event){
                console.debug("Scheduled Time", encuestame.date.getFormatTime(this.scheduledTimeWidget.get("value"),
                        encuestame.date.timeFormat));
                this.tweetPoll.options.scheduledTime = encuestame.date.getFormatTime(this.scheduledTimeWidget.get("value"),
                        encuestame.date.timeFormat);
                dojo.publish("/encuestame/tweetpoll/autosave");
            });

            this.captchaWidget = dijit.byId("captcha");
            this.captchaWidget.onChange = dojo.hitch(this, function(event){
                this.tweetPoll.options.captcha = event;
                dojo.publish("/encuestame/tweetpoll/autosave");
            });

            //Limit Votes
            this.limitVotesWidget = dijit.byId("limitVotes");
            this.limitVotesWidget.onChange = dojo.hitch(this, function(event){
                console.debug("limitVotesWidget", event);
                if (event) {
                    dojo.removeClass(this._limitNumbers, "defaultDisplayHide");
                } else {
                    dojo.addClass(this._limitNumbers, "defaultDisplayHide");
                }
                this.tweetPoll.options.limitVotes = event;
                dojo.publish("/encuestame/tweetpoll/autosave");
            });
            this.limitNumbersWidget = dijit.byId("limitNumbers");
            this.limitNumbersWidget.onChange = dojo.hitch(this, function(event){
                console.debug("maxLimitVotes ", this.limitNumbersWidget.get("value"));
              this.tweetPoll.options.maxLimitVotes = this.limitNumbersWidget.get("value");
              dojo.publish("/encuestame/tweetpoll/autosave");
            });

            //Allow Repeated Votes.
            this.ipWidget = dijit.byId("ip");
            this.ipWidget.onChange = dojo.hitch(this, function(event){
                console.debug("ipWidget", event);
                if (event) {
                    dojo.removeClass(this._repeatedNumbers, "defaultDisplayHide");
                } else {
                    dojo.addClass(this._repeatedNumbers, "defaultDisplayHide");
                }
                this.tweetPoll.options.repeatedVotes = event;
                dojo.publish("/encuestame/tweetpoll/autosave");
            });
            this.repeatedNumbersWidget = dijit.byId("repeatedNumbers");
            this.repeatedNumbersWidget.onChange = dojo.hitch(this, function(event){
                console.debug("maxLimitVotes ", this.repeatedNumbersWidget.get("value"));
              this.tweetPoll.options.maxRepeatedVotes = this.repeatedNumbersWidget.get("value");
              dojo.publish("/encuestame/tweetpoll/autosave");
            });

            //report
            this.resumeWidget = dijit.byId("resume");
            this.resumeWidget.onChange = dojo.hitch(this, function(event){
                 console.debug("resumeWidget ", event);
                this.tweetPoll.options.resumeLiveResults = event;
                dojo.publish("/encuestame/tweetpoll/autosave");
            });
            this.dashboardWidget = dijit.byId("dashboard");
            this.dashboardWidget.onChange = dojo.hitch(this, function(event){
                this.tweetPoll.options.followDashBoard = event;
                dojo.publish("/encuestame/tweetpoll/autosave");
            });
            //button publish event.
            dojo.connect(this._publish, "onClick", dojo.hitch(this, function(event) {
                this._checkPublish();
            }));
        },

        /*
         * block widgets.
         */
        _block : function(){
            this.answerWidget.block();
            this.hashTagWidget.block();
            //console.info("block this.questionWidget.maxLength ", this.questionWidget.maxLength);
            this.questionWidget.block = true;
        },

        /*
         * unblock items.
         */
        _unblock : function(){
            //console.info("unblock");
            this.answerWidget.unblock();
            this.questionWidget.block = false;
            this.hashTagWidget.unblock();
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
               console.info("tweetPol exist", this.tweetPoll.tweetPollId);
               this.tweetPoll.hashtags = this.hashTagWidget.getHashTags();
               this.tweetPoll.answers = this.answerWidget.getAnswersId();
            }
            console.debug("auto save", this.tweetPoll);
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
            var node = dojo.byId("tweetPollWrapper");
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
        initialize : function() {
           var hash = dojo.queryToObject(dojo.hash());
             if (hash.id) {
              //retrieve tweetPoll autosaved information.
              console.debug("url id ", hash.id);
              //this.tweetPoll.tweetPollId = hash.id;
             }
             dojo.subscribe("/encuestame/tweetpoll/updatePreview", this, "updatePreview");
             this.questionWidget.block = false;
             dojo.connect(this.questionWidget, "onKeyUp", dojo.hitch(this, function(event) {
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
             this.socialWidget = dijit.byId("social");
             //initialize publish widget.
             this.tweetPollPublishWidget = new encuestame.org.core.commons.tweetPoll.TweetPollPublishInfo(
                     { tweetPollWidget : this});
        },

        /*
         * update widget preview.
         */
        updatePreview : function(){
            this.previeWidget.updatePreview(this.questionWidget, this.answerWidget, this.hashTagWidget);
        },


        /*
         * check if tweetpoll is valid and start the process to publish.
         */
        _checkPublish : function() {
            var valid = this.previeWidget.isValid();
            if(!valid) {
               this._showErrorMessage(this.previeWidget.isValidMessage());
            } else {
                valid = this.socialWidget.isValid();
                if (!valid) {
                    this._showErrorMessage(this.socialWidget.isValidMessage());
                } else {
                    //if is valid.
                    if (valid) {
                        this.dialogWidget = new dijit.Dialog({
                             content: this.tweetPollPublishWidget.domNode,
                             style: "width: 700px",
                             draggable : false
                         });
                        this.tweetPollPublishWidget.setListOfSocialAccounts(this.socialWidget.getSocialCompleteAccounts());
                        this.tweetPollPublishWidget.initialize();
                        //dojo.empty(myDialog.titleBar);
                        dojo.addClass(this.dialogWidget.titleBar,"defaultDisplayHide");
                        this.dialogWidget.show();
                        this._publishTweet();
                    }
                }
            }
        },

        /*
         * show error message.
         */
        _showErrorMessage : function(errorMessage){
            this.dialogWidget = new dijit.Dialog({
                content: errorMessage,
                style: "width: 700px",
                draggable : false
            });
            this.dialogWidget.show();
        },

        /*
         *  publish tweet poll.
         */
        _publishTweet : function(){
            var params = {
                     "id" : this.tweetPoll.tweetPollId,
                     "twitterAccounts" : this.socialWidget.getSocialAccounts()
            };
            console.debug("params", params);
            var load = dojo.hitch(this, function(data){
                //TODO: DISABLE COMET AUTOSAVE;
                this.autosave = false;
                this.tweetPollPublishWidget.process(dojo.fromJson(data));
            });
            var error = function(error) {
                this.autosave = true;
                this._showErrorMessage(error.message);
            };
            encuestame.service.xhrPostParam(
                    encuestame.service.list.publishTweetPoll, params, load, error);
        },

         /*
          *  @deprecated
          */
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
            _isValid : false,
            _isValidMessage : "",
            /*
             * max length text.
             */
            _counterMax : 140,
            _lastedCounter : 0,

            _answerSize : 0,
            _hashTagsSize : 0,

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
              //emtpy.innerHTML = "The tweetPoll is emtpy";
              this._content.appendChild(emtpy);
            },

            /*
             * initialize widget.
             */
            initialize : function() {

            },

            /*
             * build question.
             */
            _buildQuestion : function(question){
              dojo.empty(this._content);
                if (this._questionBox.node == null) {
                  this._questionBox.node = dojo.doc.createElement("span");
                  dojo.addClass(this._questionBox.node, "previewQuestion");
                  this._questionBox.innerHTML = "";
                  this._questionBox.initialize = true;
                }
                if (question) {
                  var newPreview = question.get("value");
                  //question
                  this._questionBox.node.innerHTML = newPreview;
                  //console.debug("_buildQuestion newPreview", this._questionBox.node);
                  this._completeText = newPreview;
                }
                if(question.get("value") != "") {
                    this._content.appendChild(this._questionBox.node);
                }
            },

            /*
             *
             */
            _buildAnswers : function(answers){
              if (answers != null) {;
                var arrayItem = [];
                var questionDiv = dojo.doc.createElement("span");
                var wishlist = new dojo.dnd.Source(questionDiv);
                this._answerSize = answers.getAnswers().length;
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
                dojo.addClass(questionDiv, "inlineBlock");
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
                        this._hashTagsSize = hashtags.length;
                        dojo.forEach(hashtags.listItems, dojo.hitch(this, function(item,
                                index) {
                            //TODO: check if blank spaces are counted.
                            var span1 = dojo.doc.createElement("span");
                            span1.innerHTML = "#"+item.data.label;
                            this._completeText = this._completeText + " " + "#"+item.data.label.trim();
                            dojo.addClass(span1, "previewHashTag");
                            arrayItem.push(span1);
                        }));
                        wishlist.insertNodes(false, arrayItem);
                        // console.info("questionDiv", questionDiv);
                        dojo.addClass(questionDiv, "inlineBlock");
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
            _updateCounter : function(textTweet) {
              if (this._counter) {
                var counter = this._getCurrentLengthText();
                  //console.debug("counter", counter);
                  if (counter >= 0) {
                    var currentCounter = this._counterMax - textTweet.length;
                    this._counter.innerHTML = currentCounter;
                    this._lastedCounter = currentCounter;
                    dojo.publish("/encuestame/tweetpoll/unblock");
                    this._isValid = true;
                  } else {
                    this._isValid = false;
                    this._isValidMessage = encuestame.constants.errorCodes["020"];
                    dojo.publish("/encuestame/tweetpoll/block");
                    //this._lastedCounter = 0;
                    var currentCounter = this._counterMax - textTweet.length;
                    this._counter.innerHTML = currentCounter;
                    this._lastedCounter = currentCounter;
                  }
              } else {
                  console.error(encuestame.constants.errorCodes["023"]);
              }
            },

            /*
             * check required structure.
             */
            _checkTweetPollStructure : function() {
                if(this._answerSize < config.tp.a) {
                    this._isValid = false;
                    this._isValidMessage = encuestame.constants.errorCodes["021"];
                }
            },

            /*
             * check if tweet text is valid.
             */
            isValid : function() {
                this._checkTweetPollStructure();
                return this._isValid;
            },

            isValidMessage : function() {
                return this._isValidMessage;
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

/**
 *
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPublishInfo",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpollPublish.html"),

            _socialAccounts : [],

            tweetPollWidget : null,

            widgetsInTemplate: true,

            _inProcess : false,

            postCreate : function(){
                var button = dijit.byId(this._close);
                    button.onClick = dojo.hitch(this, function(event){
                          this.tweetPollWidget.dialogWidget.hide();
                          console.debug(encuestame.contextDefault+"/user/tweetpoll/list");
                          document.location.href = encuestame.contextDefault+"/user/tweetpoll/list";
                });
            },

            /*
             * set social account array.
             */
            setListOfSocialAccounts : function(accounts){
                this._socialAccounts = accounts;
            },

            /*
             * initialize widget.
             */
            initialize : function(){
                this._inProcess = true;
                this._showProcessingMessage();
            },

            /*
             *
             */
            _showProcessingMessage : function(){
                var message = dojo.doc.createElement("div");
                dojo.addClass(this._message, "defaultDisplayBlock");
                dojo.removeClass(this._message, "defaultDisplayHide");
                message.innerHTML = "Publishing your tweets, please wait ...";
                console.debug("message", message);
                this._message.appendChild(message);
            },

            /*
             *
             */
            _hideProcessingMessage : function(){
                console.debug("_hideProcessingMessage");
                dojo.removeClass(this._message, "defaultDisplayBlock");
                dojo.addClass(this._message, "defaultDisplayHide");
                dojo.empty(this._message);
            },

            /*
             * process date published.
             */
            process: function(data){
                console.debug("tweetPollPublishWidget process", data);
                if(data){
                    this._hideProcessingMessage();
                    dojo.empty(this._container);
                    dojo.empty(this._message);
                    console.debug("show accoutns", this._socialAccounts);
                    dojo.forEach(data.success.socialPublish,
                            dojo.hitch(this,function(tweet) {
                                console.debug("socialPublish", tweet);
                                var row = this._buildTweetProcessView(tweet);
                                if(row){
                                    this._container.appendChild(row);
                                }
                   }));
                } else {
                    console.error("data tweet process is empty");
                }
            },

            /*
             * search by id the complete info for selected social account.
             */
            _getSocialAccountWidget : function(id){
                //this._socialAccounts
                var selected = null;
                dojo.forEach(this._socialAccounts,
                        dojo.hitch(this,function(account) {
                            if (account.id == id) {
                                selected = account;
                            }
                 }));
                return selected;
            },

            /*
             * build tweet process view.
             */
            _buildTweetProcessView : function(data){
                  return this._createStatusTweet(data);
            },

            /*
             * create status tweet.
             */
            _createStatusTweet : function(data){
                console.debug("_createStatusTweet", data);
                var widget = new encuestame.org.core.commons.tweetPoll.TweetPollPublishItemStatus(
                        {
                            data:data,
                            socialAccount : this._getSocialAccountWidget(data.social_account_id)
                        }
                        );
                return widget.domNode;
            }
});

/*
 * Represents a status of tweet published by social account.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPublishItemStatus",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollPublishItemStatus.html"),
            data : {},
            widgetsInTemplate: true,
            socialAccount : {},
            postCreate : function(){
                console.debug("socialAccount", this.socialAccount);
                this.initialize();
            },

            /*
             * initialize widget.
             */
            initialize : function(){
                console.debug("data.status_tweet", this.data.status_tweet);
                if (this.data.status_tweet == encuestame.status[0]) { // SUCCESS
                    this._detailStatus.appendChild(this._showSuccessMessage());
                } else if (this.data.status_tweet == encuestame.status[1]) { //FAILURE
                    this._detailStatus.appendChild(this._showFailureMessage());
                } else {
                    console.error("nothing to do.");
                }
                this._accountProviderIcon.src = encuestame.social.shortPicture(this.socialAccount.type_account);
            },

            /*
             * build succes message.
             */
            _showSuccessMessage : function() {
                var success = new encuestame.org.core.commons.tweetPoll.TweetPollPublishItemSUCCESStatus({
                    metadata : this.data
                });
                return success.domNode;
            },

            /*
             * build failure message.
             */
            _showFailureMessage : function() {
                var fail = new encuestame.org.core.commons.tweetPoll.TweetPollPublishItemFAILUREStatus({
                    metadata : this.data,
                    parentStatusWidget : this
                });
                return fail.domNode;
            }
});

/*
 * success message.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPublishItemSUCCESStatus",
        [dijit._Widget, dijit._Templated],{
             templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollPublishItemSUCCESSStatus.html"),
             metadata : null,
             widgetsInTemplate: true
});

/*
 * failure message.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPublishItemFAILUREStatus",
        [dijit._Widget, dijit._Templated],{
             templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollPublishItemFAILUREStatus.html"),
             metadata : null,
             parentStatusWidget : null,
             widgetsInTemplate: true,
             postCreate : function(){

             },

             _scheduleTweet : function(events){
                 console.debug("TODO");
             }
        });