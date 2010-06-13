<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>encuestame</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/default.css" />" />
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/user.css" />" />
    <style type="css">
        img{ border:0px;}
    </style>
</head>
<body>
    <div id="mainUserWrapper">
        <div class="form">
            <form:form modelAttribute="signUpBean">
                    <div class="data">
                        <div class="form-pair">
                            <div class="form-item">
                                <label for="username">Username</label>
                            </div>
                            <div class="form-value">
                                <form:input path="username" size="30" maxlength="16" /><br /><form:errors path="username" cssClass="errors" />
                             </div>
                        </div>
                        <div class="form-pair">
                            <div class="form-item">
                                <label for="email">Email</label>
                            </div>
                            <div class="form-value">
                                <form:input path="email" size="30" maxlength="25" /><br /><form:errors path="email" cssClass="errors" />
                             </div>
                        </div>
                    </div>
                    <div class="recaptcha">
                        <c:out value="${signUpBean.captcha}" escapeXml="false" />
                        <br /><form:errors path="captcha" cssClass="error" />
                    </div>
                    <div class="form-submit-buttons">
                        <input type="submit" class="input-submit" name="submit" value="Submit" />
                    </div>
            </form:form>
        </div>
    </div>
</body>
</html>