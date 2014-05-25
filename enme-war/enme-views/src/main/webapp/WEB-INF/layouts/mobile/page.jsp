<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame mobile" />
    </title>
    <%@ include file="decorators/mobile-meta.jsp"%>
    <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp"%>
    <%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
    <body class="mobil">

    <%@ include file="/WEB-INF/jsp/includes/decorators/mobile-header.jsp"%>

    <div class="content">
        <tiles:insertAttribute name="content"/>
    </div>

    <header class="header_input_hidden">
        <%@ include file="/WEB-INF/layouts/decorators/i18n-input.jsp"%>
        <tiles:insertAttribute name="header" ignore="true" />
    </header>
    <article class="mobile-main">
        <tiles:insertAttribute name="menu" ignore="true" />
    </article>
    <footer>
        <tiles:insertAttribute name="footer" />
    </footer>
    <%@ include file="/WEB-INF/jsp/includes/javascript-mobile.jsp"%></head>
<tiles:insertAttribute name="dojo-layers" ignore="true" />
<tiles:insertAttribute name="extra-js" ignore="true" />
</body>
</html>