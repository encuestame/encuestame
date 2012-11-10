<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<!DOCTYPE html>
<!--[if IEMobile 7 ]>    <html class="no-js iem7"> <![endif]-->
<!--[if (gt IEMobile 7)|!(IEMobile)]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <title>
            <tiles:insertAttribute name="title" defaultValue="encuestame mobile" />
        </title>
        <%@ include file="/WEB-INF/jsp/includes/meta.jsp"%>
        <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp"%>
        <%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
        <%@ include file="/WEB-INF/jsp/includes/javascript.jsp"%>

        <%@ include file="decorators/mobile-meta.jsp"%>

        <%@ include file="/WEB-INF/jsp/includes/javascript-mobile.jsp"%>
    </head>
    <body class="mobile claro">
        <header>
            <tiles:insertAttribute name="header" ignore="true" />
        </header>
        <article class="mobile-main-vote">
              <tiles:insertAttribute name="content"/>
        </article>
        <footer>
            <tiles:insertAttribute name="footer" />
        </footer>
    </body>
</html>