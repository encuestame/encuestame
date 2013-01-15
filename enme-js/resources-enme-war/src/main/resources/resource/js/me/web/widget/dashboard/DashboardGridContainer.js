define([
     "dojo",
     "dojo/_base/declare",
     "me/web/widget/dashboard/Gadget",
     "me/core/enme"],
    function(
    dojo,
    declare,
    Gadget,
    _ENME) {

  return declare(null, {

    /*
     * grid container widget.
     */
    gridContainer : null,

    /*
     * list of gadgets.
     */
    _gadgets : [],

    /*
     * a list of portlet inside the grid.
     */
    _portlet_widget : [],

    /*
     * dashboard reference.
     */
    _dashboard : {},


    /*
     * dashboard id.
     */
    _dasboardId : null,


    /*
     *
     */
    constructor : function(node, zones) {
            dojo.subscribe("/encuestame/dashboard/grid/reload", this, "reload");
            dojo.subscribe("/encuestame/dashboard/grid/reload/gadgets", this, "_loadGadgets");
            dojo.subscribe("/encuestame/dashboard/grid/layout", this, "_layout");
            dojo.subscribe("/encuestame/dashboard/add/portlet", this, "_addPortlet");
            dojo.subscribe("/encuestame/dashboard/gadget/add", this, "_addGadget");

            this.gridContainer = new dojox.layout.GridContainer({
            nbZones : 3,
            opacity : .5,
            hasResizableColumns : false,
            allowAutoScroll : false,
            withHandles : true,
            dragHandleClass : 'dijitTitlePaneTitle',
            style : {
                width : '98%'
            },
            acceptTypes : [ 'Portlet' ],
            isOffset : true
        }, node);


        // prepare some Content for the Portlet:
       // var portletContent1 = [ dojo.create('div', {
       //     innerHTML : 'Some content within the Portlet "dynPortlet1".'
       // }) ];

        // create a new Portlet:
       // var portlet1 = new dojox.widget.Portlet({
       //     id : 'dynPortlet1',
       //     closable : false,
       //     dndType : 'Portlet',
       //    title : 'Portlet "dynPortlet1"',
       //     content : portletContent1
       // });

        // add the first Portlet to the
        // GridContainer:
        //this.addPortlet(portlet1);
        // startup GridContainer:
    },

    /*
    *
    */
   _addGadget : function(dasboardId, name) {
       //console.info("_addGadget", dasboardId);
       //console.info("_addGadget", name);
       if (dasboardId == this._dasboardId) {
           var params = {boardId: dasboardId, gadgetId: name};
           var load = dojo.hitch(this, function(data) {
               console.info("_addGadget added", data);
               if ("success" in data) {
                   //console.debug("_addGadget", data);
                   var gadget = data.success.gadget;
                   this._createPortlet(this._createGadget(gadget), gadget);
               }
           });
           var error = function(error) {
               //console.debug("error", error);
           };
           this.getURLService().get('encuestame.service.gadget.add', params, load, error , dojo.hitch(this, function() {

           }));
       }
   },

    /*
     * load list of gadgets for this dashboard.
     */
    _loadGadgets : function(dasboardId) {
        this._dasboardId = dasboardId;
        var load = dojo.hitch(this, function(data) {
            if ("success" in data) {
                this._gadgets = data.success.gadgets;
                this._dashboard = data.success.dashboard;
                this._printGadgets();
            } else {
                console.error("error no gadgets");
            }
        });
        var error = function(error) {
            console.debug("error", error);
        };
        this.getURLService().get('encuestame.service.gadget.list',  {dashboardId :dasboardId}, load, error , dojo.hitch(this, function() {

        }));
    },

    /*
     *
     */
    _printGadgets : function() {
        this._removeAllGadgets();
        dojo.forEach(this._gadgets,
                dojo.hitch(this,function(item) {
                //console.debug("print gadget", item);
                this._createPortlet(this._createGadget(item), item);
         }));
    },

    /*
     *
     */
    _removeAllGadgets : function() {
        //console.info("removing... size", this._portlet_widget.length);
        dojo.forEach(this._portlet_widget,
                dojo.hitch(this,function(item) {
                console.info("removing... gadget", item);
                this.gridContainer.removeChild(item);
         }));
        this._portlet_widget = [];
    },

    /*
     *
     */
    _createGadget : function(data /* gadget info*/) {
        //console.info("_createGadget", data);
        var gatget = new Gadget({data : data, dashboardId: this._dasboardId});
        gatget.startup();
        return gatget;
    },


    /*
     *  summary: create a portlet object.
     *  id : 142
     *  gadget_name: "comments"
     *  gadget_color:  "#7B2E02"
     *  gadget_column : 1
     *  gadget_position : 1
     *  gadget_status :  true
     */
    _createPortlet : function(/** gadget node **/ node, /** gadget **/ gadget) {
        // var portlet = new dojox.widget.Portlet({
        //     id : 'portlet_'+gadget.id+"_"+gadget.gadget_name+"_"+encuestame.utilities.randomString(),
        //     closable : true,
        //     dndType : 'Portlet',
        //     title : gadget.gadget_name,
        //     content : node
        // });
        // portlet.gadgetId = gadget.id;
        // portlet.onClose = function(evt) {
        //     dojo.publish("/encuestame/dashboard/gadget/remove", [gadget.id]);
        //     dojo.style(this.domNode, "display", "none");
        // };
        // this.addPortlet(portlet, gadget.gadget_column, gadget.gadget_position);
    },


    /*
     * add new porlet.
     */
    addPortlet : function(item, column, position) {
//        console.debug("addPorlet", item);
//        console.debug("addPorlet", column);
//        console.debug("addPorlet", position);
        if (column && position) {
            this.gridContainer.addChild(item, column, position);
        } else {
            this.gridContainer.addChild(item);
        }
        this._portlet_widget.push(item);
    },

    /*
     *
     */
    _addPortlet : function(array_porlet) {
        if(array_porlet instanceof Array){
            array_porlet.forEach(function(item) {
                //console.debug("new porlet", item);
            });
        }
    },

    /*
     *
     */
    _layout : function(/** type layout **/ layout) {
        console.info("grid change layout", layout);
        switch(layout){
            case "A":
                this.setColumns(1);
                break;
            case "AA":
                this.setColumns(2);
                break;
            case "AAA":
                this.setColumns(3);
                break;
            case "BA":
                this.setColumns(2);
                break;
            case "AB":
                this.setColumns(2);
                break;
            default :
                this.setColumns(3);
            break;
        }
        this.reload();
    },

    /*
     *
     */
    setColumns : function(n) {
        console.info("grid set columns", n);
        this.gridContainer.setColumns(n);
        console.info("grid set columns", n);
    },

    /*
     * reload grid.
     */
    reload : function(){
        this.gridContainer.startup();
    }


  });
});