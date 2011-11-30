<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
 <c:if test="${development}">
    <link rel="stylesheet"  href="<c:url value="/resources/css/common.css" />" />
    <link rel="stylesheet"  href="<c:url value="/resources/css/web/layout.css" />" />
    <link rel="stylesheet"  href="<c:url value="/resources/css/reset.css" />" />
    <link rel="stylesheet"  href="<c:url value="/resources/css/web/default.css" />" />
    <link rel="stylesheet"  href="<c:url value="/resources/css/web/user.css" />" />
    <link rel="stylesheet"  href="<c:url value="/resources/css/web/public.css" />" />
    <link rel="stylesheet"  href="<c:url value="/resources/css/web/menu.css" />" />
    <link rel="stylesheet"  href="<c:url value="/resources/css/web/admon.css" />" />
</c:if>
 <c:if test="${!development}">
    <link rel="stylesheet"  href="<c:url value="/resources/static/web.css" />" />
</c:if>
<link rel="stylesheet"  href="<c:url value="/resources/js/dijit/themes/claro/claro.css"/>" />
<link rel="stylesheet"  href="<c:url value="/resources/js/dojox/form/resources/Rating.css"/>" />
<link rel="stylesheet"  href="<c:url value="/resources/js/dojo/resources/dojo.css" />" />
<link rel="stylesheet"  href="<c:url value="/resources/js/dojox/widget/Toaster/Toaster.css" />" />