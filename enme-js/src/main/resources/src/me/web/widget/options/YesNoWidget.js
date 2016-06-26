define( [
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
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

          // Template string.
            templateString: template,

            /**
            * Default value.
            */
           data: false,

           /**
            * Labels.
            */
           labels: [ "Yes", "No" ],

           /*
            *
            */
           optionalParameters: "",

           /*
            *
            */
           labelsMessage: "Click to Change.",

           /**
            * Post Create.
            * @method postCreate
            */
           postCreate: function() {
               if ( this.data !== null ) {
                   this._changeValue();
               }
               new Tooltip({
                   connectId: [ this.id + "_yesNo" ],
                   label: this.labelsMessage
               });
           },

           /**
            * Change Value.
            * @method  _changeValue
            */
           _changeValue: function() {
               if ( this.data ) {
                   this._label.innerHTML = this.labels[ 0 ];
                   dojo.addClass( this._label, "badge-success" );
                   dojo.removeClass( this._label, "badge-important" );
               } else {
                   this._label.innerHTML = this.labels[ 1 ];
                   dojo.addClass( this._label, "badge-important" );
                   dojo.removeClass( this._label, "badge-success" );
               }

               //After change.
               this._onChange( this.optionalParameters );
           },

           /** Change Data. **/
           _change: function( event ) {
                dojo.stopEvent( event );
                this.data = !this.data;
                this._changeValue();
           },

           /**
            * Override.
            */
           _onChange: function() {}

    });
});
