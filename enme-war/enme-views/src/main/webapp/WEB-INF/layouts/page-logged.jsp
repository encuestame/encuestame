<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<c:if test="${detectedDevice}">
    <%@ include file="mobile/page-logged.jsp"%>
</c:if>

<c:if test="${!detectedDevice}">
    <%@ include file="page.jsp"%>
</c:if>