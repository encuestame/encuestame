dojo.provide("encuestame.org.core.commons.profile.Profile");

dojo.declare(
    "encuestame.org.core.commons.profile.Profile",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.profile", "templates/profile.inc"),

        widgetsInTemplate: true,

        _openBox : false,

        contextPath : ""
});