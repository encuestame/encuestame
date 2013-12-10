<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/members.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/members.jsp"%>
  </c:if>