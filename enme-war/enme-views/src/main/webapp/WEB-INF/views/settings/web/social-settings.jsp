<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<c:if test="${not empty message}">
  <div class="bot-m">
  	<p class="alert ${message.infoType.css}">
  		${message.message}
  	</p>
  </div>
</c:if>

<div id="web-tweetpoll-wrapper" class="enme-main-section web-wrapper-detail">
    <div class="admon-table-options panel-header">
       <div class="tb-left">
            <h3>
               <spring:message code="settings.social.title" />
               <!--  <spring:message code="tweetpoll.list.title" /> -->
            </h3>
            <p>
               <!-- <spring:message code="tweetpoll.list.subtitle" /> -->
               Manage your social network
            </p>
       </div>
    </div>
    <div data-dojo-type="me/web/widget/social/SocialAccounts"></div>
</div>