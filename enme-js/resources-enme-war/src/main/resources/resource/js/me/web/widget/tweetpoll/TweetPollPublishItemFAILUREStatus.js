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
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/Button",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollPublishItemFAILUREStatus.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Button,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          /**
           * Template string.
           * @property
           */
          templateString : template,

          /**
           * Metadaat
           * @property
           */
          metadata : null,

          /**
           * Parent status widget.
           * @property
           */
          parentStatusWidget : null,


          /**
           *
           * @method
           */
          postCreate : function () {
                if (this._ignore) {
                   this._ignore.onClick = dojo.hitch(this, function() {
                      dojo.destroy(this.parentStatusWidget.domNode);
                  });
                }
          },

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
          *
          * @param events
          */
         _scheduleTweet : function(events) {}
    });
});