<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame" />
    </title>
    <%@ include file="/WEB-INF/jsp/includes/web/css.jsp"%>
    <%@ include file="decorators/web-meta.jsp"%>

    <%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
    <tiles:insertAttribute name="rss" ignore="true" />
</head>
<body class="enme-web-context dbootstrap">
<div class="container">
    <tiles:insertAttribute name="header" ignore="true" />
</div>
<div id="mainWrapper" class="page container single-body">
    <header id="header" class="header_input_hidden">
        <%@ include file="/WEB-INF/layouts/decorators/i18n-input.jsp"%>
    </header>
    <div id="content-container">
        <tiles:insertAttribute name="menu" ignore="true" />
        <div id="enme-content" class="container vote-layout">
            <tiles:insertAttribute name="content" />
        </div>
    </div>
</div>
<tiles:insertAttribute name="extra-js" ignore="true" />
<%@ include file="/WEB-INF/jsp/includes/javascript.jsp"%>
<tiles:insertAttribute name="dojo-layers" ignore="true" />
</body>

</html>














