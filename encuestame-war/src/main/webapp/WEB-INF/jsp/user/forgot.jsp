<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
<body>
    <div id="mainUserWrapper">
        <div class="form">
            <form:form modelAttribute="unitForgotPassword">
                    <div class="data">
                        <div class="form-pair">
                            <div class="form-item">
                                <label for="email">Email</label>
                            </div>
                            <div class="form-value">
                                <form:input path="email" size="30" maxlength="25" /><br /><form:errors path="email" cssClass="errors" />
                             </div>
                        </div>
                    </div>
                    <div class="recaptcha">
                        <c:out value="${unitForgotPassword.captcha}" escapeXml="false" />
                        <br /><form:errors path="captcha" cssClass="error" />
                    </div>
                    <div class="form-submit-buttons">
                        <input type="submit" class="input-submit" name="submit" value="Submit" />
                    </div>
            </form:form>
             <a href="<%=request.getContextPath()%>/user/signin">Sign In</a>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>