/*
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module Tweetpoll
 *  @namespace Widgets
 *  @class TweetPoll
 */
define([
         "dojo",
         "dojo/_base/declare",
         "dojo/dom-geometry",
         "dojo/_base/array",
         "dojo/dom-construct",
         "dojo/ready",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/Dialog",
         "dijit/form/TextBox",
         "dijit/form/Button",
         "dijit/form/TimeTextBox",
         "dijit/form/DateTextBox",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
         "me/web/widget/tweetpoll/TweetPollPublishInfo",
         "me/web/widget/tweetpoll/TweetPollCore",
         "me/web/widget/tweetpoll/TweetPollPreview",
         "me/web/widget/social/SocialAccountPicker",
         "me/web/widget/tweetpoll/HashTags",
         "me/core/URLServices",
         "dijit/form/CheckBox",
         "dijit/form/NumberSpinner",
         "me/web/widget/tweetpoll/Answers",
         "me/web/widget/ui/HelpContext",
         "me/core/enme",
         "dojo/_base/lang",
         "dojo/topic",
         "dojo/hash",
         "dojo/io-query",
         "dijit/registry",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetpoll.html" ],
        function(
                dojo,
                declare,
                domGeom,
                array,
                domConstruct,
                ready,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Dialog,
                TextBox,
                Button,
                TimeTextBox,
                DateTextBox,
                main_widget,
                hashTagInfo,
                TweetPollPublishInfo,
                TweetPollCore,
                TweetPollPreview,
                SocialAccountPicker,
                HashTags,
                URLServices,
                CheckBox,
                NumberSpinner,
                Answers,
                HelpContext,
                _ENME,
                _lang,
                topic,
                hash,
                ioQuery,
                registry,
                 template) {
            return declare([ _WidgetBase,
                             _TemplatedMixin,
                              main_widget,
                              TweetPollCore,
                              _WidgetsInTemplateMixin], {

            // template string.
            templateString : template,

            /**
             * @private
             * @param _started manage the
             */
            _started : false,

            /***
             * @param hashTagWidget hashtag widgets
             */
            hashTagWidget: null,

            /**
             * @param answer widget reference.
             */
            answerWidget : null,

            /**
             * @param question widget.
             */
            questionWidget : null,

            /**
             * @param preview widget.
             */
            previeWidget : null,

            /**
             * @param the fixed preview appear on the top of the page.
             */
            previewFixedWiget : null,

            /**
             * @param social widget.
             */
            socialWidget : null,

            /**
             * @param the publish dialog to alocate the publish widget.
             */
            dialogWidget : null,

            /**
             * schedule widget.
             */
            scheduleWidget : null,

            /**
             *
             */
            scheduledDateWidget  : null,

            /**
             *
             */
            scheduledTimeWidget  : null,

            /**
             *
             */
            captchaWidget : null,

            /**
             * Limit Votes
             */
            limitVotesWidget  : null,

            /**
             *
             */
            limitNumbersWidget  : null,

            /**
             * Allow Repeated Votes.
             */
            ipWidget  : null,

            /**
             *
             */
            repeatedNumbersWidget  : null,

            /**
             * report widget.
             */
            resumeWidget  : null,

            /**
             *
             */
            _isValidMessage : null,

            /**
             * dashboard widget.
             */
            dashboardWidget  : null,

            /**
             *
             */
            timerAutoSave: null,

            /**
             *
             */
            _questionTextLastSucessMessage : "",

            /**
             * every 5 minutes.
             */
            delay: 300000,

            /**
             * The key to store / restore the last tweetpoll.
             * @property
             */
            _tp_storage_key : "tp_new",

           /**
            * i18n Message.
            */
           i18nMessage : {
             question : _ENME.getMessage("tp_write_questions"),
             answer : _ENME.getMessage("tp_add_answer"),
             hashtag : _ENME.getMessage("tp_add_hashtag"),
             options : _ENME.getMessage("tp_customize"),
             squeduled : _ENME.getMessage("tp_scheduled"),
             squeduled_tp : _ENME.getMessage("tp_options_scheduled_this_tweetpoll"),
             chart_features : _ENME.getMessage("tp_options_chart"),
             allow_live_results : _ENME.getMessage("tp_options_resume_live_results"),
             anti_spam : _ENME.getMessage("tp_options_spam"),
             limit_votes : _ENME.getMessage("tp_options_limit_votes"),
             repeated_votes : _ENME.getMessage("tp_options_allow_repeated_votes"),
             resume_live_results : _ENME.getMessage("tp_options_resume_live_results"),
             captcha : _ENME.getMessage("commons_captcha"),
             tp_options_report : _ENME.getMessage("tp_options_report"),
             follow_dashboard : _ENME.getMessage("tp_options_follow_dashboard"),
             button_publish : _ENME.getMessage("button_publish"),
             tp_select_publish : _ENME.getMessage("tp_select_publish"),
             leave_mesage : _ENME.getMessage("leave_mesage")
           },

            /***
             * Stored save tweetPoll.
             **/
            tweetPoll : {
               tweetPollId : null,
               started : false,
               question: {},
               scheduled : false,
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
            },

            /**
             * enable or disable autosave.
             */
            autosave : true,

            /**
             * tweet poll publish widget reference.
             */
            tweetPollPublishWidget : null,

            /**
             * Help Widget.
             * @param helpWidget
             */
            helpWidget: null,

            /**
             * Return the help status.
             */
            getHelpStatus : function () {
                return _ENME.getBoolean(_ENME.restoreItem("tp-help", true) || true);
            },

            /**
             * post create.
             */
            postCreate: function() {
                var _help_status = this.getHelpStatus();
                this.helpWidget = new HelpContext({
                        status : _help_status,
                        list_messages : [
                            "First you need write your <strong>question</strong> ",
                            "Add at least 2 answers",
                            "Add hashtag, like in Twitter, useful to navigate on it and found your tweetpoll",
                            "Select all Social Networks to Publish your beautiful creation "
                        ]
                });
                if (this._help) {
                    dojo.empty(this._help);
                    domConstruct.place( this.helpWidget.domNode, this._help);
                }

                this.cancelButton = this._cancelButton;
                this.cancelButton.onClick = dojo.hitch(this, function(){
                    this._redirectList();
                });
                this.questionWidget = registry.byId("question");
                this.answerWidget = registry.byId("answers");
                this.hashTagWidget = registry.byId("hashtags");

                // save a reference of main object
                this.answerWidget.tweetPoll = this.tweetPoll;
                this.hashTagWidget.tweetPoll = this.tweetPoll;

                //create preview widget.
                this.previeWidget = this._createPreviewWidget(this._preview);
                //create preview fixed widet.
                this.previewFixedWiget  = this._createPreviewWidget(this._previewFixed);
                if(this.questionWidget || this.answerWidget|| this.hashTagWidget){
                    this.initialize();
                }

                // scroll event for IE
                document.addEventListener(!dojo.isMozilla ? "onmousewheel" : "DOMMouseScroll", dojo.hitch(this, this.scroll), false);
                // scroll wheel for
                window.onscroll = dojo.hitch(this, this.scroll);
                // leave message
                this.unLoadSupport(this.i18nMessage.leave_mesage);

                /**
           * Bug on Table / iPads We need use dojo.touch
           * http://dojotoolkit.org/reference-guide/1.7/dojo/touch.html
           */
//            			document.addEventListener("touchstart", touchStart,
//            					false);
//            			document.addEventListener("touchmove", touchMove, false);
    //
//            			var start = {
//            				x : 0,
//            				y : 0
//            			};
    //
//            			function touchStart(e) {
    //
//            				// start.x = event.touches[0].pageX;
//            				// start.y = event.touches[0].pageY;
//            				console.debug("touch start");
//            			}
//            			var parent = this;
//            			function touchMove(e) {
    //
//            				offset = {};
    //
//            				offset.x = start.x - event.touches[0].pageX;
//            				offset.y = start.y - event.touches[0].pageY;
//            				//
//            				// return offset;
//            				console.debug(offset.x);
//            				console.debug(offset.y);
//            			}


                //enable auto save.
                if (this.autosave) {
                  //publish suport.
                  dojo.subscribe("/encuestame/tweetpoll/autosave", this, this._autoSave);
                  dojo.subscribe("/encuestame/tweetpoll/block", this, this._block);
                  dojo.subscribe("/encuestame/tweetpoll/unblock", this, this._unblock);
                  //active auto save.
                  //TODO cometd refactorization
//                  this.loadAutoSaveTimer();
//                  //add comet support on load.
//                  dojo.addOnLoad(dojo.hitch(this, function(){
//                        subscriptionAutoSave  = encuestame.activity.cometd.subscribe('/service/tweetpoll/autosave',
//                        dojo.hitch(this, function(message) {
//                            //console.info("tweetpoll autosave response", message);
//                            this._autoSaveStatus(message);
//                        }));
//                  }));
//                  //remove comet support on unload.
//                  dojo.addOnUnload(function() {
//                      if(subscriptionAutoSave != null){
//                          //console.info("un subsrcibe subscriptionAutoSave service");
//                          encuestame.activity.cometd.unsubscribe(subscriptionAutoSave);
//                      }
//                  });
                }

                //tweet poll options

                //live results.
                this.liveResultsWidget = registry.byId("liveResults");
                this.liveResultsWidget.onChange = dojo.hitch(this, function(event){
                  this.tweetPoll.liveResults = event;
                  dojo.publish("/encuestame/tweetpoll/autosave");
                });

                /**
                 * replace by encuestame.org.core.shared.options.DateToClose.
                 */
                //scheduled
                this.scheduleWidget = registry.byId("schedule");
                this.scheduleWidget.onChange = dojo.hitch(this, function(event){
                    //console.debug("shecduled", event);
                    if (event) {
                        dojo.removeClass(this._scheduledTime, "defaultDisplayHide");
                        dojo.removeClass(this._scheduledDate, "defaultDisplayHide");
                    } else {
                        dojo.addClass(this._scheduledTime, "defaultDisplayHide");
                        dojo.addClass(this._scheduledDate, "defaultDisplayHide");
                    }
                    this.tweetPoll.scheduledTime = _ENME.getFormatTime(new Date(),
                            _ENME.TIME.timeFormat);
                    this.scheduledDateWidget.set("value", new Date());
                    this.scheduledTimeWidget.set("value", new Date());
                    this.tweetPoll.scheduledTime = _ENME.getFormatTime(new Date(),
                            _ENME.TIME.timeFormat);
                    this.tweetPoll.scheduledDate = _ENME.getFormatTime(new Date(),
                            _ENME.TIME.dateFormat);
                    this.tweetPoll.scheduled = event;
                    dojo.publish("/encuestame/tweetpoll/autosave");
                });
                //date widget.
                this.scheduledDateWidget = registry.byId("scheduledDate");
                this.scheduledDateWidget.onChange = dojo.hitch(this, function(event){
                    //.debug("Scheduled Date", this.scheduledDateWidget.get("value"));
                  this.tweetPoll.scheduledDate = _ENME.getFormatTime(this.scheduledDateWidget.get("value"),
                          _ENME.TIME.dateFormat);
                  dojo.publish("/encuestame/tweetpoll/autosave");
                });
                //time widget.
                this.scheduledTimeWidget = registry.byId("scheduledTime");
                this.scheduledTimeWidget.onChange = dojo.hitch(this, function(event){
                    //console.debug("Scheduled Time", _ENME.getFormatTime(this.scheduledTimeWidget.get("value"),
                     //       _ENME.TIME.timeFormat));
                    this.tweetPoll.scheduledTime = _ENME.getFormatTime(this.scheduledTimeWidget.get("value"),
                            _ENME.TIME.timeFormat);
                    dojo.publish("/encuestame/tweetpoll/autosave");
                });

                this.captchaWidget = registry.byId("captcha");
                this.captchaWidget.onChange = dojo.hitch(this, function(event){
                    this.tweetPoll.captcha = event;
                    dojo.publish("/encuestame/tweetpoll/autosave");
                });
                /**
                 * end warning.
                 */

                //Limit Votes
                /**
                 * this code should be replace by encuestame.org.core.shared.options.LimitVotes.
                 */
                this.limitVotesWidget = registry.byId("limitVotes");
                this.limitVotesWidget.onChange = dojo.hitch(this, function(event){
                    //console.debug("limitVotesWidget", event);
                    if (event) {
                        dojo.removeClass(this._limitNumbers, "defaultDisplayHide");
                    } else {
                        dojo.addClass(this._limitNumbers, "defaultDisplayHide");
                    }
                    this.tweetPoll.limitVotes = event;
                    dojo.publish("/encuestame/tweetpoll/autosave");
                });
                this.limitNumbersWidget = registry.byId("limitNumbers");
                this.limitNumbersWidget.onChange = dojo.hitch(this, function(event){
                  //console.debug("maxLimitVotes ", this.limitNumbersWidget.get("value"));
                  this.tweetPoll.maxLimitVotes = this.limitNumbersWidget.get("value");
                  dojo.publish("/encuestame/tweetpoll/autosave");
                });
                /**
                 * end warning.
                 */

                //Allow Repeated Votes.
                /**
                 * this code should be replace by encuestame.org.core.shared.options.RepeatedVotes.
                 */
                this.ipWidget = registry.byId("ip");
                this.ipWidget.onChange = dojo.hitch(this, function(event) {
                    //console.debug("ipWidget", event);
                    if (event) {
                        dojo.removeClass(this._repeatedNumbers, "defaultDisplayHide");
                    } else {
                        dojo.addClass(this._repeatedNumbers, "defaultDisplayHide");
                    }
                    this.tweetPoll.repeatedVotes = event;
                    dojo.publish("/encuestame/tweetpoll/autosave");
                });
                this.repeatedNumbersWidget = registry.byId("repeatedNumbers");
                this.repeatedNumbersWidget.onChange = dojo.hitch(this, function(event) {
                  //console.debug("maxLimitVotes ", this.repeatedNumbersWidget.get("value"));
                  this.tweetPoll.maxRepeatedVotes = this.repeatedNumbersWidget.get("value");
                  dojo.publish("/encuestame/tweetpoll/autosave");
                });
                /**
                 * end warning.
                 */

                //report
                this.resumeWidget = registry.byId("resume");
                this.resumeWidget.onChange = dojo.hitch(this, function(event){
                    //console.debug("resumeWidget ", event);
                    this.tweetPoll.resumeLiveResults = event;
                    dojo.publish("/encuestame/tweetpoll/autosave");
                });
                this.dashboardWidget = registry.byId("dashboard");
                this.dashboardWidget.onChange = dojo.hitch(this, function(event){
                    this.tweetPoll.followDashBoard = event;
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

            /***
             *  @method Hide the current dialog.
             */
            _hideDialog : function(){
                if (this.dialogWidget != null) {
                    this.dialogWidget.hide();
                }
            },

            /***
             * @method create preview widget.
             */
            _createPreviewWidget : function(node) {
                var widget = new TweetPollPreview(
                         {
                             _widgetReferences : {question : this.questionWidget, answer : this.answerWidget, hashtag : this.hashTagWidget }
                         });
                 node.appendChild(widget.domNode);
                 return widget;
            },

            /**
             * @method block widgets.
             */
            _block : function() {
                this.answerWidget.block();
                this.hashTagWidget.block();
                //console.info("block this.questionWidget.maxLength ", this.questionWidget.maxLength);
                this.questionWidget.block = true;
            },

            /**
             * @method unblock items.
             */
            _unblock : function() {
                //console.info("unblock");
                this.answerWidget.unblock();
                this.questionWidget.block = false;
                this.hashTagWidget.unblock();
            },

            /***
             * @method Load AutoSave timer
             * Create timer to autosave the tweetpoll.
             */
            loadAutoSaveTimer : function() {
                var widget = this;
                this.timerAutoSave = new dojox.timing.Timer(this.delay);
                this.timerAutoSave.onTick = function() {
                  widget._autoSave();
                };
                this.timerAutoSave.onStart = function() {
                  //nothing to do
                };
                this.timerAutoSave.start();
            },

            /***
             * @method Auto save tweetPoll.
             * @param tweetPollId if is null we crete new tweetPoll.
             * @param question { id, question}
             * @param hastags [id,id,id,id]
             * @param anseerws { id, question}
             */
            _autoSave : function(tweetPollId, /*** widget **/ question, /*** answers. **/ answers, /***hashtags **/ hashtags) {
                var params = this.tweetPoll;
                if (this.tweetPoll.tweetPollId === null) {
                    params = { question : params.question };
                } else {
                   this.tweetPoll.hashtags = this.hashTagWidget.getHashTags();
                   this.tweetPoll.answers = this.answerWidget.getAnswersId();
                }
                this.loading_show();

                //TODO cometD refatorization
                //encuestame.activity.cometd.publish('/service/tweetpoll/autosave', { tweetPoll: this.tweetPoll});

               //var params = _lang.mixin(this.tweetPoll,{});

                var load = dojo.hitch(this, function(data) {
                    if ('success' in data) {
                        this.tweetPoll.tweetPollId = data.success.tweetPoll.id;
                        this.hashTagWidget.tweetPollId = data.success.tweetPoll.id;
                        this.answerWidget.tweetPollId = data.success.tweetPoll.id;
                        this._autoSaveStatus("message");
                    }
                });


                var error = function(error) {
                      //console.debug("error", error);
                };

                if (params.question !== '') {
                    this.getURLService().post("encuestame.service.tweetpoll.autosave", params, load, error , dojo.hitch(this, function() {}));
                } else {
                    this.loading_hide();
                }
             },

             /***
              * @method Get activity services response after save tweetpoll.
              * @param status {Object}
              */
             _autoSaveStatus : function(status) {
               // console.debug("auto save status", status);
               this.loading_hide();
                 if (this.tweetPoll.tweetPollId === null) {
                   this.tweetPoll.tweetPollId = status.data.tweetPollId;
                   var tweetPoll = {
                       id : this.tweetPoll.tweetPollId
                   };
                   this.hashTagWidget.tweetPollId = this.tweetPoll.tweetPollId;
                   this.answerWidget.tweetPollId = this.tweetPoll.tweetPollId;
                   // store the tweetpoll data
                   _ENME.storeItem(this._tp_storage_key, this.tweetPoll);
                 }
                 this._started = true;
                 this.initializeInterface();
             },



             /**
              * Initialize the interface after user create the question
              * @method initializeInterface
              */
             initializeInterface : function () {
                    var _steps = dojo.query('div [data-step]');
                    array.forEach(_steps, function(entry, i){
                        dojo.removeClass(entry, "hidden");
                        var fadeArgs = {
                            node: entry
                        };
                        dojo.fadeIn(fadeArgs).play();
                    });

                    dojo.empty(this._pre_cancel);

                    // hide the help if iexist
                   if (this.helpWidget.status) {
                       this.helpWidget.hide();
                      _ENME.storeItem("tp-help", false, true);
                    }

             },

            /***
             * @method Auto-scroll publish top bar.
             */
            scroll : function() {
                var node = this._tweetQuestion;
                var nodeFixed = dojo.byId("previewWrapperFixed");
                var coords = domGeom.position(node);
                if (coords.y < 0) {
                  this.previewFixedWiget.show(nodeFixed);
                } else {
                  this.previewFixedWiget.hide(nodeFixed);
                }
              },

            /***
             * @method Initialize new tweetpoll create.
             */
            initialize : function() {
                 // var hash = ioQuery.queryToObject(dojo.hash());
                 // if (hash.id) {
                   // if hash id previously exist, not do anything.
                 //}
                 //dojo.subscribe("/encuestame/tweetpoll/updatePreview", this, "updatePreview");
                 this.questionWidget.block = false;
                 dojo.connect(this.questionWidget, "onKeyPress", dojo.hitch(this, function(event) {
                     if (dojo.keys.DELETE == event.keyCode || dojo.keys.BACKSPACE == event.keyCode) {
                            dojo.publish("/encuestame/tweetpoll/updatePreview");
                            if (!this.questionWidget.block) {
                                this._questionTextLastSucessMessage = this.questionWidget.get("value");
                            }
                     } else {
                         dojo.publish("/encuestame/tweetpoll/updatePreview");
                         if (!this.questionWidget.block) {
                             this._questionTextLastSucessMessage = this.questionWidget.get("value");
                             this.tweetPoll.question = this._questionTextLastSucessMessage;
                             dojo.publish("/encuestame/tweetpoll/autosave");
                         } else {
                             this.questionWidget.set('value', this._questionTextLastSucessMessage);
                         }
                     }
                 }));

                 // send the data to comed service
                 this.questionWidget.onChange = dojo.hitch(this, function(){
                     this.tweetPoll.question.value = this.questionWidget.get("value");
                     dojo.publish("/encuestame/tweetpoll/autosave");
                 });
                 this.socialWidget = registry.byId("social");
                 //initialize publish widget.
                 this.tweetPollPublishWidget = new TweetPollPublishInfo(
                         {
                           tweetPollWidget : this
                         });
            },

            /**
             * @method check if tweetpoll is valid and start the process to publish.
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
                            this.dialogWidget = new Dialog({
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

            /**
             * @method show error message.
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

            /**
             *  @method publish tweet poll.
             */
            _publishTweet : function() {
                var params = {
                         "id" : this.tweetPoll.tweetPollId,
                         "twitterAccounts" : this.socialWidget.getSocialAccounts()
                };
                var load = dojo.hitch(this, function(data) {
                    var socialArray = [];
                    if ("success" in data) {
                        socialArray = data.success.socialPublish;
                    }
                    this.autosave = false;
                    this.cancelUnLoadSupport();
                    this.tweetPollPublishWidget.process(socialArray);
                });

                /***
                 * On publish error.
                 *  - Close dialog
                 *  - Display a Error message
                 *
                 */
                var error = dojo.hitch(this, function(error) {
                    this.autosave = true;
                    this.dialogWidget.hide();
                    this._showErrorMessage(error.response.data.error.message || _ENME.getMessage("tp_publish_error", "An error occurred when trying to publish your survey"));
                });
                URLServices.post('encuestame.service.list.publishTweetPoll',  params, load, error , dojo.hitch(this, function() {

                }));
            },

            /**
             * Redirect to list page.
             * @method _redirectList
             */
            _redirectList : function(){
                document.location.href = _ENME.config('contextPath') + "/user/tweetpoll/list";
            },

            /**
             * Cancel the tweetpoll.
             * @method _cancelTweetPoll
             */
            _cancelTweetPoll : function (e) {
                     dojo.stopEvent(e);
                     this._redirectList();
            }
    });
});
;