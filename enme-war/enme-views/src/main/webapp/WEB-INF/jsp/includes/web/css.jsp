<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/css-common.jsp"%>
<c:if test="${development}">
	<link rel="stylesheet"  href="<c:url value="/resources/dev/resources.css" />" />
</c:if>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/dev/sections/bootstrap/theme.css" />" />
<tiles:insertAttribute name="css_module" ignore="true" />