define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/menu/OptionMenuItem",
         "me/web/widget/support/ToggleMenu",
         "me/core/enme",
         "dojo/text!me/web/widget/menu/templates/optionMenu.html" ],
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
                   this._menu.appendChild(widget.domNode);
          }));
       }

    });
});


//dojo.provide("encuestame.org.core.shared.utils.OptionMenu");
//
//dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
//dojo.require('encuestame.org.core.commons.support.ToggleMenu');
//dojo.require('encuestame.org.core.commons');
//
//dojo.declare("encuestame.org.core.shared.utils.OptionMenu",
//            [ encuestame.org.core.commons.support.ToggleMenu ],{
//
//            /*
//             * template.
//             */
//            templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils", "template/optionMenu.html"),

//});
//
///*
// *
// */
//dojo.declare("encuestame.org.core.shared.utils.OptionMenuItem",
//        [encuestame.org.core.commons.support.ToggleMenu], {
//
//            templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
//                    "template/optionMenuItem.html"),
//

//
//});
