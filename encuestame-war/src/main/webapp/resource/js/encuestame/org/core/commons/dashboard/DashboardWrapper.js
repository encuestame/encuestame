dojo.provide("encuestame.org.core.commons.dashboard.DashboardWrapper");

dojo.require("dojo.dnd.Source");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dijit.form.ComboBox");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

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