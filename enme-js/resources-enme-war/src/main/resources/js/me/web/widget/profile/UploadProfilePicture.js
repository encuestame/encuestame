define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/RadioButton",
         "dijit/registry",
         "me/web/widget/pictures/AccountPicture",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/io/iframe",
         "dojo/text!me/web/widget/profile/templates/profilePicture.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                RadioButton,
                registry,
                AccountPicture,
                main_widget,
                _ENME,
                ioIframe,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            contextPath : _ENME.config('contextPath'),

                i18nMessage : {
                  settings_config_picture_title : _ENME.getMessage("settings_config_picture_title"),
                  settings_config_picture_description : _ENME.getMessage("settings_config_picture_description"),
                  settings_config_picture_own : _ENME.getMessage("settings_config_picture_own"),
                  settings_config_picture_restrictions : _ENME.getMessage("settings_config_picture_restrictions")
                },

                _size : ["preview", "default", "web"],

                upgradeProfileService : 'encuestame.service.list.upgradeProfile',

                username : "",

                pictureSource : "",

                imagePath : '/',

                /**
                 * Trigger upload the image.
                 * @param event
                 */
                _uploadImage : function(event) {
                    this.uploadImage();
                },

                /**
                 *
                 */
                postCreate : function() {
                    if (this.username != null) {
                        this._reloadPicture();
                    }
                    var radioGravatar = registry.byId("gravatar_radio");
                    if (this.pictureSource == "GRAVATAR") {
                        radioGravatar.setValue('checked', true);
                    }
                    radioGravatar.onChange = dojo.hitch(this, function(e) {
                         if (e) {
                             this._updatePictureSource("gravatar");
                             dojo.addClass(this._uploadedForm, "defaultDisplayHide");
                         }
                    });
                    var uploadedRadio = registry.byId("uploaded_radio");
                    if (this.pictureSource === 'UPLOADED') {
                      uploadedRadio.setValue('checked', true);
                    }
                },

                startup : function () {
                  var uploadedRadio = registry.byId("uploaded_radio");
                    uploadedRadio.onChange = dojo.hitch(this, function(e) {
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
                   //console.debug("params", params);

                   var load = dojo.hitch(this, function(data) {
                       this._reloadPicture();
                       this.successMesage(data.success.message);
                   });

                   var error = function(error) {
                       //console.error(error);
                       this.errorMessage(error.message);
                   };
                   encuestame.service.xhrPostParam(
                       this.getURLService().service('encuestame.service.list.updatePicture'), params, load, error);
                },

                /**
                 *
                 * @param url
                 */
                _reloadPicture : function(){
                    dojo.empty(this._pictureWrapper);
                    //console.debug("_reloadPicture");
                    var textData = new AccountPicture({
                      username : this.username,
                      picture_width :"128",
                        picture_height : "128", type : "default"}, "a");
                    //console.log("textData", textData.domNode);
                    this._pictureWrapper.appendChild(textData.domNode);
                },


                /**
                 *
                 */
                uploadImage : function() {
                  ioIframe.send({
                        url:  this.contextPath + "/file/upload/profile",
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
                        error: dojo.hitch(this,function (res,ioArgs) {
                            console.error("handle error: " + res);
                            console.error("handle error: " + ioArgs);
                            this.errorMessage("error");
                        }),
                        // Callback on successful call:
                        load: dojo.hitch(this, function(response, ioArgs) {
                            // do something
                            // ...
                            console.debug("response: " + response);
                            console.debug("ioArgs: " + ioArgs);
                            this.successMesage(response);
                            // return the response for succeeding callbacks
                            return response;
                        })
                    });
                }

    });
});