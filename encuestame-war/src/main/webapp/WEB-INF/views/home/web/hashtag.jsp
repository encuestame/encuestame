<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="publicMarginWrapper hashTagDetail">
   <div class="web-hashtagName"> <span> ${tagName.id}</span> | ${tagName.hashTagName} </div>
   <div class="web-tags-tweetpolls">
         <div> Last 10 TweetPolls</div>
         <c:forEach items="${tweetPolls}" var="tweets">
            <div class="web-tweetpolls"> <a href="<%=request.getContextPath()%>/tweetpoll/${tweets.id}/test">  ${tweets.questionBean.questionName} </a> |
            <span class="web-tweetpolls-time">${tweets.relativeTime}</span>
            </div>
        </c:forEach>
        <div> Last 10 rated</div>
        <c:forEach items="${tweetPollrated}" var="rated">
            <div class="web-tweetpolls"> <a href="<%=request.getContextPath()%>/tweetpoll/${rated.id}/test">  ${rated.questionBean.questionName} </a>
            <span class="web-tweetpolls-time">${rated.relativeTime}</span> |  <span class="web-tweetpolls-time">${rated.totalVotes}</span>
            </div>
        </c:forEach>
   </div>
</div>

