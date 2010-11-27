dojo.provide("encuestame.org.core.admon.user.Users");

dojo.require("dijit.Dialog");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.TimeTextBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.Menu");
dojo.require("dojox.widget.Standby");
dojo.require("dojox.widget.Dialog");
dojo.require("dijit.form.ToggleButton");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.layout.AccordionContainer");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("encuestame.org.core.shared.utils.Table");

dojo.declare(
    "encuestame.org.core.admon.user.Users",
    [encuestame.org.core.shared.utils.Table],{

        templatePath: dojo.moduleUrl("encuestame.org.core.admon.user", "template/Users.inc"),

        widgetsInTemplate: true,

        jsonServiceUrl : encuestame.service.list.userList,

        /**
         * Build Row.
         */
        buildRow : function(data){
            var widgetRow = new encuestame.org.core.admon.user.UserTableRow({data: data });
            this._body.appendChild(widgetRow.domNode);
        },

        /**
         * Iterate Items.
         */
        iterateResponseItems : function(data){
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
            console.debug("error", error);
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
                encuestame.service.xhrPost(encuestame.service.list.createUser, form, load, error);
            } else {
                console.info("form not valid");
            }
        }
    }
);

dojo.declare(
        "encuestame.org.core.admon.user.UserTableRow",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.admon.user", "template/UserTableRow.inc"),

            /** Allow other widgets in the template. **/
            widgetsInTemplate: true,

            data: null,

            postMixInProperties: function(){
            },

            /**
             * Post Create.
             */
            postCreate: function() {
                this.buildDefaultRow();
            },

            /**
             * Build Default Row.
             */
            buildDefaultRow : function(){
                var data = this.data;
                this.createInput(data.id)
                this.createColumnDialog(data.username);
                this.createColumn(data.name);
                this.createColumn(data.email);
                this.createColumn(data.email);
                this.buildStatus(data.status);
                this.createColumn(data.id);
            },

            /**
             * Build Options.
             */
            buildOptions : function(id){

            },

            /**
             * Create Column.
             */
            createColumnDialog : function(text){
                 var td = dojo.doc.createElement('td');
                 var a = dojo.doc.createElement('a');
                 a.innerHTML = text;
                 a.href = "#";
                 dojo.connect(a, "onclick", this, this.editUSer);
                 td.appendChild(a);
                 this._trbody.appendChild(td);

            },

            /**
             * Edit User.
             */
            editUSer : function(){
                var userEdit = dijit.byId("userEdit");
                userEdit.data = this.data;
                if(userEdit != null){
                    this.getUserInfo(this.data.id);
                }
            },

            /**
             * Get User.
             */
            getUserInfo : function(id){
                var load = dojo.hitch(this, function(response){
                     dijit.byId("userEdit").show();
                    var data = response.success.user;
                    dijit.byId("userEdit").title = data.username;
                    var name = dijit.byId("name");
                    name.setValue(data.username);
                    var email = dijit.byId("email");
                    email.setValue(data.email);
                    var realName = dijit.byId("realName");
                    realName.setValue(data.name);
                    //set widgets
                    if(dijit.byId("widgetPermission")){
                        dijit.byId("widgetPermission").user = data;
                        dijit.byId("widgetPermission").initialize();
                    } else {
                        console.info("Permission Widget not found");
                    }
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(encuestame.service.list.userInfo, {id: id}, load, error);
            },

            /**
             * Create Column.
             */
            createColumn : function(text){
                 var td = dojo.doc.createElement('td');
                 td.innerHTML = text;
                 this._trbody.appendChild(td);
            },

            /**
             * Create Input.
             */
            createInput : function(id){
                var td = dojo.doc.createElement('td');
                var widgetInput = new dijit.form.CheckBox({});
                widgetInput.setValue(id);
                td.appendChild(widgetInput.domNode);
                this._trbody.appendChild(td);
            },

            buildStatus : function(status){
                var td = dojo.doc.createElement('td');
                td.innerHTML = status;
                this._trbody.appendChild(td);
            }
        }
);

dojo.declare(
        "encuestame.org.core.admon.user.UserPermissions",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.admon.user", "template/UserPermissions.inc"),

            /** Allow other widgets in the template. **/
            widgetsInTemplate: true,

            user: null,

            permissions: [],

            widgetPermissions : [],

            postCreate: function() {
                this.loadPermisions();
            },

            loadPermisions : function(){
                 var load = dojo.hitch(this, function(response){
                     this.permissions = response.success.permissions;
                     dojo.forEach(this.permissions,
                         dojo.hitch(this, function(data, index) {
                             this.buildPermission(data);
                         }));
                 });
                 var error = function(error) {
                     console.debug("error", error);
                 };
                 encuestame.service.xhrGet(encuestame.service.list.listPermissions, {}, load, error);
            },

            resetWidgets : function(){
                 dojo.forEach(this.widgetPermissions,
                         dojo.hitch(this, function(data, index) {
                             data.checked = false;
                         }));
            },

            initialize: function(){
                var load = dojo.hitch(this, function(response){
                    this.resetWidgets();
                    dojo.forEach(this.widgetPermissions,
                            dojo.hitch(this, function(data, index) {
                                dojo.forEach(response.success.userPermissions,
                                        dojo.hitch(this, function(permission, index) {
                                        if(data.permission == permission.permission){
                                            data.checked = true;
                                            data.postCreate();
                                        }
                                }));
                            }));
                });
                var error = function(error) {
                    console.debug("error", error);
                };
              encuestame.service.xhrGet(encuestame.service.list.listUserPermissions, {id: this.user.id}, load, error);
            },

            buildPermission : function(response){
                 var widget = new dijit.form.ToggleButton({
                     showLabel: true,
                     checked: false,
                     layoutAlign : "left",
                     value : response.permission,
                     iconClass : "dijitCheckBoxIcon",
                     onChange:  dojo.hitch(this, function(val) {
                         console.debug(val);
                         var error = function(error) {
                             console.debug("error", error);
                         };
                         var load = dojo.hitch(this, function(response){

                         });
                         var service;
                         if (val) {
                            service = encuestame.service.list.addPermission;
                         } else {
                            service = encuestame.service.list.removePermission;
                         }
                         encuestame.service.xhrGet(service,
                                 {id: this.user.id,
                                  permission : response.description
                                 },
                         load, error);
                     }),
                     label: response.description
                 }, "programmatic");
                 widget.permission = response.permission;
                 this.widgetPermissions.push(widget);
                 var div = dojo.doc.createElement('div');
                 div.appendChild(widget.domNode);
                 this._permissions.appendChild(div);
            }
});
