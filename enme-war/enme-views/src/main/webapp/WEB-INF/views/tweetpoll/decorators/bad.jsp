<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <div class="web-vote-wrapper">

        <div class="web-poll-vote">
            <h3>
                ${tp_switch.tweetPoll.question.question}
            </h3>
            <div class="answer-selected">
                ${tp_switch.answers.answer}
            </div>
            <div class="alert alert-error">
                <spring:message code="tweetpoll.votes.error" />
            </div>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/tweetpoll/${tp_switch.tweetPoll.tweetPollId}/${tp_switch.tweetPoll.question.slugQuestion}">
                    <button class="btn btn-success">
                        <spring:message code="tweetpoll.votes.link" />
                    </button>
                </a>
            </div>
            <!-- <div data-dojo-type="me/web/widget/geo/BasicGeoWidget" tpid="${tp_switch.tweetPoll.tweetPollId}"> </div> -->
        </div>

    </div>
</div>
