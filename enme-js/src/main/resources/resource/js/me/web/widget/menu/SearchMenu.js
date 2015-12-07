define( [
     "dojo/dom-attr",
     "dijit/registry",
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/web/widget/suggestion/Suggest",
     "me/core/URLServices",
     "me/core/enme",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "dojo/on",
     "dojo/dom",
     "dojo/dom-style",
     "dojo/mouse",
     "dojo/dom-class",
     "me/web/widget/menu/SearchSuggestItemsByType",
     "dojo/text!me/web/widget/menu/template/searchMenu.html",
     "dijit/form/TextBox" ], function(
    domAttr,
    registry,
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    suggest,
    _URL,
    _ENME,
    main_widget,
    on,
    dom,
    domStyle,
    mouse,
    domClass,
    searchSuggestItemsByType,
    template ) {

  "use strict";

  var SearchMenu = declare( [ main_widget, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin ], {

    /*
     * Template string.
     */
    templateString: template,

       /*
        * Default label.
        */
       label: "Search",

       /*
        * Suggest widget referece.
        */
       textBoxWidget: null,

       /*
        * Store all items.
        */
       listItems: [],

       /*
        * Default search params.
        */
       searchParam: { limitByItem: 5, keyword: "" },

       /*
        * Index item selected.
        */
       _indexItem: -1,

       /*
        * Store the item selected temp.
        */
       _selectedNode: null,

       /*
        * Define if a key process is on curse.
        */
       _inProcessKey: false,

       /*
        * The time to delay key events.
        */
       _delay: 300,

       /*
        *
        */
       limitByItem: 5,

       /*
        * Post create process.
        */
       postCreate: function() {
          this.textBoxWidget = registry.byId( this._suggest );
          if ( this.textBoxWidget ) {
              this._searchSuggestSupport();
          }
       },

       /*
        * Set params
        * @param object of values.
        */
       _setParams: function( value ) {
           this.searchParam = value;
       },

       /*
        * Hide with fade out the suggest box.
        */
       hide: function() {
           this.listItems = [];
           var fadeArgs = {
                   node: this._suggestItems
           };
           dojo.fadeOut( fadeArgs ).play();
           this.clear();
       },

       /*
        *
        */
       clear: function() {
           if ( this.textBoxWidget ) {
               this._selectedNode = null;
               this.textBoxWidget.set("value", "");
           }
           dojo.empty( this._suggestItems );
       },

       /*
        *
        */
       _moveSelected: function( position ) {
            dojo.query(".web-search-item").forEach( function( node, index, arr ) {
                 domClass.remove( node, "selected");
            });
           if ( this._indexItem == -1 ) {
               if ( position == "up") {
                   this._indexItem = this.listItems.length;
               } else {
                   this._indexItem = 0;
               }
           } else if ( this._indexItem === 0 ) {
               if ( position == "up") {
                   this._indexItem = this.listItems.length - 1;
               } else if ( position == "down") {
                   this._indexItem = this._indexItem + 1;
               }
           } else if ( this._indexItem >= this.listItems.length ) {
                if ( position == "up") {
                    this._indexItem = this.listItems.length - 1;
                } else {
                    this._indexItem = 0;
                }
               this._indexItem = 0;
           } else {
               if ( position == "up") {
                   this._indexItem = this._indexItem - 1;
               } else {
                   this._indexItem = this._indexItem + 1;
               }
           }

           //Find node in the array.
           var node = this.listItems[ this._indexItem ];
           this._selectedNode = node;
           if ( node ) {
               this.textBoxWidget.attr("value", dojo.attr( node, "data-value") );
               domClass.add( node, "selected");
           }
       },

       /*
        * On press enter.
        * @param selectedItem the item selected by user.
        */
       processEnterAction: function( selectedItem ) {

           //If item is null, search whith value in the input, if not use the data-value attribute.
           var searchUrl =  _ENME.config( "contextPath" );
           if ( selectedItem === null || domAttr.get( selectedItem, "data-url") === null ) {
               searchUrl = searchUrl.concat("/search?q=");
               searchUrl = searchUrl.concat( this.textBoxWidget.get("value") );
           } else {
               searchUrl = searchUrl.concat( dojo.attr( selectedItem, "data-url") );
           }

           // Redirect to the asset
           window.location = _ENME.getHttpProtocol() + searchUrl;
       },

       /**
        * Search suggest support.
        */
       _searchSuggestSupport: function() {
           /**
             * Initialize the key up event.
             */
           var that = this;
           on( this.textBoxWidget, "keypress", function( e ) {
            var text = that.textBoxWidget.get("value");

            // ENTER key
            if ( dojo.keys.ENTER == e.keyCode ) {
                 e.preventDefault();
                that.processEnterAction( that._selectedNode );

            // ESC key
            } else if ( dojo.keys.ESCAPE == e.keyCode ) {
                that.hide();

            // UP ARROW KEY
            } else if ( dojo.keys.UP_ARROW == e.keyCode ) {
                that._moveSelected("up");

            // DOWN ARROW KEY
            } else if ( dojo.keys.DOWN_ARROW == e.keyCode ) {
                that._moveSelected("down");

            // THE REST OF KEYBOARD
            } else {
                if ( text !== "" && text.length > 1 ) {
                    var parent = this;
                    if ( !that._inProcessKey ) {
                       that._inProcessKey = true;
                       that._setParams(
                            { limit: _ENME.config( "suggest_limit" ),
                                keyword: text,
                                limitByItem: that.limitByItem,
                                excludes: that.exclude
                            });
                       that._inProcessKey = false;
                       that._searchCallService();
                    }
                }
            }
         });
       },

       /**
        * Make a call to search service.
        * {"error":{},"success":{"items":{"profiles":[],"questions":[],"attachments":[],
        *  "tags":[{"id":null,"hits":3000001,"typeSearchResult":"HASHTAG",
        *  "urlLocation":"/hashtag/nicaragua","score":100,
        *  "itemSearchTitle":"Nicaragua","itemSearchDescription":null}]},
        *  "label":"itemSearchTitle","identifier":"id"
        *  }}
        */
       _searchCallService: function() {
           var load = dojo.hitch( this, function( data ) {

               //Console.debug("social _searchCallService", data);
               dojo.empty( this._suggestItems );
               if ("items" in data.success ) {
                   var fadeArgs = {
                           node: this._suggestItems
                   };

                   //Reset selected values.
                   this.listItems = [];
                   this._indexItem = -1;
                   this._selectedNode = null;
                   dojo.fadeIn( fadeArgs ).play();

                   //Print new items.
                   this.printItems( data.success.items );
               }
           });
           var error = function( error ) {
               console.debug("error", error );
           };
           this.getURLService().get( "encuestame.service.search.suggest", this.searchParam, load, error, dojo.hitch( this, function() {

           }) );
       },

       /**
        * Create a list of item.
        * @param data suggested search item.
        */
       printItems: function( data ) {
           var widget = new searchSuggestItemsByType(
                   {
                    data: data,
                    parentWidget: this
                    });
           this._suggestItems.appendChild( widget.domNode );
       }
    });

  return SearchMenu;
});
