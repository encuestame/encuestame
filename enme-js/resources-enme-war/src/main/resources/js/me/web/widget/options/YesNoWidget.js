define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "dijit/Tooltip",
         "me/core/enme",
         "dojo/text!me/web/widget/options/templates/yesno.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                Tooltip,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /**
            * Default value.
            */
           data : false,

           /**
            * Labels.
            */
           labels : ['Yes', 'No'],

           /*
            *
            */
           optionalParameters : "",

           /*
            *
            */
           labelsMessage : "Click to Change.",

           /** Post Create. **/
           postCreate : function() {
               if (this.data != null) {
                   this._changeValue();
               }
               new Tooltip({
                   connectId: [this.id + "_yesNo"],
                   label: this.labelsMessage
               });
           },

           /** Change Value. **/
           _changeValue : function(){
               if(this.data){
                   this._label.innerHTML = this.labels[0];
               } else {
                   this._label.innerHTML = this.labels[1];
               }
               //after change.
               this._onChange(this.optionalParameters);
           },

           /** Change Data. **/
           _change : function(event) {
                dojo.stopEvent(event);
                this.data = !this.data;
                this._changeValue();
           },

           /**
            * Override.
            */
           _onChange : function(){}

    });
});