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
   <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
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
          <a href="javascript:var%20sourceWindow%20%3D%20window.open%28%27about%3Ablank%27%29%3B%20%0Avar%20newDoc%20%3D%20sourceWindow.document%3B%20%0AnewDoc.open%28%29%3B%20%0AnewDoc.write%28%27%3Chtml%3E%3Chead%3E%3Ctitle%3ESource%20of%20%27%20%2B%20document.location.href%20%2B%20%27%3C/title%3E%3C/head%3E%3Cbody%3E%3C/body%3E%3C/html%3E%27%29%3B%20%0AnewDoc.close%28%29%3B%20%0Avar%20pre%20%3D%20newDoc.body.appendChild%28newDoc.createElement%28%22pre%22%29%29%3B%20%0Apre.appendChild%28newDoc.createTextNode%28document.documentElement.innerHTML%29%29%3B">View Source</a>
     </div>
</body>
</html>