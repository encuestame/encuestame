<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/error.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/error.jsp"%>
  </c:if>