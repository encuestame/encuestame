<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="publicMarginWrapper hashTagDetail">
   <div class="mobile-hashtagName"> <span> ${tagName.id}</span> | ${tagName.hashTagName} </div>
   <div class="mobile-tags-tweetpolls">
         <c:forEach items="${tweetPolls}" var="tweets">
            <div class="mobile-tweetpolls"> <a href="<%=request.getContextPath()%>/tweetpoll/${tweets.id}/test">  ${tweets.questionBean.questionName} </a> |
            <span class="mobile-tweetpolls-time">${tweets.relativeTime}</span>
            </div>
        </c:forEach>
   </div>
</div>
