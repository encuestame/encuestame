<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="defaultMarginWrapper">
    <section class="web-tweetpoll-vote-options">

        <div class="web-permatlink">
            <h1>
                <spring:message code="tweetpoll.votes.completed" />
            </h1>
            <h2>
               ${tp_switch.tweetPoll.question.question}
            </h2>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/tweetpoll/${tp_switch.tweetPoll.tweetPollId}/${tp_switch.tweetPoll.question.slugQuestion}">
                    <spring:message code="tweetpoll.votes.link" />
                </a>
            </div>
        </div>

    </section>
</article>