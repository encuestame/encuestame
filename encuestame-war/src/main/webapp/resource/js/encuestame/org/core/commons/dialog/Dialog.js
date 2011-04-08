dojo.provide("encuestame.org.core.commons.dialog.Dialog");

dojo.require("dijit.Dialog");

dojo.declare(
    "encuestame.org.core.commons.dialog.Dialog",
    [dijit.Dialog],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dialog", "templates/dialog.html"),

        draggable : false
});

