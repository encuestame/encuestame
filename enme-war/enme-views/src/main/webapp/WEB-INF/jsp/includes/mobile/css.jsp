<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<link rel="stylesheet"  href="<c:url value="/resources/js/me/resources/core.css"/>" />
<tiles:insertAttribute name="css_module" ignore="false" />
<c:if test="${development}">
	<link rel="stylesheet"  href="<c:url value="/resources/dev/resources.css" />" />
</c:if>
<!-- <link rel="stylesheet"  href="<c:url value="/resources/css/me.web.css" />" /> -->
<link rel="stylesheet"  href="<c:url value="/resources/js/me/resources/app.css"/>" />




