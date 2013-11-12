<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/review.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/review.jsp"%>
  </c:if>