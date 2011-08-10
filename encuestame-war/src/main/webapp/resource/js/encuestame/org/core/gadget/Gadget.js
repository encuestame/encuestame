dojo.provide("encuestame.org.core.gadget.Gadget");

dojo.require("dojo.parser");

dojo.declare("encuestame.org.core.gadget.Gadget", null,{

    /*
     * constructor.
     */
    constructor: function(id, widget) {
        this.gadget.id = id;
        this.gadget.widget = widget;
    },

    gadget: {},

    render : function(){
       var node = dojo.create("div",{
                name:"gadget_"+this.gadget.id,
                jsId:"gadget_"+this.gadget.id,
                dojoType:this.gadget.widget});
       console.debug("dojo render", node);
       this.parse(node);
       console.debug("dojo rendered", node);
       return node;
    },

    parse : function(node){
         dojo.parser.parse(node);
    }
});