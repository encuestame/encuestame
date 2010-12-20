dojo.provide("encuestame.org.core.map.Map");

dojo.declare("encuestame.org.core.map.Map", null, {

        constructor: function(node){
                this.node = node;
        },
        node : null,
        map : null,
        zoom : 10,
        _iconImage : "images/beachflag.png",
        _markerImage : "'images/beachflag_shadow.png'",

        testLocations : [
                   ['Bondi Beach', -33.890542, 151.274856, 4],
                   ['Coogee Beach', -33.923036, 151.259052, 5],
                   ['Cronulla Beach', -34.028249, 151.157507, 3],
                   ['Manly Beach', -33.80010128657071, 151.28747820854187, 2],
                   ['Maroubra Beach', -33.950198, 151.259302, 1]
                 ],

        /*
         * Create Map
         */
        _createMap : function(long, lat){
             var mapOptions = {
                     zoom: this.zoom,
                     mapTypeId: google.maps.MapTypeId.ROADMAP,
                     center: this._newLatLng(long,lat)
                   };
              this.map = new google.maps.Map(document.getElementById(this.node),
                   mapOptions);
        },

        /*
         * Add Marker.
         */
        addMarker : function(long, lat){
             console.debug("AddMarker");
             var marker;
             var parliament = this._newLatLng(long,lat);
             marker = new google.maps.Marker({
                 map: this.map,
                 draggable:true,
                 animation: google.maps.Animation.DROP,
                 position: parliament
               });
               //google.maps.event.addListener(marker, 'click', this.toggleBounce(marker));
        },

        /*
         * New Lat Long.
         */
        _newLatLng : function(long,lat){
            return new google.maps.LatLng(long,lat);
        },

        toggleBounce : function(marker) {
              if (marker.getAnimation() != null) {
                marker.setAnimation(null);
              } else {
                marker.setAnimation(google.maps.Animation.BOUNCE);
              }
        },

        setMarkers : function() {
              // Add markers to the map
              // Marker sizes are expressed as a Size of X,Y
              // where the origin of the image (0,0) is located
              // in the top left of the image.

              // Origins, anchor positions and coordinates of the marker
              // increase in the X direction to the right and in
              // the Y direction down.
              var image = new google.maps.MarkerImage(this._iconImage,
                  // This marker is 20 pixels wide by 32 pixels tall.
                  new google.maps.Size(20, 32),
                  // The origin for this image is 0,0.
                  new google.maps.Point(0,0),
                  // The anchor for this image is the base of the flagpole at 0,32.
                  new google.maps.Point(0, 32));
              var shadow = new google.maps.MarkerImage(this._markerImage,
                  // The shadow image is larger in the horizontal dimension
                  // while the position and offset are the same as for the main image.
                  new google.maps.Size(37, 32),
                  new google.maps.Point(0,0),
                  new google.maps.Point(0, 32));
                  // Shapes define the clickable region of the icon.
                  // The type defines an HTML <area> element 'poly' which
                  // traces out a polygon as a series of X,Y points. The final
                  // coordinate closes the poly by connecting to the first
                  // coordinate.
              var shape = {
                  coord: [1, 1, 1, 20, 18, 20, 18 , 1],
                  type: 'poly'
              };
              for (var i = 0; i < this.testLocations.length; i++) {
                var beach = this.testLocations[i];
                var myLatLng = new google.maps.LatLng(beach[1], beach[2]);
                var marker = new google.maps.Marker({
                    position: myLatLng,
                    map: this.map,
                    //shadow: shadow,
                    //icon: image,
                    //shape: shape,
                    title: beach[0],
                    zIndex: beach[3]
                });
              }
            }
});