<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-tweetpoll-wrapper" class="emne-box web-wrapper-detail">
    <div class="web-tweetpoll-menu">
        <a href="<%=request.getContextPath()%>/user/tweetpoll/new">
            <button class="button">
                <spring:message code="tweetpoll.new" />
            </button>
        </a>
    </div>
    <div data-dojo-type="me/web/widget/tweetpoll/TweetPollList"></div>
</div>

