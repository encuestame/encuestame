<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if lt IE 7]>  <html class="ie ie6 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 7]>     <html class="ie ie7 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 8]>     <html class="ie ie8 lte9 lte8"> <![endif]-->
<!--[if IE 9]>     <html class="ie ie9 lte9"> <![endif]-->
<!--[if gt IE 9]>  <html> <![endif]-->
<!--[if !IE]><!--> <html>             <!--<![endif]-->
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame mobile" />
    </title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
    <%@ include file="/WEB-INF/jsp/includes/javascript.jsp" %>
    <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp" %>
</head>
<body class="claro">
     <header id="header">
         <div id="header">
            <div id="mobile-header-wrapper">
                <div class="logo">
                    <a href="<%=request.getContextPath()%>"> <img alt="logo"
                        src="<%=request.getContextPath()%>/resources/${logo}"> </a>
                </div>
                <div class="mobile-header-options">
                    <c:if test="${!logged}">
                        <span class="link"> <a
                            href="<%=request.getContextPath()%>/user/signin">Sign In</a> </span>
                    </c:if>
                    <c:if test="${logged}">
                        <!--  <img alt="logo" src="<%=request.getContextPath()%>/resources/${logo}">-->
                        <a href="<%=request.getContextPath()%>/user/dashboard">Dashboard</a>
                    </c:if>
                </div>
            </div>
        </div>
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
     </footer>
</html>