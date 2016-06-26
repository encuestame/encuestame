define( [ "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dojox/widget/Standby",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/ui/templates/standBy.html" ], function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    Standby,
    main_widget,
    _ENME, template ) {
  return declare( [ _WidgetBase, _TemplatedMixin, main_widget,
      _WidgetsInTemplateMixin ], {

    // Template string.
    templateString: template,

    size: "medium",

    target: "",

    postCreate: function() {
       this.init();
    },

    init: function() {

    },

    start: function() {

        //Console.debug("STAND BY START", standById);
        standById.show();
    },

    stop: function() {

        //Console.debug("STAND BY START", standById);
        standById.hide();
    }

  });
});
