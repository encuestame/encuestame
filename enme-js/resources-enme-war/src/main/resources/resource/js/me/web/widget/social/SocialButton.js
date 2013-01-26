define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/registry",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/hash",
         "dojo/io-query",
         "dojo/text!me/web/widget/social/templates/socialButton.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                registry,
                main_widget,
                _ENME,
                hash,
                ioQuery,
                template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

      /*
       * template string.
       */
      templateString : template,

     /*
      *
      */
     postCreate : function() {
         dojo.subscribe("/encuestame/social/clean/buttons", this, dojo.hitch(this, function(type) {
             dojo.removeClass(this.domNode, "selected");
         }));
         var _hash = ioQuery.queryToObject(hash());
         if (_hash.provider && _hash.provider == this.id) {
             dojo.addClass(this.domNode, "selected");
             this._loadAccountInterface(_hash.provider);
         }
     },

     /**
      *
      * @param id
      */
     _loadAccountInterface : function(id) {
         //console.debug("_loadAccountInterface ", id.toLowerCase()+"Detail");
         var widget = registry.byId(id.toLowerCase()+"Detail");
         //console.debug("widget ", widget);
         dojo.publish("/encuestame/social/change", [widget]);
         dojo.publish("/encuestame/social/" + id + "/loadAccounts");
     },

     /**
      *
      * @param event
      */
     _click : function(event) {
         dojo.publish("/encuestame/social/clean/buttons");
         ioQuery.queryToObject(hash());
         //console.debug("click button");
         this._loadAccountInterface(this.id);
         params = {
            provider : this.id
         };
         hash(ioQuery.objectToQuery(params));
         dojo.addClass(this.domNode, "selected");
     }

   });
});