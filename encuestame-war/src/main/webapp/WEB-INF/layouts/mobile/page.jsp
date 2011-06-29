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
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame mobile" />
    </title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
   <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
    <style type="text/css">
          @import "<%=request.getContextPath()%>/resources/js/dijit/themes/claro/claro.css";
    </style>
    <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp" %>
    <script type="text/javascript">
         dojo.require("dijit.dijit");
         dojo.require("dojo.parser");
         // Load the basic mobile widgetry and support code.
         //dojo.require("dojox.mobile");
         // Load the lightweight parser.  dojo.parser can also be used, but it requires much more code to be loaded.
         //dojo.require("dojox.mobile.parser");
         // Load the compat layer if the incoming browser isn't webkit based
         //dojo.requireIf(!dojo.isWebKit, "dojox.mobile.compat");
    </script>
</head>
<body>
<body class="mobile claro">
     <div id="mainWrapper">
        <div id="header">
            <tiles:insertAttribute name="header" />
        </div>
        <div id="content-container">
            <div id="mobile-enme-content">
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