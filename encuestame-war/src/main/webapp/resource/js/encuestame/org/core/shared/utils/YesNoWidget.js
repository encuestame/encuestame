dojo.provide("encuestame.org.core.shared.utils.YesNoWidget");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.Tooltip");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.YesNoWidget",
    [encuestame.org.main.EnmeMainLayoutWidget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/yesno.html"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,

        data : false,

        labels : ['Yes', 'No'],

        labelsMessage : "Click to Change.",

        /** Post Create. **/
        postCreate : function(){
            if(this.data != null){
                this._changeValue();
            }
            new dijit.Tooltip({
                connectId: [this.id+"_yesNo"],
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
            this._onChange();
        },

        /** Change Data. **/
        _change : function(event){
             dojo.stopEvent(event);
             this.data = !this.data;
             this._changeValue();
        },

        /**
         * Override.
         */
        _onChange : function(){
        }
});
