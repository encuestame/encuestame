dojo.provide("encuestame.org.core.shared.utils.FoldersActions");

dojo.require("dojo.dnd.Source");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.InlineEditBox");
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
          dojo.connect(this._new, "onclick", this, this._addNewFolder);
      },

      _addNewFolder : function(event){
          dojo.stopEvent(event);
          var node = this._createFolder({folderId: null, name : "Add new name."});
          this._folders.appendChild(node.domNode);
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
              dojo.forEach(
                      data.success.folders,
                      dojo.hitch(this, function(data, index) {
                          var node = this._createFolder(data);
                          this._folders.appendChild(node.domNode);
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
      },

      /*
       *
       */
      _createFolder : function(data) {
          var folder = new encuestame.org.core.shared.utils.FoldersItemAction(
                  {folderId: data.id, name : data.name, folderParentWidget: this});
          return folder;
      }
});

dojo.declare(
        "encuestame.org.core.shared.utils.FoldersItemAction",
        [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/foldersItemAction.html"),

        widgetsInTemplate: true,

        name : "",

        folderParentWidget: null,

        /*
         * enable drop support. _getContextUrlService
         */
        dropSupport : true,

        folderId : null,

        _accept : ["tweetpoll", "poll", "survey"],

        _foldersourceWidget : null,

        postCreate : function(){
            if (this.dropSupport) {
                this._folderSourceWidget  = new dojo.dnd.Target(this._folder, {
                    accept: this._accept
                    });
                    dojo.connect(this._folderSourceWidget, "onDndDrop", dojo.hitch(this, this.onDndDropFolder));
               };
               var name = dijit.byId(this._name);
               name.onChange = dojo.hitch(this, function(){
                   if (this.folderId == null) {
                       this.folderId = this._create(name.get('value'));
                   } else {
                       this._update(name.get('value'));
                   }
               });
               console.debug("widget inline", name);
               dojo.style(name.domNode, "max-width:", "60px");
        },

        /*
         * add folder.
         */
        _create : function(name) {
            console.debug("updated name to", name);
            var id = null;
            var i = false;
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                console.info("updated name");
            });
            var params = {
                name : name
                };
            var error = function(error) {
                console.debug("error", error);
            };
                encuestame.service.xhrGet(this.folderParentWidget
            ._getContextUrlService(this.folderParentWidget._actions[0]),
            params, load, error);
            return id;
        },

        /*
         * update folder.
         */
        _update : function(name) {
            console.debug("updated name to", name);
            var i = false;
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                console.info("updated name");
            });
            var params = {
                folderId :this.folderId,
                folderName : name
                };
            var error = function(error) {
                console.debug("error", error);
            };
                encuestame.service.xhrGet(this.folderParentWidget
            ._getContextUrlService(this.folderParentWidget._actions[1]),
            params, load, error);
        },

        /*
         * add item.
         */
        _addItem : function(id) {
            var i = false;
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                console.info("Item Added");
            });
            var params = {
                folderId :this.folderId,
                itemId : id
                };
            var error = function(error) {
                console.debug("error", error);
            };
                encuestame.service.xhrGet(this.folderParentWidget
            ._getContextUrlService(this.folderParentWidget._actions[2]),
            params, load, error);
        },

        /*
         * on drop on folder.
         */
        onDndDropFolder : function(source, nodes, copy, target) {
                dojo.forEach(dojo.query(".dojoDndItemSelected"), function(item){
                    dojo.removeClass(item, "dojoDndItemSelected");
                });
                dojo.forEach(dojo.query(".dojoDndItemAnchor"), function(item){
                    dojo.removeClass(item, "dojoDndItemAnchor");
                });
                if(dojo.dnd.manager().target !== this._folderSourceWidget){
                    return;
                }
                if(dojo.dnd.manager().target == dojo.dnd.manager().source){
                    console.debug("same");
                } else {
                    dojo.forEach(this._folderSourceWidget.getSelectedNodes(), dojo.hitch(this, function(item) {
                        console.debug("item", item);
                        var tweetPollId = item.getAttribute('tweetpollId');
                        var type = item.getAttribute('dndtype');
                        console.debug("tweetpollId", tweetPollId);
                        console.debug("type", type);
                        this._addItem(parseInt(tweetPollId));
                        dojo.destroy(item);
                    }));
                }
        }

});