dojo.provide("encuestame.org.class.shared.utils.Table");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('encuestame.org.class.commons');

dojo.declare(
    "encuestame.org.class.shared.utils.Table",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.shared.utils", "template/Table.inc"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,
        /** Principal service. **/
        jsonServiceUrl : encuestame.service.list.userList,

        postMixInProperties: function(){
        },

        postCreate: function() {
            this.loadUsers();
        },

        /**
         * Load Users.
         */
        loadUsers : function(){
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(this.jsonServiceUrl, {limit:10, start:10}, load, error);
        },

        next : function(event){

        },

        previous : function(event){

        },

        last : function(event){

        },

        first : function(event){

        },


    }
);
