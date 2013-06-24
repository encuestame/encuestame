<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<c:if test="${!development}">
	<link rel="stylesheet"  href="<c:url value="/resources/css/notifications.min.web.css" />" />
</c:if>

<c:if test="${development}">
	<link rel="stylesheet"  href="<c:url value="/resources/dev/notifications.css" />" />
</c:if>