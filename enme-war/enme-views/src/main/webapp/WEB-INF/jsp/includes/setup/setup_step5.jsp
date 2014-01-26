<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
 <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h3>
            <spring:message code="setup.step5"></spring:message>
            <spring:message code="setup.stepDes5"></spring:message>
        </h3>
        <hr/>
        <div class="setup-review">
                <ul>
                    <li><b><spring:message code="setup.step4.username"></spring:message>:</b> ${admon.username}</li>
                    <li><b><spring:message code="setup.step4.email"></spring:message>:</b> ${admon.email}</li>
                    <li><b><spring:message code="setup.step4.complete"></spring:message>:</b> ${admon.name}</li>
                </ul>
        </div>
        <div>
        <div class="control-group">
            <div class="progress  progress-warning progress-striped">
               <div class="bar" style="width: 85%;"></div>
            </div>
        </div>
        <div class="form-actions">
                <form:form method="post">
                     <div class="default-rigth-aling">
                        <button type="submit" name="_eventId_welcome" class="btn btn-warning">
                            <spring:message code="setup.step.next"></spring:message>
                        </button>
                        <div class="hidden" id="loading">
                            <img src="<%=request.getContextPath()%>/resources/images/loaders/setup.gif"/>
                        </div>
                     </div>
                </form:form>
            </div>
        </div>