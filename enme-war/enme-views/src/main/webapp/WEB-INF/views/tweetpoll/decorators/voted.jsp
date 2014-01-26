<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <section class="web-tweetpoll-vote-options">

        <div class="web-permatlink green">
            <h2>
                <spring:message code="tweetpoll.votes.acepted" />
            </h2>
            <h3>
               ${tp_switch.tweetPoll.question.question}
            </h3>
            <h4>
                <spring:message code="tweetpoll.votes.selection" />: <b>${tp_switch.answers.answer}</b>
            </h4>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/tweetpoll/${tp_switch.tweetPoll.tweetPollId}/${tp_switch.tweetPoll.question.slugQuestion}">
                    <button class="btn btn-success">
                        <spring:message code="tweetpoll.votes.link" />
                    </button>
                </a>
            </div>
            <!-- <div data-dojo-type="me/web/widget/geo/BasicGeoWidget" tpid="${tp_switch.tweetPoll.tweetPollId}"> </div> -->
        </div>

    </section>
</div>