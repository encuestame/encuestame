define([ "dojo/parser",
         "dojo/_base/declare", 
		 "me/core/main_widgets/URLServices" ], function(
		parser,
		declare,
		_URL) {

	console.log("ENMEMAINWIdsadsadsadsadsDGET _URL", _URL);
	
	return declare([_URL], {		
		postCreate: function() {
			console.log("SEARdsadsaCHHHHHHH _URL", _URL);
			console.log("SEARdsadsaCHHHHHHH _URL", _URL.postCreate());
//            this.domNode.innerHTML = template;
//            parser.parse(this.domNode);
        }
	});
});

//dojo.provide("encuestame.org.main.EnmeMainLayoutWidget");
//
//dojo.require("dojo.cache");
//dojo.require("encuestame.org.main.WidgetServices");
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//
//dojo.declare("encuestame.org.main.EnmeMainLayoutWidget", [ dijit._Widget,
//		dijit._Templated, encuestame.org.main.WidgetServices ], {
//
//	/*
//	 * enable widgets on html template.
//	 */
//	widgetsInTemplate : true,
//
//	/*
//	 * default placeholder.
//	 */
//	placeholder : "Type something...",
//	
//	/*
//	 * 
//	 */
//	channel : "encuestame/time/range/search/",
//
//	/*
//	 * 
//	 */
//	defaultNoResults : "Nothing find with ",
//
//	/*
//	 * default context path.
//	 */
//	contextDefaultPath : ENME.config('contextPath'),
//
//	/*
//	 * add item on drop down menu.
//	 */
//	append : function(node, place) {
//		dojo.place(node, place);
//	},
//		
//	/**
//	 * 
//	 */
//	range_actions : [ {
//		period : "All",
//		value  : "all",
//		action : dojo.hitch(this, function(channel) {
//			dojo.publish(channel, [ "all" ]);
//		})
//	}, {
//		period : "Last Year",
//		value  :  "365",
//		action : dojo.hitch(this, function(channel) {
//			dojo.publish(channel, [ "365" ]);
//		})
//	}, {
//		period : "Last Month",
//		value  : "30",
//		action : dojo.hitch(this, function(channel) {
//			dojo.publish(channel, [ "30" ]);
//		})
//	}, {
//		period : "Last Week",
//		value  : "7",
//		action : dojo.hitch(this, function(channel) {
//			dojo.publish(channel, [ "7" ]);
//		})
//	}, {
//		period : "Last Day",
//		value  : "24",
//		action : dojo.hitch(this, function(channel) {
//			dojo.publish(channel, [ "24" ]);
//		})
//	}]
//});