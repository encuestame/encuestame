<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <div class="web-vote-wrapper">

        <div class="web-poll-vote">
            <h3>
                ${poll.questionBean.questionName}
            </h3>
            <div class="alert alert-info">
                <spring:message code="poll.completed" />
            </div>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/poll/${poll.id}/${poll.questionBean.slugName}">
                    <button class="btn btn-success">
                        <spring:message code="poll.votes.link" />
                    </button>
                </a>
            </div>
        </div>

    </div>
</div>
