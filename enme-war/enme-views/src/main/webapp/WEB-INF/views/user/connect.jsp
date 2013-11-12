<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/connect.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/connect.jsp"%>
  </c:if>