<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<head>
<title><tiles:insertAttribute name="title" defaultValue="encuestame mobile" /></title>
<%@ include file="decorators/mobile-meta.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/javascript-mobile.jsp"%></head>
<body class="mobil">
    <header class="main">
        <h1 class="logo">
            <a href="." class="ss-list">jPanelMenu</a>
        </h1>
        <a href="#menu" class="menu-trigger ss-icon">&#xED50;</a>
        <nav id="menu">
            <ul>
                <li>
                    <a href="#">Overview</a>
                </li>
                <li>
                    <a href="#usage">Usage</a>
                </li>
                <li>
                    <a href="#inner-workings">Inner-Workings</a>
                </li>
                <li>
                    <a href="#animation">Animation</a>
                </li>
                <li>
                    <a href="#options">Options</a>
                </li>
                <li>
                    <a href="#api">API</a>
                </li>
                <li>
                    <a href="#tips">Tips &amp; Examples</a>
                </li>
                <li>
                    <a href="#license">License</a>
                </li>
                <li>
                    <a href="#changelog">Changelog</a>
                </li>
                <li>
                    <a href="#about">About</a>
                </li>
            </ul>
        </nav>
    </header>

    <header class="header_input_hidden">
        <%@ include file="/WEB-INF/layouts/decorators/i18n-input.jsp"%>
        <tiles:insertAttribute name="header" ignore="true" />
        <!-- <%@ include file="search.jsp"%>--></header>
    <article class="mobile-main">
        <tiles:insertAttribute name="menu" ignore="true" />
        <tiles:insertAttribute name="content"/>
    </article>
    <footer>
        <tiles:insertAttribute name="footer" />
    </footer>

    <tiles:insertAttribute name="extra-js" ignore="true" />
</body>
</html>