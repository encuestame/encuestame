define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/CheckBox",
         "dijit/form/NumberSpinner",
         "dijit/registry",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/support/PublishSupport",
         "me/core/support/ContextSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/options/templates/constrainNumberPicker.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                CheckBox,
                NumberSpinner,
                registry,
                main_widget,
                PublishSupport,
                ContextSupport,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, PublishSupport, ContextSupport, _WidgetsInTemplateMixin ], {

            // Template string.
            templateString: template,

            /*
            * Allow Repeated Votes.
            */
           checkWidget: null,

           /*
            *
            */
           numberSpinner: null,

           /*
            *
            */
           label: "Allow Repeated Votes.",

           /*
            *
            */
           contextPath: _ENME.config( "contextPath" ),

           /*
            * To enable publish support, replace null value for publish valid url.
            * eg: /encuestame/tweetpoll/autosave
            */
           publish_url: null,

           /*
            *
            */
           options: {
	           checked: false,
	           items: 2
           },

           constraints_custom: { min:2, max:10 },

           /*
            *
            */
           postCreate: function() {
               this.checkWidget = registry.byId("check_widget_" + this.id );
               this.checkWidget.onChange = dojo.hitch( this, function( event ) {
                   if ( event ) {
                       dojo.removeClass( this._repeatedNumbers, "defaultDisplayHide");
                   } else {
                       dojo.addClass( this._repeatedNumbers, "defaultDisplayHide");
                   }
                   this.options.checked = event;
                   this.publish({});
               });
               this.numberSpinner = new NumberSpinner({
                   value: this.options.items,
                   constraints: this.constraints_custom,
                   intermediateChanges: true,
                   style: "width:100px"
               });
               this._spinner.appendChild( this.numberSpinner.domNode );
           },

           /*
            *
            */
           getOptions: function() {
               if ( this.checkWidget.get( "checked" ) ) {
                               dojo.mixin( this.options, {
                               checked: true,
                               items: this.numberSpinner.get("value")
               });
               }
               return this.options;
           }

    });
});
