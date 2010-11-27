dojo.provide("encuestame.org.core.admon.project.Projects");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.admon.project.Projects",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.admon.project", "template/projects.inc"),

        widgetsInTemplate: true,

        postCreate: function() {

        }
    }
);
