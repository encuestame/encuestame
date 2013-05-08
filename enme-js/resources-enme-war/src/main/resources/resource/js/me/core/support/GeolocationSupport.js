define([
     "dojo/_base/declare",
     "me/core/enme"],
    function(
    declare,
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
  var GeoLocationSupport = function() {
       var parent = this;
       this.location = null;
       try {
          if (Modernizr.geolocation) {
              var geo_success = function(position) {
                  console.info("dddd", position.coords.latitude, position.coords.longitude);
              };

              var geo_error = function() {
                  console.error("Sorry, no position available.");
              };

              var geo_options = {
                enableHighAccuracy: true,
                maximumAge        : 30000,
                timeout           : 27000
              };

              var wpid = navigator.geolocation.watchPosition(geo_success, geo_error, geo_options);
          } else {
              parent.location = new Location();
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