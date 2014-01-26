<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">
    <section class="web-tweetpoll-vote-options">
        <div class="web-permatlink gradient-gray">
            <h2>
                ${tp_switch.tweetPoll.question.question}
            </h2>
            <h3>
                <spring:message code="tweetpoll.votes.captcha" />
            </h3>
            <div class="captcha">
                 <form:form modelAttribute="captchaForm" action="${domain}/tweetpoll/vote/process">
                        <div class="recaptcha">
                            <form:errors path="captcha" cssClass="alert alert-error" />
                            <br />
                            <br />
                            <c:out value="${captchaForm.captcha}" escapeXml="false" />


                        </div>
                        <input type="hidden" id="vote_code" name="vote_code"
                            value="${captchaForm.codeVote}" class="btn-success">
                        <input type="submit" value="Resolve Captcha" class="btn btn-success">
                    </form:form>
            </div>
        </div>

    </section>
</div>