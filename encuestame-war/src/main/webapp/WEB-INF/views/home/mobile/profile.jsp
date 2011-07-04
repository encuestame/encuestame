<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="profile">
<h2>${profile.username}</h2>
    <div class="profileWrapper">
        <div class="picture">
            <a href="<%=request.getContextPath()%>/picture/profile/${profile.username}/preview">
                <img alt="${profile.username}" src="<%=request.getContextPath()%>/picture/profile/${profile.username}/default">
            </a>
        </div>
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
<div>Cloud Page</div>
 <c:forEach items="${hashtags}" var="cloud">
     <span class="item" style="font-size: ${cloud.size}px;">
         <a href="<%=request.getContextPath()%>/tag/${cloud.hashTagName}/">${cloud.hashTagName}</a>
     </span>
   </c:forEach>

