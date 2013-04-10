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
 *  @module Admon
 *  @namespace Widget
 *  @class User
 */
define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/registry",
         "dijit/Dialog",
         "dijit/layout/AccordionContainer",
         "dijit/layout/ContentPane",
         "dijit/layout/TabContainer",
         "dijit/form/Form",
         "dijit/form/ValidationTextBox",
         "dijit/form/Button",
         "dijit/form/TextBox",
         "dijit/ProgressBar",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/admon/user/UserTableRow",
         "me/web/widget/admon/user/UserPermissions",
         "me/web/widget/validator/UsernameValidator",
         'me/web/widget/validator/EmailValidator',
         "me/web/widget/data/Table",
         "me/web/widget/ui/DialogGenericContentMessage",
         "me/core/enme",
         "dojo/text!me/web/widget/admon/user/template/Users.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                registry,
                Dialog,
                AccordionContainer,
                ContentPane,
                TabContainer,
                Form,
                ValidationTextBox,
                Button,
                TextBox,
                ProgressBar,
                main_widget,
                UserTableRow,
                UserPermissions,
                UsernameValidator,
                EmailValidator,
                Table,
                DialogGenericContentMessage,
                _ENME,
                template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, Table, _WidgetsInTemplateMixin], {

            /**
             * template string.
             *
             */
            templateString : template,

            /**
             * @param the publish dialog to alocate the publish widget.
             */
            dialogWidget : null,

            /**
             *
             * @property
             */
            jsonServiceUrl : 'encuestame.service.list.userList',

            /**
             *
             * @property
             */
            total : 0,

           /**
            * Post create.
            * @method postCreate
            */
           postCreate : function () {
                this.inherited(arguments);
           },

            /**
             * Build Row.
             * @method
             */
            buildRow : function(data) {
                var widgetRow = new UserTableRow({data: data });
                this._body.appendChild(widgetRow.domNode);
            },

            /**
             * Iterate Items.
             * @method
             */
            iterateResponseItems : function(data){
                this.total = data.success.total;
                this._total.innerHTML = this.start +" of "+this.total;
                dojo.forEach(
                    data.success.users,
                    dojo.hitch(this, function(data, index) {
                        this.buildRow(data);
                    }));
            },

            /**
             * Update User.
             */
            _updateUser : function(event) {
                registry.byId("name");
                registry.byId("email");
                registry.byId("realName");
            },

            /**
             *
             * @method
             */
            _newUser : function(event) {
                var userEdit = registry.byId("newUser");
                if (userEdit) {
                    userEdit.show();
                }
            },

            /**
             *
             * @method
             */
            _closeNewDialog : function (event) {
                registry.byId("newUser").hide();
                this._clearForms();
            },

            /**
             *
             * @method
             */
            _clearForms : function () {
                var newUsername = registry.byId("newUsername");
                var newEmailUser = registry.byId("newEmailUser");
                newUsername.clear();
                newEmailUser.clear();
            },

            /**
             * Send a user invitation.
             * @method _sendInvitation
             */
            _sendInvitation : function () {
                 var parent = this;
                    var emailInvitationText = registry.byId("emailInvitationText");
                    if (emailInvitationText.isValid) {
                        var load = dojo.hitch(this, function(data) {
                            parent.dialogWidget.hide();
                            if ('success' in data) {
                                this.loading_hide();
                                parent._request_button.disabled = false;
                                if (data.success.r === 0) {
                                     this.loadItems();
                                     parent._clearForms();
                                     parent.successMesage("The request has been sent to " + newEmailUser.getValue());
                                     newUsername.clear();
                                     newEmailUser.clear();
                                }
                            }
                        }),
                        error = function(error) {
                            //parent.loading_hide();
                            parent.dialogWidget.hide();
                            if ("message" in error.response.data.error) {
                                parent.errorMessage(error.response.data.error.message);
                            }
                            parent._request_button.disabled = false;
                        };
                        var params = {
                            email : emailInvitationText.getValue()
                        };
                        this.loading_show();
                        // create the dialog
                        var content_widget = new DialogGenericContentMessage({
                                message_content : "Sending Invitation",
                                icon_class : "loading-icon"
                             });
                             parent.dialogWidget = new Dialog({
                                 content: content_widget.domNode,
                                 style: "width: 700px",
                                 draggable : false
                             });
                             parent.dialogWidget.show();

                        this.getURLService().put('encuestame.user.invite', params, load, error , dojo.hitch(this, function() {

                        }));
                    } else {
                        this.warningMesage("the email is invalid");
                    }
            },

            /**
             * Create a user based on username and password
             * @method _createDirectlyUser
             */
            _createDirectlyUser : function(event) {
                    var parent = this;
                    var newUsername = registry.byId("newUsername");
                    var newEmailUser = registry.byId("newEmailUser");
                    if (newUsername.isValid && newEmailUser.isValid) {
                        var load = dojo.hitch(this, function(data) {
                            parent.dialogWidget.hide();
                            if ('success' in data) {
                                this.loading_hide();
                                parent._request_button.disabled = false;
                                if (data.success.r === 0) {
                                     this.loadItems();
                                     parent._clearForms();
                                     parent.successMesage("The request has been sent to " + newEmailUser.getValue());
                                     newUsername.clear();
                                     newEmailUser.clear();
                                }
                            }
                        }),
                        error = function(error) {
                            //parent.loading_hide();
                            parent.dialogWidget.hide();
                            if ("message" in error.response.data.error) {
                                parent.errorMessage(error.response.data.error.message);
                            }
                            parent._request_button.disabled = false;
                        };
                        var params = {
                            newUsername : newUsername.getValue(),
                            newEmailUser : newEmailUser.getValue()
                        };
                        this.loading_show();
                        this._request_button.disabled = true;

                        // create the dialog
                        var content_widget = new DialogGenericContentMessage({
                                message_content : "Creando usuario",
                                icon_class : "loading-icon"
                             });
                             parent.dialogWidget = new Dialog({
                                 content: content_widget.domNode,
                                 style: "width: 700px",
                                 draggable : false
                             });
                             parent.dialogWidget.show();

                        this.getURLService().post('encuestame.service.list.createUser', params, load, error , dojo.hitch(this, function() {

                        }));
                    } else {
                        this.warningMesage("the new member data it's not valid, recheck your data");
                    }
            }

    });
});