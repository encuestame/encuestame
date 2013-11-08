<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/new.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/new.jsp"%>
  </c:if>