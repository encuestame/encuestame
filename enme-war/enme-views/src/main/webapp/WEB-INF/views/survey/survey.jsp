<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/survey.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/survey.jsp"%>
  </c:if>