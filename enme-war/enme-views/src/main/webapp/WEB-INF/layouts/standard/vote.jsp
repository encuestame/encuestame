<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="decorators/html.jsp"%>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame" />
    </title>
    <%@ include file="/WEB-INF/jsp/includes/meta.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/web/css.jsp" %>
     <tiles:insertAttribute name="css_module" ignore="true" />
    <%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp" %>
</head>
<body class="claro">
     <header id="header">
         <tiles:insertAttribute name="header" ignore="true" />
         <tiles:insertAttribute name="menu" ignore="true" />
     </header>
     <div id="mainWrapper">
        <div id="content-container" class="enme-auto-center">
            <div id="enme-content" class="enme-auto-center">
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
     </div>
     <footer id="footer">
          <tiles:insertAttribute name="footer" />
          <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
     </footer>
</html>