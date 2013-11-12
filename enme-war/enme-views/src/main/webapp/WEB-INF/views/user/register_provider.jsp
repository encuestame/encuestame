<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/register_provider.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/register_provider.jsp"%>
  </c:if>