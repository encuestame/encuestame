define([ "dojo/parser",
         "dijit/registry",
         "dojo/_base/declare", 
         "dijit/_WidgetBase", 
         "dijit/_TemplatedMixin",
		 "dijit/form/TextBox", 
		 "dijit/_WidgetsInTemplateMixin",
		 //"me/web/widget/suggestion/Suggest",
		 "me/core/main_widgets/URLServices",
		 "me/core/main_widgets/EnmeMainLayoutWidget",
		 "dojo/text!me/web/widget/menu/template/searchSuggestItemSection.html" ], function(
		parser,
		registry,
		declare,
		_WidgetBase,
		_TemplatedMixin, 
		text,  
		_WidgetsInTemplateMixin,
		//suggest,
		_URL,
		main_widget,
		template) {

	//console.log("SEARCHHHHHHH MENU", parser);
	
	return declare([ _WidgetBase, _TemplatedMixin, main_widget, _URL], {		
		
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
//      dojo.forEach(this.items,
//              dojo.hitch(this,function(item) {
//           this._itemSuggest.appendChild(this._createItem(item, this.label));
//      }));
//  },
//
//  /**
//   * Create a search item.
//   * @param item
//   * @param type
//   */
//  _createItem : function(item, type) {
//      var div = dojo.create("div");
//      dojo.addClass(div, "web-search-item");
//      dojo.attr(div, "data-value", item.itemSearchTitle);
//      dojo.attr(div, "data-type", type);
//      var h4 = dojo.create("h4", null, div);
//      h4.innerHTML = item.itemSearchTitle;
//      if (item.urlLocation != "" && item.urlLocation != null) { //on click point to this url.
//         dojo.attr(div, "data-url", item.urlLocation);
//         dojo.connect(div, "onclick", dojo.hitch(this, function(event) {
//             //console.debug("click item", encuestame.contextDefault+item.urlLocation	);
//             document.location.href = encuestame.contextDefault+item.urlLocation;
//         }));
//      } else { // point to search url
//
//      }
//      this.parentWidget.listItems.push(div);
//      return div;
//  }	

	});
});