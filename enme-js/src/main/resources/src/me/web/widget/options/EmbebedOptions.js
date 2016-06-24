define( [
	"dojo/_base/declare",
	"dojo/on",
	"dojo/request/script",
	"dijit/_WidgetBase",
	"dijit/_TemplatedMixin",
	"dijit/_WidgetsInTemplateMixin",
	"me/core/main_widgets/EnmeMainLayoutWidget",
	"dijit/Tooltip",
	"me/core/enme",
	"dojo/text!me/web/widget/options/templates/embedded.html" ],
	function(
	    declare,
	    on,
	    script,
	    _WidgetBase,
	    _TemplatedMixin,
	    _WidgetsInTemplateMixin,
	    main_widget,
	    Tooltip,
	    _ENME,
	     template ) {
	return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

		/**
		* Template string.
		* @property
		*/
		templateString: template,

		/**
		*
		* @property
		*/
		itemType: "",

        print_height: "300",

        /**
         * Contains a posible dialog widget.
         */
        dialogWidget: null,

		/**
		 *
		 */
	    itemId: null,

        /**
         *
         */
        preview: true,

		/**
		 *
		 * @method postCreate
		 */
		postCreate: function() {
		  on( this._iframe, "click", dojo.hitch( this, function( e ) {
		      this.stopEvent( e );
		      this.callService("iframe", this.itemId );
		      dojo.addClass( this._iframe, "selected-script");
		      dojo.removeClass( this._script_label, "selected-script");
              dojo.removeClass( this._wordpress, "selected-script");
		  }) );

		  on( this._script_label, "click", dojo.hitch( this, function( e ) {
		      this.stopEvent( e );
		      this.callService("script", this.itemId );
		      dojo.addClass( this._script_label, "selected-script");
		      dojo.removeClass( this._iframe, "selected-script");
              dojo.removeClass( this._wordpress, "selected-script");
		  }) );

        on( this._wordpress, "click", dojo.hitch( this, function( e ) {
            this.stopEvent( e );
            this.callService("wordpress", this.itemId );
            dojo.addClass( this._wordpress, "selected-script");
            dojo.removeClass( this._iframe, "selected-script");
            dojo.removeClass( this._script_label, "selected-script");
        }) );

		},

		/**
		*
		* @method
		*/
		initialize: function() {
		  this.callService("script", this.itemId );
		},

        /**
         *
         */
        printPreview: function() {
            var parent = this;
            var iframe = this._iframe_dom;
            iframe.width = "400";
            iframe.height = this.print_height;
            iframe.scrolling = "no";
            iframe.frameborder = "0";
            iframe.allowtransparency = "true";
            iframe.src = this.contextDefaultPath + "/embebed/iframe/preview/" + this.itemType + "/" + this.itemId;
        },

		/**
		*
		* @method
		*/
		callService: function( type, id ) {
		  var parent = this;
		  embebed = function( response ) {
		    var code = response.body.replace( /(\r\n|\n|\r)/gm, "");
		    parent._script.value = decodeURIComponent( code );
		    parent._script.focus();
		    parent._script.select();
            if ( parent.preview ) {
                parent.printPreview();
            }
		  };
		  script.get( _ENME.config( "domain" ) + "/api/jsonp/generate/code/" + this.itemType + "/embedded", {
		    query: {
		      id: id,
		      embedded_type: type,
		      callback: "embebed"
		    },
		    jsonp: "callback"
		  }).then( function( response ) {
		      console.log("dsadsadsadas", response );
		  });
		}

    });
});
