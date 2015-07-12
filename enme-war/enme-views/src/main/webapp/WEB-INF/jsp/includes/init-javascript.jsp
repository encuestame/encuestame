<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!-- Temporal service to store all service. -->
<!--[if lt IE 9]>
     <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="<%=request.getContextPath()%>/resources/js/commons/init.<c:url value="${development ? '' : 'min.'}" />js"></script>

<script>
	var config = {
		contextPath: '${pageContext.request.contextPath}'
	};
</script>