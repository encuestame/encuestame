<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
    <title><tiles:insertAttribute name="title" defaultValue="encuestame mobile" /></title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
   <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
   <style type="text/css">
          @import "<%=request.getContextPath()%>/resource/js/dijit/themes/claro/claro.css";
          @import "<%=request.getContextPath()%>/resource/js/dojox/mobile/themes/iphone/iphone.css";
          @import "<%=request.getContextPath()%>/resource/js/dojox/mobile/themes/iphone/iphone-compat.css";
    </style>
    <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp" %>
    <script type="text/javascript">
         dojo.require("dijit.dijit");
         dojo.require("dojo.parser");
         // Load the basic mobile widgetry and support code.
         dojo.require("dojox.mobile");
         // Load the lightweight parser.  dojo.parser can also be used, but it requires much more code to be loaded.
         dojo.require("dojox.mobile.parser");
         // Load the compat layer if the incoming browser isn't webkit based
         dojo.requireIf(!dojo.isWebKit, "dojox.mobile.compat");
    </script>
</head>
<body>
<div class="mobile">
    <p>
        This is a encuestame mobile frontend prototype.
        <a href="http://wiki.encuestame.org/en/mobile">http://wiki.encuestame.org/en/mobile</a>
    </p>
    <img alt="encuestame logo" src="<%=request.getContextPath()%>/resource/images/logos/encuestame_header.png" />
</div>

<!--    <div id="header">-->
<!--        <tiles:insertAttribute name="header" />-->
<!--    </div>-->
<!--    <div id="content">-->
<!--        <tiles:insertAttribute name="content" />-->
<!--    </div>-->
<!--    <div id="footer">-->
<!--        <tiles:insertAttribute name="footer" />-->
<!--    </div>-->
</body>
</html>
