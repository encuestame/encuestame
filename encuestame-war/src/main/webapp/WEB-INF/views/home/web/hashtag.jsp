<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="publicMarginWrapper hashTagDetail">
   <div class="web-hashtagName"> <span> ${tagName.id}</span> | ${tagName.hashTagName} </div>
   <div class="web-tags-tweetpolls">
         <c:forEach items="${tweetPolls}" var="tweets">
            <div class="web-tweetpolls"> <a href="<%=request.getContextPath()%>/tweetpoll/${tweets.id}/test">  ${tweets.questionBean.questionName} </a> |
            <span class="web-tweetpolls-time">${tweets.relativeTime}</span>
            </div>
        </c:forEach>
   </div>
</div>

