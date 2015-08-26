<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-config.jsp"%>

<script src="<%=request.getContextPath()%>/resources/js/commons/commons-mobile.<c:url value="${development ? '' : 'min.'}" />js"></script>
<script src="<%=request.getContextPath()%>/resources/js/requirejs/require.js"></script>

<script>
    require.config({
        baseUrl: "<%=request.getContextPath()%>/resources/js/",
        paths: {
            "me": "me",
        }
    });
    require(["deliteful-build/layer"], function() {
        require(["mobile/init"]);
    });
</script>

<%@ include file="/WEB-INF/jsp/includes/decorators/log-js.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/analytics.jsp"%>

