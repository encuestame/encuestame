<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="publicMarginWrapper tweetPollDetail">
   <div class="question">
        ${tweetpoll.questionBean.questionName}
   </div>
   <c:forEach items="${answers}" var="a">
     <div class="answerVote">
          ${a.answers.answer}
     </div>
   </c:forEach>
</div>
