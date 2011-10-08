dojo.provide("encuestame.org.core.comments.Comments");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.comments.Comment');

/**
 * Comments Widgets.
 */
dojo.declare(
    "encuestame.org.core.comments.Comments",
    [dijit._Widget, dijit._Templated],{

      templatePath: dojo.moduleUrl("encuestame.org.core.comments", "templates/comments.html"),

      type : "",

      item_id : null,

      widgetsInTemplate: true,

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
                  console.info("comments", data);
                  if("success" in data) {
                      var comments = data.success.comments;
                      if (comments.length > 0) {
                          dojo.forEach(comments, dojo.hitch(this, function(data, index) {
                              var widget = new encuestame.org.core.comments.Comment({data : data});
                              this._items.appendChild(widget.domNode);
                          }));
                      } else {
                          this._printNoCommentsText();
                      }
                  }
              });
              var error = function(error) {
                  console.debug("error", error);
              };
          encuestame.service.xhrGet(encuestame.service.comments.list("tweetpoll"), { id : this.item_id}, load, error);
      },

      /*
       *
       */
      _printNoCommentsText : function() {
          console.info("No comments");
          dojo.addClass(this._comment_wrapper, "");
      }
});

/**
 *
 */
dojo.declare(
        "encuestame.org.core.comments.CommentForm",
        [dijit._Widget, dijit._Templated],{

          templatePath: dojo.moduleUrl("encuestame.org.core.comments", "templates/commentForm.html"),


        });