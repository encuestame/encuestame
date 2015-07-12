<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/css-common.jsp"%>
<c:if test="${development}">
	<link rel="stylesheet"  href="<c:url value="/resources/css/commons/resources.css" />" />
</c:if>
<link rel="stylesheet"  href="<c:url value="/resources/css/commons/font-awesome.min.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/commons/dbootstrap.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/commons/dijit.css" />" />
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<c:if test="${development}">
    <link type="text/css" rel="<c:url value="stylesheet${development ? '/less' : ''}" />"  href="<c:url value="/resources/css/${development ? 'less' : 'pages'}/${detectedDevice ? 'mobile_' : ''}${cssFile}.${development ? 'less' : 'min.css'}" />" />
</c:if>
<c:if test="${development}">
    <script src="<%=request.getContextPath()%>/resources/js/debug/less.min.js"></script>
</c:if>
<!--MAPS -->
<!--MAPS
<script src="<%=request.getContextPath()%>/resources/js/maps.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/dev/map.css" />" />
 -->
<!--MAPS -->
<!--MAPS -->