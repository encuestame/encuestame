dojo.provide("encuestame.org.core.commons.dashboard.DashboardWrapper");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.ComboBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.DropDownButton");
dojo.require("dijit.TooltipDialog");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.ValidationTextBox");

dojo.require("dojo.dnd.Source");
dojo.require("dojo.data.ItemFileReadStore");

dojo.require("encuestame.org.core.commons.dashboard.GadgetDirectory");
dojo.require("encuestame.org.core.commons.dialog.Info");

/**
 *
 */
dojo.declare(
    "encuestame.org.core.commons.dashboard.DashboardWrapper",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/dashboardWrapper.html"),

        widgetsInTemplate: true,

        _addButtonWidget : null,

        _addComboWidget : null,

        _addComboStoreWidget : null,

        /**
         * Post create.
         */
        postCreate: function() {;
           dojo.subscribe("/encuestame/dashboard/clean", this, "clean");
           dojo.subscribe("/encuestame/dashboard/insert", this, "insert");
           this._buildDashBoardList();
           dojo.connect(this._gadgets, "onclick", dojo.hitch(this, this._openDirectory));
           this._createDashboardButton();
        },

        /**
         * open directory.
         */
        _openDirectory : function(){
            console.info("open dialog gadgets");
            var dialog = this._createDialog(this._loadGadgetDirectory().domNode);
            console.info("open dialog show", dialog);
            dialog.show();
        },

         /**
          * Load new gadget directory.
          * @returns {encuestame.org.core.commons.dashboard.GadgetDirectory}
          */
        _loadGadgetDirectory : function(){
            var directory = new encuestame.org.core.commons.dashboard.GadgetDirectory({});
            return directory;
        },

        /**
         * Create dialog.
         * @param content
         * @returns {encuestame.org.core.commons.dialog.Info}
         */
        _createDialog : function(content){
            console.info("open dialog content", content);
            var dialog = new encuestame.org.core.commons.dialog.Info({content:content});
            return dialog;
        },

        /*
         *
         */
        _buildDashBoardList : function(){
            var load = dojo.hitch(this, function(data){
                this._addComboStoreWidget = new dojo.data.ItemFileReadStore({
                    data: data.success
                });
                if (this._addComboWidget == null) {
                    this._buildCombo();
                } else {
                    this._addComboWidget.store = this._addComboStoreWidget;
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
                console.debug("load dasboard", this._addComboWidget.get('value'));
                console.debug("load dasboard", (this._addComboWidget.item.id == null ?  0 : this._addComboWidget.item.id[0]));
                console.debug("load dasboard", this._addComboWidget); //TODO: check id null values.
                this.loadDashBoard({dashboardId: (this._addComboWidget.item.id == null ?  0 : this._addComboWidget.item.id[0]),
                     name: this._addComboWidget.get('value')});
            });
        },

        /**
         *
         */
        _createDashboardButton : function() {
            var dialog = new dijit.TooltipDialog({
                                                content : '<div class="web-dashboard-create"><div  dojoType="dijit.form.Form" id="createDashBoard" data-dojo-id="createDashBoard" encType="multipart/form-data"><div class="web-dashboard-create-row"><label for="name">Name:</label> <input dojoType="dijit.form.ValidationTextBox" required="true"  id="name" name="name"></div>'
                        + '<div class="web-dashboard-create-row"><label for="hobby">Description:</label> <input dojoType="dijit.form.ValidationTextBox" required="true"  id="desc" name="desc"></div>'
                        + '<div class="web-dashboard-create-actions"><button id="createDashBoardAdd" dojoType="dijit.form.Button" type="button">Add</button>'
                        + '<button dojoType="dijit.form.Button"  id="createDashBoardCancel" type="button">Cancel</button></div></div></div>'
            });
            this._addButtonWidget = new dijit.form.DropDownButton({
                label: "New Dashboard",
                dropDown: dialog
            });
            var form = dijit.byId("createDashBoard");
            var add = dijit.byId("createDashBoardAdd");
            add.onClick = dojo.hitch(this, function() {
                if(form.isValid()){
                    this._createDashboardService(dojo.byId("createDashBoard"));
                } else {
                    console.info("form is invalid");
                }
            });
            var cancel = dijit.byId("createDashBoardCancel");
            cancel.onClick = function(){
                this._addButtonWidget.closeDropDown();
            };
            this._new.appendChild(this._addButtonWidget.domNode);
        },

        /**
         *
         */
        _createDashboardService : function(form) {
            console.debug("form", form);
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                this._buildDashBoardList();
                this._addButtonWidget.closeDropDown();
                this._addComboWidget.set('displayedValue',  dijit.byId("name").get('value'));
                dijit.byId("createDashBoard").reset();
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrPost(encuestame.service.dashboard.create, form, load, error);
        },

        /*
         * create new dashboard.
         */
        _createNewDashBoard : function(data) {
            console.debug("_createNewDashBoard", data);
            var widget = new encuestame.org.core.commons.dashboard.Dashboard({dashboard: data });
            widget.initialize();
            dojo.publish("/encuestame/dashboard/insert", [widget.domNode]);
            return widget;
        },

        /**
         *
         */
        loadDashBoard : function(data) {
            dojo.publish("/encuestame/dashboard/clean");
            this._createNewDashBoard(data);
        },


        /*
         *
         */
        clean : function() {
            console.debug("cleaning dashboard wrapper");
            dojo.empty(this._dasboard);
        },

        /*
         *
         */
        insert : function(node) {
            console.debug("insert new dashboard node", node);
            this._dasboard.appendChild(node);
        },

        _switchDashBard : function(){},
        _addGadgetToDashBard : function(){},
        _addGadgetToDashBard : function(){},
    }
);