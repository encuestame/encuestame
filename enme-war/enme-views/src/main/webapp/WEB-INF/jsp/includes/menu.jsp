<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
  <c:if test="${detectedDevice}">
     <%@ include file="mobile/menu.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/menu.jsp"%>
  </c:if>