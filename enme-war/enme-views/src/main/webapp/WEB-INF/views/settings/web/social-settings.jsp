<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<h1>
  <spring:message code="settings.social.title" />
</h1>
<c:if test="${not empty message}">
  <div class="">
  	<p class="box ${message.infoType.css}">
  		${message.message}
  	</p>
  </div>
</c:if>
<div data-dojo-type="me/web/widget/social/SocialAccounts"></div>

