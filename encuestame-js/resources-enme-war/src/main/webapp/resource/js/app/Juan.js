/**
 * This file is a very simple example of a class declaration in Dojo. It defines the `app/Dialog` module as a new
 * class that extends a dijit Dialog and overrides the default title and content properties.
 */
define([ 'dojo/_base/declare', 'dojo/dom' ], function (declare, dom) {
	return declare(dom, {
		title: 'Hello World',
		content: 'Juan successfully!'
	});
});