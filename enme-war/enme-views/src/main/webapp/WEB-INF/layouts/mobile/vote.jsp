<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
    <head>
        <title>
            <tiles:insertAttribute name="title" defaultValue="encuestame mobile" />
        </title>
        <%@ include file="decorators/mobile-meta.jsp"%>
        <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp"%>
        <%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
        <%@ include file="/WEB-INF/jsp/includes/javascript.jsp"%>

        <%@ include file="decorators/mobile-meta.jsp"%>

        <%@ include file="/WEB-INF/jsp/includes/javascript-mobile.jsp"%>
    </head>
    <body class="mobil">
        <%@ include file="/WEB-INF/jsp/includes/decorators/mobile-header.jsp"%>
        <div class="content">
            <header class="header_input_hidden">
                <%@ include file="/WEB-INF/layouts/decorators/i18n-input.jsp"%>
            </header>            
            <div class="vote-page">
                <article class="mobile-main-vote">
                      <tiles:insertAttribute name="content"/>
                </article>
            </div>
        <div>      
        <footer class="vote-footer">
            <tiles:insertAttribute name="footer" />
        </footer> 
    </body>
</html>