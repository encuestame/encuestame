<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<enme:require>
encuestame.org.core.commons.tweetPoll.TweetPollList
</enme:require>
<div id="web-tweetpoll-wrapper">
    <div class="web-tweetpoll-menu default-background-dark-color">
        <a href="<%=request.getContextPath()%>/user/tweetpoll/new">New TweetPoll</a>
    </div>
    <div dojoType="encuestame.org.core.commons.tweetPoll.TweetPollList"
        contextPath="<%=request.getContextPath()%>"></div>
</div>

