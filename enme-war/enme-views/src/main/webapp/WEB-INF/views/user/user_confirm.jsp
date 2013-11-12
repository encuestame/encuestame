<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/user_confirm.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/user_confirm.jsp"%>
  </c:if>