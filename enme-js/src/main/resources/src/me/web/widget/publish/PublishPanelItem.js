define( [
         "dojo/_base/declare",
         "dojo/Deferred",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/utils/ContextSupport",
         "me/web/widget/support/Wipe",
         "me/core/enme",
         "dojo/text!me/web/widget/publish/templates/publishPanelItem.html" ],
        function(
                declare,
                Deferred,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                ContextSupport,
                Wipe,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, ContextSupport, _WidgetsInTemplateMixin ], {

         /*
          * Template string.
          */
         templateString: template,

         panelWidget: null,

         contentWidget: null,

         title: "replace this title",

         defaultDisplayHide: true,

         _open: false,

         postCreate: function() {
             this._content.appendChild( this.contentWidget.domNode );
             dojo.subscribe("/encuestame/support/panel/remote/select", this, "remoteClick");
             dojo.subscribe("/encuestame/support/panel/unselect", this, "unselect");
             if ( this.defaultDisplayHide ) {
                 dojo.addClass( this.domNode, "hidden");
             } else {
                 dojo.removeClass( this.domNode, "hidden");
             }
         },

         /*
          *
          */
         unselect: function( id ) {
            dojo.addClass( this.domNode, "hidden" );
         },

         /*
          *
          */
         remoteClick: function( id ) {
             if ( id === this ) {
                 dojo.removeClass( this.domNode, "hidden" );
             }
         }
    });
});
