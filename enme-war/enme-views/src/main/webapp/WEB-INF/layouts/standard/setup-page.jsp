<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="decorators/html.jsp"%>
<head>
<title>
    <tiles:insertAttribute name="title" defaultValue="encuestame" />

    </title>
<%@ include file="/WEB-INF/jsp/includes/meta.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/web/css.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
<script  src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script>
  function hideButtonsDisplayLoading(node) {
      var _n = document.getElementsByTagName('button');
      for (var i=0; i < _n.length; i++) {
        _n[i].className = "hidden";
      }
      var _m = document.getElementById('loading');
        _m.className = "";
  }
</script>
</head>
<body class="enme-web-context">
  <div id="mainWrapper" class="page">
    <header>

    </header>
    <tiles:insertAttribute name="menu" ignore="true" />
    <div id="content-container" class="enme-auto-center setup">
      <div id="enme-content" class="enme-auto-center">
        <tiles:insertAttribute name="content" />
      </div>
    </div>
    <footer id="footer" class="">

    </footer>
  </div>
  <div id="footer-f">
     <tiles:insertAttribute name="footer" />
  </div>
</body>

</html>