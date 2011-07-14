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
    postCreate : function(){
        this.inherited(arguments);
    },
    /*
    *
    */
   _validate : function(event){
           this._loadService(
           encuestame.service.publicService.validate.email, {
           context : this.enviroment,
           email : this._input.value
       }, this.error);
   },

    error : function(error) {
       console.debug("error", error);
    }

});