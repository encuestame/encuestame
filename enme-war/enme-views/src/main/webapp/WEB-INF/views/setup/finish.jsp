<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/finish.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/finish.jsp"%>
  </c:if>