dojo.provide("encuestame.org.core.commons.profile.ProfileMenu");

dojo.declare(
    "encuestame.org.core.commons.profile.ProfileMenu",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.profile", "templates/profileMenu.html"),

        widgetsInTemplate: true,

        _openBox : false,

        contextPath : "",

        /*
         *
         */
        _open: function(event){
            dojo.stopEvent(event);
            if(this._openBox){
                dojo.removeClass(this._profileBox, "profileOpenPanel");
                this._openBox = false;
            } else {
                dojo.addClass(this._profileBox, "profileOpenPanel");
                this._openBox = true;
            }
        }

});
