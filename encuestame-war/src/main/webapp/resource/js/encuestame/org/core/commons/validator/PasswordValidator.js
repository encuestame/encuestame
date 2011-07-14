dojo.provide("encuestame.org.core.commons.validator.PasswordValidator");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.require("encuestame.org.core.commons.validator.AbstractValidatorWidget");

dojo.declare("encuestame.org.core.commons.validator.PasswordValidator",
        [encuestame.org.core.commons.validator.AbstractValidatorWidget], {
    templatePath : dojo.moduleUrl("encuestame.org.core.commons.validator", "templates/passwordValidator.html"),
    widgetsInTemplate : true,
    noEvents : true,
    exclude : encuestame.constants.passwordExcludes,
    toolTip : false,
    postCreate : function(){
        this.inherited(arguments);
    },
    _validateBlackListPassword : function(password){
        var valid = false;
        dojo.forEach(encuestame.constants.passwordExcludes,
                dojo.hitch(this,function(item) {
                    console.debug(item);
        }));
        return valid;
    }

});