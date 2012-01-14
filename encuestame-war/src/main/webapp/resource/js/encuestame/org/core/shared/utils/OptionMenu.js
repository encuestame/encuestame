dojo.provide("encuestame.org.core.shared.utils.OptionMenu");

dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
dojo.require('encuestame.org.core.commons.support.ToggleMenu');
dojo.require('encuestame.org.core.commons');

dojo.declare("encuestame.org.core.shared.utils.OptionMenu",
        [encuestame.org.core.commons.support.ToggleMenu], {

            templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
                    "template/optionMenu.html"),

            /*
             *
             */
            _open : false,

            /*
            *
            */
           _classReplace : "",

            /*
             *
             */
            postCreate : function() {
               this.addMenuSupport(this._icon, "click");
            }
        });
