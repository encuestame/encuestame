<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/forgot.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/forgot.jsp"%>
  </c:if>