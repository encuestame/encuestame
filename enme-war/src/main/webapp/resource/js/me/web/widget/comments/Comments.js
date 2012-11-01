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
            encuestame.service.xhrGet(
                this.getURLService().service("encuestame.service.comments.list", [this.type]),
                { id : this.item_id,
                  max: 10
                  }, load, error);
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
//dojo.provide("encuestame.org.core.comments.Comments");
//
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//dojo.require("dijit.form.Button");
//dojo.require("dijit.form.TextBox");
//dojo.require('encuestame.org.core.commons');
//dojo.require('encuestame.org.core.comments.Comment');
//
///**
// * Comments Widgets.
// */
//dojo.declare(
//    "encuestame.org.core.comments.Comments",
//    [dijit._Widget, dijit._Templated],{
//
//      templatePath: dojo.moduleUrl("encuestame.org.core.comments", "templates/comments.html"),
//
//      type : "",
//
//      item_id : null,
//
//      widgetsInTemplate: true,
//
//      /*
//       *
//       */
//      postCreate : function() {
//          if (this.item_id) {
//              this._loadComments({});
//          }
//      },
//
//
//      /*
//       * load all comments.
//          error: {},
//          success: {
//            comments: [
//              {
//                id: 1,
//                comment: "xxxxxx",
//                created_at: "2011-06-29",
//                likeVote: 432,
//                dislike_vote: 31,
//                item_id: 101,
//                type: "TWEETPOLL",
//                uid: 4,
//                parent_id: null
//              }
//            ]
//          }
//       */
//      _loadComments : function() {
//              var load = dojo.hitch(this, function(data) {
//                  console.info("comments", data);
//                  if("success" in data) {
//                      var comments = data.success.comments;
//                      if (comments.length > 0) {
//                          dojo.forEach(comments, dojo.hitch(this, function(data, index) {
//                              var widget = new encuestame.org.core.comments.Comment({data : data});
//                              this._items.appendChild(widget.domNode);
//                          }));
//                      } else {
//                          this._printNoCommentsText();
//                      }
//                  }
//              });
//              var error = function(error) {
//                  console.error("error", error);
//              };
//          encuestame.service.xhrGet(encuestame.service.comments.list(this.type), { id : this.item_id, max: 10}, load, error);
//      },
//
//      /*
//       *
//       */
//      _printNoCommentsText : function() {
//          console.info("No comments");
//          if (this._items) {
//              var div = dojo.create("div");
//              dojo.addClass(div, "comments-no-content");
//              div.innerHTML = encuestame.constants.messageCodes["022"];
//              //dojo.place(this._items. div);
//              this._items.appendChild(div);
//          }
//      }
//});
//
///**
// *
// */
//dojo.declare(
//        "encuestame.org.core.comments.CommentForm",
//        [dijit._Widget, dijit._Templated],{
//
//          templatePath: dojo.moduleUrl("encuestame.org.core.comments", "templates/commentForm.html")
//
//
//        });