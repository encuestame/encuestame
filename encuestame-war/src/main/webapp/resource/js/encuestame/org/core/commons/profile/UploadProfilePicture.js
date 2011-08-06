dojo.provide("encuestame.org.core.commons.profile.UploadProfilePicture");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.require("dojox.form.FileUploader");

dojo.require("encuestame.org.core.commons.profile.ProfileSupport");

/**
*
*/
dojo.declare(
   "encuestame.org.core.commons.profile.UploadProfilePicture",
   [dijit._Widget, dijit._Templated],{
       templatePath: dojo.moduleUrl("encuestame.org.core.commons.profile", "templates/profilePicture.html"),

    widgetsInTemplate : true,

    contextPath : encuestame.contextDefault,

    _size : ["preview", "default", "web"],

    upgradeProfileService : encuestame.service.list.upgradeProfile,

    profile : null,

    imagePath : '/',

    _uploadImage : function(event){
        this.uploadImage();
    },

    /**
     *
     */
    postCreate : function(){
        console.debug("UploadProfilePicture");
        var profileSupport = new encuestame.org.core.commons.profile.ProfileSupport();
        this.profile = profileSupport.getMyProfile();
        console.info("profile ", this.profile);
    },

    /**
     *
     * @param url
     */
    _showPicture : function(url){
        console.log("_pictureWrapper", this._pictureWrapper);
        dojo.empty(this._pictureWrapper);
        var textData = dojo.doc.createElement('img');
        textData.src = url;
        console.log("textData", textData);
        this._pictureWrapper.appendChild(textData);
    },


    /**
     *
     */
    uploadImage : function() {
        dojo.io.iframe.send({
            url: this.contextPath+"/file/upload/profile",
            form : "imageForm",
            handleAs : "html",
            method: "POST",
            handle : dojo.hitch(this, function(ioResponse, args) {
                console.info("form", dojo.byId("imageForm"));
                if (ioResponse instanceof Error) {
                    console.error("handle error: " + ioResponse);
                } else {
                    var url =  this.contextPath + "/picture/profile/admin/" + this._size[1];
                    console.debug(args);
                    console.info("image path response: " + this.imagePath+ "admin/" + this._size[1]);
                    this._showPicture(url);
                }
            }),
            timeoutSeconds: 200,
            error: function (res,ioArgs) {alert(res);},
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