<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame" />
    </title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <meta name="description" content="encuestame survey system" />
    <meta name="keywords" content="survey, twitter, social, open source, etc, etc" />
    <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/web/css.jsp" %>
    <script type="text/javascript">
         dojo.require("dijit.dijit");
         dojo.require("dojo.parser");
         dojo.require("dojo.io.script");
         dojo.require("encuestame.org.core.commons.search.SearchMenu");
         dojo.require("encuestame.org.core.commons.error.ErrorSessionHandler");
         dojo.require("encuestame.org.core.commons.error.ErrorConexionHandler");
         dojo.require("encuestame.org.core.commons.error.ErrorHandler");
     <c:if test="${logged}">
         dojo.require("encuestame.org.activity.Activity");
         dojo.require("encuestame.org.core.commons.dashboard.DashBoardMenu");
         dojo.require("encuestame.org.core.commons.notifications.Notification");
         dojo.require("encuestame.org.core.commons.profile.ProfileMenu");
         encuestame.activity = new encuestame.org.activity.Activity(true);
     </c:if>
</script>
</head>
<body class="claro">
     <div id="mainWrapper">
        <div id="header">
            <tiles:insertAttribute name="header" />
        </div>
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
</body>
</html>