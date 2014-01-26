<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/css-common.jsp"%>
<c:if test="${development}">
	<link rel="stylesheet"  href="<c:url value="/resources/dev/resources.css" />" />
</c:if>
<link rel="stylesheet"  href="<c:url value="/resources/css/font-awesome.min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dbootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dijit.css" />" />
<tiles:insertAttribute name="css_module" ignore="true" />

<!--MAPS -->
<!--MAPS
<script src="<%=request.getContextPath()%>/resources/js/maps.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/dev/map.css" />" />
 -->
<!--MAPS -->
<!--MAPS -->