/*
 * Copyright (c) 2013 Juan Picado, @jotadeveloper

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

define([
  "dojo",
  "dojo/_base/declare",
  "panel/AbstractSlidePanel",
  "dojo/on",
  "dojo/_base/array",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/_base/lang",
  "dojo/dom-attr",
  "dojo/dom-construct",
  "dojo/NodeList-manipulate",
  "dojo/query",
  "hammer/hammer"
  ], function(
  dojo,
  declare,
  AbstractSlidePanel,
  on,
  array,
  domClass,
  domStyle,
  lang,
  domAttr,
  domConstruct,
  nodeList,
  query,
  Hammer) {

  return declare([AbstractSlidePanel], {

    key_css : "#top",

    key_event: "tap",

    /**
     *
     * @method constructor
     */
    constructor: function(options) {
        var top = query(this.key_css)[0],
        trigger_node = query(options.slide_top_menu)[0];
        console.log("top", top);
        var hammertime = Hammer(trigger_node).on(this.key_event, function(event) {
              //alert('ds');
              if (domClass.contains(top, "top")){
                domClass.remove(top, "top");
              } else {
                domClass.add(top, "top");
              }
              
              console.log("tap top");
         });
    }


   
  });
});