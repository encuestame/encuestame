<%@page import="org.encuestame.core.security.util.WidgetUtil"%>
<script src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"
        djConfig="parseOnLoad: true, useCommentedJson:true, isDebug: true"></script>
<script type="text/javascript">
        var config = {
            contextPath: '<%=request.getContextPath()%>',
            delay : 60000,
            activity : { levelDebug :"info", maxConnections : 2, maxNetworkDelay : 50000},
            notification : { delay : 60000, limit : 8},
            tp : {a : 2, hr : false, minsoa : 1 }
        };
        var djConfig = { isDebug: true };
</script>
<script src="<%=request.getContextPath()%>/resources/js/dojo/encuestame-commons.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/encuestame/encuestame.js"></script>
<%=WidgetUtil.getAnalytics("analytics.inc")%>