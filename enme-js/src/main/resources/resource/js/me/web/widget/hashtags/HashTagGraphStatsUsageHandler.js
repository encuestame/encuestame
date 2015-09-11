/**
 * Button handler to customize the button behaviour.
 */
define(
    [ "dojo/_base/declare",
      "dijit/_WidgetBase",
      "dijit/_TemplatedMixin",
      "dijit/_WidgetsInTemplateMixin",
      "me/core/main_widgets/EnmeMainLayoutWidget",
      "me/core/enme",
      "dojo/number",
      "dojo/text!me/web/widget/hashtags/template/hashTagGraphStatsUsageHandler.html" ],
    function(declare,
            _WidgetBase,
            _TemplatedMixin,
            _WidgetsInTemplateMixin,
            main_widget,
            _ENME,
            number,
            template) {
      return declare([
            _WidgetBase,
            _TemplatedMixin,
            main_widget,
            _WidgetsInTemplateMixin ], {

        /**
         *  Template string.
         * @property templateString
         */
        templateString : template,

        /**
         *
         * @property
         */
        data : {},

        /**
         * Default period if not set on created process.
         * @property period
         */
        period : _ENME.YEAR,

        /**
         *
         * @property
         */
        _buttonRef : null,

        /**
         * Initialize the button handler.
         *
         * @param ref
         *            {Object} button reference.
         */
        init : function(ref) {
          this._buttonRef = ref;
          this._dt1.innerHTML = this.data.title;
          this._dt2.innerHTML = number.format(this.data.value, {
            places : 0
          });
          this._dt3.innerHTML = this.data.label;
        },

        /**
         *
         */
        onClick : function(event) {
          // zthis.stopEvent(event);
          // unselect the others buttons
          dojo.publish("/encuestame/hashtag/buttons",[ this._buttonRef ]);
          // display a new chart on hashtag wrapper graphs.
          dojo.publish("/encuestame/hashtag/chart/new", [ this.data.filter, this.period ]);
        }
    });
});