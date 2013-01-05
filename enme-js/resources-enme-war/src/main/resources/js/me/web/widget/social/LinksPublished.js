define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
      "me/web/widget/data/CacheLinkedList",
     "me/web/widget/social/LinksPublishedItem",
     "me/core/enme",
     "dojo/text!me/web/widget/social/templates/linksPublished.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    cacheLinkedList,
    linksPublishedItem,
    _ENME,
     template) {

  return declare([
                   _WidgetBase,
                   _TemplatedMixin,
                   main_widget,
                   cacheLinkedList,
                   _WidgetsInTemplateMixin], {

  // template string.
  templateString : template,

   /**
    * Type.
    */
   type : "TWEETPOLL",

   /**
    * Item Id.
    */
   itemId : "",

   /**
    *
    */
   more : true,

   /**
    *
    */
   property : "links",

   /**
    *
    */
   hasthag : "",

   /**
    * Poll Navigate default parameters.
    */
   _params : { type :  null, id : null, max : 10, start : 0},


   /*
    * post create.
    */
   postCreate : function() {
       if (this.type == null || this.itemId == null) {
           _EMNE.log("type is null");
       } else {
           //enable more support.
           if (this.more) {
               this.enableMoreSupport(this._params.start, this._params.max, this._more);
           }
           this._params.id = this.itemId;
           if (this.type === _ENME.TYPE_SURVEYS[0]) { // tweeptoll
             this._params.type = _ENME.TYPE_SURVEYS[0];
           } else if(this.type === _ENME.TYPE_SURVEYS[1]) { // poll
             this._params.type = _ENME.TYPE_SURVEYS[1];
           } else if(this.type === _ENME.TYPE_SURVEYS[2]) { // survey
             this._params.type = _ENME.TYPE_SURVEYS[2];
           } else if(this.type === _ENME.TYPE_SURVEYS[3]) { // hashtag
             this._params.type = _ENME.TYPE_SURVEYS[3];
           } else {
             this._params.type = "";
           }
       }
       dojo.hitch(this, this.loadItems());
   },

   /**
    * Function to clean _items node.
    */
   _empty : function() {
       dojo.empty(this._items);
   },

   handlerError : function() {
     _EMNE.log("error", error);
   },

   /**
    * customize service params.
    */
   getParams : function() {
       return this._params;
   },

   /**
    * The url json service.
    * @returns
    */
   getUrl : function() {
       return 'encuestame.service.social.links.loadByType';
   },

   /**
    * Create a new PollNavigateItem.
    */
   processItem : function(/** poll data**/  data, /** position **/ index) {
      this._createLink(data);
   },


   /**
    * Display a empty message.
    */
   displayEmptyMessage : function () {
       var _node = this._items,
       _message = dojo.doc.createElement("h2");
       _message.innerHTML = "No links refered with this hashtag";
       dojo.place(_message, _node);
   },

   /**
    *
    */
   _showNoLinksMessage : function() {
       var message = dojo.doc.createElement("h2");
       message.innerHTML = "No Links Refered.";
       this._items.appendChild(message);
   },

   /**
    * Create link.
    * @param data link data.
    */
   _createLink : function(data){
       var widget = new linksPublishedItem(
           {
             social : data.provider_social,
             link : data.link_url,
             text : data.publishd_text,
             date : data.published_date
           });
       this._items.appendChild(widget.domNode);
   }


  });
});