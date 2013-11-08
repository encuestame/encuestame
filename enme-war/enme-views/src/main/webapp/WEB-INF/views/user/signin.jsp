<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/signin.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/signin.jsp"%>
  </c:if>