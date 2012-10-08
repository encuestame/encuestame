define([ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/web/widget/stream/HashTagInfo", "me/core/enme",
    "dojo/text!me/web/widget/stats/templates/position.html" ], function(
    declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin,
    main_widget, hashTagInfo, _ENME, template) {
  return declare([ _WidgetBase, _TemplatedMixin, main_widget,
      _WidgetsInTemplateMixin ], {

    // template string.
    templateString : template,

      /**
      * Tag name.
      */
     tagName : "",

     /**
      * Post create life cycle.
      */
     postCreate : function(){
         this._callService();
     },

     /**
      * Call to ranking service.
      */
     _callService : function () {
         var params = {
             tagName : this.tagName,
          };
          var load = dojo.hitch(this, function(data) {
            if ("success" in data) {
              this._printRate(data.success.hashTagRankingStats);
            }
          });
          this.callGET(params, this.getURLService().service('encuestame.service.list.ranking.hashtag'), load, null, null);
      },

     /**
      * Display the ranking.
      */
     _printRate : function(data) {
         dojo.forEach(data,
                 dojo.hitch(this,function(item) {
                     this._createRow(item);
         }));
     },

     /**
      * Build a ranking position row.
      * @param {Object} item
      * <tr>
             <td>USA</td>
             <td>4</td>
             <td><img src="/resources/images/icons/enme_down.png" />
             </td>
         </tr>
      */
     _createRow : function (item) {
         var tr = dojo.create("div");
         dojo.addClass(tr, 'position-row');
         var row1 = dojo.create("span", null, tr);
         row1.innerHTML = item.tagName;
         dojo.addClass(row1, 'position-label');
         dojo.addClass(row1, 'ellipsis');
         var row2 = dojo.create("span", null, tr);
         dojo.addClass(row2, 'position');
         row2.innerHTML = item.position;
         dojo.addClass(row2, "web-position-number");
         var row3 = dojo.create("span", null, tr);
         dojo.addClass(row3, 'graph');
         row3.appendChild(this._getPositionRow(parseInt(item.position), parseInt(item.lastPosition)));
         if (item.tagName === this.tagName) {
           dojo.addClass(tr, 'highlight');
         } else {
           var url_hashtag = _ENME.hashtagContext(item.tagName);
           var a = dojo.create("a");
           a.innerHTML = item.tagName;
           a.setAttribute('href', url_hashtag);
           dojo.empty(row1);
           row1.appendChild(a);
         }
         this._rows.appendChild(tr);
     },

     /**
      * Get the position row.
      */
     _getPositionRow : function (last, current) {
         var node = dojo.create("span");
         if (last > current) {
             dojo.addClass(node, "web-position-down");
         } else if (last < current) {
             dojo.addClass(node, "web-position-up");
         } else {
             dojo.addClass(node, "web-position-equal");
         }
         return node;
     }

  });
});
