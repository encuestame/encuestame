<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-profile-wrapper">
		<header>
			${profile.username} (${profile.name})
		</header>
		<div>
			<div class="profile-left">
		        <div class="picture">
		             <a href="<%=request.getContextPath()%>/picture/profile/${profile.username}/preview">
		                 <img alt="${profile.username}" src="<%=request.getContextPath()%>/picture/profile/${profile.username}/profile">
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
	        <div class="profile-right">
		         <article class="emne-box">
		                <header>
		                  	Last Published Publications
		                </header>
		         <div class="web-pup-wrapper">
		         <c:forEach items="${lastItems}" var="item">
		             <%@ include file="detail_item.jsp"%>
		        </c:forEach>
		        </div>
		        </article>
	        </div>
        </div>
</div>