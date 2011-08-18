dojo.provide("encuestame.org.core.comments.Comments");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.comments.Comment');

dojo.declare(
    "encuestame.org.core.comments.Comments",
    [dijit._Widget, dijit._Templated],{

      templatePath: dojo.moduleUrl("encuestame.org.core.comments", "templates/comments.html"),

      type : "",

      widgetsInTemplate: true,

      /*
       *
       */
      postCreate : function(){
          this._loadComments();
      },

      /*
       * load comments.
       */
      _loadComments : function() {
          var comments = [1,2,3,4,5,6,7,8,9,10];
          dojo.forEach(comments,
                  dojo.hitch(this,function(item) {
                      var widget = new encuestame.org.core.comments.Comment({});
                      this._items.appendChild(widget.domNode);
          }));
      }
});