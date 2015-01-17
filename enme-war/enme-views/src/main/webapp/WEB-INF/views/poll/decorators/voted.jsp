<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <div class="web-vote-wrapper">

        <div class="web-poll-vote">
            <h3>
                ${pollAnswer.question.question}
            </h3>
            <div class="answer-selected">
                ${pollAnswer.question.question}
            </div>
            <div class="alert alert-success">
                <spring:message code="poll.votes.thanks" />
            </div>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/poll/${pollAnswer.pollId}/${pollAnswer.question.slugQuestion}">
                    <button class="btn btn-success">
                        <spring:message code="poll.votes.link" />
                    </button>
                </a>
            </div>
        </div>

    </div>
</div>
