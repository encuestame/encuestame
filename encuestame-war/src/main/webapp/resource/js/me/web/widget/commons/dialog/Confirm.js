dojo.provide("encuestame.org.core.commons.dialog.Confirm");

dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require("dijit.form.Button");

dojo.declare(
    "encuestame.org.core.commons.dialog.Confirm",
    [encuestame.org.core.commons.dialog.Dialog],{

        //templatePath: dojo.moduleUrl("encuestame.org.core.commons.dialog", "templates/dialog.html"),

        draggable : false,

        _question : "Do you want confirm your action?",

        /*
         * post create.
         */
        postCreate : function(){
             //console.debug("functionYes");
            //this._modalconnects = [];
            this.containerNode.appendChild(this._createContent());
            this.titleNode.innerHTML = this._question;
            this.inherited(arguments);
        },

        functionYes : function(){
            //console.debug("functionYes");
        },

        functionNo : function(){
            this.hide();
        },

        /*
         * Confirm dom node content.
         * @returns
         */
        _createContent : function(){
            var div =  dojo.doc.createElement('div');
            var buttonYes = new dijit.form.Button({
                label: "Confirm",
                onClick: dojo.hitch(this, function() {
                    this.functionYes();
                })
            });
            div.appendChild(buttonYes.domNode);
            var buttonNo = new dijit.form.Button({
                label: "Cancel",
                onClick: dojo.hitch(this, function() {
                    this.functionNo();
                })
            });
            div.appendChild(buttonNo.domNode);
            console.debug("div", div);
            return div;
        }
});
