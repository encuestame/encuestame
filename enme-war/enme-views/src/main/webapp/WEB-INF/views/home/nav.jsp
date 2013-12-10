<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/nav.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/nav.jsp"%>
  </c:if>