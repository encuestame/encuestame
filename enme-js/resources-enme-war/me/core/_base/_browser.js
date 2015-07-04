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

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.147
 *  @module ModuleName
 *  @namespace Widget
 *  @class FileName
 */

define(
	["dojo/cookie",
	 "dojo/on",
	 "dojo/dom-construct",
	 "dojo/_base/lang",
	 "dojo/_base/array",
	 "dojo/dom-class",
	 "dojo/query",
	 "dojo/dom-attr"
	],function(cookie,
	           on,
	           domConstruct,
	           lang,
	           array,
	           domClass,
	           query,
	           domAttr) {
		return {

		/**
		 * Create a bootstrap caret.
		 * @method
		 */
		createCaret: function() {
			//<b class="caret"></b>
			var b = domConstruct.create('b');
			domClass.add(b,'caret');
			return b;
		},

		/**
		 * Clones the element specified by the selector and removes the id
		 * attribute.
		 * @param selector a selector
		 */
		clone : function(selector) {
			var x = query(selector),
				c = lang.clone(x);
			if(c.length === 0) {
				return;
			}
			//FIXME: I don't like so much this workaround
			domAttr.remove(c[0], "id");
			return x;
		},

		/**
		 * Toggle a class name
		 * @method
		 */
		toggleClassName : function(element, className) {
			if (element) {
				domClass.toggle(element, className);
			}
		},

		/**
		 *
		 * @method
		 */
		setVisible : function(element, show) {
			if (!element) {
				return;
			}
			query(element).forEach(function(node) {
				var isHidden = domClass.contains(node, "hidden");
				if (isHidden && show) {
					domClass.remove(node, "hidden");
				} else if (!isHidden && !show) {
					domClass.add(node, "hidden");
				}
			});
		},

		isVisible : function(element) {
			return !query(element).hasClass("hidden");
		}


	};
});