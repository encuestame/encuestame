<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/profile.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/profile.jsp"%>
  </c:if>