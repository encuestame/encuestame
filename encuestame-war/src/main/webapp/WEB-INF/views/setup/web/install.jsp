<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="defaultMarginWrapper">
     <div class="setup-description">
        <h1>
            Step 2: <span>Create Database</span>
        </h1>
        <p>
           Description of step.
        </p>

       <div>
            <b>Remember your database parameters:</b>
       </div>
       <ul>
           <c:forEach items="${sqlparam}" var="p">
              <li><c:out value="${p}" /></li>
           </c:forEach>
       </ul>

    </div>
     <c:if test="${not empty message}">
        <div class="${message.infoType.css}">${message.message}</div>
    </c:if>
    <c:if test="${state == null}">
        <h2>Create your tables</h2>
        <form:form method="post">
            <input type="submit" name="_eventId_create" value="Create Tables" />
        </form:form>
    </c:if>
    <c:if test="${state =='ok'}">
        <h2>Dou you want Install Demo Data?</h2>
        <p><em>This is ussefull to display demo site.</em></p>
        <form:form method="post">
            <input type="submit" name="_eventId_no" value="No" />
            <input type="submit" name="_eventId_yes" value="Yes" />
        </form:form>
    </c:if>
    <c:if test="${state =='fail'}">
        <form:form method="post">
            <input type="submit" name="_eventId_reinstall" value="ReInstall Database" />
        </form:form>
    </c:if>
</div>
