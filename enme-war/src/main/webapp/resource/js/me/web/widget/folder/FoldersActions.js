dojo.require("dojox.fx");
define([
         "dojo/_base/declare",
         "dojo/dom-construct",
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

            /*
            * i18n message for this widget.
            */
           i18nMessage : {
             detail_manage_new : _ENME.getMessage("detail_manage_new"),
             detail_manage_folder_title : _ENME.getMessage("detail_manage_folder_title"),
             detail_manage_search : _ENME.getMessage("detail_manage_search"),
             detail_manage_delete : _ENME.getMessage("detail_manage_delete"),
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
               this._loadFolders();
               dojo.connect(this._new, "onclick", this, this._addNewFolder);
           },

           /*
            * add new folder.
            */
           _addNewFolder : function(event){
               dojo.stopEvent(event);
               var node = this._createFolder({folderId: null, name : _ENME.getMessage("detail_manage_folder_replace_name")});
               domConstruct.place(node.domNode, this._folders, "first");
               dojox.fx.highlight({node:node.domNode, duration: 800 }).play();
           },

           /*
            * load folders.
            */
           _loadFolders : function() {
               this.getAction("list");
               var load = function(data){
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
               this._callFolderService(load, params, this.getAction("list"), false);
           },

           /*
            *
            */
           _createFolder : function(data) {
               var folder = new FoldersItemAction(
                       { folderId: data.id,
                         name : data.name,
                         folderContext : this.folderContext,
                         folderParentWidget: this
                        });
               return folder;
           }

            });
        });

///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//dojo.provide("encuestame.org.core.shared.utils.FoldersActions");
//
//dojo.require("dojo.dnd.Source");
//
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//dojo.require("dijit.InlineEditBox");
//dojo.require('encuestame.org.core.commons');
//dojo.require('encuestame.org.core.shared.utils.FolderOperations');
//
//
//dojo.declare(
//    "encuestame.org.core.shared.utils.FoldersActions",
//    [encuestame.org.core.shared.utils.FolderOperations],{
//
//      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/foldersAction.html"),
//
//      /*
//       * enable templates.
//       */
//      widgetsInTemplate: true,
//
//

//});
//
///**
// *
// */
//dojo.declare(
//        "encuestame.org.core.shared.utils.FoldersItemAction",
//        [encuestame.org.core.shared.utils.FolderOperations],{
//
//        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/foldersItemAction.html"),
//
//        widgetsInTemplate: true,
//
//        name : "",
//
//        folderParentWidget: null,
//
//        /*
//         * enable drop support. _getContextUrlService
//         */
//        dropSupport : true,
//
//        folderId : null,
//
//        _accept : ["tweetpoll", "poll", "survey"],
//
//        _foldersourceWidget : null,
//
//        /*
//         * post create cycle.
//         */
//        postCreate : function(){
//            if (this.dropSupport) {
//                this._folderSourceWidget  = new dojo.dnd.Target(this._folder, {
//                    accept: this._accept
//                    });
//                    dojo.connect(this._folderSourceWidget, "onDndDrop", dojo.hitch(this, this.onDndDropFolder));
//               };
//               var name = dijit.byId(this._name);
//               if (name != null) {
//                   /*
//                    * TODO: on change event issues, review.
//                    */
//                   name.onChange = dojo.hitch(this, function() {
//                       if (this.folderId == null) {
//                           this.folderId = this._create(name.get('value'));
//                       } else {
//                           this._update(name.get('value'));
//                       }
//                   });
//               } else {
//                   console.error("inline error");
//               }
//               this.inherited(arguments);
//        },
//
//        /*
//         * add folder.
//         */
//        _create : function(name) {
//            //console.debug("updated name to;", name);
//            var id = null;
//            var load = dojo.hitch(this, function(data){
//                console.debug("data", data);
//                console.info("updated name");
//            });
//            var params = {
//                name : name
//                };
//            this._callFolderService(load, params, this.getAction("create"));
//            return id;
//        },
//
//        /*
//         * update folder.
//         */
//        _update : function(name) {
//            //console.debug("updated name to", name);
//            var load = dojo.hitch(this, function(data){
//                console.debug("data", data);
//                console.info("updated name");
//            });
//            var params = {
//                folderId :this.folderId,
//                folderName : name
//                };
//            this._callFolderService(load, params, this.getAction("update"));
//        },
//
//        /*
//         * add item.
//         */
//        _addItem : function(id) {
//            var load = function(data){
//                console.debug("data", data);
//                console.info("Item Added");
//            };
//            var params = {
//                folderId :this.folderId,
//                itemId : id
//                };
//           this._callFolderService(load, params, this.getAction("move"));
//        },
//
//        /*
//         * on drop on folder.
//         */
//        onDndDropFolder : function(source, nodes, copy, target) {
//                dojo.forEach(dojo.query(".dojoDndItemSelected"), function(item){
//                    dojo.removeClass(item, "dojoDndItemSelected");
//                });
//                dojo.forEach(dojo.query(".dojoDndItemAnchor"), function(item){
//                    dojo.removeClass(item, "dojoDndItemAnchor");
//                });
//                if(dojo.dnd.manager().target !== this._folderSourceWidget){
//                    return;
//                }
//                if(dojo.dnd.manager().target == dojo.dnd.manager().source){
//                    console.debug("same");
//                } else {
//                    dojo.forEach(this._folderSourceWidget.getSelectedNodes(), dojo.hitch(this, function(item) {
//                        //console.debug("item", item);
//                        var tweetPollId = item.getAttribute('tweetpollId');
//                        var type = item.getAttribute('dndtype');
//                        //console.debug("tweetpollId", tweetPollId);
//                        //console.debug("type", type);
//                        this._addItem(parseInt(tweetPollId));
//                        dojo.destroy(item);
//                    }));
//                }
//        }
//
//});