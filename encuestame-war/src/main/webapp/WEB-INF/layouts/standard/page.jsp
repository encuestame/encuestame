<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame" />
    </title>
    <%@ include file="/WEB-INF/jsp/includes/meta.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/web/css.jsp" %>
</head>
<body class="claro">
     <div id="mainWrapper">
        <tiles:insertAttribute name="header" ignore="true" />
        <div id="content-container" class="enme-auto-center">
            <div id="enme-content" class="enme-auto-center">
                <tiles:insertAttribute name="menu" ignore="true" />
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
     </div>
     <div id="footer">
          <tiles:insertAttribute name="footer" />
     </div>
     <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
    <script type="text/javascript">
         dojo.require("dijit.dijit");
         dojo.require("dojo.parser");
         dojo.require("dojo.io.script");
         dojo.require("encuestame.org.core.commons");
         dojo.require("encuestame.org.core.commons.search.SearchMenu");
         dojo.require("encuestame.org.core.commons.error.ErrorSessionHandler");
         dojo.require("encuestame.org.core.commons.error.ErrorConexionHandler");
         dojo.require("encuestame.org.core.commons.error.ErrorHandler");
         <c:if test="${logged}">
             dojo.require("encuestame.org.activity.Activity");
             dojo.require("encuestame.org.core.commons.dashboard.DashBoardMenu");
             dojo.require("encuestame.org.core.commons.notifications.Notification");
             dojo.require("encuestame.org.core.commons.profile.ProfileMenu");
             dojo.require("dojox.widget.Toaster");
             encuestame.activity = new encuestame.org.activity.Activity(true);
         </c:if>
    </script>
<tiles:insertAttribute name="extra-js" ignore="true"/>
</body>
</html>