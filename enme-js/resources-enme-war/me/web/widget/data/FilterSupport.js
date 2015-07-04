/**
 *
 *
 * @author juanATencuestame.org
 * @since 2013-07-13 23:46:29
 */
define([
     "dojo/_base/declare",
     "dojo/_base/lang",
     "me/web/widget/support/ItemsFilterSupport",
     "me/core/enme"],
    function(
    declare,
    _lang,
    ItemsFilterSupport,
    _ENME) {

  return declare(null, {


      /**
       * Get filter data, if exist
       */
      getFilterData : function (params) {
        if (this._filters) {
          _lang.mixin(params, this._filters.getFilterData());
        }
        return this.applyExtraFilters(params);
      },

	  /**
	   * Apply some extra filter, (optional}
	   * @param a
	   * @returns {*}
	   */
	  applyExtraFilters : function(a) { return a; },

      /**
       * Clean filters trigger function
       */
      _cleanFilters : function (e) {
            dojo.stopEvent(e);
            this.cleanFilterData();
      },

      /**
       * Clean the widget
       */
      cleanFilterData : function () {
        if (this._filters) {
            this._filters.cleanFilterData();
        }
      }

  });

});