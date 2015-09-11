/*
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.146
 *  @module Folders
 *  @namespace Widget
 *  @class FolderSelect
 */
define([
         "dojo/_base/declare",
         "dojo/data/ItemFileReadStore",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/ComboBox",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/folder/FolderOperations",
         "me/web/widget/folder/FoldersItemAction",
         "me/core/enme",
         "dojo/text!me/web/widget/folder/templates/foldersSelect.html" ],
        function(
                declare,
                ItemFileReadStore,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                ComboBox,
                main_widget,
                FolderOperations,
                FoldersItemAction,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, FolderOperations, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

           /*
            * label.
            */
           label :  _ENME.getMessage("widget_folder_select_label"),

           /*
            *
            */
           _addComboStoreWidget : null,

           /*
            *
            */
           _addComboWidget : null,

           /*
            *
            */
           postCreate : function(){
               this.inherited(arguments);
               this._loadStore();
           },

           /*
            *
            */
           _loadStore : function() {
               var load = function(data) {
                   this._addComboStoreWidget = new ItemFileReadStore({
                       data: data.success
                   });
                   if (this._addComboWidget === null) {
                       this._buildCombo();
                   } else {
                       this._addComboWidget.store = this._addComboStoreWidget;
                   }
               };
               var params = {
                   max : 600,
                   start : 0
                   };
               this._callFolderService(load, params, this.getAction("list"), true);
           },

           /*
            *
            */
           getSelected : function() {
              return this._addComboWidget.attr('value');
           },

           /*
            *
            */
           _buildCombo : function() {
               this._addComboWidget = new ComboBox({
                   name: "folder_select",
                   store:  this._addComboStoreWidget,
                   searchAttr: "name"
               });
               dojo.empty(this._combo);
               this._combo.appendChild(this._addComboWidget.domNode);
               this._addComboWidget.onChange = dojo.hitch(this, function(value) {
                   //TODO: item is null when check id null values.
                   var id = (this._addComboWidget.item.id === null ?  0 : this._addComboWidget.item.id[0]);
                   if (id) {
                       //console.info("id", id);
                   }
               });
           }


    });
});