dojo.provide("encuestame.org.core.shared.utils.FoldersActions");

dojo.require("dojo.dnd.Source");

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
       * enable templates.
       */
      widgetsInTemplate: true,

      dropSupport : true,

      /*
       * context.
       */
      _context : ["tweetpoll", "poll", "survey"],

      /*
       * actions.
       */
      _actions : ["create", "update", "move", "list", "remove"],

      _folderSourceWidget : null,

      /*
       * post create.
       */
      postCreate : function(){
          if (this.folderContext != null) {
              this._loadFolders();
          } else {
              console.error("folderContext is required.");
          }
          if (this.dropSupport) {
              this._folderSourceWidget  = new dojo.dnd.Source(this._folders, {
                  accept: [],
                  copyOnly: true,
                  selfCopy : false,
                  selfAccept: false,
                  withHandles : false,
                  autoSync : true,
                  isSource : true
                  });
                  dojo.connect(this._folderSourceWidget, "onDrop", this, this.onDndDropFolder);
             }
          dojo.connect(this._new, "onclick", this, this._addNewFolder);
      },

      onDndDropFolder : function() {
          console.debug("droped");
      },

      _addNewFolder : function(event){
          dojo.stopEvent(event);
          console.debug("new folder");
      },

      /*
       * get service by action.
       */
      _serviceAction : function(type, context){
          if (type == this._actions[0]) {
              return encuestame.service.folder.create(context);
         } else if (type == this._actions[1]) {
             return encuestame.service.folder.update(context);
         } else if (type == this._actions[2]) {
             return encuestame.service.folder.move(context);
         } else if (type == this._actions[3]) {
             return encuestame.service.folder.list(context);
         } else if (type == this._actions[4]) {
             return encuestame.service.folder.remove(context);
         }
      },

      /*
       * get service by context.
       */
      _getContextUrlService : function(type){
         if (this.folderContext == this._context[0]) {
             return this._serviceAction(type, this._context[0]);
         } else if (this.folderContext == this._context[1]) {
             return this._serviceAction(type,this._context[1]);
         } else if (this.folderContext == this._context[2]) {
             return this._serviceAction(type, this._context[2]);
         }
      },

      /*
       * load folders.
       */
      _loadFolders : function() {
          var i = false;
          var load = dojo.hitch(this, function(data){
              dojo.empty(this._folders);
              var folderArray = [];
              dojo.forEach(
                      data.success.folders,
                      dojo.hitch(this, function(data, index) {
                          var node = this._createFolder(data);
                          folderArray.push(node.domNode);
                          //this._folders.appendChild(node.domNode);
              }));
              this._folderSourceWidget.insertNodes(false, folderArray);
          });
          var params = {
              max : this.max,
              start : this.start
              };
          var error = function(error) {
              console.debug("error", error);
          };
          encuestame.service.xhrGet(this._getContextUrlService(this._actions[3]), params, load, error);
      },

      /*
       *
       */
      _createFolder : function(data) {
          var folder = new encuestame.org.core.shared.utils.FoldersItemAction({folderId: data.id, name : data.name});
          return folder;
      }
});

dojo.declare(
        "encuestame.org.core.shared.utils.FoldersItemAction",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/foldersItemAction.html"),

        widgetsInTemplate: true,

        name : "",

        folderId : null,

        postCreate : function(){

        }

});