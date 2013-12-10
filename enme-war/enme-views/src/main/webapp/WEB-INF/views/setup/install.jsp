<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/install.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/install.jsp"%>
  </c:if>