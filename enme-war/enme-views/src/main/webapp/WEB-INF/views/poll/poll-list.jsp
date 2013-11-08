<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/poll-list.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/poll-list.jsp"%>
  </c:if>