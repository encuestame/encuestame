dojo.provide("encuestame.org.core.commons.questions.patterns.SingleResponse");

dojo.require("dijit.form.TextBox");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.Icon');
dojo.require('encuestame.org.core.commons.questions.patterns.AbstractPattern');

dojo.declare(
    "encuestame.org.core.commons.questions.patterns.SingleResponse",
    [encuestame.org.core.commons.questions.patterns.AbstractPattern],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.questions.patterns", "templates/single.html"),

        widgetsInTemplate: true,

        dndEnabled : false,

        postCreate : function(){
            if (this.dndEnabled) {
                dojo.addClass(this._handle, "dojoDndHandle");
            }
        },

        /*
         * response.
         */
        getResponse : function() {
            if (this._single) {
                var response =  dijit.byId(this._single).get('value');
                console.debug("getResponse", response);
                return response;
            } else {
                return null;
            }
        }
});