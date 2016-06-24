define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/social/SocialPickerAccount",
         "me/core/enme",
         "dojo/text!me/web/widget/social/templates/socialPickerAccount.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                SocialPickerAccount,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

          /*
           * Template string.
           */
           templateString: template,

           account: {},

           widgetsInTemplate: true,

           parentPicker: null,

           selected: false,

           /**
            * Post create cycle.
            */
           postCreate: function() {
               this._accountProviderIcon.src = _ENME.shortPicture( this.account.type_account );
               dojo.connect( this.domNode, "onclick", this, dojo.hitch( this, function() {

                   //Console.debug("pick account ", this.account);
                   //console.debug("pick account ", this.selected);
                   this.selected = !this.selected;
                   if ( this.selected ) {
                       this.markAsSelected();
                   } else {
                       this.unSelected();
                   }
                   dojo.publish("/encuestame/social/picker/counter/reload");
               }) );

               //Mark as selected default items.
               if ( this.account.default_selected ) {
                   this.selected = true;
                   this.markAsSelected();
               }
           },

           markAsSelected: function() {
               dojo.addClass( this.domNode, "selected");
               this.selected = true;
           },

           unSelected: function() {
               dojo.removeClass( this.domNode, "selected");
               this.selected = false;
           },

           show: function() {
                   dojo.removeClass( this.domNode, "defaultDisplayHide");
           },

           showIsSelected: function() {
               if ( !this.selected ) {
                   dojo.addClass( this.domNode, "defaultDisplayHide");
               }
           },

           _select: function( b ) {
               this.selected = b;
            }

   });
});
