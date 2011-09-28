<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_init.jsp"%>
    <div class="setup-description">
        <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h1>
            Step 6: <span>Customize your System</span>
        </h1>
        <div class="setup-review">
            <form:form method="post">
                <div class="setup-social-networks">
                    <div class="warning">space to list social network added.</div>
                    <input type="submit" name="_eventId_customize"
                        value="Add Social Network" />
                </div>
                <div class="setup-finish">
                    <input type="submit" name="_eventId_finish"
                        value="Customize later" />
                </div>
            </form:form>
        </div>

    </div>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_finish.jsp"%>
