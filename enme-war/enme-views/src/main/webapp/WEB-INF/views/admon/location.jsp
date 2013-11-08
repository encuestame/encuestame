<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/location.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/location.jsp"%>
  </c:if>