dojo.provide("encuestame.org.core.commons.signup.SignupProfile");

dojo.require("dojo.io.iframe");

dojo.declare("encuestame.org.core.commons.signup.SignupProfile",
        [dijit._Widget, dijit._Templated ], {
    templatePath : dojo.moduleUrl("encuestame.org.core.commons.signup.", "templates/profile.inc"),

    widgetsInTemplate : true,

    email : '',

    fullName : '',

    username : '',

    postCreate : function() {
        console.debug("username", this.username);
        console.debug("email", this.email);
        console.debug("fullName", this.fullName);
    },

    _uploadImage : function(event){
        this.uploadImage();
    },

    uploadImage : function() {
        dojo.io.iframe.send({
            form : dojo.byId("imageForm"),
            handleAs : "html",
            handle : function(ioResponse, args) {
                if (ioResponse instanceof Error) {
                    console.error("handle error: " + ioResponse);
                } else {
                    console.info("handle response: " + ioResponse);
                }
            },
            // Callback on successful call:
            load: function(response, ioArgs) {
                // do something
                // ...
                console.log("response: " + response);
                console.log("ioArgs: " + ioArgs);
                // return the response for succeeding callbacks
                return response;
            }
        });
    }

});