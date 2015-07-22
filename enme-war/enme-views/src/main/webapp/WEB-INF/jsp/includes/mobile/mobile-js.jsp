<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-config.jsp"%>

<script src="<%=request.getContextPath()%>/resources/js/commons/commons-mobile.<c:url value="${development ? '' : 'min.'}" />js"></script>
<script src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/me/run.js"></script>

<script>
    require([
        "dojo",
        "dojo/_base/declare",
        "dojo/parser",
        "dojo/ready",
        'me/support/Offline',
        "me/core/AuthStatus",
        "me/core/enme"
    ], function(dojo, declare, parser, ready, Offline, AuthStatus, _ENME) {
        ready(function(){
            <%@ include file="/WEB-INF/jsp/includes/decorators/enme-init.jsp"%>
            <c:if test="${logged}">
                var auth = new AuthStatus();
            </c:if>
            parser.parse();
            <%@ include file="/WEB-INF/jsp/includes/decorators/shared-javascript.jsp"%>
        });
    });
    <%@ include file="/WEB-INF/jsp/includes/decorators/dojo-notify.jsp"%>
</script>

<%@ include file="/WEB-INF/jsp/includes/decorators/log-js.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/analytics.jsp"%>

