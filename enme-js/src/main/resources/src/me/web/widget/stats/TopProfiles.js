define( [ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/web/widget/stream/HashTagInfo",
    "me/core/enme",
    "me/web/widget/rated/RatedProfile" ], function(
        declare,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        main_widget,
        hashTagInfo,
        _ENME,
        ratedProfile ) {
  return declare( [ _WidgetBase,
      _TemplatedMixin,
      main_widget,
      ratedProfile,
      _WidgetsInTemplateMixin ], {

    // Template string.
    //templateString : template,

    service: "encuestame.service.list.rate.profile"

   /*
    *
    */

//   PostCreate : function() {
//     if (this.service != null) {
//         this._loadItems();
//     }
//    }
  });
});

