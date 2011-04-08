dojo.provide("encuestame.org.core.commons.search.SearchMenu");

dojo.require("encuestame.org.core.shared.utils.Suggest");

/**
 * Search menu widget.
 * This widget only return suggest float window with list of results based on keyword.
 */
dojo.declare(
    "encuestame.org.core.commons.search.SearchMenu",
    [encuestame.org.core.shared.utils.SuggestItem],{
      /*
       * template.
       */
      templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchMenu.html"),

        /*
         * enable widget on template.
         */
        widgetsInTemplate: true,

        /*
         * suggest widget referece.
         */
        suggestWidget : null,

        /*
         * store all items.
         */
        listItems : [],

        /*
         * post create process.
         */
        postCreate: function() {
          //instance suggest widget.
            this.suggestWidget = new encuestame.org.core.shared.utils.Suggest(
                { url :encuestame.service.search.suggest,
                  addButton : false,
                  hideLabel: true,
                  query :  {itemSearchTitle : "*"},
                  templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/suggest.html")});
            //override build row method.
            this.suggestWidget.buildRow = dojo.hitch(this, function(data){
                  console.info("suggest buildRow...", data);
                  this.newItemSuggest(data);
              });
            //append child widget dom node.
            this._suggest.appendChild(this.suggestWidget.domNode);
            //if suggest is not null we override pricessSelectedItem method.
            if(this.suggestWidget){
                this.suggestWidget.processSelectedItem = dojo.hitch(this, function(data){
                    console.info("Processing Item Selected ...", data);
                    this.newItemSuggest(data);
                });
            }
        },

        /*
         * Create new suggest item.
         * @param data suggested search item.
         */
        newItemSuggest : function(data){
            var widget = new encuestame.org.core.commons.search.SearchSuggestItem(
                    {
                     data : data,
                     parentWidget : this,
                     suggestWidget : this.suggestWidget
                     });
            this.listItems.push(widget);
            this.suggestWidget._suggestItems.appendChild(widget.domNode);
            widget.processItem = this.suggestWidget.processSelectedItem;
        }
});

/*
 * Widget define item suggest box.
 */
dojo.declare(
        "encuestame.org.core.commons.search.SearchSuggestItem",
        [dijit._Widget, dijit._Templated],{
        //template
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchSuggestItem.html"),
        //widgets in template
        wigetsInTemplate: true,
        //reference of suggest widget.
        suggestWidget: null,

        _goTo : function(event){
            //TODO: do something when you click on it
            this.suggestWidget.hide();
        }
});