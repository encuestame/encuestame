<%@ page session="false" %>
<%@page import="org.encuestame.mvc.util.WidgetUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/spring-social/facebook/tags" prefix="facebook" %>
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
    <script type="text/javascript">
        dojo.require("dijit.dijit");
        dojo.require("dojo.parser");
        //cometd libs
        dojo.require("dojox.cometd");
        dojo.require("dojo.io.script");
        dojo.require("dojox.cometd");
        var config = {
            contextPath: '<%=request.getContextPath()%>',
            delay : 2000
        };
        var cometd = dojox.cometd;

        var contextPathRoot = '<%=request.getContextPath()%>';
    </script>
    <%@ include file="/WEB-INF/jsp/includes/web/css.jsp" %>
    <script type="text/javascript">
         dojo.require("encuestame.org.core.commons.search.SearchMenu");
         dojo.require("encuestame.org.core.commons.dashboard.DashBoardMenu");
         dojo.require("encuestame.org.core.commons.error.ErrorSessionHandler");
         dojo.require("encuestame.org.core.commons.error.ErrorConexionHandler");
         dojo.require("encuestame.org.core.commons.error.ErrorHandler");
         //dojo.require("dojox.cometd.callbackPollTransport");
</script>
<c:if test="${logged}">
  <script type="text/javascript">
             dojo.require("encuestame.org.core.commons.notifications.Notification");
             dojo.require("encuestame.org.core.commons.profile.ProfileMenu");
  </script>
</c:if>
<%--
Disabled cometD.--%>
<script src="<%=request.getContextPath()%>/resource/js/cometd.js"></script>

</head>
<body class="claro">
     <div id="mainWrapper">
        <div id="header">
            <tiles:insertAttribute name="header" />
        </div>
        <div id="content-container">
            <div id="enme-content">
                <tiles:insertAttribute name="menu" ignore="true" />
                <tiles:insertAttribute name="content"/>
            </div>
            <div id="footer">
                <tiles:insertAttribute name="footer" />
            </div>
        </div>
     </div>
</body>
</html>