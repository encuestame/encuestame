<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initBody.jsp" %>
<!-- Temporal Api -->
<script src="http://maps.google.com/maps/api/js?sensor=false"
        type="text/javascript"></script>
<script type="text/javascript">
    dojo.require("encuestame.org.core.map.Map");
    function initialize() {
        var w = new encuestame.org.core.map.Map("map_canvas");
        w._createMap(59.12522, 18.07002);
        w.addMarker(59.327383, 18.06747);
        w.addMarker(59.127383, 18.16747);
        w.addMarker(58.327383, 17.06747);
        w.setMarkers();
    }
    dojo.addOnLoad(initialize);
</script>
<div class="defaultMarginWrapper">
   <div id="map_canvas" style="width: 100%;height: 300px;"></div>
</div>
<%@ include file="/WEB-INF/jsp/includes/endBody.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/validate.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>