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

//dojo.provide("encuestame.org.core.shared.utils.DropDownMenuSelect");
//
//dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
//dojo.require('encuestame.org.core.commons');
//
//dojo.declare("encuestame.org.core.shared.utils.DropDownMenuSelect",
//        [ encuestame.org.main.EnmeMainLayoutWidget ], {
//
//            templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
//                    "template/dropDownMenuSelect.html"),
//

//
//        });
//
//dojo.declare("encuestame.org.core.shared.utils.DropDownMenuItem",
//        [ encuestame.org.main.EnmeMainLayoutWidget ], {
//
//    /*
//     * template.
//     */
//     templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
//     "template/dropDownMenuItem.html"),
//
//     /*
//      *
//      */
//    label : "",
//
//    /*
//     *
//     */
//    url : "#",
//
//    /*
//     * is called after the properties have been initialized.
//     */
//    postMixInProperties : function() {
//        console.info("label", this.url);
//         var urlConcat = encuestame.contextDefault;
//         urlConcat = urlConcat.concat(this.url);
//         this.url = urlConcat;
//         console.info("label", urlConcat);
//    },
//
//    /*
//     *
//     */
//    postCreate : function() {
//
//    }
//
//});