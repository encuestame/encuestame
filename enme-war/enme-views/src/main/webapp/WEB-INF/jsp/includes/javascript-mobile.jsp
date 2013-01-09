<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!--[if lt IE 9]>
     <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script>
         var dojoConfig= {
            baseUrl: '<%=request.getContextPath()%>/resources/js/',
            packages: [
                       { name: "dojo", location: "dojo" },
                       { name: "dijit", location: "dijit" },
                       { name: "dojox", location: "dojox" },
                       { name: "me", location: "me" }
            ],
            has: {
                    'dojo-firebug': true,
                    'dojo-debug-messages': true
                },
            useCommentedJson:true,
            parseOnLoad: false,
            isDebug: 1,
            tlmSiblingOfDojo: false,
            async: true
            };
</script>
<script  src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/me/run.js"></script>
<!-- Temporal service to store all service. -->
<script src="<%=request.getContextPath()%>/resources/js/commons.js"></script>
<script>
var config = {
    contextPath: '${pageContext.request.contextPath}'
};
require([
    "dojo/_base/declare",
    "dojo/parser",
    "dojo/ready",
    "me/core/enme",
], function(declare, parser, ready, _ENME) {
    console.log("Initialize the ENME");
    ready(function(){
        // Call the parser manually so it runs after our widget is defined, and page has finished loading
        _ENME.init({
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
        //parse all widgets.
        parser.parse();
    });
});
</script>
<%--<%=WidgetUtil.getAnalytics("analytics.inc")%>--%>

<noscript>
    <meta http-equiv="X-Frame-Options" content="deny" />
</noscript>

<script>window.localStorage&&window.localStorage.clear();</script>