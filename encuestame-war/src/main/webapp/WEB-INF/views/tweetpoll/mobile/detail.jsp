<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="mobile-detail">
    <div class="mobile-question-detail">
        ${tweetpoll.questionBean.questionName}
    </div>
    <div>
     <div id="chart" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail"
                 tweetPollid="${tweetpoll.id}"
                 completed="${tweetpoll.completed}"
                 username="${tweetpoll.ownerUsername}"></div>
    </div>
    <div class="mobile-tweetpoll-detail-hashtag">
    <c:forEach items="${answers}" var="a">
        <div class="mobile-answers-questions">
            ${a.answers.answer}
        </div>
    </c:forEach>
    </div>
</div>
