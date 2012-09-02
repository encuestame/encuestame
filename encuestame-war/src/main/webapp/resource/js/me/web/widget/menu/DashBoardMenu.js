require([
    "dojo/_base/declare", "dojo/parser", "dojo/ready",
    "dijit/_WidgetBase",
], function(declare, parser, ready, _WidgetBase){

    declare("app/web/widget/menu/DashBoardMenu", [_WidgetBase], {
        // put methods, attributes, etc. here
    	hola : function (){
    		return "hola";
    	}
    });
    ready(function(){
        // Call the parser manually so it runs after our widget is defined, and page has finished loading
        parser.parse();
        
        console.log("SEARCHHHH DashBoardMenu");
    });
});

//dojo.provide("encuestame.org.core.commons.dashboard.DashBoardMenu");
//
//dojo.declare(
//    "encuestame.org.core.commons.dashboard.DashBoardMenu",
//    [dijit._Widget, dijit._Templated],{
//
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/dashboardMenu.html"),
//
//        widgetsInTemplate: true,
//
//        contextPath : "/"
//
//});