<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<c:if test="${not empty message}">
  <div class="bot-m">
  	<p class="alert ${message.infoType.css}">
  		${message.message}
  	</p>
  </div>
</c:if>


<social-settings>
    <social
</social-settings>
