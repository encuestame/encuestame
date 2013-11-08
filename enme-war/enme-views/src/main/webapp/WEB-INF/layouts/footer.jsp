<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/footer.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/footer.jsp"%>
  </c:if>