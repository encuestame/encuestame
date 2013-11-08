<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/profile-settings.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/profile-settings.jsp"%>
  </c:if>