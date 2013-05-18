<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<tiles:insertAttribute name="css_module" ignore="false" />
<c:if test="${development}">
<link rel="stylesheet"  href="<c:url value="/resources/dev/mobile//bootstrap/bootstrap.css" />" />
<link rel="stylesheet"  href="<c:url value="/resources/dev/mobile/menu.css" />" />


</c:if>





