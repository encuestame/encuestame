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
 *  @version 1.147
 *  @module Dashboard
 *  @namespace Widget
 *  @class DashboardWrapper
 */
define(["dojo/_base/declare",
        "dojo/_base/lang",
        "dojo/DeferredList",
        "dojo/Deferred",
        "dojo/data/ItemFileReadStore",
        "dijit/_WidgetBase",
        "dijit/_TemplatedMixin",
        "dijit/_WidgetsInTemplateMixin",
        "dijit/Dialog",
        "dijit/registry",
        "dijit/TooltipDialog",
        "dijit/form/ComboBox",
        "dijit/form/DropDownButton",
        "dijit/form/TextBox",
        "dijit/form/Button",
        "me/core/enme",
        "me/core/main_widgets/EnmeMainLayoutWidget",
        "me/web/widget/dashboard/LayoutSelecter",
        "me/web/widget/dashboard/GadgetDirectory",
        "me/web/widget/dashboard/DashboardGridContainer",
        "dojo/text!me/web/widget/dashboard/template/dashboardWrapper.html" ],
        function(
                declare,
                lang,
                DeferredList,
                Deferred,
                ItemFileReadStore,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Dialog,
                registry,
                TooltipDialog,
                ComboBox,
                DropDownButton,
                TextBox,
                Button,
                _ENME,
                main_widget,
                LayoutSelecter,
                gadgetDirectory,
                DashboardGridContainer,
                template) {
          return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
          templateString : template,

          /**
           * i18n Message.
           */
          i18nMessage : {
              dashboard_title : _ENME.getMessage("dashboard_title"),
              dashboard_description : _ENME.getMessage("dashboard_description"),
	          dashboard_add_gadget : _ENME.getMessage("dashboard_add_gadget"),
              dashboard_add_gadget_tooltip : _ENME.getMessage("dashboard_add_gadget_tooltip"),
              dashboard_change_layout : _ENME.getMessage("dashboard_change_layout"),
              dashboard_change_layout_tooltip : _ENME.getMessage("dashboard_change_layout_tooltip"),
              dashboard_new_dasbboard : _ENME.getMessage("dashboard_new_dasbboard"),
              dashboard_panel_name : _ENME.getMessage("dashboard_panel_name"),
              dashboard_panel_descri : _ENME.getMessage("dashboard_panel_descri"),
	          dashboard_new_dasboard : _ENME.getMessage("dashboard_new_dasboard"),
	          commons_create : _ENME.getMessage("dashboard_new_dasboard")
          },

          /**
           *
           * @property
           */
          _addButtonWidget : null,

          /**
           *
           * @property
           */
          _addComboWidget : null,

          /**
           *
           * @property
           */
          _addComboStoreWidget : null,

          /**
           *
           * @property
           */
          dashboardWidget : null,

          /**
           *
           * @property
           */
          layoutWidget : null,

          /**
           *
           * @property
           */
          dialogWidget: null,


          helpSteps : [
              {
                  element: '.center-search',
                  intro: _ENME.getMessage('help_center_search')
              },
              {
                  element: '.searchMenu',
                  intro: _ENME.getMessage('help_menu_search')
              },
              {
                  element: '.nav-home-menu',
                  intro: _ENME.getMessage('help_home_menu')
              },
              {
                  element: '.counter',
                  intro: _ENME.getMessage('help_counter_notification')
              },
              {
                  element: '.web-profile-menu',
                  intro: _ENME.getMessage('help_profile_menu')
              },
              {
                  element: '#change_layout_button',
                  intro: _ENME.getMessage('help_change_layout_button')
              },
              {
                  element: '#add_gadget_button',
                  intro: _ENME.getMessage('help_change_gadget_button')
              },
              {
                  element: '.web-dash-list',
                  intro: _ENME.getMessage('help_dashboard_tool')
              },
              {
                  element: '.web-dash-columns',
                  intro: _ENME.getMessage('help_dashboards_columns')
              }
          ],

          /**
           * Sotre the list of dashboard retrieved by the service.
           * @property dashboard_list
           */
          dashboard_list : [],

         /**
          * Post create.
          * @method postCreate
          */
          postCreate: function() {
             dojo.subscribe("/encuestame/dashboard/clean", this, "clean");
             dojo.subscribe("/encuestame/dashboard/insert", this, "insert");
             dojo.subscribe("/encuestame/dashboard/dialog/close", this, "closeDialog");
             dojo.subscribe("/encuestame/dashboard/dashboard/redraw", this, "createDashboard");
             // deferred functions.
             var a = this._buildDashBoardList();
             var b = this._displayFavouriteDashboard();
             var c = this._createDashboardButton();
             var dl = new DeferredList([a, b,c], true, true);
             dl.then(dojo.hitch(this, function(){
                 this.initHelpLinks(dojo.hitch(this, function(){
                     this.updateHelpPageStatus(_ENME.config('currentPath'), true);
                 }));
             }));
             dojo.connect(this._gadgets, "onclick", dojo.hitch(this, this._openDirectory));
             dojo.connect(this._layout, "onclick", dojo.hitch(this, this._openLayout));
             this.layoutWidget = new LayoutSelecter({});
          },

          /**
           * Display the selected dashboard.
           * @method _displayFavouriteDashboard
           */
          _displayFavouriteDashboard : function() {
              var deferred = new Deferred();
              if (this._addComboStoreWidget) {
                  dojo.hitch(this, this._addComboStoreWidget.fetch({
                      query: {
                        selected : true
                      },
                      onComplete: dojo.hitch(this, function(items) {
                          dojo.forEach(items, dojo.hitch(this, function(item, index) {
                              this.createDashboard(this.dashboard_list[this._addComboStoreWidget.getValues(item, "id")]);
                              deferred.resolve("1");
                          }));
                      }),
                      onError: dojo.hitch(this, function(error){
                          console.error("favorite dashboard error: ", error);
                          deferred.reject();
                      })
                  }));
              }
              return deferred;
          },

          /**
           *
           * @method
           */
          createDashboard: function(dashboard) {
            try {
                 this._createDashBoard(dashboard);
              } catch(error) {
                 this.errorMessage('Error on load Dashboard');
              }
          },

          /**
           * Open directory.
           * @method _openDirectory
           */
          _openDirectory : function(event) {
              dojo.stopEvent(event);
              var directory = this._loadGadgetDirectory();
              this.dialogWidget = this._createDialog(directory.domNode, "Add new Gadget");
              directory.dialog = this.dialogWidget;
              this.dialogWidget.show();
          },

          /**
           * Close the dialog if exist.
           * @method closeDialog
           */
          closeDialog: function() {
            if (this.dialogWidget) {
                this.dialogWidget.hide();
                //this.dialogWidget.destroy();
                this.dialogWidget = null;
            }
          },

          /*
           * Open the layout dialog.
           * @method closeDialog
           */
          _openLayout : function(event) {
              dojo.stopEvent(event);
              var layout = this.layoutWidget;
              this.dialogWidget = this._createDialog(layout.domNode, "Change the layout");
              layout.dialog = this.dialogWidget;
              this.dialogWidget.show();
          },

           /**
            * Load new gadget directory.
            * @method _loadGadgetDirectory
            * @returns {encuestame.org.core.commons.dashboard.GadgetDirectory}
            */
          _loadGadgetDirectory : function() {
              if (this.dashboardWidget !== null) {
                  var directory = new gadgetDirectory(
                    {
                      dashboardWidget : this.dashboardWidget
                    });
                  return directory;
              } else {
                  //error.
              }
          },

          /**
           * Create dialog.
           * @param content
           * @method _createDialog
           * @returns {encuestame.org.core.commons.dialog.Info}
           */
          _createDialog : function(content, title) {
              var dialog = new Dialog(
                {
                  title: title,
                  style:"width:700px;",
                  content:content
                });
              return dialog;
          },

          /**
           *
           * @method
           */
          _convertDashboardList : function(items) {
            var o = {};
            for (var i = 0; i < items.length; i++) {
              var item = items[i];
              o[item.id] = lang.clone(item);
            }
            this.dashboard_list = o;
          },

          /*
           * build dashboard combobox list.
           */
          _buildDashBoardList : function() {
              var deferred = new Deferred();
              var load = dojo.hitch(this, function(data) {
                  if ("success" in data) {
                      var store = data.success;
                      if (store.items.length) {
                          this._convertDashboardList(store.items);
                          this._addComboStoreWidget = new ItemFileReadStore({
                              data: store
                          });
                          if (this._addComboWidget === null) {
                              this._buildCombo();
                              //load favorite dashboard by default.
                              this._displayFavouriteDashboard();
                          } else {
                              this._addComboWidget.store = this._addComboStoreWidget;
                          }
                      } else {
                          this.blockDashboard(true);
                          this._pleaseCreateDashboard();
                      }
                      deferred.resolve();
                  }
              });
              var error = function(error) {
                  console.error(error);
                  //TODO: error handler
                  deferred.cancel();
              };
              this.getURLService().get('encuestame.service.dashboard.list', {}, load, error , dojo.hitch(this, function() {

              }));
              return deferred;
          },

          /**
           *
           */
          blockDashboard : function() {
            //block buttons to avoid selection if the dashboard is empty
          },

          /**
           *
           * @private
           */
          _pleaseCreateDashboard: function() {
              //force to the user a create a dashboard
          },

          /*
           * create a dijit combo box.
           */
          _buildCombo : function(){
              this._addComboWidget = new ComboBox({
                  id:"change_dashboard",
                  name: "dashboard",
                  store:  this._addComboStoreWidget,
                  searchAttr: "dashboard_name"
              });
              dojo.empty(this._stateSelect);
              this._stateSelect.appendChild(this._addComboWidget.domNode);
              this._addComboWidget.onChange = dojo.hitch(this, function(value) {
                  //TODO: item is null when check id null values.
                  var id = (this._addComboWidget.item.id === null ?  0 : this._addComboWidget.item.id[0]);
                  var current_dashboard = this.dashboard_list[id];
                  if (id) {
                     this._markAsSelected(current_dashboard);
                     dojo.publish("/encuestame/dashboard/grid/reload/gadgets", [id]);
                  }
              });
          },

          /*
           * mark as selected dasboard
           * @id dasboard id.
           */
          _markAsSelected : function(current_dashboard) {
              current_dashboard.selected = true;
              var load = dojo.hitch(this, function(data) {
                 //console.debug(data);
              });
              var error = function(error) {
                  //console.error("error", error);
              };
              this.getURLService().put('encuestame.service.dashboard', current_dashboard, load, error , dojo.hitch(this, function() {

              }));
          },

          /**
           *
           */
          _createDashboardButton : function() {
              var deferred = new Deferred();
              this._new.onClick = dojo.hitch(this, function() {
                  var name = registry.byId("panel_name").get('value');
                  var descr = registry.byId("panel_descr").get('value');
                  if (name !== '') {
                     this._createDashboardService({
                         name : name,
                         desc : descr
                     }).then(function() {
                         deferred.resolve("");
                     }, function(){
                         deferred.cancel();
                     });
                  }
              });
              return deferred;
          },

          /**
           *
           */
          _createDashboardService : function(params) {
              var load = dojo.hitch(this, function(data) {
	              // clean the combo to force build it again.
	              this._addComboWidget = null;
                  this._buildDashBoardList();
	              registry.byId('new_dashboard').closeDropDown();
	              var name = registry.byId("panel_name");
	              var descr = registry.byId("panel_descr");
	              name.setValue('');
	              descr.setValue('');
                  //this._addComboWidget.set('displayedValue',  dijit.byId("name").get('value'));
                  dijit.byId("createDashBoard").reset();
              });
              var error = function(error) {};
              return this.getURLService().post("encuestame.service.dashboard", params, load, error , dojo.hitch(this, function() {}), true);
          },

          /**
           * Create new dashboard.
           * @method _createDashBoard
           */
          _createDashBoard: function(dashboard) {
              //console.debug("_createDashBoard", dashboardId);
              if (this.dashboardWidget === null) {
                  this.clean();
                  try{
                  this.dashboardWidget = new DashboardGridContainer({
                      dashboard: dashboard,
                      zones: 2
                  });
                  this._dasboard.appendChild(this.dashboardWidget.domNode);
                 } catch(error){
                  console.error('das', error);
                 }
              } else {
                  this.dashboardWidget._loadGadgets(dashboard.id);
              }
          },

          /*
           *
           */
          clean : function() {
              if (this.dashboardWidget !== null) {
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
              //console.debug("insert new dashboard node", node);
              this._dasboard.appendChild(node);
          }
    });
});
