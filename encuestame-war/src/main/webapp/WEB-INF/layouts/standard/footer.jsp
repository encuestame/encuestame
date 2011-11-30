<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="footer">
    <div class="powered">
        Powered By <a href="http://www.encuestame.org" target="_blank">Encuestame</a>
    </div>
    Build: <%=EnMePlaceHolderConfigurer.getProperty("app.version")%>
<!--     <div class="errorDialog"> -->
<!--         <div id="sessionHandler"></div> -->
<!--         <div id="errorHandler"></div> -->
<!--         <div id="errorConexionHandler"></div> -->
<!--     </div> -->
<c:if test="${logged}">
        <div dojoType="dojox.widget.Toaster" duration="<%=EnMePlaceHolderConfigurer.getProperty("not.toaster.duration")%>"
            messageTopic="/encuestame/message/publish"
            positionDirection="<%=EnMePlaceHolderConfigurer.getProperty("not.toaster.position")%>"
            id="toasted"></div>
    </c:if>
</div>
