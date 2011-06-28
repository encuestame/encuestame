<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="publicMarginWrapper hashTagDetail">
   <div class="question"> ${tagName.hashTagName} </div>
   <div> ${tagName.id}</div>
   <div class="mobile-tweetpolls-tags">
         <c:forEach items="${tweetPolls}" var="tweets">
            <div class="mobile-optionTag"> <a href="<%=request.getContextPath()%>/tweetpoll/${tweets.id}/test">  ${tweets.questionBean.questionName} </a> |
            <span class="mobile-time">${tweets.relativeTime}</span>
            </div>
        </c:forEach>
   </div>
</div>
