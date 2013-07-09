<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
        <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h3
            <spring:message code="setup.step7"></spring:message>
            <spring:message code="setup.stepDes7"></spring:message>
        </h3>
        <hr/>
        <div class="setup-finished">
            <p>
                <spring:message code="setup.step7.des1"></spring:message>
            </p>
            <p>
                <spring:message code="setup.step7.des2"></spring:message>
            </p>
            <div class="control-group">
                <div class="progress  progress-warning progress-striped">
                   <div class="bar" style="width: 100%;"></div>
                </div>
            </div>
             <div class="form-actions">
                <form:form method="post">
                    <div class="default-rigth-aling">
                        <button type="submit" name="_eventId_home"
                            class="btn btn-warning" onclick="hideButtonsDisplayLoading(this);">
                            <spring:message code="setup.step7.button"></spring:message>
                        </button>
                        <div class="hidden" id="loading">
                            <img src="<%=request.getContextPath()%>/resources/images/loaders/setup.gif"/>
                        </div>
                     </div>
                </form:form>
            </div>
        </div>