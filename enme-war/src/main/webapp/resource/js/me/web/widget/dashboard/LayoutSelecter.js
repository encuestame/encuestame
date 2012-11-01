define([ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/core/enme",
    "dojo/text!me/web/widget/dashboard/template/layout.html" ], function(
    declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin,
    main_widget, _ENME, template) {
  return declare([ _WidgetBase, _TemplatedMixin, main_widget,
      _WidgetsInTemplateMixin ], {

    // template string.
    templateString : template,

     /*
      *
      */
     _selectLayout : function(layout) {
         if (typeof layout == "string") {
             dojo.publish("/encuestame/dashboard/grid/layout", [layout]);
         }
     },

     /*
      *
      */
     postCreate : function() {
          console.info("layouta", this.layouta);
          dojo.connect(this.layouta, "onclick", dojo.hitch(this, function() {
              this._selectLayout("A");
          }));
          dojo.connect(this.layoutaa, "onclick", dojo.hitch(this,function() {
              this._selectLayout("AA");
          }));
          dojo.connect(this.layoutba, "onclick", dojo.hitch(this, function() {
              this._selectLayout("BA");
          }));
          dojo.connect(this.layoutab, "onclick", dojo.hitch(this,function() {
              this._selectLayout("AB");
          }));
          dojo.connect(this.layoutaaa, "onclick", dojo.hitch(this,function() {
              this._selectLayout("AAA");
          }));
     }

  });
});