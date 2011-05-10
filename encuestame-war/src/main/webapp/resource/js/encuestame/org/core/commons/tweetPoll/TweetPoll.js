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

        scheduleWidget : null,
        scheduledDateWidget  : null,
        scheduledTimeWidget  : null,

        captchaWidget : null,
        //Limit Votes
        limitVotesWidget  : null,
        limitNumbersWidget  : null,
        //Allow Repeated Votes.
        ipWidget  : null,
        repeatedNumbersWidget  : null,

        //report
        resumeWidget  : null,
        dashboardWidget  : null,

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

            //tweet poll options
            this.liveResultsWidget = dijit.byId("liveResults");

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
            });

            this.scheduledDateWidget = dijit.byId("scheduledDate");
            this.scheduledTimeWidget = dijit.byId("scheduledTime");

            this.captchaWidget = dijit.byId("captcha");

            //Limit Votes
            this.limitVotesWidget = dijit.byId("limitVotes");
            this.limitVotesWidget.onChange = dojo.hitch(this, function(event){
                console.debug("limitVotesWidget", event);
                if (event) {
                    dojo.removeClass(this._limitNumbers, "defaultDisplayHide");
                } else {
                    dojo.addClass(this._limitNumbers, "defaultDisplayHide");
                }
            });
            this.limitNumbersWidget = dijit.byId("limitNumbers");

            //Allow Repeated Votes.
            this.ipWidget = dijit.byId("ip");
            this.ipWidget.onChange = dojo.hitch(this, function(event){
                console.debug("ipWidget", event);
                if (event) {
                    dojo.removeClass(this._repeatedNumbers, "defaultDisplayHide");
                } else {
                    dojo.addClass(this._repeatedNumbers, "defaultDisplayHide");
                }
            });
            this.repeatedNumbersWidget = dijit.byId("repeatedNumbers");

            //report
            this.resumeWidget = dijit.byId("resume");
            this.dashboardWidget = dijit.byId("dashboard");

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
            console.info("block this.questionWidget.maxLength ", this.questionWidget.maxLength);
            this.questionWidget.block = true;
        },

        /*
         * unblock items.
         */
        _unblock : function(){
            console.info("unblock");
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
        initialize : function() {
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
             this.socialWidget = dijit.byId("social");
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
        _checkPublish : function(){
            this._publishTweet();
        },

        /*
         *  publish tweet poll.
         */
        _publishTweet : function(){
            var params = {
                     "id" : this.tweetPoll.tweetPollId,
                    "twitterAccounts" : this.socialWidget.getSocialAccounts(),
                    "scheduled" : this.scheduleWidget.get('checked'),
                    "scheduledDate" : this.scheduledDateWidget.get('displayedValue'),
                    "scheduledTime" : this.scheduledTimeWidget.get('displayedValue'),
                    "captcha" : this.captchaWidget.get('checked'),
                    "limitVotes" : this.limitVotesWidget.get('checked'),
                    "limitNumbers" : this.scheduleWidget.get('checked'),
                    "ip" : this.ipWidget.get('checked'),
                    "repeatedNumbers" : this.repeatedNumbersWidget.get('checked')
            };
            console.debug("params", params);
            var load = dojo.hitch(this, function(data){
                console.debug(data);
            });
            var error = function(error) {
                console.debug("error", error);
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