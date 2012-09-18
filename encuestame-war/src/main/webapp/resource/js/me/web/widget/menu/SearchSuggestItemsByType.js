define([ "dojo/parser",
         "dijit/registry",
         "dojo/_base/declare", 
         "dijit/_WidgetBase", 
         "dijit/_TemplatedMixin", 
		 "dijit/_WidgetsInTemplateMixin",
		 "me/core/main_widgets/EnmeMainLayoutWidget",
		 "me/web/widget/menu/SearchSuggestItemSection",
		 "dojo/text!me/web/widget/menu/template/searchSuggestItem.html" ], function(
		parser,
		registry,
		declare,
		_WidgetBase,
		_TemplatedMixin,   
		_WidgetsInTemplateMixin,
		main_widget,
		searchSuggestItemSection,
		template) {
	
	return declare([ _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {		
		
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
	     console.info("SearchSuggestItem", this.data);
	     console.info("SearchSuggestItem",this.checkIfDataIsEmtpy());
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
	    console.info("_printHashtags", items);
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

///**
// * Widget define item suggest box.
// */
//dojo.declare(
//        "encuestame.org.core.commons.search.SearchSuggestItemsByType",
//        [encuestame.org.main.EnmeMainLayoutWidget],{
//
//        /*
//         * template
//         */
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchSuggestItem.html"),
//
//        /*
//         * widgets in template
//         */
//        wigetsInTemplate: true,
//        
//        /*
//         * reference of suggest widget.
//         */
//        parentWidget: null,
//
//        /*
//         * results
//         */
//        data: null,       
//
//        /**
//         * Post Create Life Cycle.
//         */
//        postCreate : function() {
//            console.info("SearchSuggestItem", this.data);
//            console.info("SearchSuggestItem",this.checkIfDataIsEmtpy());
//            if (this.data && !this.checkIfDataIsEmtpy()) {
//                dojo.empty(this._container);
//                if ("tags" in this.data) {
//                    this._printItems("Hashtags", this.data.tags);
//                };
//
//                if ("profiles" in this.data) {
//                    this._printItems("Profiles", this.data.profiles);
//                };
//
//                if ("questions" in this.data) {
//                    this._printItems("Questions", this.data.questions);
//                };
//
//                if ("attachments" in this.data) {
//                    this._printItems("Documents", this.data.attachments);
//                };
//            } else {
//                var div = dojo.create("div");
//                dojo.addClass(div, "web-suggest-noresults");
//                dojo.addClass(div, "wrap");
//                div.innerHTML = this.parentWidget.defaultNoResults + " ";
//                var span = dojo.create("span", null, div);
//                span.innerHTML = this.parentWidget.textBoxWidget.get("value");
//                this._container.appendChild(div);
//            }
//        },
//
//        /**
//         * Check if data for each item is empty.
//         * @returns {Boolean}
//         */
//        checkIfDataIsEmtpy : function() {
//            var isEmpty = true;
//            if ("tags" in this.data) {
//                isEmpty = this.data.tags.length == 0 ? true : false;
//            };
//
//            if ("profiles" in this.data && isEmpty) {
//                isEmpty = this.data.profiles.length == 0 ? true : false;
//            };
//
//            if ("questions" in this.data && isEmpty) {
//                isEmpty = this.data.questions.length == 0 ? true : false;
//            };
//
//            if ("attachments" in this.data && isEmpty) {
//                isEmpty = this.data.attachments.length == 0 ? true : false;
//            };
//            return isEmpty;
//        },
//
//       /**
//        * Print items.
//        */
//       _printItems : function(label, items) {
//           console.info("_printHashtags", items);
//           if (items.length > 0) {
//               var hash = new encuestame.org.core.commons.search.SearchSuggestItemSection(
//                       {
//                        label : label,
//                        parentWidget : this.parentWidget,
//                        items : items
//                       });
//               this._container.appendChild(hash.domNode);
//           }
//       }
//});

///**
// * Search Suggest Item Secction.
// * Is a space to store items separated by section, questions, hashstag, attachmetns.
// */
//dojo.declare(
//        "encuestame.org.core.commons.search.SearchSuggestItemSection",
//        [encuestame.org.main.EnmeMainLayoutWidget],{
//
//        template
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchSuggestItemSection.html"),
//
//        
//        items : [],       
//        
//        /*
//         * 
//         */
//        parentWidget : null,
//
//        /*
//         * 
//         */
//        label : "",
//
//        /**
//         * Post Create.
//         */
//        postCreate : function() {
//            dojo.forEach(this.items,
//                    dojo.hitch(this,function(item) {
//                 this._itemSuggest.appendChild(this._createItem(item, this.label));
//            }));
//        },
//
//        /**
//         * Create a search item.
//         * @param item
//         * @param type
//         */
//        _createItem : function(item, type) {
//            var div = dojo.create("div");
//            dojo.addClass(div, "web-search-item");
//            dojo.attr(div, "data-value", item.itemSearchTitle);
//            dojo.attr(div, "data-type", type);
//            var h4 = dojo.create("h4", null, div);
//            h4.innerHTML = item.itemSearchTitle;
//            if (item.urlLocation != "" && item.urlLocation != null) { on click point to this url.
//               dojo.attr(div, "data-url", item.urlLocation);
//               dojo.connect(div, "onclick", dojo.hitch(this, function(event) {
//                   console.debug("click item", encuestame.contextDefault+item.urlLocation	);
//                   document.location.href = encuestame.contextDefault+item.urlLocation;
//               }));
//            } else {  point to search url
//
//            }
//            this.parentWidget.listItems.push(div);
//            return div;
//        }
//});