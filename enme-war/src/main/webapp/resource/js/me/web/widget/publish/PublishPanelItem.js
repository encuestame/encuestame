define([
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
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, ContextSupport, _WidgetsInTemplateMixin], {

         /*
          * template string.
          */
         templateString : template,

         panelWidget : null,

         contentWidget : null,

         title : "replace this title",

         defaultDisplayHide : true,

         _open : false,

         postCreate : function(){
             this._content.appendChild(this.contentWidget.domNode);
             this.panelWidget = new Wipe(this._content, 300, 200);
             dojo.connect(this._title, "onclick", dojo.hitch(this, this._onClickItem));
             dojo.subscribe("/encuestame/support/panel/remote/select", this, "remoteClick");
             dojo.subscribe("/encuestame/support/panel/unselect", this, "unselect");
             if (this.defaultDisplayHide) {
                 dojo.addClass(this._content, "defaultDisplayHide");
             } else {
                 this.panelWidget.wipeOutOne();
                 dojo.removeClass(this._content, "defaultDisplayHide");
             }
         },

         /*
          *
          */
         unselect : function(id){
             if (this._open && this != id) {
                 this._switchSuport();
             }
         },

         /*
          *
          */
         remoteClick : function(id){
             if(this.contentWidget == id){
                 if (!this._open) {
                     this._switchSuport();
                 }
             }
         },

         /*
          *
          */
         _switchSuport : function(){
             if(this._open){
                 this.panelWidget.wipeOutOne();
                 //dojo.addClass(this._content, "defaultDisplayHide");
            } else {
                 this.panelWidget.wipeInOne();
                 dojo.removeClass(this._content, "defaultDisplayHide");
            }
            this._open = !this._open;
         },

         _onClickItem : function(){
             dojo.publish("/encuestame/support/panel/unselect", [this]);
             this._switchSuport();
         }

    });
});