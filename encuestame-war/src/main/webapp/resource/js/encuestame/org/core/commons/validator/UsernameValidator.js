dojo.provide("encuestame.org.core.commons.validator.UsernameValidator");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.require("encuestame.org.core.commons.validator.AbstractValidatorWidget");

dojo.declare("encuestame.org.core.commons.validator.UsernameValidator",
        [encuestame.org.core.commons.validator.AbstractValidatorWidget], {
    templatePath : dojo.moduleUrl("encuestame.org.core.commons.validator", "templates/usernameValidator.html"),
    widgetsInTemplate : true,
    postCreate : function(){
        this.inherited(arguments);
    },

    /*
     *
     */
    _validate : function(event){
            this._loadService(
        encuestame.service.publicService.validate.username, {
            context : this.enviroment,
            username :  this._input.value
        }, this.error);
    },

    /**
     * Add suggestions
     * @param data
     */
    _additionalErrorHandler : function(data){
        if (data.suggestions.length > 0) {
            dojo.style(this._block, "display", "block");
            dojo.empty(this._suggest);
            //<li><a href="#" data-sugg-sources="username,full_name" -->
            //<!--                 data-sugg-technik="make_pairs">Jota148Jota</a></li>
            dojo.style(this._suggest, "opacity", "0");
            var fadeArgs = {
                    node: this._suggest,
                    duration: 500
                };
            dojo.fadeIn(fadeArgs).play();
            dojo.forEach(data.suggestions,
                    dojo.hitch(this,function(item) {
                        var li = dojo.doc.createElement("li");
                        li.innerHTML = item;
                        dojo.connect(li, "onclick", dojo.hitch(this, function(event) {
                            this._replaceUsername(item);
                        }));
                        this._suggest.appendChild(li);
            }));
        } else {
            dojo.style(this._block, "display", "none");
        }
    },

    _additionalSuccessHandler : function(data){
        var fadeArgs = {
                node: this._suggest,
                duration: 500
            };
            dojo.fadeOut(fadeArgs).play();
            dojo.empty(this._suggest);
    },

    _replaceUsername : function(item){
        console.debug("replace", item);
        dojo.style(this._block, "display", "none");
        this._input.value = item;
        this._loadService(
                encuestame.service.publicService.validate.username, {
                    context : this.enviroment,
                    username :  item
                }, this.error);
    },


     error : function(error) {
        console.debug("error", error);
     }

});