<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/detail.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/detail.jsp"%>
  </c:if>