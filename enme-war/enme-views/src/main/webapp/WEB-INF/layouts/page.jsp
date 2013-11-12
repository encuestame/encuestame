<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
  <c:if test="${detectedDevice}">
     <%@ include file="mobile/page.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/page.jsp"%>
  </c:if>