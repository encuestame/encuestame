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
dojo.require("encuestame.org.core.shared.utils.GenericDialogContent");
dojo.require("encuestame.org.core.commons.social.SocialAccountPicker");
dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollCore");

/**
 * Create TweetPoll Interfaces.
 */
dojo.declare(
    "encuestame.org.core.commons.tweetPoll.TweetPoll",
    [encuestame.org.main.EnmeMainLayoutWidget, encuestame.org.core.commons.tweetPoll.TweetPollCore],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpoll.html"),

        hashTagWidget: null,

        /*
         * answer widget reference.
         */
        answerWidget : null,

        /*
         * question widget.
         */
        questionWidget : null,

        /*
         * preview widget.
         */
        previeWidget : null,

        /*
         * the fixed preview appear on the top of the page.
         */
        previewFixedWiget : null,

        /*
         * social widget.
         */
        socialWidget : null,

        /*
         * the publish dialog to alocate the publish widget.
         */
        dialogWidget : null,

        /*
         * schedule widget.
         */
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
            //create preview widget.
            this.previeWidget = this._createPreviewWidget(this._preview);
            //create preview fixed widet.
            this.previewFixedWiget  = this._createPreviewWidget(this._previewFixed);
            if(this.questionWidget
                    || this.answerWidget
                    || this.hashTagWidget
                    ){
                this.initialize();
            } else {
                this.errorLoading();
            }

            //scroll event for IE
            document.addEventListener(!dojo.isMozilla ? "onmousewheel" : "DOMMouseScroll", dojo.hitch(this, this.scroll), false);
            //scroll wheel for
            window.onscroll = dojo.hitch(this, this.scroll);

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
                        //console.info("tweetpoll autosave response", message);
                        this._autoSaveStatus(message);
                    }));
              }));
              //remove comet support on unload.
              dojo.addOnUnload(function() {
                  if(subscriptionAutoSave != null){
                      //console.info("un subsrcibe subscriptionAutoSave service");
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

            /*
             * replace by encuestame.org.core.shared.options.DateToClose.
             */
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
                //.debug("Scheduled Date", this.scheduledDateWidget.get("value"));
              this.tweetPoll.options.scheduledDate = encuestame.date.getFormatTime(this.scheduledDateWidget.get("value"),
                      encuestame.date.dateFormat);
              dojo.publish("/encuestame/tweetpoll/autosave");
            });
            //time widget.
            this.scheduledTimeWidget = dijit.byId("scheduledTime");
            this.scheduledTimeWidget.onChange = dojo.hitch(this, function(event){
                //console.debug("Scheduled Time", encuestame.date.getFormatTime(this.scheduledTimeWidget.get("value"),
                 //       encuestame.date.timeFormat));
                this.tweetPoll.options.scheduledTime = encuestame.date.getFormatTime(this.scheduledTimeWidget.get("value"),
                        encuestame.date.timeFormat);
                dojo.publish("/encuestame/tweetpoll/autosave");
            });

            this.captchaWidget = dijit.byId("captcha");
            this.captchaWidget.onChange = dojo.hitch(this, function(event){
                this.tweetPoll.options.captcha = event;
                dojo.publish("/encuestame/tweetpoll/autosave");
            });
            /*
             * end warning.
             */

            //Limit Votes
            /*
             * this code should be replace by encuestame.org.core.shared.options.LimitVotes.
             */
            this.limitVotesWidget = dijit.byId("limitVotes");
            this.limitVotesWidget.onChange = dojo.hitch(this, function(event){
                //console.debug("limitVotesWidget", event);
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
              //console.debug("maxLimitVotes ", this.limitNumbersWidget.get("value"));
              this.tweetPoll.options.maxLimitVotes = this.limitNumbersWidget.get("value");
              dojo.publish("/encuestame/tweetpoll/autosave");
            });
            /*
             * end warning.
             */

            //Allow Repeated Votes.
            /*
             * this code should be replace by encuestame.org.core.shared.options.RepeatedVotes.
             */
            this.ipWidget = dijit.byId("ip");
            this.ipWidget.onChange = dojo.hitch(this, function(event){
                //console.debug("ipWidget", event);
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
              //console.debug("maxLimitVotes ", this.repeatedNumbersWidget.get("value"));
              this.tweetPoll.options.maxRepeatedVotes = this.repeatedNumbersWidget.get("value");
              dojo.publish("/encuestame/tweetpoll/autosave");
            });
            /*
             * end warning.
             */

            //report
            this.resumeWidget = dijit.byId("resume");
            this.resumeWidget.onChange = dojo.hitch(this, function(event){
                //console.debug("resumeWidget ", event);
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

            dojo.subscribe("/encuestame/dialog/close", this, this._hideDialog);
            dojo.subscribe("/encuestame/tweetpoll/dialog/error", this, this._showErrorMessage);
            this.enableBlockTweetPollOnProcess();
        },

        /**
         * Hide the current dialog.
         */
        _hideDialog : function(){
            if (this.dialogWidget != null) {
                this.dialogWidget.hide();
            }
        },

        /**
         * create preview widget.
         */
        _createPreviewWidget : function(node) {
            var widget = new encuestame.org.core.commons.tweetPoll.TweetPollPreview(
                     {
                         _widgetReferences : {question : this.questionWidget, answer : this.answerWidget, hashtag : this.hashTagWidget }
                     });
             node.appendChild(widget.domNode);
             return widget;
        },

        /*
         * block widgets.
         */
        _block : function() {
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
            //console.debug("auto save");
            if(this.tweetPoll.tweetPollId == null){
               //console.debug("tweet poll is autosaving ...", this.tweetPoll);
            } else {
               console.info("tweetPol exist", this.tweetPoll.tweetPollId);
               this.tweetPoll.hashtags = this.hashTagWidget.getHashTags();
               this.tweetPoll.answers = this.answerWidget.getAnswersId();
            }
            //console.debug("auto save", this.tweetPoll);
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
             //console.debug("_autoSaveStatus FINISH", this.tweetPoll);
         },

        /*
         * auto-scroll publish top bar.
         */
        scroll : function() {
            var node = this._tweetQuestion;
            var nodeFixed = dojo.byId("previewWrapperFixed");
            var coords = dojo.coords(node);
            if (coords.y < 0) {
             this.previewFixedWiget.show(nodeFixed);

            } else {
              this.previewFixedWiget.hide(nodeFixed);
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
             //dojo.subscribe("/encuestame/tweetpoll/updatePreview", this, "updatePreview");
             this.questionWidget.block = false;
             dojo.connect(this.questionWidget, "onKeyUp", dojo.hitch(this, function(event) {
                 if (dojo.keys.DELETE == event.keyCode || dojo.keys.BACKSPACE == event.keyCode) {
                        //console.debug("is removing");
                        dojo.publish("/encuestame/tweetpoll/updatePreview");
                        if(!this.questionWidget.block){
                            this._questionTextLastSucessMessage = this.questionWidget.get("value");
                        }
                 } else {
                     dojo.publish("/encuestame/tweetpoll/updatePreview");
                     if (!this.questionWidget.block) {
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
        _showErrorMessage : function(errorMessage) {
            //var widget = new encuestame.org.core.shared.utils.GenericDialogContent({content : errorMessage});
            //this.dialogWidget = new dijit.Dialog({
            //    content: widget.domNode,
            //    style: "width: 400px",
           //     draggable : false
           // });
            //this.dialogWidget.show();
            this.infoMesage(errorMessage);
        },

        /*
         *  publish tweet poll.
         */
        _publishTweet : function(){
            var params = {
                     "id" : this.tweetPoll.tweetPollId,
                     "twitterAccounts" : this.socialWidget.getSocialAccounts()
            };
            var load = dojo.hitch(this, function(data) {
                //TODO: DISABLE COMET AUTOSAVE;
                var socialArray = [];
                if ("success" in data) {
                    socialArray = data.success.socialPublish;
                }
                this.autosave = false;
                this.tweetPollPublishWidget.process(socialArray);
            });
            var error = dojo.hitch(this, function(error) {
                console.info("error", error);
                this.autosave = true;
                this._showErrorMessage(error.message);
            });
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


/**
 * This widget represent the list of social of site to publish the tweetpoll.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPublishInfo",
        [encuestame.org.main.EnmeMainLayoutWidget],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpollPublish.html"),

            /**
             * The list of social accounts.
             */
            _socialAccounts : [],

            /**
             * TweetPoll widget.
             */
            tweetPollWidget : null,

            /**
             *
             */
            _inProcess : false,

            /**
             * Post create.
             */
            postCreate : function() {
                var button = dijit.byId(this._close);
                    button.onClick = dojo.hitch(this, function(event) {
                       dojo.publish("/encuestame/dialog/close");
                       document.location.href = encuestame.contextDefault+"/user/tweetpoll/list";
                });
            },

            /**
             * set social account array.
             */
            setListOfSocialAccounts : function(accounts){
                this._socialAccounts = accounts;
            },

            /**
             * initialize widget.
             */
            initialize : function() {
                this._inProcess = true;
                this._showProcessingMessage();
            },

            /**
             * Display proccessing message.
             */
            _showProcessingMessage : function(){
                var message = dojo.doc.createElement("div");
                dojo.addClass(this._message, "defaultDisplayBlock");
                dojo.removeClass(this._message, "defaultDisplayHide");
                message.innerHTML = encuestame.constants.messageCodes["025"];
                //console.debug("message", message);
                this._message.appendChild(message);
            },

            /**
             * Hidde the processing message.
             */
            _hideProcessingMessage : function() {
                dojo.removeClass(this._message, "defaultDisplayBlock");
                dojo.addClass(this._message, "defaultDisplayHide");
                dojo.empty(this._message);
            },

            /**
             * Process date published.
             * @param socialPublish
             */
            process: function(socialPublish){
                if (socialPublish) {
                    this._hideProcessingMessage();
                    dojo.empty(this._container);
                    dojo.empty(this._message);
                    dojo.forEach(socialPublish,
                            dojo.hitch(this,function(tweet) {
                                var row = this._buildTweetProcessView(tweet);
                                if (row) {
                                    this._container.appendChild(row);
                                }
                   }));
                } else {
                    this.errorMesage("data tweet process is empty");
                }
            },

            /**
             * search by id the complete info for selected social account.
             */
            _getSocialAccountWidget : function(id) {
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

            /**
             * Build tweet process view.
             */
            _buildTweetProcessView : function(data){
                  return this._createStatusTweet(data);
            },

            /**
             * Create status tweet.
             */
            _createStatusTweet : function(data){
                var widget = new encuestame.org.core.commons.tweetPoll.TweetPollPublishItemStatus(
                        {
                            data:data,
                            socialAccount : this._getSocialAccountWidget(data.social_account_id)
                        }
                        );
                return widget.domNode;
            }
});

/**
 * Represents a status of tweet published by social account.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPublishItemStatus",
        [encuestame.org.main.EnmeMainLayoutWidget],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollPublishItemStatus.html"),
            data : {},
            widgetsInTemplate: true,
            socialAccount : {},
            postCreate : function(){
                this.initialize();
            },

            /*
             * initialize widget.
             */
            initialize : function() {
                if (this.data.status_tweet == encuestame.status[0]) { // SUCCESS
                    this._detailStatus.appendChild(this._showSuccessMessage());
                } else if (this.data.status_tweet == encuestame.status[1]) { //FAILURE
                    this._detailStatus.appendChild(this._showFailureMessage());
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
        [encuestame.org.main.EnmeMainLayoutWidget],{
             templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollPublishItemSUCCESSStatus.html"),
             metadata : null,
             widgetsInTemplate: true
});

/*
 * failure message.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPublishItemFAILUREStatus",
        [encuestame.org.main.EnmeMainLayoutWidget],{
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