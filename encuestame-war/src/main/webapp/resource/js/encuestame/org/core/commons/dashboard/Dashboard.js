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

        /*
         *
         */
        layoutWidget : null,

        /*
         * Post create.
         */
        postCreate: function() {
//             this.dashBoardSource  = new dojo.dnd.Source(this._gadgets, {
//                 accept: [],
//                 copyOnly: false,
//                 selfCopy : false,
//                 selfAccept: true,
//                 withHandles : true,
//                 autoSync : true,
//                 isSource : true
//                 //creator: this.dndNodeCreator
//                 });
             if(this.layoutWidget == null) {
                 var parent = this;
                 this.layoutWidget = parent._configureLayout();
                 console.debug("layoutWidget", this.layoutWidget);
                 this.layoutWidget.placeAt(dojo.byId(parent._layout));
                 this.layoutWidget.startup();
             }
        },

        /*
         * configure layout
         */
        _configureLayout : function() {
            this.layoutWidget = new encuestame.org.core.commons.dashboard.DashboardLayout(
                         {dashboardWidget: this, style: "height: 700px; width: 100%;"});
            console.debug("_configureLayout", this.layoutWidget);
            return this.layoutWidget;
        },

        insertGadget : function(gadget){},

        printGadget : function(gadget){},

        /*
         * initialize.
         */
        initialize : function(){
//            console.debug("initalize");
//            var itemArray = [];
//            dojo.forEach(
//                    this._test_gadgets,
//                    dojo.hitch(this, function(data, index) {
//                        var widget = this._createGadget(data, index);
//                        itemArray.push(widget.domNode);
//            }));
            //this.dashBoardSource.insertNodes(false, itemArray);
        }

    }
);
