dojo.provide("encuestame.org.core.comments.Comments");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.comments.Comments",
    [dijit._Widget, dijit._Templated],{

      templatePath: dojo.moduleUrl("encuestame.org.core.comments", "templates/comments.html"),

      type : ""


});