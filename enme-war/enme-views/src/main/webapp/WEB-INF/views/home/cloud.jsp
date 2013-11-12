<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/cloud.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/cloud.jsp"%>
  </c:if>