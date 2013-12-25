<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-config.jsp"%>

<script src="<%=request.getContextPath()%>/resources/js/commons.js"></script>
<script  src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/run.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/enme.chart.js"></script>

<script>
require([
    "dojo",
    "dojo/_base/declare",
    "dojo/parser",
    "dojo/ready",
    'me/support/Websocket',
    'me/support/Offline',
    "me/web/widget/signup/LoginDialog",
    "me/core/enme"
], function(dojo, declare, parser, ready, Websocket, Offline, LoginDialog, _ENME) {
    ready(function(){
        <%@ include file="/WEB-INF/jsp/includes/decorators/enme-init.jsp"%>
        dojo.subscribe('/encuestame/login/show', this, dojo.hitch(this, function(expired_session) {
            var login = new LoginDialog({
                expired_session: expired_session,
                action_url : '<%=request.getContextPath()%>/user/signin/authenticate'
            });
        }));
        parser.parse();
        <%@ include file="/WEB-INF/jsp/includes/decorators/shared-javascript.jsp"%>
    });
});
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-notify.jsp"%>
</script>
<%@ include file="/WEB-INF/jsp/includes/decorators/log-js.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/analytics.jsp"%>
