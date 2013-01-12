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
 *  @module Comment
 *  @namespace Widget
 *  @class Comments
 */
define([
         "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/comments/Comment",
     "me/core/enme",
     "dojo/text!me/web/widget/comments/templates/comments.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    comment,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

     // template string.
     templateString : template,

      type : "",

       item_id : null,


        /*
         *
         */
        postCreate : function() {
            if (this.item_id) {
                this._loadComments({});
            }
        },


        /*
         * load all comments.
            error: {},
            success: {
              comments: [
                {
                  id: 1,
                  comment: "xxxxxx",
                  created_at: "2011-06-29",
                  likeVote: 432,
                  dislike_vote: 31,
                  item_id: 101,
                  type: "TWEETPOLL",
                  uid: 4,
                  parent_id: null
                }
              ]
            }
         */
        _loadComments : function() {
                var load = dojo.hitch(this, function(data) {
                    //console.info("comments", data);
                    if("success" in data) {
                        var comments = data.success.comments;
                        if (comments.length > 0) {
                            dojo.forEach(comments, dojo.hitch(this, function(data, index) {
                                var widget = new comment({data : data});
                                this._items.appendChild(widget.domNode);
                            }));
                        } else {
                            this._printNoCommentsText();
                        }
                    }
                });
                var error = function(error) {
                    console.error("error", error);
                };
                this.getURLService().get(['encuestame.service.comments.list', [this.type]], { id : this.item_id, max: 10}, load, error , dojo.hitch(this, function() {

                }));
        },

        /*
         *
         */
        _printNoCommentsText : function() {
            console.info("No comments");
            if (this._items) {
                var div = dojo.create("div");
                dojo.addClass(div, "comments-no-content");
                div.innerHTML = encuestame.constants.messageCodes["022"];
                //dojo.place(this._items. div);
                this._items.appendChild(div);
            }
        }

  });
});