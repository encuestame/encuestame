define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/menu/SettingsButton",
         "me/core/enme",
         "dojo/text!me/web/widget/menu/template/settings_switch.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                SettingsButton,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
           templateString : template,

            //
          _widget_references : [],

          /**
           * Post create.
           */
          postCreate : function() {
             dojo.query("> [data-dojo-type]", this.srcNodeRef).forEach(
                       dojo.hitch(this, function(node) {
                          _ENME.log(node);
                          //this._responses.appendChild(node);
                          //var widget = dijit.byId()
//                          /console.log('widget', node);
                          var enabled = node.getAttribute("data-enabled");
                          this._createDetail(node.getAttribute('id'), node.getAttribute('data-label'), node, enabled);
                          this._cretateButton(node.getAttribute('id'), node.getAttribute('data-label'), node.getAttribute('data-label'), enabled);
                       })
                   );
             // hide details
               dojo.subscribe("/encuestame/settings/hide/all", this, dojo.hitch(this, function(type) {
                 dojo.query("div.setting-detail", this._detail).forEach(
                           dojo.hitch(this, function(node) {
                             dojo.addClass(node, "hidden");
                           })
                   );
               }));
          },

          /*
           *
           */
         _createDetail : function(id, provider, widget, enabled) {
           //_widget_references.
           if (!_ENME.getBoolean(enabled)) {
             dojo.addClass(widget, "hidden");
           }
             dojo.addClass(widget, "setting-detail");
             this._detail.appendChild(widget);
         },

         /**
          * Create a settings button.
          */
         _cretateButton : function(id, label, provider, enabled) {
             var widget = new SettingsButton(
                     {
                         ref_id : id,
                         label : label,
                         provider : provider
                     });
             this._buttons.appendChild(widget.domNode);
             if (_ENME.getBoolean(enabled)) {
                dojo.addClass(widget.domNode, "selected");
             }
         }

    });
});