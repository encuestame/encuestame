dojo.provide("encuestame.org.core.commons.dashboard.DashboardWrapper");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.ComboBox");

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

        _dadsboards_test : [{id: 1, label: "dasboard 1"},{id: 2, label: "dasboard 2"},{id: 3, label: "dasboard 3"}],

        _dashboard_store : {"items":[
                                     {"id":1205,"name":"My Dashboard"},
                                     {"id":1201,"name":"TweetPoll Resume"},
                                     {"id":1204,"name":"Chart Votes"},
                                     {"id":12025,"name":"Comments Top Rate"},
                                     {"id":14205,"name":"Activity Stream"},
                                     ],"label":"name","identifier":"id"},

        /*
         * Post create.
         */
        postCreate: function() {;
           dojo.subscribe("/encuestame/dashboard/clean", this, "clean");
           dojo.subscribe("/encuestame/dashboard/insert", this, "insert");
           this._buildDashBoardList();
           dojo.connect(this._gadgets, "onclick", dojo.hitch(this, this._openDirectory));
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

        /*
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
            var stateStore = new dojo.data.ItemFileReadStore({
                data: this._dashboard_store
            });
            var filteringSelect = new dijit.form.ComboBox({
                name: "dashboard",
                store: stateStore,
                searchAttr: "name"
            });
            dojo.byId("stateSelect_"+this.id).appendChild(filteringSelect.domNode);
            filteringSelect.onChange = dojo.hitch(this, function(value){
                this.loadDashBoard({id:123, name: value});
            });
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