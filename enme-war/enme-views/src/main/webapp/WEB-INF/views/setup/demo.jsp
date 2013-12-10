<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/demo.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/demo.jsp"%>
  </c:if>