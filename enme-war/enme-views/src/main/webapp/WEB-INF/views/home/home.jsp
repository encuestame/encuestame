<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/home.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/home.jsp"%>
  </c:if>