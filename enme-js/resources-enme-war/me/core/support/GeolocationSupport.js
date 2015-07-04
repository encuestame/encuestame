define([
     "dojo/_base/declare",
	 'me/core/features/base',
     "me/core/enme"],
    function(
    declare,
    features,
    _ENME) {

    'use strict';


  /**
    * Represent a geo location
    * @property lat latitude
    * @property long longitude
    * @method Location
    */
   var Location = function(lat, lng) {
      this.lat = lat || 0.0;
      this.lng = lng || 0.0;
   };

   Location.prototype = {

      /**
       * Get the latitude.
       * @method
       */
      getLatitude : function() {
        return this.lng;
      },

      /**
       * Get the latitude.
       * @method
       */
      getLongitude : function() {
        return this.lat;
      }
   };

  /**
   * Geolocation support
   * @method
   */
  var GeoLocationSupport = function(options) {
       var parent = this;
       this.location = null;
       try {
          if (features.geolocation) {
              navigator.geolocation.getCurrentPosition(
                      function(){
                        options.success.apply(parent, arguments);
                      },
                      options.error , {
                        timeout: 10000
              });
          } else {
              parent.location = new Location();
              options.error();
          }
       } catch(error) {
          console.info("error geo object", error);
       }

  };

  GeoLocationSupport.prototype = {

    /**
     * Return a boolean response if the objects contains a longitud and latitude.
     * @method
     */
    isLocated : function() {
        return this.isLocated || false;
    },

    /**
     * Return the location Object.
     * @method
     */
    getLocation : function() {
        return this.position;
    }
  };

  return GeoLocationSupport;
});