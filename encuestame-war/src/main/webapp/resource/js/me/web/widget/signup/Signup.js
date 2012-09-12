dojo.provide("encuestame.org.core.commons.signup.Signup");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.declare("encuestame.org.core.commons.signup.Signup",
        [dijit._Widget, dijit._Templated ], {
    templatePath : dojo.moduleUrl("encuestame.org.core.commons.signup", "templates/signup.html"),

    widgetsInTemplate : true,

    value : 'Sign Up Now',

    userWidget: null,
    passWidget: null,
    emailWidget: null,
    realWidget: null,
    standWidget: null,

    /*
     *
     */
    postCreate : function() {
        this.userWidget = dijit.byId("usrva");
        this.passWidget = dijit.byId("pssw");
        this.emailWidget = dijit.byId("em");
        this.realWidget = dijit.byId("rm");
                if (this.userWidget == null || this.passWidget == null
                || this.emailWidget == null || this.realWidget == null) {
                    console.error("NO WIDGETS FOUND");
                }
                //dojo.connect(this._submit, "onclick", dojo.hitch(this, this._onSubmit()));
                dojo.connect(this._input, "ondoubleclick", dojo.hitch(this, function(event) {
                    console.debug("calm down cowboy !!");
                }));
        //}
    },

    _onSubmit : function(event) {
        dojo.stopEvent(event);
        dijit.byId("standby").startup();
        dijit.byId("standby").start();
        this._checkValidWidgets();
    },

    createNewAccountService : function(){
         //console.debug("createNewAccountService signupForm", signupForm);
         signupForm.submit();
    },

    _checkValidWidgets : function(){
        console.debug("standby init 3");
        if(this.userWidget.isValid && this.passWidget.isValid && this.emailWidget.isValid && this.realWidget.isValid){
            //console.debug("_checkValidWidgets 1");
            this.createNewAccountService(this.userWidget, this.passWidget, this.emailWidget, this.realWidget);
        } else {
            //console.debug("_checkValidWidgets 2");
            dijit.byId("standby").stop();
            this.userWidget.recheck("username");
            this.passWidget.validatePassword();
            this.emailWidget.recheck("email");
            this.realWidget.recheck("real_name");
        }
    },

    _create : function(event){
        this.uploadImage();
    }
});