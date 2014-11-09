<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<c:if test="${detectedDevice}">
    <%@ include file="mobile/fail-check.jsp"%>
</c:if>

<c:if test="${!detectedDevice}">
    <%@ include file="web/fail-check.jsp"%>
</c:if>