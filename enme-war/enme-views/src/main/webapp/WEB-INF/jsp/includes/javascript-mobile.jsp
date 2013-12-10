<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!--[if lt IE 9]>
     <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script  src="<%=request.getContextPath()%>/resources/js/commons-mobile.js"></script>
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-config.jsp"%>

<c:if test="${!development}">
    <script src="<%=request.getContextPath()%>/resources/mobile-js/dojo/dojo.js"></script>
    <script  src="<%=request.getContextPath()%>/resources/mobile-js/mobile/mobile-run.js"></script>
</c:if>

<c:if test="${development}">
    <script src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
    <script  src="<%=request.getContextPath()%>/resources/js/mobile-run.js"></script>
</c:if>

<script>
require([
    "dojo",
    "dojo/_base/declare",
    "dojo/parser",
    "dojo/ready",
    'me/support/Websocket',
    'me/support/Offline',
    "me/core/enme",
], function(dojo, declare, parser, ready,  Websocket, Offline, _ENME) {
    ready(function(){
        <%@ include file="/WEB-INF/jsp/includes/decorators/enme-init.jsp"%>
        parser.parse();
        <%@ include file="/WEB-INF/jsp/includes/decorators/shared-javascript.jsp"%>
    });
});
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-notify.jsp"%>
</script>

<%@ include file="/WEB-INF/jsp/includes/decorators/log-js.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/analytics.jsp"%>

