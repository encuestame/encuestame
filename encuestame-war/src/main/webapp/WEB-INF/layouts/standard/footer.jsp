<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="footer">
    <%=EnMePlaceHolderConfigurer.getProperty("app.version")%>
    <div class="errorDialog">
        <div id="sessionHandler"></div>
        <div id="errorHandler"></div>
        <div id="errorConexionHandler"></div>
    </div>
</div>
