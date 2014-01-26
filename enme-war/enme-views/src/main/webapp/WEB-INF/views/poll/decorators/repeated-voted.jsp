<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <section class="web-tweetpoll-vote-options">        
        <div class="web-permatlink yellow-bk">
            <h2>
                <spring:message code="poll.votes.repeated" />
            </h2>
            <h3>
               ${pollAnswer.question.question}
            </h3>            
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/poll/${pollAnswer.pollId}/${pollAnswer.question.slugQuestion}">
                    <button class="btn btn-warning">
                        <spring:message code="poll.votes.link" />
                    </button>
                </a>
            </div>
            <!-- <div data-dojo-type="me/web/widget/geo/BasicGeoWidget" tpid="${tp_switch.tweetPoll.tweetPollId}"> </div> -->
        </div>

    </section>
</div>