<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
<body>
    <h2>VOTE WITH CAPTCHA 1</h2>
        <form:form modelAttribute="captchaForm">
              <div class="recaptcha">
                <c:out value="${captchaForm.captcha}" escapeXml="false" />
                <br /><form:errors path="captcha" cssClass="error" />
             </div>
             <input type="hidden" id="vote_code" name="vote_code" value="${captchaForm.codeVote}">
             <input type="submit" value="Vote">
        </form:form>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>