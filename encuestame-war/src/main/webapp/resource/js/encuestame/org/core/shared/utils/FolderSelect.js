/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.shared.utils.FolderSelect");

dojo.require("dojo.dnd.Source");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.InlineEditBox");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dijit.form.ComboBox");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.FolderOperations');


dojo.declare(
    "encuestame.org.core.shared.utils.FolderSelect",
    [encuestame.org.core.shared.utils.FolderOperations],{

      /*
       *
       */
      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/foldersSelect.html"),

      /*
       * enable templates.
       */
      widgetsInTemplate: true,

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
              this._addComboStoreWidget = new dojo.data.ItemFileReadStore({
                  data: data.success
              });
              if (this._addComboWidget == null) {
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
      _buildCombo : function(){
          console.info("_buildCombo");
          this._addComboWidget = new dijit.form.ComboBox({
              name: "folder_select",
              store:  this._addComboStoreWidget,
              searchAttr: "name"
          });
          dojo.empty(this._combo);
          this._combo.appendChild(this._addComboWidget.domNode);
          this._addComboWidget.onChange = dojo.hitch(this, function(value) {
              //TODO: item is null when check id null values.
              var id = (this._addComboWidget.item.id == null ?  0 : this._addComboWidget.item.id[0]);
              if (id) {
                  console.info("id", id);
              }
          });
      }

});