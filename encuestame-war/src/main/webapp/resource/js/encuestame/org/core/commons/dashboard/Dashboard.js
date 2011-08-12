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


        dashBoardSource : null,


        dashboard : null,

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
            console.debug("_configureLayout", data);
            this.layoutWidget = new encuestame.org.core.commons.dashboard.DashboardLayout(
                         {dashboardWidget: this, dashboard: data.dashboard, gadgets : data.gadgets, style: "height: 700px; width: 100%;"});
            return this.layoutWidget;
        },

        /*
         *
         */
        loadLayOut : function(data){
             if(this.layoutWidget == null) {
                 this.layoutWidget = this._configureLayout(data);
                 this._layout.appendChild(this.layoutWidget.domNode);
                 //this.layoutWidget.startup();
             }
        },


        /**
         *
         */
        _loadGadgets : function(){
            var load = dojo.hitch(this, function(data) {
                console.debug("_loadGadgets initialize", data);
                if (data.success) {
                    this.loadLayOut(data.success);
                } else {
                    //posible error message.
                }
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.gadget.list, {dashboardId : this.dashboard.dashboardId}, load, error);
        },

        /*
         * initialize.
         */
        initialize : function(){
            console.debug("_configureLayout initialize", this.dashboard);
            if (this.dashboard !=  null) {
                this._loadGadgets();
            }
        }

    }
);
