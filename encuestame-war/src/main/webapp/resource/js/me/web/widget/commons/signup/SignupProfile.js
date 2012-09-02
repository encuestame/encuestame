dojo.provide("encuestame.org.core.commons.signup.SignupProfile");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.declare("encuestame.org.core.commons.signup.SignupProfile",
        [dijit._Widget, dijit._Templated ], {
    templatePath : dojo.moduleUrl("encuestame.org.core.commons.signup", "templates/profile.html"),

    widgetsInTemplate : true,

    email : '',

    fullName : '',

    username : '',

    context : '/',

    imagePath : '/',

    contextPath : '',

    _size : ["preview", "default", "web"],

    upgradeProfileService : encuestame.service.list.upgradeProfile,

    postCreate : function() {
        console.debug("username", this.username);
        console.debug("email", this.email);
        console.debug("fullName", this.fullName);
        console.debug("dijit.byId", dijit.byId("completeName"));
        var name = dijit.byId("completeName");
        name.onChange = dojo.hitch(this, function(){
            var name = dijit.byId("completeName").get('value');
            console.debug("complete Name", name);
            var d = dojo.hitch(this, function(respose){
                console.debug(response);
            });
            this._upgradeProfile("completeName", name);
        });
    },

    /**
     * Upgrade Profile.
     * @param property
     * @param value
     * @param response
     */
    _upgradeProfile : function(property, value){
        console.debug(property, value);
        var params = {
                 "property" : property,
                "value" : value
        };
        var load = function(response){
            console.debug("response", response);
        };
        var error = function(error) {
            console.debug("error", error);
        };
        encuestame.service.xhrGet(
                encuestame.service.list.upgradeProfile, params, load, error);
    },

    /**
     * Upgrade CompleteName
     * @param event
     */
    _upgradeCompleteName : function(event){

    },

    _upgradeEmail : function(event){
        dojo.stopEvent(event);
    }

});