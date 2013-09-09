<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!--[if lt IE 9]>
     <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-config.jsp"%>
<script  src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/mobile-run.js"></script>
<script>
require([
    "dojo",
    "dojo/_base/declare",
    "dojo/parser",
    "dojo/ready",
    "me/core/enme",
], function(dojo, declare, parser, ready, _ENME) {
    ready(function(){
        // Call the parser manually so it runs after our widget is defined, and page has finished loading
        <%@ include file="/WEB-INF/jsp/includes/decorators/enme-init.jsp"%>
        //parse all widgets.
        parser.parse();
    });
});
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-notify.jsp"%>
</script>

<%@ include file="/WEB-INF/jsp/includes/decorators/log-js.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/analytics.jsp"%>

