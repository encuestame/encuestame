<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/css-common.jsp"%>
<link rel="stylesheet"  href="<c:url value="/resources/css/commons/font-awesome.min.css" />" />
<c:if test="${development}">
    <link type="text/css" rel="<c:url value="stylesheet${development ? '/less' : ''}" />"  href="<c:url value="/resources/css/${development ? 'less' : 'pages'}/${detectedDevice ? 'mobile_' : ''}${cssFile}.${development ? 'less' : 'min.css'}" />" />
</c:if>
<c:if test="${development}">
    <script src="<%=request.getContextPath()%>/resources/js/debug/less.min.js"></script>
</c:if>
