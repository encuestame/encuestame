<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-vote-wrapper">
    <h3>
        ${poll.questionBean.questionName}
    </h3>
    <p>
    <form action="<%=request.getContextPath()%>/poll/vote/password/post" method="post" class="form-horizontal">
        <div class="web-poll-vote">
            <c:if test="${not empty votePollError}">
                <div class="alert alert-error">
                    ${votePollError}
                </div>
            </c:if>
           <div class="control-group">
                <label class="control-label" for="poll_password">
                    Type the password provided
                </label>
                <div class="controls">
                   <input type="password" name="poll_password" class="input-small" placeholder="Password">
               </div>
            </div>
            <input name="poll" type="hidden" value="${responseId}">
            <input name="itemId" type="hidden" value="${poll.id}">
            <input name="itemId" type="hidden" value="${poll.id}">
            <input name="type" type="hidden" value="${poll.questionBean.pattern}">
            <input name="slugName" type="hidden" value="${poll.questionBean.slugName}">
            <input name="type_form" type="hidden" value="${type_form}">
            <input name="type" type="hidden" value="${type}">
            <input name="multiplesVotes" type="hidden" value="${multiplesVotes}">
        </div>
        <p>
            <button class="btn  btn-large btn-block btn-success" type="submit">
                Vote
            </button>
        </p>
    </form>
    </p>
</div>