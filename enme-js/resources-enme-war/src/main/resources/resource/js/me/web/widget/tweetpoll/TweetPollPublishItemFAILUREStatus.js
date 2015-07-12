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
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.146
 *  @module TweetPoll
 *  @namespace Widget
 *  @class TweetPollPublishItemFAILUREStatus
 */
define([
        "dojo/_base/declare",
        "dojo/dom-construct",
         "me/core/enme",
         "me/core/URLServices",
         "me/web/widget/tweetpoll/TweetPollPublishItemAbstractStatus",
         "dijit/form/TimeTextBox",
         "dijit/form/DateTextBox",
         "dijit/form/Button",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollPublishItemFAILUREStatus.html" ],
        function(
                declare,
                domConstruct,
                _ENME,
                URLServices,
                TweetPollPublishItemAbstractStatus,
                TimeTextBox,
                DateTextBox,
                Button,
                template) {
            return declare([TweetPollPublishItemAbstractStatus], {

        /**
        * Template string.
        * @property
        */
        templateString : template,

        /**
         * Store all TweetPoll widget data
         * @param tweetPollWidget
         */
        tweetPollWidget : null,


        _attempts : 5,

        /**
         * i18N Message.
         * @property
         */
        i18nMessage : {
            commons_failure : _ENME.getMessage("commons_failure", "FAILURE"),
            button_try_later : _ENME.getMessage("button_try_later", ""),
            button_ignore : _ENME.getMessage("button_ignore", ""),
            button_try_again : _ENME.getMessage("button_try_again", ""),
            pubication_failure_status : _ENME.getMessage("pubication_failure_status", "pubication_failure_status")
        },


        /**
        * Post Create life cycle.
        * @method postCreate
        */
        postCreate : function () {
         this.inherited(arguments);
         var that = this;
         var date = new Date();
         date.setDate(date.getDate() + 365);
         var _max = moment(date);
         this._calendar.constraints = {
            min : moment(),
            datePattern: 'dd-MMM-yyyy',
            max : _max
         };
         if (this._ignore) {
               this._ignore.onClick = dojo.hitch(this, function() {
                  dojo.destroy(this.parentStatusWidget.domNode);
              });
           }

         // trigger the try to publish later
         this._try_later.onClick = dojo.hitch(this, function() {
              dojo.removeClass(this._options_scheduled, "hidden");
         });

         // trigger on confirm the scheduled date
         this._confirm.onClick = dojo.hitch(this, function() {
              this._confirmExecute();
         });

         // triggered on cancel the "try later"  action
         this._cancel_scheduled.onClick = dojo.hitch(this, function() {
              dojo.addClass(this._options_scheduled, "hidden");
         });


         // trigger the try to publish again
         this._try.onClick = dojo.hitch(this, function() {
                var moveAttemps = dojo.hitch(this, function() {
                    dojo.addClass(this._att, "badge badge-important");
                    this._att.innerHTML = this._attempts;
                });
                if (this._attempts > 0) {
                    var params = {
                                 "id" : this.tweetPollWidget.tweetPoll.tweetPollId,
                                 "twitterAccounts" : [this.metadata.social_account_id]
                   };
                   var load = dojo.hitch(this, function(data) {
                      var __status = data.success.socialPublish[0].status_tweet;
                      if (__status === 'FAILED') {
                          moveAttemps();
                      } else if (__status === 'SUCCESS') {
                           //  create a success status
                          var _node = this.parentStatusWidget.convertToSuccess(data.success.socialPublish[0]);
                          domConstruct.place(_node, this.parentStatusWidget._detailStatus);
                          // destroy the current node
                          dojo.addClass(this.domNode, "hidden");
                      } else {
                          console.error("not defined status", __status);
                      }
                   });
                   var error = dojo.hitch(this, function(error) {
                     this.errorMessage(error.response.data ? error.response.data.error.message : _ENME.getMessage("tp_publish_error", "Service unavailable, please try again"));
                     moveAttemps();
                   });
                   this._attempts --;
                   URLServices.post('encuestame.service.list.publishTweetPoll', params, load, error , dojo.hitch(this, function() {

                   }), true);
              } else {
                this.removeTryAgainButton();
              }
          });
        },

        /**
         *
         * @method _confirm
         */
        _confirmExecute: function() {
              var convertDate = dojo.hitch(this, function() {
                  var time = moment(this._time.get('value')).format("hh:mm:ss");
                  var date = moment(this._calendar.get('value')).format("MM-DD-YYYY");
                  var merged_date = new Date(date + " " + time);
                  return merged_date.getTime();
              });
              if (this._time.isValid() && this._calendar.isValid()) {
                    var params = {
                      "publish_response" : this.metadata,
                      "scheduled_date" :  convertDate()
                    };
                    var load = dojo.hitch(this, function(data) {
                         //  create a success status
                         var _node = this.parentStatusWidget.convertToSuccess(data.success.schedulded_item, 'scheduled');
                         domConstruct.place(_node, this.parentStatusWidget._detailStatus);
                         // destroy the current node
                        dojo.addClass(this.domNode, "hidden");
                    });
                    var error = dojo.hitch(this, function(error) {
                       this.errorMessage(error.response.data ? error.response.data.error.message : _ENME.getMessage("tp_publish_error", "Is not possible to schedule, please try again"));
                    });
                    var publish_date = new Date();
                    URLServices.post(['encuestame.service.list.scheduleTweetPoll', [this.metadata.type_item, this.metadata.id]], params, load, error , dojo.hitch(this, function() {

                    }), true);
                } else {
                    this.errorMessage(_ENME.getMessage("scheduled_error_confirm", "Your scheduled date is not valid"));
                }
        },


        /**
         * Remove the try again button;
         * @method removeTryAgainButton
         */
        removeTryAgainButton: function() {
            dojo.destroy(this._try.domNode);
            dojo.destroy(this._att);
        }
    });
});