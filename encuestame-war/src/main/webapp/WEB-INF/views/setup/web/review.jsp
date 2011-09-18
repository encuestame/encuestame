<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="defaultMarginWrapper">
    <div class="setup-description">
        <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h1>
            Step 5: <span>Review your Account Data</span>
        </h1>
        <div class="setup-review">
                <ul>
                    <li><b>Username:</b> ${admon.username}</li>
                    <li><b>Email:</b> ${admon.email}</li>
                    <li><b>Complete Name:</b> ${admon.name}</li>
                </ul>
        </div>
        <div>
            <form:form method="post">
                <!--             <input type="submit" name="_eventId_edit-user" value="Edit User" /> -->
                <input type="submit" name="_eventId_valid-user" value="Continue" />
            </form:form>
        </div>
    </div>
</div>
