<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/denied.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/denied.jsp"%>
  </c:if>