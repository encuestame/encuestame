<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="defaultMarginWrapper">
<h2>Create Administration User</h2>
<form:form modelAttribute="administrator">
    <div>
        <label>Username:</label>
        <form:input path="username" size="30" maxlength="40" />
        <form:errors path="username" cssClass="errors" />
    </div>
    <div>
        <label>Email:</label>
        <form:input path="email" size="30" maxlength="40" />
        <form:errors path="email" cssClass="errors" />
    </div>
    <div>
        <label>Password:</label>
        <form:password path="password"  size="30" maxlength="40" />
    </div>
    <div>
        <label>Confirm Password:</label>
        <form:password path="confirmPassword"  size="30" maxlength="40" />
        <form:errors path="confirmPassword" cssClass="errors" />
    </div>
    <div>
        <input type="submit" id="saveUser" name="_eventId_create-user" value="Save User"/>
    </div>
</form:form>
</div>
