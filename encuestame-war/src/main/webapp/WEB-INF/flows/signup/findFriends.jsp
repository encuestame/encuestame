<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/publicBody.jsp" %>
    <div id="mainUserWrapper">
        <div class="form">
            find friends.
        </div>
         <form:form modelAttribute="signUpBean">
            <input type="submit" name="_eventId_next" value="Next"/>
        </form:form>
    </div>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>