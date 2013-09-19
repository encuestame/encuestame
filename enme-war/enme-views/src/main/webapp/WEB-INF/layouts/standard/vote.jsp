<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame" />
    </title>
    <%@ include file="/WEB-INF/jsp/includes/meta.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/web/css.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp" %>
</head>
<body class="vote">
      <div class="container">
          <tiles:insertAttribute name="header" ignore="true" />
      </div>
     <div id="mainWrapper">
        <div id="content-container" class="enme-auto-center">
            <div id="enme-content">
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
     </div>
     <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
</html>