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
            console.debug("_configureLayout...", data);
            this.layoutWidget = new encuestame.org.core.commons.dashboard.DashboardLayout(
                         {dashboardWidget: this, dashboard: data.dashboard, gadgets : data.gadgets, style: "height: 700px; width: 100%;"});
            this.layoutWidget.startup();
            return this.layoutWidget;
        },

        /*
         * load new layout.
         */
        loadLayOut : function(data) {
             console.debug("LAYOUT DEBERIA SER NULO ", this.layoutWidget);
             if (this.layoutWidget == null) {
                 this.layoutWidget = this._configureLayout(data);
                 console.debug("NEW LAYOUT...", this.layoutWidget);
                 this._layout.appendChild(this.layoutWidget.domNode);
             }
        },

        /*
         * destroy current layout.
         */
        destroyLayout : function() {
             if (this.layoutWidget != null) {
                 //console.info("destroying layout...", this.layoutWidget);
                 //this.layoutWidget.cleanLayout();
                 //this.layoutWidget.destroyRecursive(true);
                 console.info("destroyed layout...", this.layoutWidget);
                 this.layoutWidget.destroy(false);
                 //dojo.destroy(this.layoutWidget.domNode);
                 console.info("LAYOUT NODEEEEEEEEEEEEEEE", this.layoutWidget.domNode);
                 dojo.publish("/encuestame/dashboard/gadget/unsubscribe", [null]);
                 this.layoutWidget = null;
             }
        },


        /*
         * load list of gadgets for this dashboard.
         */
        _loadGadgets : function(){
            var load = dojo.hitch(this, function(data) {
                console.debug("_loadGadgets initialize", data.success);
                if (data.success) {
                    this.loadLayOut(data.success);
                } else {
                    console.error("error no gadgets");
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
            console.debug("DASHBOARD initialize", this.dashboard);
            if (this.dashboard !=  null) {
                this.destroyLayout();
                this._loadGadgets();
            }
        }

    }
);
