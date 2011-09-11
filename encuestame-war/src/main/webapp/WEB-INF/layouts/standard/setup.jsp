<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="Install / Upgrade Encuestame" />
    </title>
    <%@ include file="/WEB-INF/jsp/includes/meta.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/web/css.jsp" %>
</head>
<body class="claro">
     <div id="mainWrapper">
        <div id="header">
            <tiles:insertAttribute name="header" />
        </div>
        <div id="content-container" class="enme-auto-center">
            <div id="enme-content" class="enme-auto-center">
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
     </div>
     <div id="footer">
          <tiles:insertAttribute name="footer" />
     </div>
</body>
</html>