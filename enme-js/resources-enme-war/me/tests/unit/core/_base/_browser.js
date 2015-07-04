/**
 * Copyright 2014 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

define([
	'intern!object',
	'intern/chai!assert',
	'../../Helper',
	'me/core/_base/_browser'
], function (
		registerSuite,
		assert,
		Helper,
		_browser) {

	registerSuite({
		name: 'Base Browser Test',

		setup: function () {
			Helper.init();
		},

		'createCaret Test' : function() {
			var a = _browser.createCaret();
			assert.isDefined(a);
			Helper.removeElement(a);
		},

		'toggleClassName Test' : function() {
			var dom1 = Helper.createElement("test1");
			assert.isDefined(dom1);
			Helper.addClass(dom1, "test1-class");
			_browser.toggleClassName(dom1, "test2-class");
			assert.isTrue(Helper.hasClass(dom1, "test2-class"));
			Helper.removeElement(dom1);
		},

		'clone Test' : function() {
			var dom1 = Helper.createElement("test2");
			assert.isDefined(dom1);
			var clonedNode = _browser.clone("#test2");
			assert.isDefined(clonedNode);
			Helper.removeElement(dom1);
		},


		'setVisible Test' : function() {
			var dom1 = Helper.createElement("test-d-1");
			var dom2 = Helper.createElement("test-d-2");
			var dom3 = Helper.createElement("test-d-3");
			var dom4 = Helper.createElement("test-d-4");
			var dom5 = Helper.createElement("test-d-5");
			Helper.addClass(dom1, "item");
			Helper.addClass(dom2, "item");
			Helper.addClass(dom3, "item");
			Helper.addClass(dom4, "item");
			Helper.addClass(dom5, "item");
			_browser.setVisible(".item");
			assert.isTrue(Helper.hasClass(dom1, "item"));
			assert.isTrue(Helper.hasClass(dom2, "item"));
			assert.isTrue(Helper.hasClass(dom3, "item"));
			assert.isTrue(Helper.hasClass(dom4, "item"));
			assert.isTrue(Helper.hasClass(dom5, "item"));
			Helper.removeElement(dom1);
			Helper.removeElement(dom2);
			Helper.removeElement(dom3);
			Helper.removeElement(dom4);
			Helper.removeElement(dom5);
		},

		teardown: function () {

		}
	});
});
