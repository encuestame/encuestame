<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
 <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h1>
            <spring:message code="setup.step5"></spring:message>
            <spring:message code="setup.stepDes5"></spring:message>
        </h1>
        <div class="setup-review">
                <ul>
                    <li><b><spring:message code="setup.step4.username"></spring:message>:</b> ${admon.username}</li>
                    <li><b><spring:message code="setup.step4.email"></spring:message>:</b> ${admon.email}</li>
                    <li><b><spring:message code="setup.step4.complete"></spring:message>:</b> ${admon.name}</li>
                </ul>
        </div>
        <div>
            <form:form method="post">
                <!--             <input type="submit" name="_eventId_edit-user" value="Edit User" /> -->
                 <div class="default-rigth-aling">
                    <button type="submit" name="_eventId_valid-user"
                        class="btn-default" onclick="hideButtonsDisplayLoading(this);">
                        <spring:message code="setup.step.next"></spring:message>
                    </button>
                    <div class="hidden" id="loading">
                        <img src="<%=request.getContextPath()%>/resources/images/loaders/setup.gif"/>
                    </div>
                 </div>
            </form:form>
        </div>