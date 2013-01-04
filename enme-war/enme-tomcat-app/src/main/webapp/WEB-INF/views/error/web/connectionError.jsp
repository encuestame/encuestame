<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<div>conection error
      <c:if test="${not empty message}">
           <div class="${message.infoType.css}">${message.message}</div>
      </c:if>
</div>
