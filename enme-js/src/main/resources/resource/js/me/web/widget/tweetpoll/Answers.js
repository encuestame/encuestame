/**
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

/*****
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module TweetPoll.Answer
 *  @namespace Widgets
 *  @class Answer
 */

define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/Button",
         "dijit/registry",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollCore",
         "me/web/widget/tweetpoll/AnswerItem",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/answer.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Button,
                registry,
                main_widget,
                TweetPollCore,
                AnswerItem,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, TweetPollCore, _WidgetsInTemplateMixin ], {

         // Template string.
         templateString: template,

          /**
          * List of items.
          */
         listItems: [],

         /**
          * Default short url provider.
          */
         _provider: _ENME.shortUrlProvider,

         /**
          * Button widget.
          */
         buttonWidget: null,

         /**
          * Default label.
          */
         label: "Answer",

         /**
          * TweetpollId.
          */
         tweetPollId: null,

         /**
          * Answer source.
          */
         answerSource: null,

         /**
          * Flag to control bloked.
          */
         _blocked: false,

         /**
          * I18n Messages
          */
         i18nMessage: {
           add_button: _ENME.getMessage("button_add")
         },

        /**
         * Post create lifecycle.
         * @method postCreate
         */
         postCreate: function() {
              this.buttonWidget = new Button({
                  label: this.i18nMessage.add_button,
                  onClick: dojo.hitch( this, function( event ) {
                      dojo.stopEvent( event );
                      this.addAnswer();
                  })
              },
              this._addButton );

              // On key up.
              dojo.connect( this._suggest, "onKeyUp", dojo.hitch( this, function( e ) {
                  if ( dojo.keys.ENTER == e.keyCode ) {
                      this.addAnswer();
                  }
              }) );
              this.enableBlockTweetPollOnProcess();
         },

         /**
          *
          */
         block: function() {
             this.buttonWidget.disabled = true;
             registry.byId("answerAddText").disabled = true;
         },

         /**
          *
          */
         unblock: function() {
             this.buttonWidget.disabled = false;
             registry.byId("answerAddText").disabled = false;
         },

         /**
          *
          */
         getAnswers: function() {
             var array = [];
              dojo.forEach( this.listItems,
                    dojo.hitch( this, function( item ) {
                    array.push({ value: item.getAnswerText() });
                    }) );
             return array;
         },

         /**
          *
          */
         getAnswersId: function() {
             var array = [];
              dojo.forEach( this.listItems,
                    dojo.hitch( this, function( item ) {
                    array.push( item.data.answer.answer_id );
                    }) );
             return array;
         },

         /***
          * Start the process to save the answer.
          */
         addAnswer: function() {
             var text = registry.byId( this._suggest );
             var params = {
                     "id": this.tweetPollId,
                     "answer": text.get("value"),
                     "shortUrl": ""
                };

                //Console.debug("params", params);
                var parent = this;
                var load = dojo.hitch( this, function( data ) {

                    //Console.debug(data);
                    parent.loading_hide();
                    var items = [];

                    //Answer: { answer_id:298, answers:sadsa, url:null, short_url:tinyurl, qid:123, color:#67BE9C, provider:null }
                    //id: 166
                    //relative_url: "http://localhost:8080/encuestame/tweetpoll/vote/c5c6855dcc7bc9085e5262eb816a320a"
                    //results: null
                    //short_url: "http://tinyurl.com/nsnkh9x"
                    //tweet_poll_id: 91
                    var answerWidget = new AnswerItem({

                       //  Answer :{
                       //      answerId : data.success.newAnswer.answer.answer_id,
                       //      label: data.success.newAnswer.answer.answers,
                       //      shortUrl : data.success.newAnswer.short_url,
                       //      provider: encuestame.shortUrlProvider[1]
                       // },
                       data: data.success.newAnswer,
                       parentAnswer: this,
                       tweetPollId: this.tweetPollId
                    });
                    this._listAnswers.appendChild( answerWidget.domNode );

                    //Items.push(answerWidget.domNode);
                    this.listItems.push( answerWidget );

                    // This._listAnswers.appendChild(items);
                    text.set( "value", "");
                    dojo.publish("/encuestame/tweetpoll/updatePreview");
                });
                /***
                 * On error.
                 */
                var error = function( error ) {
                  this.loading_hide();
                    dojo.publish("/encuestame/tweetpoll/dialog/error", [ error ] );
                };
                if ( this.tweetPollId !== null ) {
                  this.loading_show();
                    this.getURLService().get( "encuestame.service.list.addAnswer",
                      params,
                      load,
                      error );
                } else {

                  //TODO: replace by EMNE.getMessage();
                  dojo.publish("/encuestame/tweetpoll/dialog/error", [ _ENME.getMessage("e_024") ] );
                }
         },

         /**
          *
          */
         getDialog: function() {
             var dialog = registry.byId("option_" + this.id );
             return dialog;
         }

    });
});
