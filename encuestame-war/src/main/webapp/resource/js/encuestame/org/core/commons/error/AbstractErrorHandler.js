dojo.provide("encuestame.org.core.commons.error.AbstractErrorHandler");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.commons.error.AbstractErrorHandler",
    [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.error", "template/error.html"),

        widgetsInTemplate: true,

        type : "",

        dialog : null,


        postCreate: function() {
            //console.debug("postCreate Abstract Error ");
            /*this.dialog = new dojox.widget.Dialog();
            console.debug("dialog 1", this.dialog);
            this.dialog.set("dimensions", [400, 200]); // [width, height]
            this.dialog.layout(); //starts the resize
            console.debug("dialog 2", this.dialog);
            this._errorBox.appenChild(this.dialog.domNode);*/
        },

        show : function(){
             //console.debug("show");
            if(this.dialog != null){
                this.dialog.show();
            } else {
                //console.info("no error duialog");
            }
        }
    }
);