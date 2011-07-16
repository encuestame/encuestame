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
        this.standWidget = dijit.byId("standby");
                if (this.userWidget == null || this.passWidget == null
                || this.emailWidget == null || this.realWidget == null
                || this.standWidget == null) {
        } else {
             dojo.connect(this._submit, "onclick", dojo.hitch(this, function(event) {
                 console.debug("standby init");
                 this.standWidget.startup();
                 this.standWidget.start();
                 console.debug("standby init 2");
                 this._checkValidWidgets();
             }));
             dojo.connect(this._input, "ondoubleclick", dojo.hitch(this, function(event) {
                 console.debug("calm down cowboy !!");
             }));
        }
    },

    createNewAccountService : function(){
         console.debug("createNewAccountService signupForm", signupForm);
         signupForm.submit();
    },

    _checkValidWidgets : function(){
        console.debug("standby init 3");
        console.debug("standby init 3",this.userWidget.isValid);
        console.debug("standby init 3", this.passWidget.isValid);
        console.debug("standby init 3",this.emailWidget.isValid);
        console.debug("standby init 3",this.realWidget.isValid);
        if(this.userWidget.isValid && this.passWidget.isValid && this.emailWidget.isValid && this.realWidget.isValid){
            console.debug("_checkValidWidgets 1");
            this.createNewAccountService(this.userWidget, this.passWidget, this.emailWidget, this.realWidget);
        } else {
            console.debug("_checkValidWidgets 2");
            this.standWidget.stop();
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