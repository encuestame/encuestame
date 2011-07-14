dojo.provide("encuestame.org.core.commons.validator.RealNameValidator");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.require("encuestame.org.core.commons.validator.AbstractValidatorWidget");

dojo.declare("encuestame.org.core.commons.validator.RealNameValidator",
        [encuestame.org.core.commons.validator.AbstractValidatorWidget], {
    templatePath : dojo.moduleUrl("encuestame.org.core.commons.validator", "templates/realNameValidator.html"),
    widgetsInTemplate : true,
    focusDefault: true,
    postCreate : function(){
        this.inherited(arguments);
    },
    /*
    *
    */
   _validate : function(event){
           this._loadService(
       encuestame.service.publicService.validate.realName, {
           context : this.enviroment,
           real_name : "432432342"
       }, this.error);
   },

    error : function(error) {
       console.debug("error", error);
    }

});