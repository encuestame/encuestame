<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/surveyUser.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/surveyUser.jsp"%>
  </c:if>