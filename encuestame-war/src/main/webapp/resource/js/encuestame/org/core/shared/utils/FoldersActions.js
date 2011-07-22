dojo.provide("encuestame.org.core.shared.utils.FoldersActions");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('dojox.timing');
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.FoldersActions",
    [dijit._Widget, dijit._Templated],{

      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/foldersAction.html"),

      /*
       * folder context.
       */
      folderContext : null,

      /*
       * context.
       */
      _context : ["tweetpoll", "poll", "survey"],

      /*
       * actions.
       */
      _actions : ["create", "update", "move", "list", "remove"],

      /*
       * post create.
       */
      postCreate : function(){
          if (this.folderContext != null) {
              this._loadFolders();
          } else {
              console.error("folderContext is required.");
          }
      },

      /*
       * get service by action.
       */
      _serviceAction : function(type, context){
          if (type == this._action[0]) {
              return encuestame.service.folder.create(context);
         } else if (type == this._action[1]) {
             return encuestame.service.folder.update(context);
         } else if (type == this._action[2]) {
             return encuestame.service.folder.move(context);
         } else if (type == this._action[3]) {
             return encuestame.service.folder.list(context);
         } else if (type == this._action[4]) {
             return encuestame.service.folder.remove(context);
         }
      },

      /*
       * get service by context.
       */
      _getContextUrlService : function(type){
         if (this.folderContext == this._context[0]) {
             return this._service(type, this._context[0]);
         } else if (this.folderContext == this._context[1]) {
             return this._service(type,this._context[1]);
         } else if (this.folderContext == this._context[2]) {
             return this._service(type, this._context[2]);
         }
      },

      /*
       * load folders.
       */
      _loadFolders : function() {
          var i = false;
          var load = dojo.hitch(this, function(data){
              dojo.empty(this._items);
              dojo.forEach(
                      data.success.tweetPolls,
                      dojo.hitch(this, function(data, index) {
                          this.createTweetPollItem(data, i);
                          if(!i) {
                              i = true;
                          }
              }));
          });
          var params = {
              max : this.max,
              start : this.start
              };
          var error = function(error) {
              console.debug("error", error);
          };
          encuestame.service.xhrGet(this._getContextUrlService(this._actions[3]), params, load, error);
      }
});