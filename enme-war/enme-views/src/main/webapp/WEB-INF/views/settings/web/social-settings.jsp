<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div id="web-tweetpoll-wrapper" class="enme-main-section web-wrapper-detail">
    <div class="admon-table-options panel-header">
       <div class="tb-left">
            <h3>
               <spring:message code="settings.social.title" />
            </h3>
            <p>
               <!-- <spring:message code="tweetpoll.list.subtitle" /> -->
               Manage your socials accounts
            </p>
       </div>
    </div>
    <c:if test="${not empty message}">
        <p class="alert ${message.infoType.css}" style="margin: 0;">
            ${message.message}
        </p>
    </c:if>

    <div data-dojo-type="me/web/widget/social/SocialAccounts"></div>
</div>