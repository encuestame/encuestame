define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/Dialog",
         "dijit/layout/AccordionContainer",
         "dijit/layout/ContentPane",
         "dijit/layout/TabContainer",
         "dijit/form/Form",
         "dijit/form/ValidationTextBox",
         "dijit/form/Button",
         "dijit/form/TextBox",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/admon/user/UserTableRow",
         "me/web/widget/admon/user/UserPermissions",
         "me/web/widget/data/Table",
         "me/core/enme",
         "dojo/text!me/web/widget/admon/user/template/Users.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Dialog,
                AccordionContainer,
                ContentPane,
                TabContainer,
                Form,
                ValidationTextBox,
                Button,
                TextBox,
                main_widget,
                UserTableRow,
                UserPermissions,
                Table,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, Table, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            jsonServiceUrl : 'encuestame.service.list.userList',

            total : 0,

            /**
             * Build Row.
             */
            buildRow : function(data) {
                var widgetRow = new UserTableRow({data: data });
                this._body.appendChild(widgetRow.domNode);
            },

            /**
             * Iterate Items.
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
             * Error Resonse.
             */
            errorResponse : function(error){
                console.error("error", error);
            },

            /**
             * Update User.
             */
            _updateUser : function(event){
                dijit.byId("name");
                dijit.byId("email");
                dijit.byId("realName");
            },

            _newUser : function(event){
                var userEdit = dijit.byId("newUser");
                if(userEdit){
                    userEdit.show();
                }
            },

            _createDirectlyUser : function(event){
                basicStandby6.show();
                var form = dojo.byId("newUserSimpleForm");
                var formDijit = dijit.byId("newUserSimpleForm");
                if(formDijit.isValid()){
                    var load = dojo.hitch(this, function(data){
                        basicStandby6.hide();
                        if(data.success){
                            if(data.success.userAdded == "ok"){
                                this.loadItems();
                                dijit.byId("newUser").hide();
                            }
                        }
                    });
                    var error = function(error) {
                        basicStandby6.hide();
                        console.debug("error", error);
                    };
                    //encuestame.service.xhrPost(encuestame.service.list.createUser, form, load, error);
                    this.getURLService().post('encuestame.service.list.createUser', form, load, error , dojo.hitch(this, function() {

                    }));
                } else {
                    console.info("form not valid");
                }
            }

    });
});


//});
// /*
//  ************************************************************************************
//  * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
//  * encuestame Development Team.
//  * Licensed under the Apache Software License version 2.0
//  * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
//  * Unless required by applicable law or agreed to  in writing,  software  distributed
//  * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
//  * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
//  * specific language governing permissions and limitations under the License.
//  ************************************************************************************
//  */
// dojo.provide("encuestame.org.core.admon.user.Users");

// dojo.require("dijit.Dialog");
// dojo.require("dijit.form.TextBox");
// dojo.require("dijit.form.TimeTextBox");
// dojo.require("dijit.form.Button");
// dojo.require("dijit.Menu");
// dojo.require("dojox.widget.Standby");
// dojo.require("dojox.widget.Dialog");
// dojo.require("dijit.form.ToggleButton");
// dojo.require("dijit.form.DateTextBox");
// dojo.require("dijit.layout.TabContainer");
// dojo.require("dijit.layout.ContentPane");
// dojo.require("dijit.layout.AccordionContainer");
// dojo.require("dijit.form.ValidationTextBox");
// dojo.require("encuestame.org.core.shared.utils.Table");

// dojo.declare(
//     "encuestame.org.core.admon.user.Users",
//     [encuestame.org.core.shared.utils.Table],{

//         templatePath: dojo.moduleUrl("encuestame.org.core.admon.user", "template/Users.html"),

//         widgetsInTemplate: true,


// );

// dojo.declare(
//         "encuestame.org.core.admon.user.UserTableRow",
//         [dijit._Widget, dijit._Templated],{
//             templatePath: dojo.moduleUrl("encuestame.org.core.admon.user", "template/UserTableRow.html"),

//             /** Allow other widgets in the template. **/
//             widgetsInTemplate: true,


//         }
// );

// dojo.declare(
//         "encuestame.org.core.admon.user.UserPermissions",
//         [dijit._Widget, dijit._Templated],{
//             templatePath: dojo.moduleUrl("encuestame.org.core.admon.user", "template/UserPermissions.html"),

//             /** Allow other widgets in the template. **/
//             widgetsInTemplate: true,


// });

// /**
//  * Group.
//  */
// dojo.declare(
//         "encuestame.org.core.admon.user.UserGroup",
//         [dijit._Widget, dijit._Templated],{
//             templatePath: dojo.moduleUrl("encuestame.org.core.admon.user", "template/UserGroup.html"),

//             /** Allow other widgets in the template. **/
//             widgetsInTemplate: true,

// });