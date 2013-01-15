<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="defaultMarginWrapper">
    <section class="web-tweetpoll-vote-options">

        <div class="web-permatlink gradient-gray">
            <h1>
                ${switch.tweetPoll.question.question}
            </h1>
            <h3>
                <spring:message code="tweetpoll.votes.captcha" />
            </h3>
            <div class="captcha">
                 <form:form modelAttribute="captchaForm" action="${domain}/tweetpoll/vote/process">
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

    </section>
</article>