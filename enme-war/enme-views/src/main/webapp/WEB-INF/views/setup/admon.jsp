<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/admon.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/question.jsp"%>
  </c:if>