define([ "dojo/parser",
         "dojo/_base/declare", 
         "dijit/_WidgetBase", 
         "dijit/_TemplatedMixin",
         "dijit/form/TextBox", 
         "dijit/_WidgetsInTemplateMixin",
         "dojo/text!me/web/widget/menu/template/searchMenu.html" ], function(
        parser,
        declare,
        _WidgetBase,
        _TemplatedMixin, 
        text,  
        _WidgetsInTemplateMixin,
        template) {
    console.log("SEARCHHHHHHH MENU", parser);
    
    return declare([ _WidgetBase, _TemplatedMixin], {       
        templateString: template,
        postCreate: function() {
            console.log("SEARCHHHHHHH postCreate");
            this.domNode.innerHTML = template;
            parser.parse(this.domNode);
        }
    });
});