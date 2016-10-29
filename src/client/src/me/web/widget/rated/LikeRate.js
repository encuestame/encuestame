define( [
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme",
     "dojo/text!me/web/widget/rated/templates/likeRate.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    _ENME,
     template ) {

  return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

     // Template string.
    templateString: template,

    value: 0,

    negative: false,

    positive: false,

    postCreate: function() {
      this._value.innerHTML = this.value;
      if ( this.positive && this.negative ) {

        // Nothing to do.
      } else if ( this.positive ) {
        dojo.addClass( this._likeRate, "positive");
      } else if ( this.negative ) {
        dojo.addClass( this._likeRate, "negative");
      }
    }

  });
});
