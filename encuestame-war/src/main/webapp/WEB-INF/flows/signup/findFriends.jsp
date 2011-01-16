<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/publicBody.jsp" %>
    <div id="mainUserWrapper">
        <!--
            //FUTURE: get Friends
            http://code.google.com/apis/contacts/docs/3.0/developers_guide_java.html#Retrieving
         -->
         <form:form modelAttribute="signUpBean">
            <h2>
                No friend contacts right now, check in the future.
            </h2>
            <input type="submit" name="_eventId_next" value="Next"/>
        </form:form>
    </div>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>