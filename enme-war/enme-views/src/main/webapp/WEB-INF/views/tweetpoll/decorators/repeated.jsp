<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="web-tweetpoll-vote-options">
    <div class="web-permatlink">
        <h2>
            ${tp_switch.tweetPoll.question.question}
        </h2>
        <h4>
            <spring:message code="tweetpoll.votes.selection" />:
            <b>
                ${tp_switch.answers.answer}
            </b>
        </h4>
        <h3>
            <spring:message code="tweetpoll.votes.repeated" />
        </h3>
        <div class="web-tweetpoll-info">
            <a href="<%=request.getContextPath()%>/tweetpoll/${tp_switch.tweetPoll.tweetPollId}/${tp_switch.tweetPoll.question.slugQuestion}">
               <button class="btn btn-warning">
                    <spring:message code="tweetpoll.votes.link" />
               </button>
            </a>
        </div>
    </div>
</section>