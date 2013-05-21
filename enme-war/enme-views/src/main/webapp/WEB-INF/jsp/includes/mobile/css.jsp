<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/css-common.jsp"%>
<tiles:insertAttribute name="css_module" ignore="false" />
<c:if test="${development}">
<link rel="stylesheet"  href="<c:url value="/resources/dev/mobile//bootstrap/bootstrap.css" />" />
<link rel="stylesheet"  href="<c:url value="/resources/dev/mobile/menu.css" />" />


</c:if>





