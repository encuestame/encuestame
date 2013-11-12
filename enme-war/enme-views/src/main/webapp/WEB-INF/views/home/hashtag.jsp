<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/hashtag.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/hashtag.jsp"%>
  </c:if>