define([ "dojo/_base/declare", "dijit/_WidgetBase", "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/web/widget/stream/HashTagInfo", "me/core/enme",
    "dojo/text!me/web/widget/stats/templates/stats.html" ], function(
    declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin,
    main_widget, hashTagInfo, _ENME, template) {
  return declare([ _WidgetBase, _TemplatedMixin, main_widget,
      _WidgetsInTemplateMixin ], {

    // template string.
    templateString : template,

    /**
     * service json string.
     */
    _service : 'encuestame.service.list.generic.stats',

    /**
     * typeGeneric of stats.
     */
    typeGeneric : "",

    /**
     * Item id.
     */
    generic : "",

    /*
     *
     */
    postCreate : function() {
      this._callGenericStats();
    },

    /**
     *
     */
    _callGenericStats : function() {
      var params = {
        id : this.generic,
        filter : this.typeGeneric,
      };
      var load = dojo.hitch(this, function(data) {
        if ("success" in data) {
          this._buildStats(data.success.generic);
        }
      });
      this.getURLService().get(this._service, params,  load, null, null);
    },

    /**
     *  Build the stats table.
     *  @param {Object} Array of stats.
     */
    _buildStats : function(stats) {
      for ( var i in stats) {
        var value = stats[i];
        if (value != null) {
          this._createRow(i, stats[i]);
        }
      }
    },

    /**
     * Create a row.
     * @param {String} header
     * @param {String} value
     */
    _createRow : function(header, value) {
      var tr = dojo.create("tr");
      var label = dojo.create("td", null, tr);
      label.innerHTML = header;
      var label = dojo.create("td", null, tr);
      label.innerHTML = value;
      this._rows.appendChild(tr);
    }

  });
});