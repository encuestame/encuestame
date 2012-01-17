<!-- Temporal Api -->
<script src="http://maps.google.com/maps/api/js?sensor=false"
        type="text/javascript"></script>
<script type="text/javascript">

    function watchLocation(successCallback, errorCallback) {

        successCallback = successCallback || function() {
        };
        errorCallback = errorCallback || function() {
        };

        // Try HTML5-spec geolocation.
        var geolocation = navigator.geolocation;

        if (geolocation) {
            // We have a real geolocation service.

            try {
                function handleSuccess(position) {
                    successCallback(position.coords);
                }

                geolocation.watchPosition(handleSuccess, errorCallback, {
                    enableHighAccuracy : true,
                    maximumAge : 5000
                // 5 sec.
                });
            } catch (err) {
                errorCallback();
            }
        } else {
            errorCallback();
        }
    }

    dojo.require("encuestame.org.core.map.Map");
    watchLocation(function(coords) {
        console.debug("COORDS", coords);
        var w = new encuestame.org.core.map.Map("map_canvas");
        w._createMap(coords.latitude, coords.longitude);
        w.addMarker(coords.latitude, coords.longitude);
        w.addMarker(59.127383, 18.16747);
        w.addMarker(58.327383, 17.06747);
        w.setMarkers();
    }, function() {
        console.error("ERROR");
    });
    //dojo.addOnLoad(initialize);
</script>