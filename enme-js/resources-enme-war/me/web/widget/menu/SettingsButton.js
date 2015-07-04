define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "dojo/hash",
         "dojo/io-query",
         "me/core/enme",
         "dojo/text!me/web/widget/menu/template/settingsButton.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                hash,
                ioQuery,
                _ENME,
                 template) {

            "use strict";

            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
           templateString : template,

           /**
           *
           */
          postCreate : function() {
              dojo.subscribe("/encuestame/settings/clean/buttons", this, dojo.hitch(this, function(type) {
                  dojo.removeClass(this.domNode, "selected");
              }));
              var _hash = ioQuery.queryToObject(hash());
              if (_hash.provider && _hash.provider == this.id) {
                  this.clickEvent(_hash.provider);
              }
          },

          /**
           *
           */
          clickEvent : function (id) {
            // default click event
            var widget = dijit.byId(id);
            if (widget.domNode) {
              dojo.removeClass(widget.domNode, "hidden");
            }
          },

          /**
           *
           * @param event
           */
          _click : function (event) {
              dojo.publish("/encuestame/settings/clean/buttons");
              dojo.publish('/encuestame/settings/hide/all');
              //var _hash = ioQuery.queryToObject(hash());
              //console.debug("click button");
              this.clickEvent(this.ref_id);
              var params = {
                 provider : this.provider
              };
              hash(ioQuery.objectToQuery(params));
              dojo.addClass(this.domNode, "selected");
          }

    });
});