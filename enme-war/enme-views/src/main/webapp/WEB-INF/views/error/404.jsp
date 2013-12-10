<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/404.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/404.jsp"%>
  </c:if>