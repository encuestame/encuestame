dojo.provide("encuestame.org.main.EnmeMainLayoutWidget");

dojo.require("dojo.cache");
dojo.require("encuestame.org.main.WidgetServices");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare("encuestame.org.main.EnmeMainLayoutWidget", [ dijit._Widget,
		dijit._Templated, encuestame.org.main.WidgetServices ], {

	/*
	 * enable widgets on html template.
	 */
	widgetsInTemplate : true,

	/*
	 * default placeholder.
	 */
	placeholder : "Type something...",
	
	/*
	 * 
	 */
	channel : "encuestame/time/range/search/",

	/*
	 * 
	 */
	defaultNoResults : "Nothing find with ",

	/*
	 * default context path.
	 */
	contextDefaultPath : config.contextPath,

	/*
	 * add item on drop down menu.
	 */
	append : function(node, place) {
		dojo.place(node, place);
	},
		
	/**
	 * 
	 */
	range_actions : [ {
		period : "All",
		action : dojo.hitch(this, function(channel) {
			dojo.publish(channel, [ "all" ]);
		})
	}, {
		period : "Last Year",
		action : dojo.hitch(this, function(channel) {
			dojo.publish(channel, [ "365" ]);
		})
	}, {
		period : "Last Month",
		action : dojo.hitch(this, function(channel) {
			dojo.publish(channel, [ "30" ]);
		})
	}, {
		period : "Last Week",
		action : dojo.hitch(this, function(channel) {
			dojo.publish(channel, [ "7" ]);
		})
	}, {
		period : "Last Day",
		action : dojo.hitch(this, function(channel) {
			dojo.publish(channel, [ "24" ]);
		})
	}]
});