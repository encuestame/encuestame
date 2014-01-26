<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <div class="row web-tweetpoll-vote-options">
        <div class="web-permatlink green span10">
            <h2>
                <spring:message code="tweetpoll.votes.completed" />
            </h2>
            <h3>
               ${pollAnswer.question.question}
            </h3>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/poll/${pollAnswer.pollId}/${pollAnswer.question.slugQuestion}">
                    <button class="btn btn-success">
                        <spring:message code="tweetpoll.votes.link" />
                    </button>
                </a>
            </div>
            <!-- <div data-dojo-type="me/web/widget/geo/BasicGeoWidget" tpid="${tp_switch.tweetPoll.tweetPollId}"> </div> -->
        </div>

    </div>
</div>