dojo.provide("encuestame.org.class.shared.utils.Suggest");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.TextBox");
dojo.require('encuestame.org.class.commons');

dojo.require("dojox.data.QueryReadStore");

dojo.declare(
    "encuestame.org.class.shared.utils.Suggest",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.shared.utils", "template/suggest.inc"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,

        store: null,

        postCreate: function() {

            this.store = new dojox.data.QueryReadStore({
                    url: encuestame.service.list.getNotifications,
                    sortFields : [{attribute: 'name', descending: true}],
                    requestMethod : "get"}
            );
        }
    });
