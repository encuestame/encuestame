dojo.provide("encuestame.org.class.admon.user.Users");

dojo.require("dijit.Dialog");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.TimeTextBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("encuestame.org.class.shared.utils.Table");

dojo.declare(
    "encuestame.org.class.admon.user.Users",
    [encuestame.org.class.shared.utils.Table],{
        templatePath: dojo.moduleUrl("encuestame.org.class.admon.user", "template/Users.inc"),
        widgetsInTemplate: true,

        /**
         * Build Row.
         */
        buildRow : function(data){
            var widgetRow = new encuestame.org.class.admon.user.UserTableRow({data: data });
            this._body.appendChild(widgetRow.domNode);
        },

        /**
         * Load Users.
         */
        loadItems : function(){
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                var array = data.success.users;
                dojo.forEach(
                        data.success.users,
                        dojo.hitch(this, function(data, index) {
                            console.debug(data, index);
                            this.buildRow(data);
                        }));
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(this.jsonServiceUrl, {limit:10, start:10}, load, error);
        },

        _updateUser : function(event){
            dijit.byId("name");
            dijit.byId("email");
            dijit.byId("realName");
        },
    }
);

dojo.declare(
        "encuestame.org.class.admon.user.UserTableRow",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.class.admon.user", "template/UserTableRow.inc"),

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
                this.createColumnDialog(data.name);
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
                console.debug("openDialog", userEdit);
                if(userEdit != null){
                    userEdit.show();
                    this.getUserInfo(this.data.id);
                }
            },

            /**
             * Get User.
             */
            getUserInfo : function(id){
                var load = dojo.hitch(this, function(response){
                    console.debug("response", response);
                    var data = response.success.user;
                    var name = dijit.byId("name");
                    console.debug(name, data.username);
                    name.setValue(data.username);
                    var email = dijit.byId("email");
                    email.setValue(data.email);
                    var realName = dijit.byId("realName");
                    realName.setValue(data.name);
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
            },
        }
);