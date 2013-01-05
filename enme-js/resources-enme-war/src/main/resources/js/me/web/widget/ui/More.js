define([ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "me/core/main_widgets/EnmeMainLayoutWidget", "me/core/enme",
    "dojo/text!me/web/widget/ui/templates/more.html" ], function(declare,
    _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, main_widget,
    _ENME, template) {
  return declare([ _WidgetBase, _TemplatedMixin, main_widget,
      _WidgetsInTemplateMixin ], {

    // template string.
    templateString : template,

    /*
     *
     */
    pagination : {
      _maxResults : 0,
      _start : 0
    },

    /*
     *
     */
    postCreate : function() {
      dojo.connect(this._stream, "onclick", dojo.hitch(this, function(
          event) {
        if (dojo.isFunction(this.loadItems)) {
          this.loadItems();
          this.pagination._start = this.pagination._start
              + this.pagination._maxResults;
        }
      }));
    },

    /**
     *
     */
    loadItems : function() {

    },

    /**
     *
     */
    hide : function() {
      dojo.addClass(this.domNode, "hidden");
    },

    /**
     *
     */
    show : function() {
      dojo.removeClass(this.domNode, "hidden");
    }

  });
});