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

define( [
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
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

         // Template string.
         templateString: template,

           /**
           * Tweetpoll Id reference.
           */
          tweetPollId: null,

          /**
           * Provider list.
           */
          _provider: null,

          /**
           * Answer data.
           */
          data: {},

          /**
           * Parent answer.
           */
          parentAnswer: null,

          /**
           * Loading reference.
           */
          loadingRef: null,

          /**
           * @method constructor.
           */
          postCreate: function() {
              this._provider = _ENME.shortUrlProvider;
              if ( this._item ) {

                  // Var answer = dojo.doc.createElement("div");
                  // answer.innerHTML = this.data.answer.answers;
                  // dojo.addClass(answer, "answerItemTitle");
                  // dojo.addClass(answer, "wrap");
                  // var url = dojo.doc.createElement("div");
                  // //url
                  // var urlA = dojo.doc.createElement("a");
                  // urlA.innerHTML = this.data.short_url;
                  // urlA.href = "#";
                  // //urlA.target = "_blank";
                  // url.appendChild(urlA);
                  // dojo.addClass(url, "answerItemShortUrl");
                  // dojo.addClass(url, "wrap");
                  var menuWidget = new OptionMenu({
                      _classReplace: "hidden",
                      menu_items: [{
                          label: _ENME.getMessage("button_remove", "Remove"),
                          action: dojo.hitch( this, this._removeAnswer ) }
                  ] });
                  this._options.appendChild( menuWidget.domNode );

                  //This._item.appendChild(answer);
                  //this._item.appendChild(url);
              }
          },

          /**
           * Display or short url
           */
          editShortUrl: function( event ) {
              dojo.stopEvent( event );
          },

          /***
           * Start the process to remove this answer.
           */
          _removeAnswer: function() {
              /**
               * Parameters.
               */
            var params = {
                "id": this.data.tweet_poll_id,
                "answerId": this.data.answer.answer_id
              };
              /**
               * On success
               */
              var load = dojo.hitch( this, function( data ) {
                this.loading_hide();
                  var i = dojo.indexOf( this.parentAnswer.listItems, this );
                  console.debug("removing answer", i );
                  this.parentAnswer.listItems.splice( i, 1 );
                  dojo.publish("/encuestame/tweetpoll/updatePreview");
                  dojo.destroy( this.domNode, true );
              });

              /**
               * On error.
               */
              var error = function( error ) {
                this.loading_hide();
                  dojo.publish("/encuestame/tweetpoll/dialog/error", [ error ] );
              };
              this.loading_show();
              this.getURLService().get("encuestame.service.list.removeAnswer", params, load, error, dojo.hitch( this, function() {

              }) );
          },

          /**
           * Get the answer text.
           * @method getAnswerText
           */
          getAnswerText: function() {
              var answer = this.data.answer.answers + " " + this.data.short_url;
              return answer;
          }

    });
});
