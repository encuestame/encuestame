<script type="text/javascript">
    dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollList");
</script>
<div id="web-tweetpoll-wrapper">
    <div class="web-tweetpoll-menu">
        <a href="<%=request.getContextPath()%>/user/tweetpoll/new">New
            TweetPoll</a>
    </div>
    <div dojoType="encuestame.org.core.commons.tweetPoll.TweetPollList"
        contextPath="<%=request.getContextPath()%>"></div>
</div>

