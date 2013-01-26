define([
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
                template) {
            return declare([dialog], {

	   // template string.
       templateString : template,

       /**
        *
        * @property
        */
       draggable : false,

       /**
        *
        * @property
        */
       label : {
       		question : "Do you want confirm your action?",
       		yes : "Yes",
       		no : "No"
       },

       /*
        * post create.
        */
       postCreate : function() {
            //console.debug("functionYes");
           //this._modalconnects = [];
           this.containerNode.appendChild(this._createContent());
           this.titleNode.innerHTML = this._question;
           this.inherited(arguments);
       },

       /**
        *
        * @method
        */
       functionYes : function(){},

       /**
        *
        * @method
        */
       functionNo : function() {
           this.hide();
       },

       /*
        * Confirm dom node content.
        * @returns
        */
       _createContent : function() {
           var div =  dojo.doc.createElement('div');
           dojo.addClass(div, "web-confirm-wrapper");
           var h2 =  dojo.doc.createElement('h2');
           h2.innerHTML = this.label.question;
           div.appendChild(h2);
           var buttonYes = new Button({
               label: this.label.yes,
               onClick: dojo.hitch(this, function() {
                   this.functionYes();
               })
           });
           div.appendChild(buttonYes.domNode);
           var buttonNo = new Button({
               label: this.label.no,
               onClick: dojo.hitch(this, function() {
                   this.functionNo();
               })
           });
           div.appendChild(buttonNo.domNode);
           return div;
       }

    });
});