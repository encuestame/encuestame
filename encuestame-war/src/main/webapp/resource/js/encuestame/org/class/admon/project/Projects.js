dojo.provide("encuestame.org.class.admon.project.Projects");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.class.admon.project.Projects",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.admon.project", "template/project.inc"),

        widgetsInTemplate: true,

        postCreate: function() {

        }
    }
);
