define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
         "me/core/enme",
         "dojo/text!me/web/widget/menu/template/dropDownMenuSelect.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                hashTagInfo,
                _ENME,
                 template) {

            "use strict";

            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
           templateString : template,

            /*
            * default text.
            */
           defaultText : "Select the Action...",

           /*
            * post create.
            */
           postCreate : function() {
             this._createItems();
           },

           /**
            * Append item.
            */
           _appendItem : function (menuItem) {
              this._addItem(menuItem.domNode);
           },

           /**
            * Create default Items.
            */
           _createItems : function() {
               // new poll
  //             var newPoll = new encuestame.org.core.shared.utils.DropDownMenuItem({
  //                         label : "New Poll",
  //                         url : "/user/poll/new"
  //                     });
               //this._appendItem(newPoll);
           },

           /*
            * add item on drop down menu.
            */
           _addItem : function(node) {
               dojo.place(node, this._items);
           }

    });
});