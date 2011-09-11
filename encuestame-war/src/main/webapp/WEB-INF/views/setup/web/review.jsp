<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="defaultMarginWrapper">
<h2>Create Administration User</h2>
<form:form>
    <div>
        <label>
            Username:
        </label>
        <span>
            ${admon}
        </span>
    </div>
    <div>
        <label>
            Email:
        </label>
        <span>
            ${admon}
        </span>
    </div>
    <div>
        <form:form method="post">
            <input type="submit" name="_eventId_edit-user" value="Edit User" />
            <input type="submit" name="_eventId_valid-user" value="Continue" />
        </form:form>

    </div>
</form:form>
</div>
