<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <section class="web-tweetpoll-vote-options">        
        <div class="web-permatlink yellow-bk">
            <div>
                <spring:message code="poll.votes.repeated" />
            </div>
            <div>
               ${pollAnswer.question.question}
            </div>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/poll/${pollAnswer.pollId}/${pollAnswer.question.slugQuestion}">
                    <button class="btn btn-warning">
                        <spring:message code="poll.votes.link" />
                    </button>
                </a>
            </div>
        </div>

    </section>
</div>