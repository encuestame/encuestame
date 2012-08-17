<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame mobile" />
    </title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp" %>
</head>
<body>
<body class="mobile claro">
     <div id="mainWrapper">
        <tiles:insertAttribute name="header" ignore="true" />
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
     <%@ include file="/WEB-INF/jsp/includes/javascript-mobile.jsp" %>
     <tiles:insertAttribute name="extra-js" ignore="true" />
</body>
</html>