<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!-- dojo css to override easily -->
<link rel="stylesheet"  href="<c:url value="/resources/js/dijit/themes/claro/claro.css"/>" />
<c:if test="${development}">
    <link rel="stylesheet"  href="<c:url value="/resources/css-compile/development.css" />" />
</c:if>
<link rel="stylesheet"  href="<c:url value="/resources/css/resources.css" />" />
<c:if test="${!development}">
	<link rel="stylesheet"  href="<c:url value="/resources/static/web.css" />" />
</c:if>
<link rel="stylesheet"  href="<c:url value="/resources/js/dojox/form/resources/Rating.css"/>" />
<link rel="stylesheet"  href="<c:url value="/resources/js/dojo/resources/dojo.css" />" />
<link rel="stylesheet"  href="<c:url value="/resources/js/dojox/widget/Toaster/Toaster.css" />" />
