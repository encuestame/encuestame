define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/folder/FolderOperations",
         "me/web/widget/folder/FoldersItemAction",
         "dijit/InlineEditBox",
         "me/core/enme",
         "dojo/dnd/Target",
         "dijit/registry",
         "dojo/text!me/web/widget/folder/templates/foldersItemAction.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                FolderOperations,
                FoldersItemAction,
                InlineEditBox,
                _ENME,
                Target,
                registry,
                template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, FolderOperations, _WidgetsInTemplateMixin], {

          // template string.
          templateString : template,


          name : "",

          folderParentWidget: null,

          /*
           * enable drop support. _getContextUrlService
           */
          dropSupport : true,

          folderId : null,

          _accept : ["tweetpoll", "poll", "survey"],

          _foldersourceWidget : null,

          /*
           * post create cycle.
           */
          postCreate : function(){
              if (this.dropSupport) {
                  this._folderSourceWidget  = new Target(this._folder, {
                      accept: this._accept
                      });
                      dojo.connect(this._folderSourceWidget, "onDndDrop", dojo.hitch(this, this.onDndDropFolder));
                 };
                 var name = this._name;
                 if (name != null) {
                     /*
                      * TODO: on change event issues, review.
                      */
                     name.onChange = dojo.hitch(this, function() {
                         if (this.folderId == null) {
                             this.folderId = this._create(name.get('value'));
                         } else {
                             this._update(name.get('value'));
                         }
                     });
                 } else {
                     console.error("inline error");
                 }
                 this.inherited(arguments);
          },

          /*
           * add folder.
           */
          _create : function(name) {
              console.debug("updated name to;", name);
              var id = null;
              var load = dojo.hitch(this, function(data){
                  console.debug("data", data);
                  console.info("updated name");
              });
              var params = {
                  name : name
                  };
              this._callFolderService(load, params, this.getAction("create"));
              return id;
          },

          /*
           * update folder.
           */
          _update : function(name) {
              console.debug("updated name to", name);
              var load = dojo.hitch(this, function(data){
                  console.debug("data", data);
                  console.info("updated name");
              });
              var params = {
                  folderId :this.folderId,
                  folderName : name
                  };
              this._callFolderService(load, params, this.getAction("update"));
          },

          /*
           * add item.
           */
          _addItem : function(id) {
              var load = function(data){
                  console.debug("data", data);
                  console.info("Item Added");
              };
              var params = {
                  folderId :this.folderId,
                  itemId : id
                  };
             this._callFolderService(load, params, this.getAction("move"));
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
});