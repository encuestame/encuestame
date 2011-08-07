dojo.provide("encuestame.org.core.commons.profile.UploadProfilePicture");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.form.RadioButton");
dojo.require("encuestame.org.core.shared.utils.AccountPicture");
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

    username : "",

    pictureSource : "",

    imagePath : '/',

    _uploadImage : function(event){
        this.uploadImage();
    },

    /**
     *
     */
    postCreate : function(){
        console.debug("UploadProfilePicture", this.pictureSource);
        if (this.username != null) {
            this._reloadPicture();
        }
        var radioGravatar = dijit.byId("gravatar_radio");
        if (this.pictureSource == "gravatar") {
            radioGravatar.value('checked', true);
        }
        radioGravatar.onChange = dojo.hitch(this, function(e){
             if (e) {
                 this._updatePictureSource("gravatar");
                 dojo.addClass(this._uploadedForm, "defaultDisplayHide");
             }
        });
        var uploadedRadio = dijit.byId("uploaded_radio");
        if (this.pictureSource == "uploaded") {
            uploadedRadio.value('checked', true);
        }
        uploadedRadio.onChange = dojo.hitch(this, function(e){
             if(e){
                 this._updatePictureSource("uploaded");
                 dojo.removeClass(this._uploadedForm, "defaultDisplayHide");
             }
        });
    },

    /*
     *
     */
    _updatePictureSource : function(value) {
        var params = {
                "data" : value
       };
       console.debug("params", params);
       var load = dojo.hitch(this, function(data) {
           this._reloadPicture();
       });
       var error = function(error) {
           console.error(error);
       };
       encuestame.service.xhrPostParam(
               encuestame.service.list.updatePicture, params, load, error);
    },

    /**
     *
     * @param url
     */
    _reloadPicture : function(){
        dojo.empty(this._pictureWrapper);
        console.debug("_reloadPicture");
        var textData = new encuestame.org.core.shared.utils.AccountPicture({username : this.username, picture_width :"128",
            picture_height : "128", type : "default"}, "a");
        console.log("textData", textData.domNode);
        this._pictureWrapper.appendChild(textData.domNode);
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
                if (ioResponse instanceof Error) {
                    console.error("handle error: " + ioResponse);
                } else {
                    console.debug(args);
                }
            }),
            timeoutSeconds: 2000,
            error: function (res,ioArgs) {
                console.error("handle error: " + res);
                console.error("handle error: " + ioArgs);
            },
            // Callback on successful call:
            load: function(response, ioArgs) {
                // do something
                // ...
                console.debug("response: " + response);
                console.debug("ioArgs: " + ioArgs);
                // return the response for succeeding callbacks
                return response;
            }
        });
    }

});