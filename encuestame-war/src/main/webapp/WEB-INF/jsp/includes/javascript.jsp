<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!--[if lt IE 9]>
     <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script>
        /* dojoConfig= {
                has: {
                    "dojo-firebug": true
                },
                useCommentedJson:true,
                parseOnLoad: false,
                foo: "bar",
                locale : "en-us",
                isDebug: 1,
                tlmSiblingOfDojo: false,
                async: true
            };
       */     
</script>
<script data-dojo-config="has:{'dojo-firebug': true, 'dojo-debug-messages': true}, parseOnLoad: true,isDebug: 1, async: 1" src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script src="<%=request.getContextPath()%>/resources/static/common.js"></script>
<script>
         //dojo.require("dijit.dijit");
         //dojo.require("dojo.parser");
         //dojo.require("dojo.io.script");
</script>
<script src="<%=request.getContextPath()%>/resources/js/encuestame/run.js"></script>
<script>
require(["dojo/parser", "ready!"], function(parser) {
        ENME.init({
            contextPath: '<%=request.getContextPath()%>',
            domain : '<%=WidgetUtil.getDomain(request)%>',
            suggest_limit : 10,
            delay : 1800000,
            message_delay : 5000,
            activity_levelDebug : "<%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.levelDebug")%>",
            activity_maxConnections : <%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.maxConnections")%>,
            activity_maxNetworkDelay : <%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.maxNetworkDelay")%>,
            notification_delay : <%=EnMePlaceHolderConfigurer.getProperty("not.main.delay")%>,
            notification_limit : <%=EnMePlaceHolderConfigurer.getProperty("not.main.limit")%>,
           	tp_a : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.allowed")%>,
           	tp_hr : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.hr")%>,
           	tp_minsoa : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.minsoa")%>
        });
});
</script>
<!-- 
<script src="<%=request.getContextPath()%>/resources/js/default.js"></script>
 -->
<c:if test="${!development}">
    <script src="<%=request.getContextPath()%>/resources/js/dojo/encuestame-commons.js?<%=EnMePlaceHolderConfigurer.getProperty("app.version")%>"></script>
</c:if>
<%-- <script src="<%=request.getContextPath()%>/resources/js/encuestame/encuestame.js"></script> --%>
<%--<%=WidgetUtil.getAnalytics("analytics.inc")%>--%>