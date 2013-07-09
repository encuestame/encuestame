<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<h3>
    <spring:message code="setup.step2"></spring:message>
    <spring:message code="setup.stepDes2"></spring:message>
</h3>
<hr/>
<p>
    <spring:message code="setup.step2.long.des"></spring:message>
    <a href="http://www.encuestame.org/wiki/display/DOC/Basic+Configuration" target="_blank">
        <spring:message code="setup.step2.url"></spring:message> </a>
</p>
<c:if test="${not empty message}">
    <div class="${message.infoType.css}">${message.message}</div>
</c:if>
<c:if test="${existDatabase}">
    <div class="alert">
        <spring:message code="setup.step2.waring.database"></spring:message>
    </div>
    <div>
        <form:form method="post">
            <div class="default-rigth-aling">
                    <button type="submit" name="_eventId_try-again" value="Install"
                        class="btn btn-info ">
                        <spring:message code="setup.step2.button.try"></spring:message>
                    </button>
                </div>
        </form:form>
    </div>
</c:if>
<c:if test="${existDatabase != true}">
    <div>
        <b> <spring:message code="setup.step2.remember"></spring:message>
        </b>
    </div>
    <ul>
        <c:forEach items="${sqlparam}" var="p">
            <li><c:out value="${p}" /></li>
        </c:forEach>
    </ul>
    <div class="progress  progress-warning progress-striped">
      <div class="bar" style="width: 15%;"></div>
    </div>
    <c:if test="${state == null}">
        <form:form method="post">
            <div class="default-rigth-aling form-actions">
                <button type="submit" name="_eventId_create" value="Install"
                    class="btn btn-warning " onclick="hideButtonsDisplayLoading(this);">
                    <spring:message code="setup.step2.button"></spring:message>
                </button>
                <div class="hidden" id="loading">
                    <img src="<%=request.getContextPath()%>/resources/images/loaders/setup.gif"/>
                </div>
            </div>
        </form:form>
    </c:if>
    <c:if test="${state =='fail'}">
        <div class="default-rigth-aling form-actions">
                <button type="submit" name="_eventId_reinstall" value="Install"
                    class="btn btn-warning">
                    <spring:message code="setup.step2.button.reinstall"></spring:message>
                </button>
            </div>
    </c:if>
    <c:if test="${state =='ok'}">
        <div class="success">
            <spring:message code="setup.step2.success"></spring:message>
            .
        </div>
        <form:form method="post">
            <div class="default-rigth-aling form-actions">
                <button type="submit" name="_eventId_demo" value="Install"
                    class="btn btn-warning">
                    <spring:message code="setup.step.next"></spring:message>
                </button>
            </div>
        </form:form>
    </c:if>
</c:if>
