//dojo.require("dojox.fx");
define([
 "dojo/_base/declare",
 "dojo/dom-construct",
 "dojo/topic",
 "dojo/_base/lang",
 "dijit/_WidgetBase",
 "dijit/_TemplatedMixin",
 "dijit/_WidgetsInTemplateMixin",
 "me/core/main_widgets/EnmeMainLayoutWidget",
 "me/web/widget/folder/FolderOperations",
 "me/web/widget/folder/FoldersItemAction",
 "me/core/enme",
 "dojo/text!me/web/widget/folder/templates/foldersAction.html" ],
function(
    declare,
    domConstruct,
    topic,
    lang,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    FolderOperations,
    FoldersItemAction,
    _ENME,
     template) {
    return declare([ _WidgetBase, _TemplatedMixin, main_widget, FolderOperations, _WidgetsInTemplateMixin], {

    // template string.
    templateString : template,

	// load items on load
	loadFolderItems : true,

	// save folders on load
    _foldersStorage : [],

    /*
    * i18n message for this widget.
    */
   i18nMessage : {
     detail_manage_new : _ENME.getMessage("detail_manage_new"),
     detail_manage_folder_title : _ENME.getMessage("detail_manage_folder_title"),
     detail_manage_search : _ENME.getMessage("detail_manage_search"),
     detail_manage_delete : _ENME.getMessage("detail_manage_delete")
   },

   /*
    *
    */
   _folderSourceWidget : null,

   /*
    * post create.
    */
   postCreate : function() {
       this.inherited(arguments);
	   if (this.loadFolderItems) {
		   this._loadFolders();
	   }
       dojo.connect(this._new, "onclick", this, this._addNewFolder);
	   topic.subscribe("/encuestame/folder/distribute/update", lang.hitch(this, function() {
		   topic.publish("/encuestame/folder/distribute/load", this._foldersStorage);
	   }));
	   topic.subscribe("/encuestame/folder/item/add", lang.hitch(this, function(folderId, itemId) {
		   this._callFolderService( lang.hitch(this,function(items) {
			    //on save the item into the folder
			   topic.publish("/encuestame/folder/item/" + itemId + "/update/counter/", items);
		   }), {
			   folderId : folderId,
			   itemId : itemId
		   }, this.getAction("move"), false);
	   }));
   },

   /**
    * Add new folder.
    */
   _addNewFolder : function(event){
       dojo.stopEvent(event);
       var node = this._createFolder({folderId: null, items: 0, name : _ENME.getMessage("detail_manage_folder_replace_name")});
       domConstruct.place(node.domNode, this._folders, "first");
       // Not ready for AMD modules
       // http://dojotoolkit.org/reference-guide/1.8/dojox/fx.html
       //dojox.fx.highlight({node:node.domNode, duration: 800 }).play();
   },

    /**
     * Load folders.
     */
	loadFolders : function() {
		this._loadFolders();
	},

   /**
    * Load folders.
    */
   _loadFolders : function() {
       this.getAction("list");
       var load = function(data) {
	       this._foldersStorage = data;
	       topic.publish("/encuestame/folder/distribute/load", data);
           dojo.empty(this._folders);
           dojo.forEach(
               data.success.folders,
               dojo.hitch(this, function(data, index) {
                   var node = this._createFolder(data);
                   this._folders.appendChild(node.domNode);
           }));
       };
       var params = {
           max : this.max,
           start : this.start
           };
       return this._callFolderService(load, params, this.getAction("list"), false);
   },

   /**
    * Create folder
    */
   _createFolder : function(data) {
       var folder = new FoldersItemAction(
               {
	             folderId: data.id,
                 name : data.name,
	             counter : data.items,
                 folderContext : this.folderContext,
                 folderParentWidget: this
                });
       return folder;
   }
    });
});