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

/**
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module TweetPoll.HashtagItem
 *  @namespace Widgets
 *  @class HashtagItem
 */

define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/HashTagsSuggest",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/hashtagItem.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                HashTagsSuggest,
                _ENME,
                 template) {
            return declare([ _WidgetBase,
                             _TemplatedMixin,
                             main_widget,
                             _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

          /***
          * the body of hashtag.
          */
           data : null,

           /***
            * the label of the hashtag.
            */
           label : null,

           /***
            * Parent widget reference.
            */
           parentWidget : null,

           /***
            *
            */
           postCreate : function() {
               //console.debug("new HashTag", this.label);
           },

           /***
            *
            * @param event
            */
           _options : function(event){
               var dialog = this.parentWidget.getDialog();
               dialog.item = this;
               dialog.show();
           }

    });
});
