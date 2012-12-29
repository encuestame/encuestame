define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "dijit/form/ToggleButton",
         "me/core/enme",
         "dojo/text!me/web/widget/admon/user/template/UserPermissions.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                ToggleButton,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            user: null,

            permissions: [],

            widgetPermissions : [],

            postCreate: function() {
                this.loadPermisions();
            },

            loadPermisions : function() {
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
                 this.getURLService().get('encuestame.service.list.listPermissions', {}, load, error , dojo.hitch(this, function() {

                 }));
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
               this.getURLService().get('encuestame.service.list.listUserPermissions', {id: this.user.id}, load, error , dojo.hitch(this, function() {

                }));
            },

            buildPermission : function(response){
                 var widget = new ToggleButton({
                     showLabel: true,
                     checked: false,
                     layoutAlign : "left",
                     value : response.permission,
                     iconClass : "dijitCheckBoxIcon",
                     onChange:  dojo.hitch(this, function(val) {
                         //console.debug(val);
                         var error = function(error) {
                             console.debug("error", error);
                         };
                         var load = dojo.hitch(this, function(response){

                         });
                         var service;
                         if (val) {
                            service = 'encuestame.service.list.addPermission';
                         } else {
                            service = 'encuestame.service.list.removePermission';
                         }
                         // encuestame.service.xhrGet(service,
                         //         {id: this.user.id,
                         //          permission : response.description
                         //         },
                         // load, error);
                       this.getURLService().get(service, {
                                      id: this.user.id,
                                      permission : response.description
                                  }, load, error , dojo.hitch(this, function() {}));
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
});