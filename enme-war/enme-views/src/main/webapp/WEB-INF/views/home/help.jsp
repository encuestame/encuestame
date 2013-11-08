<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/help.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/help.jsp"%>
  </c:if>