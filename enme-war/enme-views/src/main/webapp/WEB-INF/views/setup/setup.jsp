<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/setup.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/setup.jsp"%>
  </c:if>