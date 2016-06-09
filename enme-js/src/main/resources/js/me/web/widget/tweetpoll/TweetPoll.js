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
define( [
 "dojo",
 "me/third-party/moment",
 "dojo/on",
 "dojo/dom",
 "dojo/_base/declare",
 "dojo/dom-geometry",
 "dojo/_base/array",
 "dojo/dom-class",
 "dojo/dom-style",
 "dojo/dom-construct",
 "dojo/query",
 "dojo/ready",
 "dijit/_WidgetBase",
 "dijit/_TemplatedMixin",
 "dijit/_WidgetsInTemplateMixin",
 "dijit/Tooltip",
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
 "me/core/support/ContextSupport",
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
 "dojo/text!me/web/widget/tweetpoll/templates/tweetpoll.html"
],
function(
        dojo,
        moment,
        on,
        dom,
        declare,
        domGeom,
        array,
        domClass,
        domStyle,
        domConstruct,
        query,
        ready,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        Tooltip,
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
        ContextSupport,
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
        template ) {
    return declare( [
        _WidgetBase,
        _TemplatedMixin,
        main_widget,
        TweetPollCore,
        ContextSupport,
        _WidgetsInTemplateMixin ], {

    // Template string.
    templateString: template,

    /**
     * @private
     * @param _started manage the
     */
    _started: false,

    /**
     * Define the context
     */
    context: "tweetpoll",

    /***
     * @param hashTagWidget hashtag widgets
     */
    hashTagWidget: null,

    /**
     * @param answer widget reference.
     */
    answerWidget: null,

    /**
     * @param question widget.
     */
    questionWidget: null,

    /**
     * @param preview widget.
     */
    previeWidget: null,

    /**
     * @param the fixed preview appear on the top of the page.
     */
    previewFixedWiget: null,

    /**
     * @param social widget.
     */
    socialWidget: null,

    /**
     * @param the publish dialog to alocate the publish widget.
     */
    dialogWidget: null,

    /**
     * Schedule widget.
     */
    scheduleWidget: null,

    /**
     *
     */
    scheduledDateWidget: null,

    /**
     *
     */
    scheduledTimeWidget: null,

    /**
     *
     */
    captchaWidget: null,

    /**
     * Limit Votes
     */
    limitVotesWidget: null,

    /**
     *
     */
    limitNumbersWidget: null,

    /**
     * Allow Repeated Votes.
     */
    ipWidget: null,

    /**
     *
     */
    repeatedNumbersWidget: null,

    /**
     * Report widget.
     */
    resumeWidget: null,

    /**
     *
     */
    _isValidMessage: null,

    /**
     * Dashboard widget.
     */
    dashboardWidget: null,

    /**
     *
     */
    timerAutoSave: null,

    /**
     *
     */
    _questionTextLastSucessMessage: "",

    /**
     * Every 5 minutes.
     */
    delay: 300000,

    /**
     * Scroll flag
     */
    _scroll: true,

    /**
     * Disable unload on close window
      */
    forceClose: false,

    /**
     * The key to store / restore the last tweetpoll.
     * @property
     */
    _tp_storage_key: "tp_new",

   /**
    * I18n Message.
    */
   i18nMessage: {
     question: _ENME.getMessage("tp_write_questions"),
     answer: _ENME.getMessage("tp_add_answer"),
     hashtag: _ENME.getMessage("tp_add_hashtag"),
     options: _ENME.getMessage("tp_customize"),
     button_scheduled: _ENME.getMessage("button_scheduled"),
     squeduled: _ENME.getMessage("tp_scheduled"),
     squeduled_tp: _ENME.getMessage("tp_options_scheduled_this_tweetpoll"),
     chart_features: _ENME.getMessage("tp_options_chart"),
     allow_live_results: _ENME.getMessage("tp_options_resume_live_results"),
     anti_spam: _ENME.getMessage("tp_options_spam"),
     limit_votes: _ENME.getMessage("tp_options_limit_votes"),
     repeated_votes: _ENME.getMessage("tp_options_allow_repeated_votes"),
     resume_live_results: _ENME.getMessage("tp_options_resume_live_results"),
     captcha: _ENME.getMessage("commons_captcha"),
     tp_options_report: _ENME.getMessage("tp_options_report"),
     follow_dashboard: _ENME.getMessage("tp_options_follow_dashboard"),
     button_publish: _ENME.getMessage("button_publish"),
     tp_select_publish: _ENME.getMessage("tp_select_publish"),
     leave_mesage: _ENME.getMessage("leave_mesage"),
     commons_tab_to_save: _ENME.getMessage("commons_tab_to_save")
   },

    helpSteps: [
        {
            element: ".tp-help",
            intro: _ENME.getMessage( "help_tp_quick_help" )
        },
        {
            element: ".help-option",
            intro: _ENME.getMessage( "help_tp_hide_help" )
        },
        {
            element: ".web-tweetpoll-preview-top",
            intro: _ENME.getMessage( "help_tp_counter_characters" )
        },
        {
            element: ".web-tweetpoll-preview-top  .tp-counter",
            intro: _ENME.getMessage( "help_tp_counter_available" )
        },
        {
            element: ".tweetThis",
            intro: _ENME.getMessage( "help_tp_question_text" )
        },
        {
            element: ".cancel-button > .dijitButton",
            intro: _ENME.getMessage( "help_tp_new_cancel_button" )
        }
    ],

    /***
     * Stored save tweetPoll.
     **/
    tweetPoll: {
       tweetPollId: null,
       started: false,
       question: {},
       scheduled: false,
       scheduledTime: null,
       scheduledDate: null,
       liveResults: false,
       captcha: false,
       limitVotes: false,
       maxLimitVotes: 100,
       repeatedVotes: false,
       maxRepeatedVotes: 2,
       resumeLiveResults: false,
       followDashBoard: false
    },

    /**
     * Enable or disable autosave.
     */
    autosave: true,

    /**
     * Save the status if is saving.
     * @property _is_saving
     */
    _is_saving: false,

    /**
     * Tweet poll publish widget reference.
     */
    tweetPollPublishWidget: null,

    /**
     * Help Widget.
     * @param helpWidget
     */
    helpWidget: null,

    /**
     * Return the help status.
     */
    getHelpStatus: function() {

        //Return _ENME.getBoolean(_ENME.restoreItem("tp-help", true) || true);
        return true; //Always we must show the help
    },

    /**
     * Post create.
     */
    postCreate: function() {
        var r = _ENME.FORMAT_TIME.timeFormat;
        var _help_status = this.getHelpStatus();
        this.helpWidget = new HelpContext({
                status: _help_status,
                list_messages: [
                    _ENME.getMessage("tp_help_1"),
                    _ENME.getMessage("tp_help_2"),
                    _ENME.getMessage("tp_help_3"),
                    _ENME.getMessage("tp_help_4")
                ]
        });

        if ( this._help ) {
            domConstruct.empty( this._help );
            domConstruct.place( this.helpWidget.domNode, this._help );
        }

        // Button events

        this.cancelButton = this._cancelButton;
        this.cancelButton.onClick = _lang.hitch( this, function() {
            this._redirectList();
        });
        this.questionWidget = registry.byId("question");

        this.answerWidget = registry.byId("answers");
        this.hashTagWidget = registry.byId("hashtags");

        // Save a reference of main object
        this.answerWidget.tweetPoll = this.tweetPoll;
        this.hashTagWidget.tweetPoll = this.tweetPoll;

        //Create preview widget.
        this.previeWidget = this._createPreviewWidget( this._preview );

        //Create preview fixed widet.
        this.previewFixedWiget  = this._createPreviewWidget( this._previewFixed );
        if ( this.questionWidget || this.answerWidget || this.hashTagWidget ) {
            this.initialize();
        }

        //Hack// scroll event for IE
        document.addEventListener( !dojo.isMozilla ? "onmousewheel" : "DOMMouseScroll", _lang.hitch( this, this.scroll ), false );

        // Scroll wheel for
        if ( this._scroll ) {
            window.onscroll = _lang.hitch( this, this.scroll );
        }

        // Leave message
        if ( !this.forceClose ) {
            this.unLoadSupport( this.i18nMessage.leave_mesage );
        }

        // Offline execution
        this.addOfflineExecution({
            "down": function() {

                //Console.log("tweetpoll DOWN1");
            },
            "up": function() {

                //Console.log("tweetpoll UP2");
            }
        });

        // Enable auto save channels
        if ( this.autosave ) {

          //Publish suport.
          topic.subscribe("/encuestame/tweetpoll/autosave", _lang.hitch( this, this._autoSave ) );
          topic.subscribe("/encuestame/tweetpoll/block", _lang.hitch( this, this._block ) );
          topic.subscribe("/encuestame/tweetpoll/unblock", _lang.hitch( this, this._unblock ) );
        }

        // Live results.
        this.liveResultsWidget = registry.byId("liveResults");
        this.liveResultsWidget.onChange = _lang.hitch( this, function( event ) {
          this.tweetPoll.liveResults = event;
          topic.publish("/encuestame/tweetpoll/autosave");
        });

        /**
         * Replace by encuestame.org.core.shared.options.DateToClose.
         */

        //Scheduled
        this.scheduleWidget = registry.byId("schedule");
        this.scheduleWidget.onChange = _lang.hitch( this, function( event ) {

            //Console.debug("shecduled", event);
            if ( event ) {
                domClass.remove( this._scheduledTime, "defaultDisplayHide");
                domClass.remove( this._scheduledDate, "defaultDisplayHide");
                domClass.add( this._publish.domNode, "hidden");
                domClass.remove( this._scheduled_tweetpoll.domNode, "hidden");
            } else {
                domClass.add( this._scheduledTime, "defaultDisplayHide");
                domClass.add( this._scheduledDate, "defaultDisplayHide");
                domClass.remove( this._publish.domNode, "hidden");
                domClass.add( this._scheduled_tweetpoll.domNode, "hidden");
            }
            this.tweetPoll.scheduledTime = _ENME.getFormatTime( new Date(), _ENME.FORMAT_TIME.timeFormat );
            this.scheduledDateWidget.set("value", moment( new Date() ).toDate() );
            this.scheduledTimeWidget.set("value", new Date() );
            this.tweetPoll.scheduledTime = _ENME.getFormatTime( new Date(), _ENME.FORMAT_TIME.timeFormat );
            this.tweetPoll.scheduledDate = _ENME.getFormatTime( new Date(), _ENME.FORMAT_TIME.dateFormat );
            this.tweetPoll.scheduled = event;
            topic.publish("/encuestame/tweetpoll/autosave");
        });

        // Date widget.
        this.scheduledDateWidget = registry.byId("scheduledDate");
        var date = new Date(); //FIXME: repeated code in TweetPollPublishedItemFAILUREStatus
        date.setDate( date.getDate() + 365 );
        var _max = moment( date );
        this.scheduledDateWidget.constraints = {
            min: moment().toDate(),
            datePattern: "dd-MMM-yyyy",
            selector: "date",
            max: _max.toDate()
        };
        this.scheduledDateWidget.onChange = _lang.hitch( this, function( event ) {

            //.debug("Scheduled Date", this.scheduledDateWidget.get("value"));
          this.tweetPoll.scheduledDate = _ENME.getFormatTime( this.scheduledDateWidget.get("value"),
                  _ENME.FORMAT_TIME.dateFormat );
          topic.publish("/encuestame/tweetpoll/autosave");
        });

        // Time widget.
        this.scheduledTimeWidget = registry.byId("scheduledTime");
        this.scheduledTimeWidget.onChange = _lang.hitch( this, function( event ) {

            //Console.debug("Scheduled Time", _ENME.getFormatTime(this.scheduledTimeWidget.get("value"),
             //       _ENME.TIME.timeFormat));
            this.tweetPoll.scheduledTime = _ENME.getFormatTime( this.scheduledTimeWidget.get("value"),
                    _ENME.FORMAT_TIME.timeFormat );
            topic.publish("/encuestame/tweetpoll/autosave");
        });

        this.captchaWidget = registry.byId("captcha");
        this.captchaWidget.onChange = _lang.hitch( this, function( event ) {
            this.tweetPoll.captcha = event;
            topic.publish("/encuestame/tweetpoll/autosave");
        });

        // Limit Votes
        // this code should be replace by encuestame.org.core.shared.options.LimitVotes.
        this.limitVotesWidget = registry.byId("limitVotes");
        this.limitVotesWidget.onChange = _lang.hitch( this, function( event ) {
            if ( event ) {
                domClass.remove( this._limitNumbers, "defaultDisplayHide");
            } else {
                domClass.add( this._limitNumbers, "defaultDisplayHide");
            }
            this.tweetPoll.limitVotes = event;
            topic.publish("/encuestame/tweetpoll/autosave");
        });
        this.limitNumbersWidget = registry.byId("limitNumbers");
        this.limitNumbersWidget.onChange = _lang.hitch( this, function( event ) {
          this.tweetPoll.maxLimitVotes = this.limitNumbersWidget.get("value");
          topic.publish("/encuestame/tweetpoll/autosave");
        });

        /**
         * This code should be replace by encuestame.org.core.shared.options.RepeatedVotes.
         */
        this.ipWidget = registry.byId("ip");
        this.ipWidget.onChange = _lang.hitch( this, function( event ) {

            //Console.debug("ipWidget", event);
            if ( event ) {
                domClass.remove( this._repeatedNumbers, "defaultDisplayHide");
            } else {
                domClass.add( this._repeatedNumbers, "defaultDisplayHide");
            }
            this.tweetPoll.repeatedVotes = event;
            topic.publish("/encuestame/tweetpoll/autosave");
        });
        this.repeatedNumbersWidget = registry.byId("repeatedNumbers");
        this.repeatedNumbersWidget.onChange = _lang.hitch( this, function( event ) {
          this.tweetPoll.maxRepeatedVotes = this.repeatedNumbersWidget.get("value");
          topic.publish("/encuestame/tweetpoll/autosave");
        });
        /**
         * End warning.
         */

        //Report
        this.resumeWidget = registry.byId("resume");
        this.resumeWidget.onChange = _lang.hitch( this, function( event ) {

            //Console.debug("resumeWidget ", event);
            this.tweetPoll.resumeLiveResults = event;
            topic.publish("/encuestame/tweetpoll/autosave");
        });
        this.dashboardWidget = registry.byId("dashboard");
        this.dashboardWidget.onChange = _lang.hitch( this, function( event ) {
            this.tweetPoll.followDashBoard = event;
            topic.publish("/encuestame/tweetpoll/autosave");
        });

        //Button publish event.
        on( this._publish, "click", _lang.hitch( this, function( event ) {
            this._checkPublish();
        }) );

        on( this._scheduled_tweetpoll, "click", _lang.hitch( this, function( event ) {
            this._checkPublish("scheduled");
        }) );

        topic.subscribe("/encuestame/dialog/close", _lang.hitch( this, this._hideDialog ) );
        topic.subscribe("/encuestame/tweetpoll/dialog/error", _lang.hitch( this, this._showErrorMessage ) );
        this.enableBlockTweetPollOnProcess();

        //Help links
        this.initHelpLinks( _lang.hitch( this, function() {
            this.updateHelpPageStatus( _ENME.config( "currentPath" ), true );
        }) );
    },

    /***
     *  @method Hide the current dialog.
     */
    _hideDialog: function() {
        if ( this.dialogWidget !== null ) {
            this.dialogWidget.hide();
        }
    },

    /***
     * @method create preview widget.
     */
    _createPreviewWidget: function( node ) {
        var widget = new TweetPollPreview(
                 {
                     _widgetReferences: { question: this.questionWidget, answer: this.answerWidget, hashtag: this.hashTagWidget }
                 });
         node.appendChild( widget.domNode );
         return widget;
    },

    /**
     * @method block widgets.
     */
    _block: function() {
        this.answerWidget.block();
        this.hashTagWidget.block();
        this.questionWidget.block = true;
    },

    /**
     * @method unblock items.
     */
    _unblock: function() {
        this.answerWidget.unblock();
        this.questionWidget.block = false;
        this.hashTagWidget.unblock();
    },

    /***
     * @method Load AutoSave timer
     * Create timer to autosave the tweetpoll.
     */
    loadAutoSaveTimer: function() {
        var widget = this;
        this.timerAutoSave = new dojox.timing.Timer( this.delay );
        this.timerAutoSave.onTick = function() {
          widget._autoSave();
        };
        this.timerAutoSave.onStart = function() {

          //Nothing to do
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
    _autoSave: function( tweetPollId, /*** widget **/ question, /*** answers. **/ answers, /***hashtags **/ hashtags ) {
        var params = this.tweetPoll;
        if ( this.tweetPoll.tweetPollId === null ) {
            params = { question: params.question };
        } else {
           this.tweetPoll.hashtags = this.hashTagWidget.getHashTags();
           this.tweetPoll.answers = this.answerWidget.getAnswersId();
        }
        this.loading_show();

        var load = _lang.hitch( this, function( data ) {
            if ( "success" in data ) {
                var id = data.success.tweetPoll.id;
                this.tweetPoll.tweetPollId = id;
                this.hashTagWidget.tweetPollId = this.tweetPoll.tweetPollId;
                this.answerWidget.tweetPollId = this.tweetPoll.tweetPollId;
                this._autoSaveStatus("message");
                this._is_saving = false;
            }
        });

        var error = _lang.hitch( this, function( error ) {
              this.loading_hide();
              this._showErrorMessage( error.response.data.error.message || _ENME.getMessage("tp_autosave_error", "An error occurred when trying to save") );
              this._is_saving = false;
        });

        if ( params.question !== "" && !this._is_saving ) {
            this._is_saving = true;
            this.getURLService().post("encuestame.service.tweetpoll.autosave", params, load, error, _lang.hitch( this, function() {}) );
        } else {
            this.loading_hide();
        }
     },

     /***
      * @method Get activity services response after save tweetpoll.
      * @param status {Object}
      */
     _autoSaveStatus: function( status ) {

        // Console.debug("auto save status", status);
        this.loading_hide();
        if ( this.tweetPoll.tweetPollId === null ) {
           this.tweetPoll.tweetPollId = status.data.tweetPollId;
           var tweetPoll = {
               id: this.tweetPoll.tweetPollId
           };
           this.hashTagWidget.tweetPollId = this.tweetPoll.tweetPollId;
           this.answerWidget.tweetPollId = this.tweetPoll.tweetPollId;

           // Store the tweetpoll data
           _ENME.storeItem( this._tp_storage_key, this.tweetPoll );
         }
         this._started = true;
         this.initializeInterface();
     },

     /**
      * Initialize the interface after user create the question
      * @method initializeInterface
      */
     initializeInterface: function() {
            var _steps = query( "div [data-step]" );
            array.forEach( _steps, function( entry, i ) {
                domClass.remove( entry, "hidden");
                var fadeArgs = {
                    node: entry
                };
                dojo.fadeIn( fadeArgs ).play();
            });

            domConstruct.empty( this._pre_cancel );

            // Hide the help if iexist
           if ( this.helpWidget.status ) {
               this.helpWidget.hide();
              _ENME.storeItem("tp-help", false, true );
            }

     },

    /***
     * @method Auto-scroll publish top bar.
     */
    scroll: function() {
        var node = this._tweetQuestion;
        var mainWrapper = dom.byId("mainWrapper");
        var nodeFixed = dom.byId("previewWrapperFixed");
        var _preview_fixed  = query(".web-tweetpoll-preview-top")[ 0 ];
        domStyle.set( nodeFixed, {
            width: mainWrapper.offsetWidth - 20 + "px",
            top: nodeFixed.offsetHeight - 6 + "px"
        });
        var coords2 = domGeom.position( _preview_fixed );
        if ( coords2.y < 0 ) {
          this.previewFixedWiget.show( nodeFixed );
        } else {
          this.previewFixedWiget.hide( nodeFixed );
        }
      },

    /***
     * @method Initialize new tweetpoll create.
     */
    initialize: function() {
         this.questionWidget.block = false;

         //FIXME: changed by on change event
         //on(this.questionWidget, "onKeyPress",);
         //var handle = on(this.questionWidget, "keypress",  _lang.hitch(this, this._questionKeypress));

         // Send the data to comed service
         this.questionWidget.onChange = _lang.hitch( this, function() {
             this.tweetPoll.question = this.questionWidget.get("value");
             topic.publish("/encuestame/tweetpoll/autosave");
         });
         this.socialWidget = registry.byId("social");

         //Initialize publish widget.
         this.tweetPollPublishWidget = new TweetPollPublishInfo({
            tweetPollWidget: this
         });

//         This.questionWidget.onKeyPress = function(e){
//            e.preventDefault();
//         };
    },

//    _questionKeypress : function(event){
//         if (dojo.keys.DELETE == event.keyCode || dojo.keys.BACKSPACE == event.keyCode) {
//                if (!this.questionWidget.block) {
//                    topic.publish("/encuestame/tweetpoll/updatePreview");
//                    this._questionTextLastSucessMessage = this.questionWidget.get("value");
//                }
//         } else {
//             topic.publish("/encuestame/tweetpoll/updatePreview");
//             if (!this.questionWidget.block) {
//                 this._questionTextLastSucessMessage = this.questionWidget.get("value");
//                 this.tweetPoll.question = this._questionTextLastSucessMessage;
//                 topic.publish("/encuestame/tweetpoll/autosave");
//             } else {
//                 this.questionWidget.set('value', this._questionTextLastSucessMessage);
//             }
//         }
//         return false;
//    },

    /**
     * @method check if tweetpoll is valid and start the process to publish.
     */
    _checkPublish: function( type ) {
        topic.publish("/encuestame/tweetpoll/updatePreview");
        var valid = this.previeWidget.isValid();
        this.tweetPollPublishWidget.initialize( type );
        this.tweetPollPublishWidget.setListOfSocialAccounts( this.socialWidget.getSocialCompleteAccounts() );
        if ( !valid ) {
           this.errorMessage( this.previeWidget.isValidMessage() );
        } else {
            valid = this.socialWidget.isValid();
            if ( !valid ) {
                this.errorMessage( this.socialWidget.isValidMessage() );
            } else {

                //If is valid.
                if ( valid ) {
                    this.dialogWidget = new Dialog({
                         content: this.tweetPollPublishWidget.domNode,
                         style: "width: 700px",
                         draggable: false
                     });

                    //DomConstruct.empty(myDialog.titleBar);
                    domClass.add( this.dialogWidget.titleBar, "defaultDisplayHide");
                    this.dialogWidget.show();
                    if ( typeof type === "undefined" ) {
                        this._publishTweet();
                    } else if ( type === "scheduled" ) {
                        this._scheduledTweetPoll();
                    }
                }
            }
        }
    },

    /**
     * @method show error message.
     */
    _showErrorMessage: function( errorMessage ) {
        this.infoMesage( errorMessage );
    },

    /**
     *  @method publish tweet poll.
     */
    _publishTweet: function() {
        var params = {
                 "id": this.tweetPoll.tweetPollId,
                 "twitterAccounts": this.socialWidget.getSocialAccounts()
        };
        var load = _lang.hitch( this, function( data ) {
            var socialArray = [];
            if ("success" in data ) {
                socialArray = data.success.socialPublish;
            }
            this.autosave = false;
            this.cancelUnLoadSupport();
            this.tweetPollPublishWidget.process( socialArray );
        });

        /***
         * On publish error.
         *  - Close dialog
         *  - Display a Error message
         *
         */
        var error = _lang.hitch( this, function( error ) {
            this.autosave = true;
            this.dialogWidget.hide();
            this._showErrorMessage( error.response.data.error.message || _ENME.getMessage("tp_publish_error", "An error occurred when trying to publish your survey") );
        });
        return URLServices.post( "encuestame.service.list.publishTweetPoll",  params, load, error, _lang.hitch( this, function() {

        }) );
    },

    /**
     *
     * @method
     */
    _scheduledTweetPoll: function() {
         var params = {
                 "id": this.tweetPoll.tweetPollId,
                 "social_accounts": this.socialWidget.getSocialCompleteAccounts()
        };
        var load = _lang.hitch( this, function( data ) {
            this.autosave = false; //FIXME ???? is used?
            this.cancelUnLoadSupport();
            if ( "success" in data ) {
               var items = data.success.schedulded_items;
               this.tweetPollPublishWidget.processScheduledFinalStep( items );
            }

        });

        /***
         * On publish error.
         *  - Close dialog
         *  - Display a Error message
         *
         */
        var error = _lang.hitch( this, function( error ) {
            this.autosave = true;
            this.dialogWidget.hide();
            this._showErrorMessage( error.response.data.error.message || _ENME.getMessage("tp_publish_error", "An error occurred when trying to publish your survey") );
        });
        return URLServices.post( [ "encuestame.service.list.scheduleUnPublishedTweetPoll", [ "tweetpoll" ]],  params, load, error, _lang.hitch( this, function() {

        }) );
    },

    /**
     * Redirect to list page.
     * @method _redirectList
     */
    _redirectList: function() {
        document.location.href = _ENME.config( "contextPath" ) + "/user/tweetpoll/list";
    },

    /**
     * Cancel the tweetpoll.
     * @method _cancelTweetPoll
     */
    _cancelTweetPoll: function( e ) {
             e.preventDefault();
             this._redirectList();
    }
  });
});
