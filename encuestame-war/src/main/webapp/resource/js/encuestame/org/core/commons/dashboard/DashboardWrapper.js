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

        dashboardWidget : null,

        layoutWidget : null,

        /**
         * Post create.
         */
        postCreate: function() {;
           dojo.subscribe("/encuestame/dashboard/clean", this, "clean");
           dojo.subscribe("/encuestame/dashboard/insert", this, "insert");
           this._buildDashBoardList();
           dojo.connect(this._gadgets, "onclick", dojo.hitch(this, this._openDirectory));
           dojo.connect(this._layout, "onclick", dojo.hitch(this, this._openLayout));
           this._createDashboardButton();
           this.layoutWidget = new encuestame.org.core.commons.dashboard.Layout();
        },

        /**
         * open directory.
         */
        _openDirectory : function(event){
            dojo.stopEvent(event);
            console.info("open dialog gadgets");
            var dialog = this._createDialog(this._loadGadgetDirectory().domNode);
            console.info("open dialog show", dialog);
            dialog.show();
        },

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
                console.info("dashboardWidget", this.dashboardWidget);
                var directory = new encuestame.org.core.commons.dashboard.GadgetDirectory({dashboardWidget : this.dashboardWidget});
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
                //TODO: item is null when check id null values.
                var id = (this._addComboWidget.item.id == null ?  0 : this._addComboWidget.item.id[0]);
                if (id) {
                    this._markAsSelected(id);
                    this.loadDashBoard({dashboardId: id,
                         name: this._addComboWidget.get('value')});
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
            encuestame.service.xhrGet(encuestame.service.dashboard.select, {id : id}, load, error);
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
            var load = dojo.hitch(this, function(data) {
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
        _createDashBoard : function(data) {
            console.debug("_createDashBoard", data);
            if (this.dashboardWidget == null) {
                this.clean();
                this.dashboardWidget = new encuestame.org.core.commons.dashboard.Dashboard({dashboard: data });
                this.dashboardWidget.startup();
                dojo.publish("/encuestame/dashboard/insert", [this.dashboardWidget.domNode]);
            } else {
                this.dashboardWidget.dashboard = data;
            }
            this.dashboardWidget.initialize();
            return this.dashboardWidget;
        },

        /**
         *
         */
        loadDashBoard : function(data) {
            this._createDashBoard(data);
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
    }
);

/*
 *
 */
dojo.declare(
        "encuestame.org.core.commons.dashboard.Layout",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/layout.html"),

            widgetsInTemplate: true,

            postCreate : function(){
                // dojo.connect(this.layout-a, "onclick", dojo.hitch(this, this._openDirectory));
                // dojo.connect(this.layout-aa, "onclick", dojo.hitch(this, this._openDirectory));
                // dojo.connect(this.layout-ba, "onclick", dojo.hitch(this, this._openDirectory));
                // dojo.connect(this.layout-ab, "onclick", dojo.hitch(this, this._openDirectory));
                 //dojo.connect(this.layout-aaa, "onclick", dojo.hitch(this, this._openDirectory));
            }

});