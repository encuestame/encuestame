<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/dashboard.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/dashboard.jsp"%>
  </c:if>