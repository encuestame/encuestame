<div id="web-tweetpoll-wrapper" class="emne-box">
    <div class="web-tweetpoll-menu default-background-dark-color">
        <a href="<%=request.getContextPath()%>/user/tweetpoll/new">
            <button>
                New TweetPoll
            </button>
        </a>
    </div>
    <div dojoType="encuestame.org.core.commons.tweetPoll.TweetPollList"
        contextPath="<%=request.getContextPath()%>"></div>
</div>

