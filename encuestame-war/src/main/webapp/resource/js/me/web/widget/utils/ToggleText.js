dojo.provide("encuestame.org.core.shared.utils.ToggleText");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.Tooltip");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.ToggleText",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/toggleText.html"),

        /*
         * default limit.
         */
        limit : 50,

        /*
         * default text.
         */
        text : "",

        /*
         * Text after being shortened.
         */
        _shortText : "",

        _expanded : false,

        /*
         * define the text to display.
         */
        _toggleText : function(){
            if (!this._expanded) {
                 this._text.innerHTML = this.text;
            } else {
                 this._text.innerHTML = this._shortText;
            }
            this._expanded = !this._expanded;
        },

        /*
         * post create life cycle.
         */
        postCreate : function() {
            //check if text exceeded the limit defined
            if (this.text.length > this.limit ) {
                this._shortText = this.text.substring(0, this.limit);
                this._shortText = this._shortText.concat(" ");
                this._shortText = this._shortText.concat("...");
                this._text.innerHTML = this._shortText;
                dojo.addClass(this._text, "togglePointer");
                 dojo.connect(this._text, "onclick", dojo.hitch(this, function(event) {
                     this._toggleText();
                 }));
            } else {
                this._text.innerHTML = this.text;
            }
        }
});