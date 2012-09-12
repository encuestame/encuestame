dojo.provide("encuestame.org.core.commons.validator.EmailValidator");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.require("encuestame.org.core.commons.validator.AbstractValidatorWidget");

dojo.declare("encuestame.org.core.commons.validator.EmailValidator",
        [encuestame.org.core.commons.validator.AbstractValidatorWidget], {
    templatePath : dojo.moduleUrl("encuestame.org.core.commons.validator", "templates/emailValidator.html"),
    widgetsInTemplate : true,

    placeholder : "Write your email",

    postCreate : function(){
        this.inherited(arguments);
    },
    /*
    *
    */
   _validate : function(event) {
           this.inputTextValue = this._input.value;
           this._loadService(
           this.getServiceUrl(), {
           context : this.enviroment,
           email : this._input.value
       }, this.error);
   },

   getServiceUrl : function(){
       return encuestame.service.publicService.validate.email;
   },

    error : function(error) {
       console.debug("error", error);
    }

});