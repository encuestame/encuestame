define([ "dojo/_base/declare",
         "dojo/query",
         "dojo/cookie",
         "dojo/on",
         "dojo/dom-attr",
         "dojo/_base/event",
         "dojo/_base/array",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dojox/widget/Standby",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/ui/templates/upgradeBar.html" ], function(
    declare,
    query,
    cookie,
    on,
    domAttr,
    event,
    array,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    Standby,
    main_widget,
    _ENME, template) {
  return declare([ _WidgetBase, _TemplatedMixin, main_widget,
      _WidgetsInTemplateMixin ], {

    // template string.
    templateString: template,

    //
    validate: true,

    /**
     *
     * @method
     */
    postCreate : function() {
       var isHidden = cookie("enme-active-bar") || false,
       domNode = this.domNode,
       p = this;
       if (!this.validate && !isHidden) {
          // get all internal messages
          dojo.removeClass(domNode, 'hidden');
          var messages = query(".up-message", this.srcNodeRef),
          total = messages.length;
          domNode = this._content;
          if (total > 0) {
               messages.forEach(dojo.hitch(this, function(node, index) {
                  query('a', index).forEach(function(node) {
                      on(node, "click", function(e) {
                          event.stop(e);
                          var url = domAttr.get(node, 'data-href');
                          p.setCookie();
                          window.location.href = url;
                        });
                  });
                  domNode.appendChild(index);
               }, this));
               // close the bar
               on(this._close, "click", function(e) {
                    event.stop(e);
                    p.setCookie();
                    dojo.destroy(domNode);
               });
          }
       } else {
          dojo.destroy(this.domNode);
       }
    },

    /**
     * Set the cookie.
     * @method
     */
    setCookie : function() {
      cookie("enme-active-bar", true, { expires:3650 });
    }
  });
});