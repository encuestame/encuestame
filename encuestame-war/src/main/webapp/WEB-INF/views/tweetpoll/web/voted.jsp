<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="defaultMarginWrapper">
    <section class="web-tweetpoll-vote-options">

        <div class="web-permatlink gradient-green">
            <h1>
                ${switch.tweetPoll.question.question}
            </h1>
            <h3>
                <spring:message code="tweetpoll.votes.acepted" />
            </h3>
            <div class="web-tweetpoll-info">

                <a href="<%=request.getContextPath()%>/tweetpoll/${switch.tweetPoll.tweetPollId}/${switch.tweetPoll.question.slugQuestion}">
                    <spring:message code="tweetpoll.votes.link" />
                </a>
            </div>
        </div>

    </section>
</article>