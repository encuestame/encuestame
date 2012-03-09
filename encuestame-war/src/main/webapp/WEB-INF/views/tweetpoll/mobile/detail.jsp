<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="mobile-detail">
    <div class="mobile-question-detail">
        ${tweetpoll.questionBean.questionName}</div>
    <div>
        <div id="chart"
            dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail"
            tweetPollid="${tweetpoll.id}" completed="${tweetpoll.completed}"
            username="${tweetpoll.ownerUsername}"></div>
    </div>
    <div class="mobile-tweetpoll-detail-hashtag">
        <table>
            <tbody>
                <c:forEach items="${answers}" var="a">
                    <tr class="answer"
                        dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer"
                        aId="${a.answers.questionAnswerId}" color="${a.answers.color}"
                        label="${a.answers.answer}" owner="${tweetpoll.ownerUsername}"
                        completed="${tweetpoll.completed}" url="${a.shortUrl}">
                        <a href="<c:url value="${a.shortUrl}"/>">${a.answers.answer}</a>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
