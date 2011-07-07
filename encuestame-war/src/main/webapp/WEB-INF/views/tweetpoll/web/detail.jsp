<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-tweetpoll-detail ">
   <div class="question defaultSectionTitle">
        ${tweetpoll.questionBean.questionName}
   </div>
   <c:forEach items="${answers}" var="a">
     <div class="answerVote">
          ${a}
     </div>
   </c:forEach>
</div>
