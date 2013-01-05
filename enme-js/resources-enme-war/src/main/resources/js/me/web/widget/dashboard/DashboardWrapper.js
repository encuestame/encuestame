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
 *  @module Dashboard
 *  @namespace Widget
 *  @class DasboardWrapper
 */
define(["dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/dashboard/LayoutSelecter",
         "me/web/widget/dashboard/GadgetDirectory",
         "me/web/widget/dialog/Info",
         "dijit/TooltipDialog",
         "dijit/form/DropDownButton",
         "me/core/enme",
         "dojo/text!me/web/widget/dashboard/template/dashboardWrapper.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                layoutSelecter,
                gadgetDirectory,
                Info,
                TooltipDialog,
                DropDownButton,
                _ENME,
                 template) {
          return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
          templateString : template,

          _addButtonWidget : null,

          _addComboWidget : null,

          _addComboStoreWidget : null,

          dashboardWidget : null,

          layoutWidget : null,

          /**
           * Post create.
           */
          postCreate: function() {
             // subscribe calls
             dojo.subscribe("/encuestame/dashboard/clean", this, "clean");
             dojo.subscribe("/encuestame/dashboard/insert", this, "insert");
             // deferred functions.
             var def = new dojo.Deferred();
             def.then(dojo.hitch(this, this._buildDashBoardList));
             //def.then(dojo.hitch(this, this._displayFavouriteDashboard));
             def.then(dojo.hitch(this, this._createDashboardButton));
             // connect behaviours
             dojo.connect(this._gadgets, "onclick", dojo.hitch(this, this._openDirectory));
             dojo.connect(this._layout, "onclick", dojo.hitch(this, this._openLayout));
             // init layout select
             this.layoutWidget = new layoutSelecter({});
             def.callback(true);
          },

          /*
           *
           */
          _displayFavouriteDashboard : function(){
              if( this._addComboStoreWidget) {
                  dojo.hitch(this, this._addComboStoreWidget.fetch({
                      query: {favorite : true},
                      queryOptions: {ignoreCase: true, deep: true},
                      onComplete: dojo.hitch(this, function(items){
                          dojo.forEach(items, dojo.hitch(this, function(item, index) {
                              var dasboardId = this._addComboStoreWidget.getValues(item, "id");
                              //console.info("dashboard id ", dasboardId);
                              // load favorite dashboard.
                              this._createDashBoard(dasboardId);
                          }));
                      }),
                      onError: dojo.hitch(this, function(error){
                          console.debug("error: ", error);
                      })
                  }));
              }
          },

          /**
           * open directory.
           */
          _openDirectory : function(event){
              dojo.stopEvent(event);
              //console.info("open dialog gadgets");
              var dialog = this._createDialog(this._loadGadgetDirectory().domNode);
              //console.info("open dialog show", dialog);
              dialog.show();
          },

          /*
           * open layout.
           */
          _openLayout : function(event){
              dojo.stopEvent(event);
              var dialog = this._createDialog(this.layoutWidget.domNode);
              dialog.show();
          },

           /**
            * Load new gadget directory.
            * @returns {encuestame.org.core.commons.dashboard.GadgetDirectory}
            */
          _loadGadgetDirectory : function(){
              if (this.dashboardWidget != null) {
                  //console.info("dashboardWidget", this.dashboardWidget);
                  var directory = new gadgetDirectory({dashboardWidget : this.dashboardWidget});
                  return directory;
              } else {
                  //error.
              }
          },

          /**
           * Create dialog.
           * @param content
           * @returns {encuestame.org.core.commons.dialog.Info}
           */
          _createDialog : function(content){
              console.info("open dialog content", content);
              var dialog = new Info({content:content});
              return dialog;
          },

          /*
           * build dashboard combobox list.
           */
          _buildDashBoardList : function() {
              var load = dojo.hitch(this, function(data){
                  if("success" in data) {
                      var store = data.success;
                      //http://livedocs.dojotoolkit.org/dojo/data/ItemFileReadStore
                      this._addComboStoreWidget = new dojo.data.ItemFileReadStore({
                          data: store
                      });
                      if (this._addComboWidget == null) {
                          this._buildCombo();
                          //load favorite dashboard by default.
                          this._displayFavouriteDashboard();
                      } else {
                          this._addComboWidget.store = this._addComboStoreWidget;
                      }
                  }
              });
              var error = function(error) {
                  console.debug("error", error);
              };
              encuestame.service.xhrGet(encuestame.service.dashboard.list, {}, load, error);
          },

          /*
           * create a dijit combo box.
           */
          _buildCombo : function(){
              this._addComboWidget = new dijit.form.ComboBox({
                  name: "dashboard",
                  store:  this._addComboStoreWidget,
                  searchAttr: "dashboard_name"
              });
              dojo.empty(dojo.byId("stateSelect_"+this.id));
              dojo.byId("stateSelect_"+this.id).appendChild(this._addComboWidget.domNode);
              this._addComboWidget.onChange = dojo.hitch(this, function(value) {
                  //TODO: item is null when check id null values.
                  var id = (this._addComboWidget.item.id == null ?  0 : this._addComboWidget.item.id[0]);
                  if (id) {
                     this._markAsSelected(id);
                     dojo.publish("/encuestame/dashboard/grid/reload/gadgets", [id]);
                  }
              });
          },

          /*
           * mark as selected dasboard
           * @id dasboard id.
           */
          _markAsSelected : function(id){
              var load = dojo.hitch(this, function(data) {
                 console.debug(data);
              });
              var error = function(error) {
                  console.error("error", error);
              };
              encuestame.service.xhrGet(this.getURLService().service('encuestame.service.dashboard.select'), {id : id}, load, error);
          },

          /**
           *
           */
          _createDashboardButton : function() {
              var dialog = new TooltipDialog({
                                                  content : '<div class="web-dashboard-create"><div  data-dojo-type="dijit/form/Form" id="createDashBoard" data-dojo-id="createDashBoard" encType="multipart/form-data"><div class="web-dashboard-create-row"><label for="name">Name:</label> <input dojoType="dijit.form.ValidationTextBox" required="true"  id="name" name="name"></div>'
                          + '<div class="web-dashboard-create-row"><label for="hobby">Description:</label> <input data-dojo-type="dijit/form/ValidationTextBox" required="true"  id="desc" name="desc"></div>'
                          + '<div class="web-dashboard-create-actions"><button id="createDashBoardAdd" data-dojo-type="dijit/form/Button" type="button">Add</button>'
                          + '<button data-dojo-type="dijit/form/Button" id="createDashBoardCancel" type="button">Cancel</button></div></div></div>'
              });
              //http://livedocs.dojotoolkit.org/dijit/form/DropDownButton
              //TODO: temp disabled, needed refactor this code.
//              this._addButtonWidget = new DropDownButton({
//                  label: "New Dashboard",
//                  dropDown: dialog
//              });
//              var form = dijit.byId("createDashBoard");
//              var add = dijit.byId("createDashBoardAdd");
//              add.onClick = dojo.hitch(this, function() {
//                  if(form.isValid()){
//                      this._createDashboardService(dojo.byId("createDashBoard"));
//                  } else {
//                      console.info("form is invalid");
//                  }
//              });
//              var cancel = dijit.byId("createDashBoardCancel");
//              cancel.onClick = function(){
//                  this._addButtonWidget.closeDropDown();
//              };
//              this._new.appendChild(this._addButtonWidget.domNode);
          },

          /**
           *
           */
          _createDashboardService : function(form) {
              var load = dojo.hitch(this, function(data) {
                  this._buildDashBoardList();
                  this._addButtonWidget.closeDropDown();
                  this._addComboWidget.set('displayedValue',  dijit.byId("name").get('value'));
                  dijit.byId("createDashBoard").reset();
              });
              var error = function(error) {
                  console.debug("error", error);
              };
              encuestame.service.xhrPost(this.getURLService().service('encuestame.service.dashboard.create'), form, load, error);
          },

          /*
           * create new dashboard.
           */
          _createDashBoard : function(/** dashboard id **/ dashboardId) {
              console.debug("_createDashBoard", dashboardId);
              if (this.dashboardWidget == null) {
                  this.clean();
                  this.dashboardWidget = new encuestame.org.core.commons.dashboard.DashboardGridContainer(this._dasboard, dashboardId, 3);
                  this.dashboardWidget.reload();
              } else {
                  this.dashboardWidget._loadGadgets(dashboardId);
                  this.dashboardWidget.reload();
              }
          },

          /*
           *
           */
          clean : function() {
              if (this.dashboardWidget != null) {
                  /*
                   * TODO: issues on try to remove this widget. destroyRecursive don't seems work properly.
                   */
                  this.dashboardWidget.destroyLayout();
                  this.dashboardWidget.destroyRecursive(true);
                  //dojo.destroy(this.dashboardWidget.layoutWidget);
                  //dojo.destroy(this.dashboardWidget);
              }
              if(this._dasboard){
                  dojo.empty(this._dasboard);
              }
          },

          /*
           *
           */
          insert : function(node) {
              console.debug("insert new dashboard node", node);
              this._dasboard.appendChild(node);
          }
    });
});
