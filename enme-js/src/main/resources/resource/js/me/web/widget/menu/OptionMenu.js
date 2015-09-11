define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/menu/OptionMenuItem",
         "me/web/widget/support/ToggleMenu",
         "me/core/enme",
         "dojo/text!me/web/widget/menu/template/optionMenu.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                OptionMenuItem,
                ToggleMenu,
                _ENME,
                 template) {

        "use strict";

        return declare([ _WidgetBase, _TemplatedMixin, main_widget, ToggleMenu, _WidgetsInTemplateMixin], {

       // template string.
       templateString : template,

       /*
        *
        */
       _openBox : true,

       /*
        * Display icon by default.
        */
       enableIcon : true,

       /*
        *
        */
       _classReplace : "",

       /*
        *
        */
       menu_items : [{
           label : "label1",
           action : function() {
           }},
           {label : "label2",
           action : function() {
           }
       }],

       /*
        *
        */
       postCreate : function() {
           if (this.enableIcon) {
               this.addMenuSupport(this._icon, "click");
           } else {
               //TODO: enable open context menu on click defined dom node.
           }
           this._buildMenus();
       },

       /*
        * Build a item menu.
        */
       _buildMenus : function() {
           dojo.forEach(this.menu_items,
               dojo.hitch(this, function(item, action) {
                    //console.debug("_buildMenus", item);
                   var widget = new OptionMenuItem(
                           {
                               label : item.label,
                               action : item.action
                           });
                   dojo.connect(widget.domNode, "onclick", this, item.action);
                   this._menu.appendChild(widget.domNode);
          }));
       }

    });
});