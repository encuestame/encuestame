<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-tweetpoll-wrapper" class="emne-box web-wrapper-detail">
    <div class="web-tweetpoll-menu">
        <a href="<%=request.getContextPath()%>/user/tweetpoll/new">
            <button class="enme-ui-button">
                <spring:message code="tweetpoll.new" />
            </button>
        </a>
    </div>
    <div dojoType="encuestame.org.core.commons.tweetPoll.TweetPollList"
         contextPath="<%=request.getContextPath()%>"></div>
</div>

