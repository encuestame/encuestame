<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!--[if lt IE 9]>
     <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script>
        dojoConfig = {
                    parseOnLoad: true,
                    useCommentedJson:true,
                    isDebug: false
                    };
</script>
<script src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<!-- Production
<script src="<%=request.getContextPath()%>/resources/js/req/raphael-min.js"></script>
-->
<!-- Dev -->
<script src="<%=request.getContextPath()%>/resources/js/req/raphael.js"></script>
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
            activity : {
                levelDebug : "<%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.levelDebug")%>",
                maxConnections : <%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.maxConnections")%>,
                maxNetworkDelay : <%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.maxNetworkDelay")%>
                },
            notification : {
                delay : <%=EnMePlaceHolderConfigurer.getProperty("not.main.delay")%>,
                limit : <%=EnMePlaceHolderConfigurer.getProperty("not.main.limit")%>
                },
            tp : {
                a : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.allowed")%>,
                hr : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.hr")%>,
                minsoa : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.minsoa")%>
                },
            pll : {

            }
        };
</script>
<script src="<%=request.getContextPath()%>/resources/js/default.js"></script>
 <c:if test="${!development}">
    <script src="<%=request.getContextPath()%>/resources/js/dojo/encuestame-commons.js?<%=EnMePlaceHolderConfigurer.getProperty("app.version")%>"></script>
</c:if>
<%-- <script src="<%=request.getContextPath()%>/resources/js/encuestame/encuestame.js"></script> --%>
<%=WidgetUtil.getAnalytics("analytics.inc")%>