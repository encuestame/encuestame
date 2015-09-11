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
 *  @class CommentModeration
 */
define([
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
         "dojo/text!me/web/widget/support/templates/commentsModeration.html",
         "dojo/text!me/web/widget/support/templates/commentResponse.html",
         "dojo/text!me/web/widget/support/templates/commentsModerationItem.html" ],
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
                template,
                templateResponse,
                templateItem) {

          /**
           * Comment Moderation Item Widget.
           * @property
           */
          var CommentResponse = declare([
                      _WidgetBase,
                      _TemplatedMixin,
                      main_widget,
                      _WidgetsInTemplateMixin], {

            /**
             * template string
             * @property
             */
            templateString : templateResponse,

            /**
             *
             * @property
             */
            data: null,

            /**
             *
             * @property
             */
            dialog: null,

            /**
             *
             * @method
             */
            postCreate: function() {
                // create a comment response
                this._createCommentResponse.onClick = dojo.hitch(this, function() {
                    var value = this._textarea.value;
                    if (this.isValid(value)) {
                      this.sendComment(this._textarea.value);
                    } else {
                      this.displayError('Comment is empty');
                    }
                });
                // cancel the comment response
                this._cancelCommentResponse.onClick = dojo.hitch(this, function() {
                    this.dialog.hide();
                });
            },

            /**
             * Display a error inside the dialog content.
             * @method displayError
             */
            displayError: function(error) {
              dojo.empty(this._message);
              var widget = this.createAlert(error, "error");
              this._message.appendChild(widget.domNode);
            },

            /**
             * Check if the comment if valid.
             * @method isValid
             */
            isValid: function(value) {
                //TODO: More validations
                return value !== '' ? true: false;
            }

          });

          /**
           * Comment Moderation Item Widget.
           * @property
           */
          var CommentModerationItem = declare([
                      _WidgetBase,
                      _TemplatedMixin,
                      main_widget,
                      _WidgetsInTemplateMixin], {

            /**
             * template string
             * @property
             */
            templateString : templateItem,

            /**
             *
             * @property
             */
            data: null,

            /**
             *
             * @property
             */
            type: null,


            /**
             *
             * @property
             */
            item_id: null,

            /**
             *
             * @property
             */
            limit_comment: 200,

            /**
             *
             * @method
             */
            postCreate: function() {
              this._negative.appendChild(this._createLinkRate(false, true, this.data.dislike_vote));
              this._positive.appendChild(this._createLinkRate(true, false, this.data.likeVote));

              var menu = new DropDownMenu({ style: "display: none;"});

              // set as spam
              var menuItem1 = new MenuItem({
                  label: "SPAM",
                  iconClass:"dijitEditorIcon dijitEditorIconSave",
                  onClick: dojo.hitch(this, function() {
                    var div = this.createAlert("This message will be removed in 24 hours", "warn"),
                    parent = this;
                    this. createConfirmDialog("Dou you want set this comment as SPAM?", div.domNode, function(){
                        console.log('marcado como spam');
                        parent.spamComment();
                    })
                  })
              });
              menu.addChild(menuItem1);

              // remove the comment
              var menuItem2 = new MenuItem({
                  label: "Remove",
                  iconClass:"dijitEditorIcon dijitEditorIconCut",
                  onClick: dojo.hitch(this, function() {
                    var div = this.createAlert("You are removing this comment, it'll not be possible recover it.", "warn"),
                    parent = this;
                    this. createConfirmDialog("Are you sure to remove this comment?", div.domNode, function(){
                        console.log('remove');
                        parent.removeComment();
                    });
                  })
              });
              menu.addChild(menuItem2);

              // response this comment
              var menuItem3 = new MenuItem({
                  label: "Response",
                  iconClass:"dijitEditorIcon dijitEditorIconCut",
                  onClick: dojo.hitch(this, function() {
                    this.responseComment();
                  })
              });
              menu.addChild(menuItem3);

              var button = new DropDownButton({
                  label: "Options",
                  name: "drop_comment_optionw",
                  dropDown: menu
              });
              this._spam.appendChild(button.domNode);

              // publish comment events
              on(this._publish, "click", dojo.hitch(this, function(e) {
                    this.stopEvent(e);
                    var div = this.createAlert("Will be available for all users", "warn"),
                    parent = this;
                    this. createConfirmDialog("This comment will be published, Are you sure?", div.domNode, function(){
                        console.log('publish comment');
                        parent.publishComment();
                    });
              }));
            },

            /**
             *
             * @method commentItemErrorHandler
             */
            commentItemErrorHandler: function(error) {
                console.error('commentItemErrorHandler', error);
            },

            /**
             *
             * @method
             */
            _changeStatus: function(successHandler, status, params) {
                 var load = dojo.hitch(this, function(data) {
                    if ("success" in data) {
                        this.successHandler(data);
                    }
                });
                // error handler
                var error = dojo.hitch(this, function(error) {
                    this.commentItemErrorHandler(error);
                });
                this.getURLService().post(['encuestame.service.comments.admon.status', [this.type, status]], params, load, error , dojo.hitch(this, function() {

                }));
            },

            /**
             * Publish a comment.
             * @method publishComment
             */
            publishComment: function() {
                this._changeStatus(dojo.hitch(this, function(data) {
                  //TODO: remove publish button
                }), 'publish', {
                    id: this.item_id
                });
            },

            /**
             * Set a comment as SPAM.
             * @method spamComment
             */
            spamComment: function() {
                this._changeStatus(dojo.hitch(this, function(data) {
                  //TODO: set opacity class
                }), 'spam', {
                    id: this.item_id
                });
            },

            /**
             * Remove a comment.
             * @method publishComment
             */
            removeComment: function() {
                this._changeStatus(dojo.hitch(this, function(data) {
                  //TODO: remove comment
                }), 'remove', {
                    id: this.item_id
                });
            },

            /**
             * Save a new comment.
             * @property
             */
            _saveComment: function(params, successHandler, errorHandler) {
                var load = dojo.hitch(this, function(data) {
                    if ("success" in data) {
                        successHandler(data);
                    }
                });
                // error handler
                var error = dojo.hitch(this, function(error) {
                    errorHandler(error);
                });
                this.getURLService().post(['encuestame.service.comments.admon.create', [this.type]], params, load, error , dojo.hitch(this, function() {

                }));
            },

            /**
             * Create a response comment view.
             * @method publishComment
             */
            responseComment: function() {
                var widget = new CommentResponse({
                    data: this.data,
                    item_id: this.item_id
                }), parent = this;
                var myDialog = new Dialog({
                    title: "Your comment",
                    content: widget.domNode,
                    style: "width: 800px"
                });
                widget.dialog = myDialog;
                widget.sendComment = dojo.hitch(this, function(value) {
                  var params = {
                      commentId: this.data.id,
                      tweetPollId: this.data.item_id,
                      comment: value
                  };
                  this._saveComment(params, function() {
                      myDialog.hide();
                  }, dojo.hitch(this, function(error) {
                      widget.displayError(parent.getErrorMessage(error));
                  }));

                });
                myDialog.onHide = dojo.hitch(this, function(){
                    myDialog.destroy();
                    widget.destroy();
                });
                myDialog.show();
            },

            /**
              * Create a link rate widget.
              * @param value
              * @returns
              */
             _createLinkRate : function(positive, negative, value) {
               var widget = new LikeRate({
                 positive : positive,
                 value : value,
                 negative : negative
               });
               return widget.domNode;
             }

          });

          return declare([Comments, main_widget], {

          /**
           * The template reference.
           */
          templateString : template,

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
          * i18n message for this widget.
          */
         i18nMessage : {
           social_picker_filter_selected : _ENME.getMessage("social_picker_filter_selected"),
           commons_filter : _ENME.getMessage("commons_filter")
         },

         /**
          *
          * @method successHandler
          */
         successHandler: function(data) {
            var comments = data.success.comments;
            if (comments.length > 0) {
                dojo.forEach(comments, dojo.hitch(this, function(data, index) {
                    var widget = new CommentModerationItem(
                      {
                        data: data,
                        type: this.type,
                        item_id: this.item_id
                      });
                  this._comments.appendChild(widget.domNode);
                }));
            } else {
                this._printNoCommentsText(comments);
            }
         },

          /**
           *
           */
          _printNoCommentsText : function(comments) {
              //TODO
//              if (comments.length === 0) {
//                  var div = dojo.create("div");
//                  dojo.addClass(div, "comments-no-content");
//                  div.innerHTML = _ENME.getMessage("m_022");
//                  this._comments.appendChild(div);
//              }
          },

         /**
          *
          */
         postCreate : function() {
            this.inherited(arguments);
         }
    });
});
