<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/publicBody.jsp" %>
<script type="text/javascript">
    dojo.require("encuestame.org.core.commons.signup.SignupProfile");
</script>
    <div id="mainUserWrapper">
        <form:form>
            <div class="">
                <div dojoType="encuestame.org.core.commons.signup.SignupProfile"
                     username="${signUpBean.username}"
                     email="${signUpBean.email}"
                     fullName="${signUpBean.fullName}"
                     contextPath="<%=WidgetUtil.getDomain(request)%>"
                     imagePath="<%=WidgetUtil.getUserProfileImagePath(request)%>">
                </div>

            </div>
            <br/>
            <input type="submit" name="_eventId_next" value="Go to DashBoard"/>
        </form:form>
    </div>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>