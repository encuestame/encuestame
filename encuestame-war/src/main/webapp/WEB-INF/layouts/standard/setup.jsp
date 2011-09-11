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
<body>
     <div id="mainWrapper" class="web-setup-wrapper enme-auto-center" style="overflow: hidden;">
        <div>
            <tiles:insertAttribute name="header" />
        </div>
        <div id="content-container" class="enme-auto-center">
            <div id="enme-content" class="enme-auto-center web-setup-content">
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
     </div>
</body>
</html>