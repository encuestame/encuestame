<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

  <c:if test="${detectedDevice}">
     <%@ include file="mobile/list-tweetpoll.jsp"%>
  </c:if>

  <c:if test="${!detectedDevice}">
     <%@ include file="web/list-tweetpoll.jsp"%>
  </c:if>