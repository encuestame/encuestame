<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/edit.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/edit.jsp"%>
  </c:if>