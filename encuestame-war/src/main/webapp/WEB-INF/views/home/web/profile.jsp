<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="profile">
<h2>${profile.username}</h2>


<div>
    <ul>
    <li>
        rss : <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">TweetPoll RSS for ${profile.username}</a>
   </li>
   <li>
        atom : <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.atom">TweetPoll ATOM for ${profile.username}</a>
    </li>
    </ul>

</div>

</div>