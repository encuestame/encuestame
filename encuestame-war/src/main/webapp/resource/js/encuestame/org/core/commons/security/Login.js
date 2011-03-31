dojo.provide("encuestame.org.core.commons.security.Login");

dojo.declare(
    "encuestame.org.core.commons.security.Login",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.security", "templates/login.inc"),

        widgetsInTemplate: true

});