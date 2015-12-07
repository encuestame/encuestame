define( [
     "dojo",
     "dojo/_base/declare",
     "dojo/dom-construct",
     "dojo/dom-attr",
     "dojo/query",
     "dojo/dom-class",
     "me/web/widget/dashboard/Gadget",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/registry",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/support/DnDSupport",
     "me/core/enme",
     "dojo/text!me/web/widget/dashboard/template/dashboardGrid.html" ],
    function(
    dojo,
    declare,
    domConstruct,
    domAttr,
    query,
    domClass,
    Gadget,
    _WidgetBase,
    _TemplatedMixin,
    registry,
    _WidgetsInTemplateMixin,
    main_widget,
    DnDSupport,
    _ENME,
    template ) {

  return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin, DnDSupport ], {

    /**
     * Template string.
     * @property templateString
     */
    templateString: template,

    /*
     * Grid container widget.
     */
    gridContainer: null,

    /**
     *
     * @property
     */
    zones: 1,

    /*
     * List of gadgets.
     */
    _gadgets: [],

    /*
     * A list of portlet inside the grid.
     */
    _portlet_widget: [],

    /*
     * Dashboard reference.
     */
    dashboard: {},

    /*
     * Dashboard id.
     */
    _dasboardId: null,

    /*
     *
     */
    postCreate: function() {
            dojo.subscribe("/encuestame/dashboard/grid/reload", this, "reload");
            dojo.subscribe("/encuestame/dashboard/grid/reload/gadgets", this, "_loadGadgets");
            dojo.subscribe("/encuestame/dashboard/grid/layout", this, "changeLayout");
            dojo.subscribe("/encuestame/dashboard/add/portlet", this, "_addPortlet");
            dojo.subscribe("/encuestame/dashboard/gadget/add", this, "_addGadget");
            this._dasboardId = this.dashboard.id;
            this._loadGadgets( this._dasboardId );
    },

    /**
     * Add new gadget into dasboard grid.
     * @method _addGadget
     */
   _addGadget: function( dasboardId, name ) {
       if ( dasboardId == this._dasboardId ) {
           var params = {
               boardId: dasboardId
           };
           var load = dojo.hitch( this, function( data ) {
               if ("success" in data ) {
                   var gadget = data.success.gadget;
                   this._appendGadget( this._createGadget( gadget ), gadget );
                   this.successMesage("Added");
               }
           });
           var error = dojo.hitch( this, function( error ) {
               this.errorMessage("Error on add");
           });
           this.getURLService().post( [ "encuestame.service.gadget", [ name ]], params, load, error, dojo.hitch( this, function() {

           }), true );
       }
   },

    /**
     * Load list of gadgets for this dashboard.
     * @method _loadGadgets
     */
    _loadGadgets: function( dasboardId ) {
        this._dasboardId = dasboardId;
        var load = dojo.hitch( this, function( data ) {
            if ("success" in data ) {
                var dashboard = data.success.dashboard;
                this._gadgets = data.success.gadgets;
                this._dashboard = dashboard;
                this.setLayout( dashboard.layout || "BB" );
                this._printGadgets();
            } else {
                this.errorMessage("error no gadgets");
            }
        });
        var error = function( error ) {
            console.debug("error", error );
        };
        this.getURLService().get( "encuestame.service.gadget.list",  { dashboardId:dasboardId }, load, error, dojo.hitch( this, function() {

        }) );
    },

    /*
     *
     */
    _printGadgets: function() {
        this._removeAllGadgets();
        dojo.forEach( this._gadgets,
                dojo.hitch( this, function( item ) {
                var gadget = this._createGadget( item ),
                col = item.gadget_column;
                if ( col > this.zones ) {
                    col = this.zones;
                }
                var nl = query( 'div[data-col="' + col + '"]' )[ 0 ];
                nl.appendChild( gadget.domNode );
         }) );
    },

    /*
     *
     */
    _removeAllGadgets: function() {

        //Console.info("removing... size", this._portlet_widget.length);
        dojo.forEach( this._portlet_widget,
                dojo.hitch( this, function( item ) {
                this.gridContainer.removeChild( item );
         }) );
        this._portlet_widget = [];
    },

    /*
     *
     */
    _createGadget: function( data /* gadget info*/) {
        var gatget = new Gadget(
            { data: data,
                dashboardId: this._dasboardId
            });
        gatget.startup();
        return gatget;
    },

    /**
     *  Summary: create a portlet object.
     *  id : 142
     *  gadget_name: "comments"
     *  gadget_color:  "#7B2E02"
     *  gadget_column : 1
     *  gadget_position : 1
     *  gadget_status :  true
     *  @method _appendGadget
     */
    _appendGadget: function( widgetGadget, gadget ) {
        var column = dojo.byId( "dashboard_column_" + gadget.gadget_position || 1 );
        if ( column ) {
            column.appendChild( widgetGadget.domNode );
        }

        //Console.log('widgetGadget', widgetGadget);
    },

    /*
     * Add new porlet.
     */
    addPortlet: function( item, column, position ) {
        if ( column && position ) {
            this.gridContainer.addChild( item, column, position );
        } else {
            this.gridContainer.addChild( item );
        }
        this._portlet_widget.push( item );
    },

    /*
     *
     */
    _addPortlet: function( array_porlet ) {
        if ( array_porlet instanceof Array ) {
            array_porlet.forEach( function( item ) {

            });
        }
    },

    /**
     *
     * @method
     */
    _updateDasboard: function() {

        // Success handler
        var load = dojo.hitch( this, function( data ) {
            if ("success" in data ) {
              this.successMesage( "Saved" );
            }
        });

        // Error handler
        var error = function( error ) {
            this.errorMessage( "Error on move" );
        };
        this.getURLService().put( "encuestame.service.dashboard", this._dashboard, load, error, dojo.hitch( this, function() {

        }) );
    },

    /**
     * Update the layout.
     * @method
     */
    changeLayout: function( layout ) {
        this._dashboard.layout = layout;
        this._updateDasboard();
        dojo.publish( "/encuestame/dashboard/dialog/close" );
        dojo.publish( "/encuestame/dashboard/dashboard/redraw", [ this._dashboard ] );
    },

    /**
     *
     * @method setLayout
     * @param layout
     */
    setLayout: function( layout ) {
        switch ( layout ) {
            case "A":
                this.setColumns( 1, layout );
                break;
            case "BB":
                this.setColumns( 2, layout );
                break;
            case "BA":
                this.setColumns( 4, layout );
                break;
            case "AB":
                this.setColumns( 3, layout );
                break;
            default :
                this.setColumns( 1, layout );
            break;
        }
        this.reload();
    },

    /**
     *
     * @method setColumns
     */
    setColumns: function( n, layout ) {

        // Define current available zones
        this.zones = n;
        var _l = this._layout,
        setId = function( node, id ) {
            domAttr.set( node, "id", "dashboard_column_" + id );
        },
        _attr = "data-col",
        body = domConstruct.create( "div" );
        domClass.add( body, "row" );
        domClass.add( body, layout );
        options = {
          cssClassOver: "ui-dragged",

          // Triggered on enter to the drop zone
          onDragEnter: function() {

            //Console.log('drag enter 2', this);
          },

          // Triggered when the node is over the drop zone
          onDragOver: function() {

            //Console.log('drop', this);
          },

          // Triggered when the source leave the drop zone
          onDragLeave: function() {

          },

          // Triggered on drop inside the zone
          onDrop: dojo.hitch( this, function( contextDrop, dt, target_id, value, _node, options ) {
            var item = dojo.byId( target_id );
            _node.appendChild( item );

            // Gadget parameters
            var params = {
                column: domAttr.get( contextDrop, "data-col" ),
                position: 1,
                dashboardId: this._dasboardId
            };

            // Success handler
            var load = dojo.hitch( this, function( data ) {
                if ("success" in data ) {
                  this.successMesage( "Saved" );
                }
            });

            // Error handler
            var error = function( error ) {
                this.errorMessage( "Error on move" );
            };
            this.getURLService().put( [ "encuestame.service.gadget", [ value ]], params, load, error, dojo.hitch( this, function() {

            }) );
          })
        };

        // Create a layout column
        var parent = this;
        var createColum = function( span, id ) {
            var col1 = domConstruct.create( "div" );
            domClass.add( col1, span );
            domClass.add( col1, "ui-dasboard-column" );
            domAttr.set( col1, _attr, id );
            setId( col1, id );
            parent.dropTarget( col1, options );
            body.appendChild( col1 );
        };

        dojo.empty( _l );

        if ( n === 2 ) {
            createColum( "span5", 1 );
            createColum( "span5", 2 );
        } else if ( n === 1 ) {
            createColum( "span12", 1 );
        } else if ( n === 3 ) {
            createColum( "span3", 1 );
            createColum( "span7", 2 );
        } else if ( n === 4 ) {
            createColum( "span7", 1 );
            createColum( "span3", 2 );
        }
        _l.appendChild( body );
    },

    /*
     * Reload grid.
     */
    reload: function() {}

  });
});
