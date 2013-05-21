<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/css-common.jsp"%>
<!-- <link rel="stylesheet"  href="<c:url value="/resources/js/me/resources/core.css"/>" /> -->
<!-- <link rel="stylesheet"  href="<c:url value="/resources/js/me/resources/app.css"/>" /> -->
<!-- <link rel="stylesheet"  href="<c:url value="/resources/dev/mobile//bootstrap/bootstrap.css" />" />
<link rel="stylesheet"  href="<c:url value="/resources/dev/mobile//bootstrap/bootstrap-responsive.css" />" /> -->
<tiles:insertAttribute name="css_module" ignore="true" />
<c:if test="${development}">
	<link rel="stylesheet"  href="<c:url value="/resources/dev/resources.css" />" />
</c:if>
<!-- <link rel="stylesheet"  href="<c:url value="/resources/css/me.web.css" />" /> -->


<style>

body{

}

</style>
