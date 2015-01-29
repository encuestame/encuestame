<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div id="web-tweetpoll-wrapper" class="enme-main-section web-wrapper-detail">
    <div class="admon-table-options panel-header">
        <div class="tb-left">
            <h3>
                <spring:message code="notification.all.title" />
            </h3>
        </div>
    </div>
    <div class="web-dasboard-wrapper">
        <div data-dojo-type="me/web/widget/notifications/NotificationList"></div>
    </div>
</div>

