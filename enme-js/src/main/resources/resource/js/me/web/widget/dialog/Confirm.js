define( [
         "dojo/_base/declare",
         "dijit/Dialog",
         "dijit/form/Button",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/dialog/templates/dialog.html" ],
        function(
                declare,
                dialog,
                Button,
                main_widget,
                _ENME,
                template ) {
            return declare( [ dialog ], {

       // Template string.
       templateString: template,

       /**
        *
        * @property
        */
       draggable: false,

       /**
        * Dialog title
        * @property
        */
       title: "",

       /**
        *
        * @property
        */
       label: {
          question: "Do you want confirm your action?",
          yes: "Yes",
          no: "No"
       },

       /*
        * Post create.
        */
       postCreate: function() {
          if ( this.content_widget ) {
             this.containerNode.appendChild( this.content_widget );
          }
          this._buttons.appendChild( this._createContent() );
          this.inherited( arguments );
       },

       /**
        *
        * @method
        */
       functionYes: function() {},

       /**
        *
        * @method
        */
       functionNo: function() {
           this.hide();
       },

       /*
        * Confirm dom node content.
        * @returns
        */
       _createContent: function() {
           var div =  dojo.doc.createElement( "span" );
           dojo.addClass( div, "web-confirm-wrapper");
           var buttonYes = new Button({
               label: this.label.yes,
               "class": "success",
               onClick: dojo.hitch( this, function() {
                   this.functionYes();
               })
           });
           div.appendChild( buttonYes.domNode );
           var buttonNo = new Button({
               label: this.label.no,
               "class":"danger",
               onClick: dojo.hitch( this, function() {
                   this.functionNo();
               })
           });
           div.appendChild( buttonNo.domNode );
           return div;
       }

    });
});
