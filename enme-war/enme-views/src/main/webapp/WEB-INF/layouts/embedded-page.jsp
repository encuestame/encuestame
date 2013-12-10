<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<head>
    <title></title>
    <%@ include file="/WEB-INF/jsp/includes/meta.jsp" %>
</head>
<body>
      <header>
            Embebed Header
      </header>
      <article>
            <tiles:insertAttribute name="content"/>
      </article>
      <footer>
        Embebed Footer
      </footer>
</html>