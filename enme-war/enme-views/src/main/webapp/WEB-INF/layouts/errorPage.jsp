<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/errorPage.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/errorPage.jsp"%>
  </c:if>