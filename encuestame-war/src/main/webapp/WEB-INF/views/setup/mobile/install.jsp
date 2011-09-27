<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_init.jsp"%>
    <div class="setup-description">
        <h1>
             Step 2: <span>Create Database</span>
        </h1>
        <p>Description of step.</p>
        Exist:? ${existDatabase}
        <c:if test="${not empty message}">
             <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <c:if test="${existDatabase}">
            <div class="warning">The database selected is exist previously. Please delete all content at the database selected.</div>
            <form:form method="post">
                 <input type="submit" name="_eventId_try-again" value="Try Again." />
            </form:form>
      </c:if>
      <c:if test="${existDatabase != true}">
            <div>
                <b>Remember your database parameters:</b>
            </div>
            <ul>
                <c:forEach items="${sqlparam}" var="p">
                    <li>
                        <c:out value="${p}" />
                    </li>
                </c:forEach>
            </ul>
            <c:if test="${state == null}">
                <h2>Create your tables</h2>
                <form:form method="post">
                    <input type="submit" name="_eventId_create" value="Create Tables" />
                </form:form>
            </c:if>
            <c:if test="${state =='fail'}">
                <form:form method="post">
                    <input type="submit" name="_eventId_reinstall"
                        value="ReInstall Database" />
                </form:form>
            </c:if>
            <c:if test="${state =='ok'}">
                <div class="success">Database installed successfully.</div>
                <form:form method="post">
                    <input type="submit" name="_eventId_demo"
                        value="Next Step" />
                </form:form>
            </c:if>
        </c:if>
    </div>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_finish.jsp"%>
