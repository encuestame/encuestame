define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme",
     "dojo/text!me/web/widget/stream/templates/frontEndItem.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

      // template string.
        templateString : template,

        contextPath : _ENME.config('contextPaht'),
        questionName : "",
        id : 0,
        owner : "dasd",
        votes : 0,
        views : 0,
        relativeTime: '',
        url : "#",

        /*
         * Post create.
         */
        postCreate : function() {
            // if (this._hashtag) {
            //    // this._hashtag.href = encuestame.contextDefault+"/tag/"+this.hashTagName+"/";
            // }
        },

        _geTtotalVotes : function(){

        }

  });
});