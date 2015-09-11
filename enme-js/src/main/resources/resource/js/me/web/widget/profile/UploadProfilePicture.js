/*
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.146
 *  @module UploadProfilePicture
 *  @namespace Widget
 *  @class UploadProfilePicture
 */
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
                 * Create the radio buttons dynamically picture
                 * @method postCreate
                 */
                postCreate : function() {
                    if (this.username !== null) {
                        this._reloadPicture();
                    }
                    // create the gravatar radio
                    var radioGravatar = new RadioButton({
                        value : 'GRAVATAR',
                        checked: this.pictureSource == "GRAVATAR"
                    });
                    radioGravatar.onChange = dojo.hitch(this, function(e) {
                         if (e) {
                             this._updatePictureSource("gravatar");
                             dojo.addClass(this._uploadedForm, "defaultDisplayHide");
                         }
                    });
                    this._gravatar_source.appendChild(radioGravatar.domNode);

                    // create the uploader radio
                    var uploadedRadio = new RadioButton({
                        value : 'UPLOADED',
                        checked: this.pictureSource == "UPLOADED"
                    });
                    uploadedRadio.onChange = dojo.hitch(this, function(e) {
                        if (e) {
                            this._updatePictureSource("uploaded");
                            dojo.removeClass(this._uploadedForm, "defaultDisplayHide");
                        }
                   });
                    this._upload_source.appendChild(uploadedRadio.domNode);
                },

                /**
                 *
                 * @method _updatePictureSource
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
                       URLServices.post('encuestame.service.list.updatePicture',  params, load, error , dojo.hitch(this, function() {

                        }));
                },

                /**
                 * Reload the picture.
                 * @param url
                 * @method _reloadPicture
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
                            //console.error("handle error: " + res);
                            //console.error("handle error: " + ioArgs);
                            this.errorMessage("error");
                        }),
                        // Callback on successful call:
                        load: dojo.hitch(this, function(response, ioArgs) {
                            this.successMesage(parent.i18nMessage.commons_update);
                            parent._reloadPicture();
                            // return the response for succeeding callbacks
                            return response;
                        })
                    });
                }

    });
});