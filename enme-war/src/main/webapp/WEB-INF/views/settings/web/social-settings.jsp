<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<c:if test="${not empty message}">
  <div class="${message.infoType.css} mainError">${message.message}</div>
</c:if>
<h1>
  <spring:message code="settings.social.title" />
</h1>
<div data-dojo-type="me/web/widget/social/SocialAccounts"></div>

