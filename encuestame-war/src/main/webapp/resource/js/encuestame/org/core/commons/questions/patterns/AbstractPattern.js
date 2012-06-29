dojo.provide("encuestame.org.core.commons.questions.patterns.AbstractPattern");


dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.commons.questions.patterns.AbstractPattern",
    [encuestame.org.main.EnmeMainLayoutWidget],{
    	
    	itemId : "",
    	
    	label : "",

       /**
        * PostCreate life cycle.
        */
        postCreate : function() {},

        /**
         * Create a simple button.
         */
        _createSimpleButton : function(name, node, value, id, required) {
            required = required == null ? false : required;
            var option = dojo.create("input", {
                type : "radio",
                name : name,
                value : id
                });
            if (required) {
                option.setAttribute("required", "");
            }
            dojo.place(option, node);
            var label = dojo.create("label", {innerHTML : value});
            dojo.place(label, node);
        },
});