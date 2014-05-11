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
  <%@ include file="decorators/ui_bar.jsp"%>
  <div class="container">
      <tiles:insertAttribute name="header" ignore="true" />
  </div>
  <div id="mainWrapper" class="page container">
    <header id="header" class="header_input_hidden">
          <%@ include file="/WEB-INF/layouts/decorators/i18n-input.jsp"%>
    </header>
    <div id="content-container">
      <tiles:insertAttribute name="menu" ignore="true" />
      <div id="enme-content" class="container">
        <tiles:insertAttribute name="content" />
      </div>
    </div>
  </div>
  <!-- Insert additional javascript  -->
  <tiles:insertAttribute name="extra-js" ignore="true" />
  <c:if test="${logged}">
     <div id="modal-box"></div>
     <div id="loading"></div>
  </c:if>
  <%@ include file="/WEB-INF/jsp/includes/javascript.jsp"%>
  JUAN
  <tiles:insertAttribute name="dojo-layers" ignore="true" />
</body>

</html>