<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <section class="web-tweetpoll-vote-options">

        <div class="web-permatlink yellow">
            <h2>
                <spring:message code="tweetpoll.votes.repeated" />
            </h2>
            <h3>
               ${tp_switch.tweetPoll.question.question}
            </h3>
            <h4>
                <spring:message code="tweetpoll.votes.selection" />: <b>${tp_switch.answers.answer}</b>
            </h4>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/tweetpoll/${tp_switch.tweetPoll.tweetPollId}/${tp_switch.tweetPoll.question.slugQuestion}">
                   <button class="btn btn-warning">
                        <spring:message code="tweetpoll.votes.link" />
                   </button>
                </a>
            </div>
        </div>

    </section>
  </div>