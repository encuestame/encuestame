dojo.provide("encuestame.org.core.commons.profile.ProfileMenu");

dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.support.ToggleMenu");
dojo.require('encuestame.org.core.commons');

/**
 * Widget to define the profile of menu.
 */
dojo.declare(
    "encuestame.org.core.commons.profile.ProfileMenu",
    [encuestame.org.core.commons.support.ToggleMenu],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.profile", "templates/profileMenu.html"),

        /*
         *
         */
        completeName : "",

        /*
         *
         */
        username : "",

        /*
         *
         */
        _classReplace : "profileOpenPanel",

        /*
        *
        */
       postCreate : function() {
           this.addMenuSupport(this._image);
           this.addMenuSupport(this._name);
       }
});
