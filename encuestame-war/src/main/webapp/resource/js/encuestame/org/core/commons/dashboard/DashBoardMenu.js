dojo.provide("encuestame.org.core.commons.dashboard.DashBoardMenu");

dojo.declare(
    "encuestame.org.core.commons.dashboard.DashBoardMenu",
    [dijit._Widget, dijit._Templated],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/dashboardMenu.inc"),

        widgetsInTemplate: true,

        contextPath : "/"

});