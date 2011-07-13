dojo.provide("encuestame.org.core.shared.utils.AccountPicture");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dojox.data.QueryReadStore");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.AccountPicture",
    [dijit._Widget, dijit._Templated],{

      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/accountPicture.html"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,

        picture_width :"32",

        picture_height : "32",

        type : "thumbnail",

        target : "_self",

        contextPath : encuestame.contextDefault,

        username : ""
});
