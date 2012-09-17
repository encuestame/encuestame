define([ "dojo/parser",
         "dijit/registry",
         "dojo/_base/declare", 
         "dijit/_WidgetBase", 
         "dijit/_TemplatedMixin", 
		 "dijit/_WidgetsInTemplateMixin",
		 "me/web/widget/suggestion/Suggest",
		 "me/core/URLServices",
		 "me/core/enme",
		 "me/core/main_widgets/EnmeMainLayoutWidget",
		 "dojo/on", 
		 "dojo/dom", 
		 "dojo/dom-style", 
		 "dojo/mouse",
		 "dojo/text!me/web/widget/menu/template/searchMenu.html",
		 "dijit/form/TextBox"], function(
		parser,
		registry,
		declare,
		_WidgetBase,
		_TemplatedMixin, 		
		_WidgetsInTemplateMixin,
		suggest,
		_URL,
		_ENME,
		main_widget,
		on, 
		dom, 
		domStyle, 
		mouse,
		template) {

	//console.log("SEARCHHHHHHH MENU", parser);
	
	var searchSuggestItemSection = define([ "dojo/parser",
	                                        "dijit/registry",
	                                        "dojo/_base/declare", 
	                                        "dijit/_WidgetBase", 
	                                        "dijit/_TemplatedMixin",
	                               		 "dijit/form/TextBox", 
	                               		 "dijit/_WidgetsInTemplateMixin",
	                               		"me/core/URLServices",
	                               		 "me/core/main_widgets/EnmeMainLayoutWidget",
	                               		 "dojo/text!me/web/widget/menu/template/searchSuggestItemSection.html" ], function(
	                               		parser,
	                               		registry,
	                               		declare,
	                               		_WidgetBase,
	                               		_TemplatedMixin, 
	                               		text,  
	                               		_WidgetsInTemplateMixin,
	                               		_URL,
	                               		main_widget,
	                               		template) {

	                               	//console.log("SEARCHHHHHHH MENU", parser);
	                               	
	                               	return declare([ _WidgetBase, _TemplatedMixin, main_widget], {		
	                               		
	                               	/*
	                               	 * template string. 
	                               	 */
	                               	templateString: template,

	                               //  //
	                               //  items : [],       
	                               //  
	                               //  /*
	                               //   * 
	                               //   */
	                               //  parentWidget : null,
	                               //
	                               //  /*
	                               //   * 
	                               //   */
	                               //  label : "",
	                               //
	                               //  /**
	                               //   * Post Create.
	                               //   */
	                               //  postCreate : function() {
//	                                     dojo.forEach(this.items,
//	                                             dojo.hitch(this,function(item) {
//	                                          this._itemSuggest.appendChild(this._createItem(item, this.label));
//	                                     }));
	                               //  },
	                               //
	                               //  /**
	                               //   * Create a search item.
	                               //   * @param item
	                               //   * @param type
	                               //   */
	                               //  _createItem : function(item, type) {
//	                                     var div = dojo.create("div");
//	                                     dojo.addClass(div, "web-search-item");
//	                                     dojo.attr(div, "data-value", item.itemSearchTitle);
//	                                     dojo.attr(div, "data-type", type);
//	                                     var h4 = dojo.create("h4", null, div);
//	                                     h4.innerHTML = item.itemSearchTitle;
//	                                     if (item.urlLocation != "" && item.urlLocation != null) { //on click point to this url.
//	                                        dojo.attr(div, "data-url", item.urlLocation);
//	                                        dojo.connect(div, "onclick", dojo.hitch(this, function(event) {
//	                                            //console.debug("click item", encuestame.contextDefault+item.urlLocation	);
//	                                            document.location.href = encuestame.contextDefault+item.urlLocation;
//	                                        }));
//	                                     } else { // point to search url
	                               //
//	                                     }
//	                                     this.parentWidget.listItems.push(div);
//	                                     return div;
	                               //  }	

	                               	});
	                               });
	
	
	var searchSuggestItemsByType = define([ "dojo/parser",
	                     "dijit/registry",
	                     "dojo/_base/declare", 
	                     "dijit/_WidgetBase", 
	                     "dijit/_TemplatedMixin",
	            		 "dijit/form/TextBox", 
	            		 "dijit/_WidgetsInTemplateMixin",
	            		 "me/core/main_widgets/EnmeMainLayoutWidget",
	            		 "dojo/text!me/web/widget/menu/template/searchSuggestItem.html" ], function(
	            		parser,
	            		registry,
	            		declare,
	            		_WidgetBase,
	            		_TemplatedMixin, 
	            		text,  
	            		_WidgetsInTemplateMixin,
	            		main_widget,
	            		template) {

	            	//console.log("SEARCHHHHHHH MENU", parser);
	            	
	            	return declare([ _WidgetBase, _TemplatedMixin, main_widget], {		
	            		
	            	/*
	            	 * template string. 
	            	 */
	            	templateString: template,

	            	  /*
	            	  * reference of suggest widget.
	            	  */
	            	 parentWidget: null,
	            	
	            	 /*
	            	  * results
	            	  */
	            	 data: null,       
	            	
	            	 /**
	            	  * Post Create Life Cycle.
	            	  */
	            	 postCreate : function() {
	            	     //console.info("SearchSuggestItem", this.data);
	            	     //console.info("SearchSuggestItem",this.checkIfDataIsEmtpy());
	            	     if (this.data && !this.checkIfDataIsEmtpy()) {
	            	         dojo.empty(this._container);
	            	         if ("tags" in this.data) {
	            	             this._printItems("Hashtags", this.data.tags);
	            	         };
	            	
	            	         if ("profiles" in this.data) {
	            	             this._printItems("Profiles", this.data.profiles);
	            	         };
	            	
	            	         if ("questions" in this.data) {
	            	             this._printItems("Questions", this.data.questions);
	            	         };
	            	
	            	         if ("attachments" in this.data) {
	            	             this._printItems("Documents", this.data.attachments);
	            	         };
	            	     } else {
	            	         var div = dojo.create("div");
	            	         dojo.addClass(div, "web-suggest-noresults");
	            	         dojo.addClass(div, "wrap");
	            	         div.innerHTML = this.parentWidget.defaultNoResults + " ";
	            	         var span = dojo.create("span", null, div);
	            	         span.innerHTML = this.parentWidget.textBoxWidget.get("value");
	            	         this._container.appendChild(div);
	            	     }
	            	 },
	            	
	            	 /**
	            	  * Check if data for each item is empty.
	            	  * @returns {Boolean}
	            	  */
	            	 checkIfDataIsEmtpy : function() {
	            	     var isEmpty = true;
	            	     if ("tags" in this.data) {
	            	         isEmpty = this.data.tags.length == 0 ? true : false;
	            	     };
	            	
	            	     if ("profiles" in this.data && isEmpty) {
	            	         isEmpty = this.data.profiles.length == 0 ? true : false;
	            	     };
	            	
	            	     if ("questions" in this.data && isEmpty) {
	            	         isEmpty = this.data.questions.length == 0 ? true : false;
	            	     };
	            	
	            	     if ("attachments" in this.data && isEmpty) {
	            	         isEmpty = this.data.attachments.length == 0 ? true : false;
	            	     };
	            	     return isEmpty;
	            	 },
	            	
	            	/**
	            	 * Print items.
	            	 */
	            	_printItems : function(label, items) {
	            	    //console.info("_printHashtags", items);
	            	    if (items.length > 0) {
	            	        var hash = new searchSuggestItemSection(
	            	                {
	            	                 label : label,
	            	                 parentWidget : this.parentWidget,
	            	                 items : items
	            	                });
	            	        this._container.appendChild(hash.domNode);
	            	    }
	            	}

	            	});
	            });
	
	
	var SearchMenu = declare([main_widget, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {		
		
		/*
		 * template string. 
		 */
		templateString: template,

//			postCreate: function() {
//				
//				console.log("SEARCHHHHHHH postCreate", x);
//				console.log("SEARCHHHHHHH postCreate", main_widget);
//				console.log("SEARCHHHHHHH postCreate", suggest);
//	            this.domNode.innerHTML = template;
//	            parser.parse(this.domNode);
//	        }
			
	     /*
	      * default label.
	      */
	     label : "Search",

	     /*
	      * suggest widget referece.
	      */
	     textBoxWidget : null,

	     /*
	      * store all items.
	      */
	     listItems : [],

	     /*
	      * default search params.
	      */
	     searchParam: { limitByItem : 5, keyword : ""},

	     /*
	      * index item selected.
	      */
	     _indexItem : -1,

	     /*
	      * store the item selected temp.
	      */
	     _selectedNode : null,

	     /*
	      * define if a key process is on curse.
	      */
	     _inProcessKey : false,

	     /*
	      * the time to delay key events.
	      */
	     _delay : 700,
	     
	     /*
	      * 
	      */
	     limitByItem : 5,

	     /*
	      * post create process.
	      */
	     postCreate: function() {
	    	 console.log("ENMEEE222EEEEE", _ENME.params);
	        this.textBoxWidget = registry.byId(this._suggest);
	        if (this.textBoxWidget) {
	            this._searchSuggestSupport();
	        }
//	        this.domNode.innerHTML = template;
	   //     parser.parse(this.domNode);   

	     },

	     /*
	      * set params
	      * @param object of values.
	      */
	     _setParams: function(value){
	         this.searchParam = value;
	     },


	     /*
	      * hide with fade out the suggest box.
	      */
	     hide : function(){
	         //console.info("HIDE");
	         this.listItems = [];
	         var fadeArgs = {
	                 node: this._suggestItems
	         };
	         dojo.fadeOut(fadeArgs).play();
	         this.clear();
	     },

	     /*
	      *
	      */
	     clear : function(){
	         if (this.textBoxWidget) {
	             this._selectedNode = null;
	             this.textBoxWidget.set("value", "");
	         }
	         dojo.empty(this._suggestItems);
	     },

	     /*
	      *
	      */
	     _moveSelected : function(position) {
	          dojo.query(".web-search-item").forEach(function(node, index, arr){
	               dojo.removeClass(node, "selected");
	          });
	         if (this._indexItem == -1) {
	             if (position == "up") {
	                 this._indexItem = this.listItems.length;
	             } else {
	                 this._indexItem = 0;
	             }
	         } else  if (this._indexItem == 0) {
	             if (position == "up") {
	                 this._indexItem = this.listItems.length - 1;
	             } else if (position == "down") {
	                 this._indexItem = this._indexItem + 1;
	             }
	         } else if (this._indexItem >= this.listItems.length) {
	              if (position == "up") {
	                  this._indexItem = this.listItems.length - 1;
	              } else {
	                  this._indexItem = 0;
	              }
	             this._indexItem = 0;
	         } else {
	             if (position == "up") {
	                 this._indexItem = this._indexItem - 1;
	             } else {
	                 this._indexItem = this._indexItem + 1;
	             }
	         }
	         //find node in the array.
	         var node = this.listItems[this._indexItem];
	         this._selectedNode = node;
	         if (node) {
	             this.textBoxWidget.attr("value", dojo.attr(node, "data-value"));
	             dojo.addClass(node, "selected");
	         }
	     },

	     /*
	      * on press enter.
	      * @param selectedItem the item selected by user.
	      */
	     processEnterAction : function(selectedItem) {
	         //if item is null, search whith value in the input, if not use the data-value attribute.
	         var searchUrl = encuestame.contextDefault;
	         if (selectedItem == null || dojo.attr(selectedItem, "data-url") == undefined) {
	             searchUrl = searchUrl.concat("/search?q=");
	             searchUrl = searchUrl.concat(this.textBoxWidget.get("value"));
	         } else {
	             searchUrl = searchUrl.concat(dojo.attr(selectedItem, "data-url"));
	         }
	         document.location.href = searchUrl;
	     },

	     /**
	      * Search suggest support.
	      */
	     _searchSuggestSupport : function() {

	         /**
	           * initialize the key up event.
	           */
	    	 on(this.textBoxWidget, "keypress", dojo.hitch(this, function(/* dojo event */e) {
	              var text = this.textBoxWidget.get("value");
	              // ENTER key
	              if (dojo.keys.ENTER == e.keyCode) {
	                   this.processEnterAction(this._selectedNode);
	              // ESC key
	              } else if (dojo.keys.ESCAPE == e.keyCode) {
	                  this.hide();
	              // UP ARROW KEY
	              } else if (dojo.keys.UP_ARROW == e.keyCode) {
	                  this._moveSelected("up");
	              // DOWN ARROW KEY
	              } else if (dojo.keys.DOWN_ARROW == e.keyCode) {
	                  this._moveSelected("down");
	              // THE REST OF KEYBOARD
	              } else {
	                  this._setParams(
	                          { limit: ENME.config('suggest_limit'),
	                            keyword : text,
	                            limitByItem : this.limitByItem,
	                            excludes : this.exclude});
	                  if (!encuestame.utilities.isEmpty(text) && text.length > 1) {
	                      var parent = this;
	                      if (!this._inProcessKey) {
	                          this._inProcessKey = true;
	                          setTimeout(function () {
	                              parent._inProcessKey = false;
	                              parent._searchCallService();
	                          }, this._delay);
	                      }
	                  }
	              }
	              //this.textBoxWidget //TODO: this.hide() on lost focus.
	          }));
	     },

	     /**
	      * Make a call to search service.
	      * {"error":{},"success":{"items":{"profiles":[],"questions":[],"attachments":[],
	      *  "tags":[{"id":null,"hits":3000001,"typeSearchResult":"HASHTAG",
	      *  "urlLocation":"/hashtag/nicaragua","score":100,
	      *  "itemSearchTitle":"Nicaragua","itemSearchDescription":null}]},
	      *  "label":"itemSearchTitle","identifier":"id"
	      *  }}
	      */
	     _searchCallService : function() {
	         var load = dojo.hitch(this, function(data) {
	             //console.debug("social _searchCallService", data);
	             dojo.empty(this._suggestItems);
	             if("items" in data.success) {
	                 var fadeArgs = {
	                         node: this._suggestItems
	                 };
	                 //reset selected values.
	                 this.listItems = [];
	                 this._indexItem = -1;
	                 this._selectedNode = null;
	                 dojo.fadeIn(fadeArgs).play();
	                 //print new items.
	                 this.printItems(data.success.items);
	             }
	         });
	         var error = function(error) {
	             console.debug("error", error);
	         };
	         encuestame.service.xhrGet(
	        		 _URL.service('encuestame.service.search.suggest'), 
	        		 this.searchParam, load, error);
	     },

	     /**
	      * Create a list of item.
	      * @param data suggested search item.
	      */
	     printItems : function(data) {
	         var widget = new searchSuggestItemsByType(
	                 {
	                  data : data,
	                  parentWidget : this
	                  });
	         this._suggestItems.appendChild(widget.domNode);
	     }
		});
	
	
	return SearchMenu;
});


//dojo.provide("encuestame.org.core.commons.search.SearchMenu");
//
//dojo.require("dijit.form.TextBox");
//dojo.require('encuestame.org.core.commons');
//dojo.require("encuestame.org.core.shared.utils.Suggest");
//dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
//
///**
// * Search menu widget.
// * This widget only return suggest float window with list of results based on keyword.
// */
//dojo.declare(
//    "encuestame.org.core.commons.search.SearchMenu",
//    [encuestame.org.main.EnmeMainLayoutWidget],{
//      /*
//       * template.
//       */
//      templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchMenu.html"),
//
//
//        /*
//         * default label.
//         */
//        label : "Search",
//
//        /*
//         * suggest widget referece.
//         */
//        textBoxWidget : null,
//
//        /*
//         * store all items.
//         */
//        listItems : [],
//
//        /*
//         * default search params.
//         */
//        searchParam: { limitByItem : 5, keyword : ""},
//
//        /*
//         * index item selected.
//         */
//        _indexItem : -1,
//
//        /*
//         * store the item selected temp.
//         */
//        _selectedNode : null,
//
//        /*
//         * define if a key process is on curse.
//         */
//        _inProcessKey : false,
//
//        /*
//         * the time to delay key events.
//         */
//        _delay : 700,
//        
//        /*
//         * 
//         */
//        limitByItem : 5,
//
//        /*
//         * post create process.
//         */
//        postCreate: function() {
//           this.textBoxWidget = registry.byId(this._suggest);
//           if (this.textBoxWidget) {
//               this._searchSuggestSupport();
//           }
//        },
//
//        /*
//         * set params
//         * @param object of values.
//         */
//        _setParams: function(value){
//            this.searchParam = value;
//        },
//
//
//        /*
//         * hide with fade out the suggest box.
//         */
//        hide : function(){
//            //console.info("HIDE");
//            this.listItems = [];
//            var fadeArgs = {
//                    node: this._suggestItems
//            };
//            dojo.fadeOut(fadeArgs).play();
//            this.clear();
//        },
//
//        /*
//         *
//         */
//        clear : function(){
//            if (this.textBoxWidget) {
//                this._selectedNode = null;
//                this.textBoxWidget.set("value", "");
//            }
//            dojo.empty(this._suggestItems);
//        },
//
//        /*
//         *
//         */
//        _moveSelected : function(position) {
//             dojo.query(".web-search-item").forEach(function(node, index, arr){
//                  dojo.removeClass(node, "selected");
//             });
//            if (this._indexItem == -1) {
//                if (position == "up") {
//                    this._indexItem = this.listItems.length;
//                } else {
//                    this._indexItem = 0;
//                }
//            } else  if (this._indexItem == 0) {
//                if (position == "up") {
//                    this._indexItem = this.listItems.length - 1;
//                } else if (position == "down") {
//                    this._indexItem = this._indexItem + 1;
//                }
//            } else if (this._indexItem >= this.listItems.length) {
//                 if (position == "up") {
//                     this._indexItem = this.listItems.length - 1;
//                 } else {
//                     this._indexItem = 0;
//                 }
//                this._indexItem = 0;
//            } else {
//                if (position == "up") {
//                    this._indexItem = this._indexItem - 1;
//                } else {
//                    this._indexItem = this._indexItem + 1;
//                }
//            }
//            //find node in the array.
//            var node = this.listItems[this._indexItem];
//            this._selectedNode = node;
//            if (node) {
//                this.textBoxWidget.attr("value", dojo.attr(node, "data-value"));
//                dojo.addClass(node, "selected");
//            }
//        },
//
//        /*
//         * on press enter.
//         * @param selectedItem the item selected by user.
//         */
//        processEnterAction : function(selectedItem) {
//            //if item is null, search whith value in the input, if not use the data-value attribute.
//              var searchUrl = encuestame.contextDefault;
//            if (selectedItem == null || dojo.attr(selectedItem, "data-url") == undefined) {
//                searchUrl = searchUrl.concat("/search?q=");
//                searchUrl = searchUrl.concat(this.textBoxWidget.get("value"));
//            } else {
//                searchUrl = searchUrl.concat(dojo.attr(selectedItem, "data-url"));
//            }
//            document.location.href = searchUrl;
//        },
//
//        /**
//         * Search suggest support.
//         */
//        _searchSuggestSupport : function() {
//
//            /**
//              * initialize the key up event.
//              */
//             dojo.connect(this.textBoxWidget, "onKeyUp", dojo.hitch(this, function(/* dojo event */e) {
//                 var text = this.textBoxWidget.get("value");
//                 // ENTER key
//                 if (dojo.keys.ENTER == e.keyCode) {
//                      this.processEnterAction(this._selectedNode);
//                 // ESC key
//                 } else if (dojo.keys.ESCAPE == e.keyCode) {
//                     this.hide();
//                 // UP ARROW KEY
//                 } else if (dojo.keys.UP_ARROW == e.keyCode) {
//                     this._moveSelected("up");
//                 // DOWN ARROW KEY
//                 } else if (dojo.keys.DOWN_ARROW == e.keyCode) {
//                     this._moveSelected("down");
//                 // THE REST OF KEYBOARD
//                 } else {
//                     this._setParams(
//                             { limit: ENME.config('suggest_limit'),
//                               keyword : text,
//                               limitByItem : this.limitByItem,
//                               excludes : this.exclude});
//                     if (!encuestame.utilities.isEmpty(text) && text.length > 1) {
//                         var parent = this;
//                         if (!this._inProcessKey) {
//                             this._inProcessKey = true;
//                             setTimeout(function () {
//                                 parent._inProcessKey = false;
//                                 parent._searchCallService();
//                             }, this._delay);
//                         }
//                     }
//                 }
//                 //this.textBoxWidget //TODO: this.hide() on lost focus.
//             }));
//        },
//
//        /**
//         * Make a call to search service.
//         * {"error":{},"success":{"items":{"profiles":[],"questions":[],"attachments":[],"tags":[{"id":null,"hits":3000001,"typeSearchResult":"HASHTAG","urlLocation":"/hashtag/nicaragua","score":100,"itemSearchTitle":"Nicaragua","itemSearchDescription":null}]},"label":"itemSearchTitle","identifier":"id"}}
//         */
//        _searchCallService : function(){
//            var load = dojo.hitch(this, function(data) {
//                //console.debug("social _searchCallService", data);
//                dojo.empty(this._suggestItems);
//                if("items" in data.success) {
//                    var fadeArgs = {
//                            node: this._suggestItems
//                    };
//                    //reset selected values.
//                    this.listItems = [];
//                    this._indexItem = -1;
//                    this._selectedNode = null;
//                    dojo.fadeIn(fadeArgs).play();
//                    //print new items.
//                    this.printItems(data.success.items);
//                }
//            });
//            var error = function(error) {
//                console.debug("error", error);
//            };
//            encuestame.service.xhrGet(
//                    encuestame.service.search.suggest, this.searchParam, load, error);
//        },
//
//        /**
//         * Create a list of item.
//         * @param data suggested search item.
//         */
//        printItems : function(data) {
//            var widget = new encuestame.org.core.commons.search.SearchSuggestItemsByType(
//                    {
//                     data : data,
//                     parentWidget : this
//                     });
//            this._suggestItems.appendChild(widget.domNode);
//        }
//});
//
