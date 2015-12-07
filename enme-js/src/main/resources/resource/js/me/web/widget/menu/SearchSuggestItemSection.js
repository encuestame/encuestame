define( [
         "dojo/parser",
         "dijit/registry",
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/dom-construct",
         "dojo/dom-class",
         "dojo/dom-attr",
         "dojo/on",
         "dojo/text!me/web/widget/menu/template/searchSuggestItemSection.html" ],
        function( parser, registry, declare, _WidgetBase, _TemplatedMixin,
                _WidgetsInTemplateMixin, main_widget, _ENME, domConstruct, domClass, domAttr, on, template ) {

            "use strict";

            return declare( [ _WidgetBase, _TemplatedMixin,
                    _WidgetsInTemplateMixin ], {

                // Template string.
                templateString: template,

                // Store the items
                items: [],

                // Parent widget reference
                parentWidget: null,

                /*
                 * Section label.
                 */
                label: "",

                // Post Create.
                postCreate: function() {
                    dojo.forEach( this.items, dojo.hitch( this, function( item ) {
                        this._itemSuggest.appendChild( this._createItem( item,
                                this.label ) );
                    }) );
                },

                /**
                 * Create a search item.
                 *
                 * @param item
                 * @param type
                 */
                _createItem: function( item, type ) {
                    var div = domConstruct.create("div");
                    domClass.add( div, "web-search-item");
                    dojo.attr( div, "data-value", item.itemSearchTitle );
                    dojo.attr( div, "data-type", type );
                    var h4 = domConstruct.create("span", null, div );
                    h4.innerHTML = item.itemSearchTitle;
                    if ( item.urlLocation !== "" && item.urlLocation !== null ) {
                        domAttr.set( div, "data-url", item.urlLocation );
                        dojo.connect( div, "onclick", dojo.hitch( this, function( event ) {
                            var url =  _ENME.config( "contextPath" ) + item.urlLocation;
                            document.location.href = url;
                        }) );
                    } else { // Point to search url

                    }
                    this.parentWidget.listItems.push( div );
                    return div;
                }

            });
        });
