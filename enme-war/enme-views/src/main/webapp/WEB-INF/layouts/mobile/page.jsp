<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<head>
<title><tiles:insertAttribute name="title" defaultValue="encuestame mobile" /></title>
<%@ include file="decorators/mobile-meta.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
<body class="mobil">
    
    <a href="<%=request.getContextPath()%>/home">
        <header class="mobile-header" style="background: url('<%=request.getContextPath()%>/resources/images/logos/2013/horizontal/encuestame_horizontal_small_alpha.png')"></header>
    </a>

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