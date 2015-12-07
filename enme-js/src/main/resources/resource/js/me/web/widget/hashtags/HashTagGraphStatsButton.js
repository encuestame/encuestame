/**
 * Widget to display a hashtag stats button.
 */
define(
    [
      "dojo/_base/declare",
      "dojo/on",
      "dijit/_WidgetBase", "dijit/_TemplatedMixin",
      "dijit/_WidgetsInTemplateMixin",
      "me/core/main_widgets/EnmeMainLayoutWidget",
      "me/core/enme",
      "dojo/text!me/web/widget/hashtags/template/hashTagGraphStatsButton.html" ],
    function( declare, on, _WidgetBase, _TemplatedMixin,
        _WidgetsInTemplateMixin, main_widget, _ENME, template ) {
      return declare( [ _WidgetBase, _TemplatedMixin, main_widget,
          _WidgetsInTemplateMixin ], {

        // Template string.
        templateString: template,

              /**
         * Button handler.
         */
        _handler: null,

        /**
         * Define if the button is selected by default.
         */
        selectedButton: false,

        /**
         * Post create cycle.
         */
        postCreate: function() {
          if ( this._handler !== null ) {
            var that = this;
            dojo.subscribe("/encuestame/hashtag/buttons", this,
                this._switchButton );
            dojo.connect( this._button, "onclick", dojo.hitch( this,
                function( event ) {
                  this._handler.onClick();
                }) );
            this._handler.init( this );
            if ( this.selectedButton ) {
              dojo.addClass( this.domNode, "selected");
            }
            this._button.appendChild( this._handler.domNode );

//            If (this.isMobile) {
//               console.log("ww", window.innerHeight);
//               on(window, "resize", function() {
//                  console.log("got resize");
//                  console.log("ww", window.innerHeight / 4);
//                  that._button.style.with = ( window.innerHeight / 4) + "px";
//               });
//               // width
//               that._button.style['width'] = ( (window.innerHeight / 4)) + "px";
//            }
          }
        },

        /**
         * Review the state of the button.
         */
        _switchButton: function( ref ) {
          if ( this.domNode == ref.domNode ) {
            dojo.addClass( ref.domNode, "selected");
            ref.selectedButton = true;
          } else {
            dojo.removeClass( this.domNode, "selected");
            this.selectedButton = false;
          }
        }

      });
    });
