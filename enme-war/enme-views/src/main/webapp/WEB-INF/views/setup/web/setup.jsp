<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/setup/web/setup_init.jsp"%>
<form:form method="post">
    <c:if test="${status != null}">
        <c:if test="${status == 'install'}">
            <div class="setup-description">
                <%@ include file="/WEB-INF/jsp/includes/setup/setup_step1.jsp"%>
            </div>
            <div class="form-actions default-rigth-aling">
                <button type="submit" name="_eventId_check-submit"
                        value="Install" class="btn btn-warning" onclick="hideButtonsDisplayLoading(this);">
                    <spring:message code="setup.step1.button"></spring:message>
                </button>
                <div class="hidden" id="loading">
                    <img src="<%=request.getContextPath()%>/resources/images/loaders/setup.gif"/>
                </div>
            </div>
        </c:if>
        <c:if test="${state == 'upgrade'}">
            <div class="setup-description">
                <p>
                    RELEASE NOTES for:
                    <%=EnMePlaceHolderConfigurer
                            .getProperty("app.version")%></p>
                <div class="release-notes"></div>
            </div>
            <div class="default-rigth-aling">
                <button type="submit" name="_eventId_upgrade-submit" class="btn btn-warning" >
                    <spring:message code="setup.step1.update.button"></spring:message>
                </button>
            </div>
        </c:if>
    </c:if>
    <c:if test="${status == null}">
        <div class="alert alert-error">
            <spring:message code="setup.upgrade.fail"></spring:message>
        </div>
    </c:if>
</form:form>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_finish.jsp"%>