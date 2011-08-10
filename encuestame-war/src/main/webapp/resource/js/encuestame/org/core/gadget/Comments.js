dojo.provide("encuestame.org.core.gadget.Comments");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.gadget.Comments",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.gadget", "template/comments.html"),

});