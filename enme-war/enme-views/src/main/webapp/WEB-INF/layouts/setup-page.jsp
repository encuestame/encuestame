<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/setup-page.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/setup-page.jsp"%>
  </c:if>