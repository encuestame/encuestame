define([
     "dojo/_base/declare",
     "me/core/enme"],
    function(
    declare,
    _ENME) {

  return declare(null, {

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
    }

  });
});