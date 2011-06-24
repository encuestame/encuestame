<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="mobile-detail">
    <div class="mobile-question-detail">
        ${tweetpoll.questionBean.questionName}
    </div>
    <c:forEach items="${answers}" var="a">
        <div class="mobile-answers-questions">
            ${a.answers.answer}
        </div>
    </c:forEach>
</div>
