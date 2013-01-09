/**
 * Widget to display a hashtag stats button.
 */
define(
    [ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_TemplatedMixin",
        "dijit/_WidgetsInTemplateMixin",
        "me/core/main_widgets/EnmeMainLayoutWidget", "me/core/enme",
        "dojo/text!me/web/widget/hashtags/template/hashTagGraphStatsButton.html" ],
    function(declare, _WidgetBase, _TemplatedMixin,
        _WidgetsInTemplateMixin, main_widget, _ENME, template) {
      return declare([ _WidgetBase, _TemplatedMixin, main_widget,
          _WidgetsInTemplateMixin ], {

        // template string.
        templateString : template,

              /**
         * Button handler.
         */
        _handler : null,

        /**
         * Define if the button is selected by default.
         */
        selectedButton : false,

        /**
         * Post create cycle.
         */
        postCreate : function() {
          if (this._handler != null) {
            dojo.subscribe("/encuestame/hashtag/buttons", this,
                this._switchButton);
            dojo.connect(this._button, "onclick", dojo.hitch(this,
                function(event) {
                  this._handler.onClick();
                }));
            this._handler.init(this);
            if (this.selectedButton) {
              dojo.addClass(this.domNode, "selected");
            }
            this._button.appendChild(this._handler.domNode);
          }
        },

        /**
         * review the state of the button.
         */
        _switchButton : function(ref) {
          if (this.domNode == ref.domNode) {
            dojo.addClass(ref.domNode, "selected");
            ref.selectedButton = true;
          } else {
            dojo.removeClass(this.domNode, "selected");
            this.selectedButton = false;
          }
        }

      });
    });