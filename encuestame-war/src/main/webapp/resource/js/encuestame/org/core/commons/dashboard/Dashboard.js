dojo.provide("encuestame.org.core.commons.dashboard.Dashboard");

dojo.require("dojo.dnd.Source");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.require("encuestame.org.core.commons.dashboard.Gadget");
dojo.require("encuestame.org.core.commons.dashboard.DashboardLayout");

dojo.declare(
    "encuestame.org.core.commons.dashboard.Dashboard",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/dashboard.html"),

        /*
         *
         */
        widgetsInTemplate: true,

        /*
         *
         */
        _test_gadgets : [{id:5, name:"gadget1"}, {id:1, name:"gadget2"}],


        dashBoardSource : null,

        dashboardId : null,

        name : null,

        /*
         *
         */
        layoutWidget : null,

        /*
         * Post create.
         */
        postCreate: function() {

        },

        /*
         * configure layout
         */
        _configureLayout : function(data) {
            this.layoutWidget = new encuestame.org.core.commons.dashboard.DashboardLayout(
                         {dashboardWidget: this, dashboard: data.dasboard, gadgets : data.gadgets, style: "height: 700px; width: 100%;"});
            console.debug("_configureLayout", this.layoutWidget);
            return this.layoutWidget;
        },

        insertGadget : function(gadget){},

        printGadget : function(gadget){},


        loadLayOut : function(data){
             if(this.layoutWidget == null) {
                 var parent = this;
                 this.layoutWidget = parent._configureLayout(data);
                 this.layoutWidget.placeAt(dojo.byId(parent._layout));
                 this.layoutWidget.startup();
             }
        },


        /**
         *
         */
        _loadGadgets : function(){
            var load = dojo.hitch(this, function(data) {
                this.loadLayOut(data);
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.gadget.list, {dashboardId : this.dashboardId}, load, error);
        },

        /*
         * initialize.
         */
        initialize : function(){
            if (this.dashboardId) {
                this._loadGadgets();
            }
        }

    }
);
