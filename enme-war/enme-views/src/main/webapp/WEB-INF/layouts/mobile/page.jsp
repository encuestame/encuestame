<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<head>
<title><tiles:insertAttribute name="title" defaultValue="encuestame mobile" /></title>
<%@ include file="decorators/mobile-meta.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
<body class="mobil">
    <header class="main">
        <h1 class="logo">    
            <a href="#" class="ss-list">                
                    dojoPanelMenu                
            </a>    
        </h1>
        <a href="#menu" class="menu-trigger ss-icon">&#xED50;</a>
        <nav id="menu">
            <ul>
                <li class="selected">
                    <a href="#">
                        Menu 1
                    </a>
                </li>
                <li>
                    <a href="#usage">
                        Menu 2
                    </a>
                </li>
                <li>
                    <a href="#inner-workings">
                        Menu 2
                    </a>
                </li>
                <li>
                    <a href="#animation">
                        Menu 2
                    </a>
                </li>
                <li>
                    <a href="#options">
                        Menu 3
                    </a>
                </li>
            </ul>
        </nav>       
    </header>

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
    <tiles:insertAttribute name="extra-js" ignore="true" />
</body>
</html>