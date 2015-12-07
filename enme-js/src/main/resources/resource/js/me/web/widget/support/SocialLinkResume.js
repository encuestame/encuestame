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
 *  @module Support
 *  @namespace Widget
 *  @class SocialLinkResume
 */
define( [
         "dojo/_base/declare",
         "dojo/on",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/comments/Comments",
         "me/web/widget/utils/ToggleText",
         "me/web/widget/rated/LikeRate",
         "me/core/enme",
         "dijit/form/DropDownButton",
         "dijit/DropDownMenu",
         "dijit/MenuItem",
         "dijit/Dialog",

         // "dojo/text!me/web/widget/support/templates/commentsModeration.html",
         // "dojo/text!me/web/widget/support/templates/commentResponse.html",
         "dojo/text!me/web/widget/support/templates/socialLinkResume.html" ],
        function(
                declare,
                on,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                Comments,
                ToggleText,
                LikeRate,
                _ENME,
                DropDownButton,
                DropDownMenu,
                MenuItem,
                Dialog,

//                Template,
//                templateResponse,
                template ) {

          return declare( [ Comments, main_widget ], {

          /**
           * The template reference.
           */
          templateString: template,

          /**
           * Define the type of comments source
           * @property
           */
          type: "",

          /**
           * Define the item id.
           * @property
           */
          item_id: null,

         /**
          * I18n message for this widget.
          */
         i18nMessage: {
           social_picker_filter_selected: _ENME.getMessage("social_picker_filter_selected"),
           commons_filter: _ENME.getMessage("commons_filter")
         },

         /**
          *
          * @method successHandler
          */
         successHandler: function( data ) {
            var comments = data.success.comments;
            if ( comments.length > 0 ) {
                dojo.forEach( comments, dojo.hitch( this, function( data, index ) {
                    var widget = new CommentModerationItem(
                      {
                        data: data,
                        type: this.type,
                        item_id: this.item_id
                      });
                  this._comments.appendChild( widget.domNode );
                }) );
            } else {
                this._printNoCommentsText();
            }
         },

         /**
          *
          */
         postCreate: function() {

            //This.inherited(arguments);
         }
    });
});
