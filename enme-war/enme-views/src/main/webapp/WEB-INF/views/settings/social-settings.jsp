<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/social-settings.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/social-settings.jsp"%>
  </c:if>