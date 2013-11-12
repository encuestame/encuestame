<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/upgrade.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/upgrade.jsp"%>
  </c:if>