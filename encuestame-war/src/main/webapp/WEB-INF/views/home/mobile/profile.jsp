<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="mobile-profile-wrapper">
<div class="profile-name defaultSectionTitle">${profile.username} (${profile.name})</div>
    <div class="profileWrapper">
        <div class="picture">
             <a href="<%=request.getContextPath()%>/picture/profile/${profile.username}/preview">
                 <img alt="${profile.username}" src="<%=request.getContextPath()%>/picture/profile/${profile.username}/default">
             </a>
            <ul class="rss">
                <li>
                    <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png"> <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">TweetPoll RSS</a>
                </li>
                <li>
                    <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png"> <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.atom">TweetPoll ATOM</a>
                </li>
            </ul>
    </div>
</div>
