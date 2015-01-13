<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-vote-wrapper">
    <header>
        <%@ include file="/WEB-INF/layouts/logo.jsp"%>
    </header>
    <h3>
        ${poll.questionBean.questionName}
    </h3>
    <p>
    <form action="<%=request.getContextPath()%>/poll/vote/password/post" method="post">
        <div class="web-poll-vote">
            ${votePollError}
            <c:if test="${votePollError}">
                <div class="alert alert-error">
                    <spring:message code="poll.error"/>
                </div>
            </c:if>
            <p>
                This poll is protected
            </p>
            <label>Enter the password</label>
            <input name="itemId" type="hidden" value="${poll.id}">
            <input name="poll" type="hidden" value="${responseId}">
            <input type="password" name="poll_password">
            <input name="itemId" type="hidden" value="${poll.id}">
            <input name="type" type="hidden" value="${poll.questionBean.pattern}">
            <input name="slugName" type="hidden" value="${poll.questionBean.slugName}">
            <input name="type_form" type="hidden" value="${type_form}">
            <input name="type" type="hidden" value="${type}">
            <input name="multiplesVotes" type="hidden" value="${multiplesVotes}">
        </div>
        <p>
            <button class="btn  btn-large btn-block btn-success" type="submit">Valid and Vote</button>
        </p>
    </form>
    </p>
</div>