<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<div class="footer">
    <div class="powered">
        Build: <%=EnMePlaceHolderConfigurer.getProperty("app.version")%> <br/>
        Powered By <a href="http://www.encuestame.org" target="_blank">encuestame</a>
    </div>
<c:if test="${logged}">
        <div dojoType="dojox.widget.Toaster" duration="<%=EnMePlaceHolderConfigurer.getProperty("not.toaster.duration")%>"
            messageTopic="/encuestame/message/publish"
            positionDirection="<%=EnMePlaceHolderConfigurer.getProperty("not.toaster.position")%>"
            id="toasted"></div>
    </c:if>
</div>
