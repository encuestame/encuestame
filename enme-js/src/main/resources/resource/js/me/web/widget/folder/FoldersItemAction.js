define([
 "dojo/_base/declare",
 "dojo/_base/lang",
 "dojo/_base/array",
 "dojo/on",
 "dojo/topic",
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
    lang,
    array,
    on,
    topic,
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

   counter: 0,

  folderParentWidget: null,

  /**
   * enable drop support. _getContextUrlService
   */
  dropSupport : false,

  folderId : null,

  _accept : ["tweetpoll", "poll", "survey"],

  _foldersourceWidget : null,

  /*
   * post create cycle.
   */
  postCreate : function() {
	    // DnD support removed, used combo instead
        //if (this.dropSupport) {
        //  this._folderSourceWidget  = new Target(this._folder, {
        //      accept: this._accept
        //      });
        //      dojo.connect(this._folderSourceWidget, "onDndDrop", lang.hitch(this, this.onDndDropFolder));
        // }
         var name = this._name;
         if (name !== null) {
             // TODO: on change event issues, review.
             name.onChange = lang.hitch(this, function() {
                 if (!this.folderId) {
                     this.folderId = this._create(name.get('value'));
                 } else {
                     this._update(name.get('value'));
                 }
             });
         } else {
           console.error("inline error");
         }
        on(this.domNode, "click",  lang.hitch(this, function() {
            this._items();
        }));
        this.inherited(arguments);
  },

    /**
     * Return all items for this folder
     * @private
     */
	_items : function() {
        var load = lang.hitch(this, function(data) {
	        if ('success' in data) {
		        if ("success" in data) {
			        var items;
			        if(this.folderContext === 'poll') {
				        items = data.success.pollsByFolder;
			        } else if(this.folderContext === 'tweetpoll') {
				        items = data.success.tweetPollsByFolder;
			        }
			        topic.publish('/encuestame/list/items/print', items);
		        }
	        }
	    });
	    var params = {
	      folderId : this.folderId
	    };
	    this._callFolderService(load, params, this.getAction("items"));
	},

  /*
   * add folder.
   */
  _create : function(name) {
      var id = null;
      var load = lang.hitch(this, function(data) {
          if ('success' in data) {
              this.folderId = data.success.folder.id;
              id = this.folderId;
          }
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
      var load = lang.hitch(this, function(data) {
          // nothing to do
      });
      var params = {
        folderId : this.folderId,
        folderName : name
      };
      this._callFolderService(load, params, this.getAction("update"));
  },

  /**
   * Add item.
   * @id id of the item
   */
  _addItem : function(id) {
     var load = function(data) {
          //console.debug("data", data);
          //console.info("Item Added");
     };
     var params = {
          folderId :this.folderId,
          itemId : id
     };
     this._callFolderService(load, params, this.getAction("move"));
  },

  /**
   * On drop on iten into a folder
   * @source
   * @nodes
   * @copy
   * @target
   */
  onDndDropFolder : function(source, nodes, copy, target) {
      array.forEach(dojo.query(".dojoDndItemSelected"), function(item) {
          dojo.removeClass(item, "dojoDndItemSelected");
      });
      array.forEach(dojo.query(".dojoDndItemAnchor"), function(item) {
          dojo.removeClass(item, "dojoDndItemAnchor");
      });
      if(dojo.dnd.manager().target !== this._folderSourceWidget) {
          return;
      }
      if(dojo.dnd.manager().target == dojo.dnd.manager().source) {
          console.debug("same");
      } else {
          array.forEach(this._folderSourceWidget.getSelectedNodes(), lang.hitch(this, function(item) {
              //console.debug("item", item);
              var tweetPollId = item.getAttribute('tweetpollId');
              var type = item.getAttribute('dndtype');
              //console.debug("tweetpollId", tweetPollId);
              //console.debug("type", type);
              this._addItem(parseInt(tweetPollId));
              dojo.destroy(item);
          }));
      }
  }
  });
});
