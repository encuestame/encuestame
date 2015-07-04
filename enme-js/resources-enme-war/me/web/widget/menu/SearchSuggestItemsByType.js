define([ "dojo/parser",
         "dijit/registry",
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/menu/SearchSuggestItemSection",
         "dojo/dom-class",
         "dojo/dom-construct",
         "dojo/text!me/web/widget/menu/template/searchSuggestItem.html" ], function(
        parser,
        registry,
        declare,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        main_widget,
        searchSuggestItemSection,
        domClass,
        domConstruct,
        template) {

    "use strict";

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
         //console.info("SearchSuggestItem", this.data);
         //console.info("SearchSuggestItem",this.checkIfDataIsEmtpy());
         if (this.data && !this.checkIfDataIsEmtpy()) {
             dojo.empty(this._container);
             if ("tags" in this.data) {
                 this._printItems("Hashtags", this.data.tags);
             }

             if ("profiles" in this.data) {
                 this._printItems("Profiles", this.data.profiles);
             }

             if ("questions" in this.data) {
                 this._printItems("Questions", this.data.questions);
             }

             if ("attachments" in this.data) {
                 this._printItems("Documents", this.data.attachments);
             }
         } else {
             var div = domConstruct.create("div");
             domClass.add(div, "web-suggest-noresults");
             domClass.add(div, "wrap");
             div.innerHTML = this.parentWidget.defaultNoResults + " ";
             var span = domConstruct.create("span", null, div);
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
             isEmpty = this.data.tags.length === 0 ? true : false;
         }

         if ("profiles" in this.data && isEmpty) {
             isEmpty = this.data.profiles.length === 0 ? true : false;
         }

         if ("questions" in this.data && isEmpty) {
             isEmpty = this.data.questions.length === 0 ? true : false;
         }

         if ("attachments" in this.data && isEmpty) {
             isEmpty = this.data.attachments.length === 0 ? true : false;
         }
         return isEmpty;
     },

    /**
     * Print items.
     */
    _printItems : function(label, items) {
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