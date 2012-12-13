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

/****
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module TweetPoll.AnswerItem
 *  @namespace Widgets
 *  @class AnswerItem
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
         "me/web/widget/menu/OptionMenu",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/answerItem.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Source,
                Button,
                registry,
                main_widget,
                OptionMenu,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

         // template string.
         templateString : template,

           /**
           * tweetpoll Id reference.
           */
          tweetPollId : null,

          /**
           * provider list.
           */
          _provider : null,

          /**
           * answer data.
           */
          answer : {},

          /**
           * parent answer.
           */
          parentAnswer : null,

          /**
           * loading reference.
           */
          loadingRef : null,

          /**
           * @method constructor.
           */
          postCreate : function() {
              this._provider = encuestame.shortUrlProvider;
              if (this._item) {
                  var answer = dojo.doc.createElement("div");
                  answer.innerHTML = this.answer.label;
                  dojo.addClass(answer, "answerItemTitle");
                  dojo.addClass(answer, "wrap");
                  var url = dojo.doc.createElement("div");
                  //url
                  var urlA = dojo.doc.createElement("a");
                  urlA.innerHTML = this.answer.shortUrl;
                  urlA.href = "#";
                  //urlA.target = "_blank";
                  url.appendChild(urlA);
                  dojo.addClass(url, "answerItemShortUrl");
                  dojo.addClass(url, "wrap");

                  var menuWidget = new OptionMenu({
                      _classReplace : "hidden",
                      menu_items : [{
                          label : _ENME.getMessage("button_remove", "Remove"),
                          action : dojo.hitch(this, this._removeAnswer)}
                  ]});
                  this._options.appendChild(menuWidget.domNode);
                  this._item.appendChild(answer);
                  this._item.appendChild(url);
              }
          },

          /**
           * display or short url
           */
          editShortUrl : function(event) {
              dojo.stopEvent(event);
              //console.debug(event);
          },

          /***
           * start the process to remove this answer.
           */
          _removeAnswer : function() {
              /**
               * parameters.
               */
            var params = {
                       "id" : this.tweetPollId,
                       "answerId" : this.answer.answerId
              };
              /**
               * on success
               */
              var load = dojo.hitch(this, function(data) {
                this.loading_hide();
                  var i = dojo.indexOf(this.parentAnswer.listItems, this);
                  console.debug("removing answer", i);
                  this.parentAnswer.listItems.splice(i, 1);
                  dojo.publish("/encuestame/tweetpoll/updatePreview");
                  dojo.destroy(this.domNode, true);
              });

              /**
               * on error.
               */
              var error = function(error) {
                this.loading_hide();
                  dojo.publish("/encuestame/tweetpoll/dialog/error", [error]);
              };
              this.loading_show();
              encuestame.service.xhrGet(
                  this.getURLService().service('encuestame.service.list.removeAnswer'), params, load, error);
          },

          /**
           * answer text.
           */
          getAnswerText: function() {
              var answer = this.answer.label + " " + this.answer.shortUrl;
              return answer;
          }

    });
});
//dojo.provide("encuestame.org.core.commons.tweetPoll.Answers");
//
//dojo.require("dojo.dnd.Source");
//dojo.require("dijit.form.Form");
//dojo.require("dijit.form.Textarea");
//dojo.require("dijit.form.Button");
//dojo.require("dijit.Dialog");
//dojo.require("dijit.form.TextBox");
//dojo.require("encuestame.org.core.shared.utils.OptionMenu");
//dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
//dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollCore");
//
///***
// * Widget to list of answers.
// */
//dojo.declare(
//    "encuestame.org.core.commons.tweetPoll.Answers",
//    [encuestame.org.main.EnmeMainLayoutWidget, encuestame.org.core.commons.tweetPoll.TweetPollCore],{
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/answer.html"),
//

//);
//
///***
// * Widget to represent an answer item.
// */
//dojo.declare(
//        "encuestame.org.core.commons.tweetPoll.AnswerItem",
//        [encuestame.org.main.EnmeMainLayoutWidget],{
//            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/answerItem.html"),
//
//

//});