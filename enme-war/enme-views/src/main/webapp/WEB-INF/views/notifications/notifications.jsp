<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/notifications.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/notifications.jsp"%>
  </c:if>