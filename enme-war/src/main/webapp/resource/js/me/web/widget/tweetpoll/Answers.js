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

define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dojo/dnd/Source",
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
                Source,
                Button,
                registry,
                main_widget,
                TweetPollCore,
                AnswerItem,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, TweetPollCore, _WidgetsInTemplateMixin], {

         // template string.
         templateString : template,

          /**
          * list of items.
          */
         listItems : [],

         /**
          * default short url provider.
          */
         _provider : encuestame.shortUrlProvider,

         /**
          * button widget.
          */
         buttonWidget : null,

         /**
          * default label.
          */
         label : "Answer",

         /**
          * tweetpollId.
          */
         tweetPollId : null,

         /**
          * Answer source.
          */
         answerSource : null,

         /**
          * Flag to control bloked.
          */
         _blocked : false,

         /**
          * i18n Messages
          */
         i18nMessage : {
           add_button : _ENME.getMessage("button_add")
         },

         /***
          * Post create lifecycle.
          */
         postCreate: function() {
              this.buttonWidget = new Button({
                  label: this.i18nMessage.add_button,
                  onClick: dojo.hitch(this, function(event) {
                      dojo.stopEvent(event);
                      this.addAnswer();
                  })
              },
              this._addButton);
              this.answerSource  = new Source(this._listAnswers, {
                  accept: [],
                  copyOnly: false,
                  selfCopy : false,
                  selfAccept: true,
                  withHandles : false,
                  autoSync : true,
                  isSource : true
               });
              dojo.connect(this.answerSource, "onDrop", this, this.onDrop);
              // on key up.
              dojo.connect(this._suggest, "onKeyUp", dojo.hitch(this, function(e) {
                  if (dojo.keys.ENTER == e.keyCode) {
                      this.addAnswer();
                  }
              }));
              this.enableBlockTweetPollOnProcess();
         },

         /**
          *
          */
         block : function() {
             this.buttonWidget.disabled = true;
             registry.byId("answerAddText").disabled = true;
         },

         /**
          *
          */
         unblock : function() {
             this.buttonWidget.disabled = false;
             registry.byId("answerAddText").disabled = false;
         },

         /**
          *
          */
         getAnswers : function(){
             var array = [];
              dojo.forEach(this.listItems,
                    dojo.hitch(this,function(item) {
                    array.push({ value : item.getAnswerText()});
                    }));
             return array;
         },

         /**
          *
          */
         getAnswersId : function() {
             var array = [];
              dojo.forEach(this.listItems,
                    dojo.hitch(this,function(item) {
                    array.push(item.answer.answerId);
                    }));
             //console.debug("getAnswers", array);
             return array;
         },

         /**
          *
          */
         onDrop : function() {
                 // check out
              if (dojo.dnd.manager().target !== this.answerSource) {
                  return;
              }

              //TODO @deprecated http://livedocs.dojotoolkit.org/dojo/dnd#source
              if (dojo.dnd.manager().target == dojo.dnd.manager().source) {
                   //var newOrder = [];
                   this.listItems = [];
                   dojo.forEach
                   (this.answerSource.getAllNodes(),
                       dojo.hitch(this,function(item) {
                           var widget = registry.byId(item.id);
                           this.listItems.push(widget);
                       }));
                   dojo.publish("/encuestame/tweetpoll/updatePreview");
               }
         },

         /***
          * start the process to save the answer.
          */
         addAnswer : function() {
             var text = registry.byId(this._suggest);
             var params = {
                     "id" : this.tweetPollId,
                     "answer" : text.get("value"),
                     "shortUrl" : encuestame.shortUrlProvider[1].code
                };
                //console.debug("params", params);
                var load = dojo.hitch(this, function(data) {
                    //console.debug(data);
                  this.loading_hide();
                    var items = [];
                    var answerWidget = new AnswerItem({
                        answer :{
                            answerId : data.success.newAnswer.answer.answer_id,
                            label: data.success.newAnswer.answer.answers,
                            shortUrl : data.success.newAnswer.short_url,
                            provider: encuestame.shortUrlProvider[1]
                       },
                       parentAnswer : this,
                       tweetPollId : this.tweetPollId
                    });
                    items.push(answerWidget.domNode);
                    this.listItems.push(answerWidget);
                    this.answerSource.insertNodes(false, items);
                    text.set('value', "");
                    dojo.publish("/encuestame/tweetpoll/updatePreview");
                });
                /***
                 * On error.
                 */
                var error = function(error) {
                  this.loading_hide();
                    dojo.publish("/encuestame/tweetpoll/dialog/error", [error]);
                };
                if (this.tweetPollId != null) {
                  this.loading_show();
                    this.getURLService().get('encuestame.service.list.addAnswer',
                      params,
                      load,
                      error);
                } else {
                  //TODO: replace by EMNE.getMessage();
                    dojo.publish("/encuestame/tweetpoll/dialog/error", [_ENME.getMessage("e_024")]);
                }
         },

         /**
          *
          */
         getDialog : function() {
             var dialog = registry.byId("option_"+this.id);
             return dialog;
         }

    });
});