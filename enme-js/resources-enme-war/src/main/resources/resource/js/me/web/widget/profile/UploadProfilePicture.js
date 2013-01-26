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
         "me/core/URLServices",
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
                URLServices,
                ioIframe,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

                /**
                 * template string.
                 * @property templateString
                 */
                templateString : template,

                /**
                 *
                 * @property contextPath
                 */
                contextPath : _ENME.config('contextPath'),

                /**
                 * i18n Messages
                 * @property i18nMessage
                 */
                i18nMessage : {
                  settings_config_picture_title : _ENME.getMessage("settings_config_picture_title"),
                  settings_config_picture_description : _ENME.getMessage("settings_config_picture_description"),
                  settings_config_picture_own : _ENME.getMessage("settings_config_picture_own"),
                  settings_config_picture_restrictions : _ENME.getMessage("settings_config_picture_restrictions"),
                  commons_update :  _ENME.getMessage("commons_update")
                },

                /**
                 *
                 * @property _size
                 */
                _size : ["preview", "default", "web"],

                /**
                 *
                 * @property
                 */
                upgradeProfileService : 'encuestame.service.list.upgradeProfile',

                /**
                 *
                 * @property
                 */
                username : "",

                /**
                 *
                 * @property
                 */
                pictureSource : "",

                /**
                 *
                 * @property
                 */
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
                 * @method postCreate
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

                /**
                 *
                 * @method
                 */
                startup : function () {
                  var uploadedRadio = registry.byId("uploaded_radio");
                    uploadedRadio.onChange = dojo.hitch(this, function(e) {
                        if(e){
                            this._updatePictureSource("uploaded");
                            dojo.removeClass(this._uploadedForm, "defaultDisplayHide");
                        }
                   });

                },


                /**
                 *
                 * @method
                 */
                _updatePictureSource : function(value) {
                      var parent = this,
                      params = {
                                "data" : value
                      };

                      // succes handler
                       var load = dojo.hitch(this, function(data) {
                           parent._reloadPicture();
                           parent.successMesage(data.success.message);
                       });

                       // error handler
                       var error = dojo.hitch(this, function(error) {
                           parent.errorMessage(error);
                       });
                       // encuestame.service.xhrPostParam(
                       //     this.getURLService().service('encuestame.service.list.updatePicture'), params, load, error);
                       URLServices.post('encuestame.service.list.updatePicture',  params, load, error , dojo.hitch(this, function() {

                        }));
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
                * Update the image to the server
                * @method uploadImage
                */
                uploadImage : function() {
                  var parent = this;
                  ioIframe.send({
                        url:  this.contextPath + "/file/upload/profile",
                        form : "imageForm",
                        handleAs : "html",
                        method: "POST",
                        handle : dojo.hitch(this, function(ioResponse, args) {
                            if (ioResponse instanceof Error) {
                                this.errorMessage("handle error: " + ioResponse);
                            }
                        }),
                        // time out
                        timeoutSeconds: 2000,
                        // error handler
                        error: dojo.hitch(this,function (res,ioArgs) {
                            console.error("handle error: " + res);
                            console.error("handle error: " + ioArgs);
                            this.errorMessage("error");
                        }),
                        // Callback on successful call:
                        load: dojo.hitch(this, function(response, ioArgs) {
                            this.successMesage(parent.i18nMessage.commons_update);
                            // return the response for succeeding callbacks
                            return response;
                        })
                    });
                }

    });
});