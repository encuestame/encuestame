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
 *  @module TweetPoll
 *  @namespace Widgets
 *  @class PublishSocialStatus
 */
define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollPublishItemStatus",
         "me/core/enme",
         "dijit/registry",
         "dojo/text!me/web/widget/publish/templates/publish_social_status.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TweetPollPublishItemStatus,
                _ENME,
                registry,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

          /**
           * The list of social accounts.
           */
          socialPublish : [],

          /**
           *
           * @property
           */
          socialAccounts : [],

          /**
           * i18N Message.
           */
          i18nMessage : {
            button_finish : _ENME.getMessage("button_finish", "Close")
          },

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
              if (this.socialPublish) {
                  dojo.forEach(this.socialPublish,
                          dojo.hitch(this,function(tweet) {
                              var row = this._buildTweetProcessView(tweet);
                              if (row) {
                                  this._container.appendChild(row);
                              }
                 }));
                 //dojo.removeClass(this._closeWrapper, "hidden");
              } else {
                  this.errorMesage("data tweet process is empty");
              }
          },

          /**
           * Display proccessing message.
           */
          _showProcessingMessage : function() {
            dojo.empty(this._message);
              var message = dojo.doc.createElement("div");
              dojo.addClass(this._message, "defaultDisplayBlock");
              dojo.removeClass(this._message, "defaultDisplayHide");
              message.innerHTML = _ENME.getMessage("pubication_inprocess_status");
              this._message.appendChild(message);
          },

          /**
           * Hidde the processing message.
           */
          _hideProcessingMessage : function() {
            dojo.empty(this._message);
              dojo.removeClass(this._message, "defaultDisplayBlock");
              dojo.addClass(this._message, "defaultDisplayHide");
              dojo.empty(this._message);
          },

          /**
           * Process date published.
           * @param socialPublish
           */
          process: function(socialPublish) {

          },

          /**
           * search by id the complete info for selected social account.
           */
          _getSocialAccountWidget : function(id) {
              //this._socialAccounts
              var selected = null;
              dojo.forEach(this.socialAccounts,
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
          _buildTweetProcessView : function(data) {
                return this._createStatusTweet(data);
          },

          /**
           * Create status tweet.
           */
          _createStatusTweet : function(data){
              var widget = new TweetPollPublishItemStatus(
                      {
                          data : data,
                          socialAccount : this._getSocialAccountWidget(data.social_account_id)
                      });
              return widget.domNode;
          }
    });
});