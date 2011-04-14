<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div class="defaultMarginWrapper votes tweetPollVote" id="defaultMarginWrapper">
    <div class="question">
        ${switch.tweetPoll.question.question}
    </div>
    <div class="answer">
        ${switch.answers.answer}
    </div>
    <div align="center">
        <form:form modelAttribute="captchaForm">
            <div class="recaptcha">
                <c:out value="${captchaForm.captcha}" escapeXml="false" />
                <br />
                <form:errors path="captcha" cssClass="error" />
            </div>
            <input type="hidden" id="vote_code" name="vote_code"
                value="${captchaForm.codeVote}">
            <input type="submit" value="Resolve Captcha" class="defaultButton">
        </form:form>
    </div>
</div>
