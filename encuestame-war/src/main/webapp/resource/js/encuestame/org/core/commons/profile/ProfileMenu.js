dojo.provide("encuestame.org.core.commons.profile.ProfileMenu");

dojo.declare(
    "encuestame.org.core.commons.profile.ProfileMenu",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.profile", "templates/profileMenu.inc"),

        widgetsInTemplate: true

});