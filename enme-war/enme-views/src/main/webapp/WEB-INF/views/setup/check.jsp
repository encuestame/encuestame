<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/check.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/check.jsp"%>
  </c:if>