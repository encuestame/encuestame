define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/menu/template/dropDownMenuItem.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          /*
           * template string.
           */
          templateString : template,

         /*
          *
          */
          label : "",

          /*
           *
           */
          url : "#",

          /*
           * is called after the properties have been initialized.
           */
          postMixInProperties : function() {
              console.info("label", this.url);
               var urlConcat = _ENME.config('contextPath');
               urlConcat = urlConcat.concat(this.url);
               this.url = urlConcat;
               console.info("label", urlConcat);
          },

          /*
           *
           */
          postCreate : function() {}

    });
});