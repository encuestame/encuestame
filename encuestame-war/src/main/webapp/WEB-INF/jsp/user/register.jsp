<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
<body>
    <div id="mainUserWrapper">
        <div class="form">
            <form:form modelAttribute="signUpBean">
                    <div class="data">
                        <div class="form-pair">
                            <div class="form-item">
                                <label for="username">Username</label>
                            </div>
                            <div class="form-value">
                                <form:input path="username" size="30" maxlength="16" />
                                <br />
                                <form:errors path="username" cssClass="errors" />
                             </div>
                        </div>
                        <div class="form-pair">
                            <div class="form-item">
                                <label for="email">Email</label>
                            </div>
                            <div class="form-value">
                                <form:input path="email" size="30" maxlength="25" />
                                <br />
                                <form:errors path="email" cssClass="errors" />
                             </div>
                        </div>
                    </div>
                    <div class="recaptcha">
                        <c:out value="${signUpBean.captcha}" escapeXml="false" />
                        <br />
                        <form:errors path="captcha" cssClass="error" />
                    </div>
                    <div class="form-submit-buttons">
                        <input type="submit" class="input-submit" name="submit" value="Submit" />
                    </div>
            </form:form>
            <a href="<%=request.getContextPath()%>/user/forgot">Forgot Password?</a>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>