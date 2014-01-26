
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
  <c:if test="${detectedDevice}">
     <%@ include file="mobile/search.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/search.jsp"%>
  </c:if>