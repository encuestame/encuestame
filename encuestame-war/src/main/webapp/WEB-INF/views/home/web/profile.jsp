<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-profile-wrapper web-wrapper-detail">
		<div class="web-detail-header">
			<div class="title">
				<h2>${profile.username} (${profile.name}) </h2>
			</div>
        </div>
		<div class="profile-content-wrapper">
			<div class="profile-left">
		        <div class="picture">
		        	<!-- Replace by dojo widget. -->
		             <a href="<%=request.getContextPath()%>/picture/profile/${profile.username}/preview">
		                 <img alt="${profile.username}" src="<%=request.getContextPath()%>/picture/profile/${profile.username}/profile">
		             </a>
		        </div>
		        <ul class="rss">
	                <li>
	                    <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png"> <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">TweetPoll</a>
	                </li>
	                 <li>
	                    <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png"> <a href="<%=request.getContextPath()%>/feed/${profile.username}/poll.rss">Poll</a>
	                </li>
	                <li>
	                    <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png"> <a href="<%=request.getContextPath()%>/feed/${profile.username}/project.rss">Projects</a>
	                </li>		                		                		                		                		                		                		               
		        </ul>
	        </div>
	        <div class="profile-right">
		         <article class="emne-box">
		                <header>
		                  	<span>
		                  	   <spring:message code="home.profile.lastpub" />
		                  	</span>
		                  	<div class="rss">
		                  		<a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">
		                  			<img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
		                  	    </a>
		                  	</div>
		                </header>
		         <div class="web-pup-wrapper emne-box-gray">
		         <c:forEach items="${lastItems}" var="item">
		             <%@ include file="detail_item.jsp"%>
		        </c:forEach>
		        <c:if test="${empty lastItems}">
		        	 <h3 class="no-results">
		        		<spring:message code="results.noresults" />
		        	</h3>
		        </c:if>
		        </div>
		        </article>
	        </div>
        </div>
</div>