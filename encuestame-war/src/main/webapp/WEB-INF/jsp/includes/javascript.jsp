<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script>
        dojoConfig = {
                    parseOnLoad: true,
                    useCommentedJson:true,
                    isDebug: false
                    };
</script>
<script src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script>
         dojo.require("dijit.dijit");
         dojo.require("dojo.parser");
         dojo.require("dojo.io.script");
</script>
<script>
        var config = {
            contextPath: '<%=request.getContextPath()%>',
            domain : '<%=WidgetUtil.getDomain(request)%>',
            delay : 60000,
            activity : { levelDebug :"info", maxConnections : 2, maxNetworkDelay : 50000},
            notification : { delay : 60000, limit : 8},
            tp : {a : 2, hr : false, minsoa : 1 }
        };
</script>
<script src="<%=request.getContextPath()%>/resources/js/default.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/dojo/encuestame-commons.js"></script>
<%-- <script src="<%=request.getContextPath()%>/resources/js/encuestame/encuestame.js"></script> --%>
<%=WidgetUtil.getAnalytics("analytics.inc")%>