<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<c:if test="${development}">
	<link type="text/css" rel="<c:url value="stylesheet${development ? '/less' : ''}" />"  href="<c:url value="/resources/css/${development ? 'less' : 'pages'}/${detectedDevice ? 'mobile_' : ''}${cssFile}.${development ? 'less' : 'min.css'}" />" />
</c:if>