dojo.provide("encuestame.org.core.comments.Comment");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.AccountPicture');

dojo.declare(
        "encuestame.org.core.comments.Comment",
        [dijit._Widget, dijit._Templated],{

          templatePath: dojo.moduleUrl("encuestame.org.core.comments", "templates/comment.html"),

          widgetsInTemplate: true,

          type : "",

          /**
           *
           */
          postCreate : function() {
              this.loadComments({});
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
          loadComments : function(params) {
                  var load = dojo.hitch(this, function(data) {
                      //dojo.empty(this._items);
                      var itemArray = [];
                      dojo.forEach(
                              data.success.tweetPolls,
                              dojo.hitch(this, function(data, index) {
                                 console.info("comment", data);
                      }));
                  });
                  var error = function(error) {
                      console.debug("error", error);
                  };
                  encuestame.service.xhrGet(encuestame.service.comments.list, params, load, error);
          }
    });