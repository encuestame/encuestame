<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!DOCTYPE html>
<!--[if lt IE 7]>  <html class="ie ie6 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 7]>     <html class="ie ie7 lte9 lte8 lte7"> <![endif]-->
<!--[if IE 8]>     <html class="ie ie8 lte9 lte8"> <![endif]-->
<!--[if IE 9]>     <html class="ie ie9 lte9"> <![endif]-->
<!--[if gt IE 9]>  <html> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
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
      var _n = dojo.query('button');
      _n.forEach(function(node){
          dojo.addClass(node, 'hidden');
      });
      var _m = dojo.byId('loading');
      if (_m) {
          dojo.removeClass(_m, 'hidden');
      }
  }
</script>
</head>
<body class="enme-web-context">
  <div id="mainWrapper" class="page">
    <header>

    </header>
    <tiles:insertAttribute name="menu" ignore="true" />
    <div id="content-container" class="enme-auto-center">
      <div id="enme-content" class="enme-auto-center">
        <tiles:insertAttribute name="content" />
      </div>
      <footer id="footer">
        <tiles:insertAttribute name="footer" />
      </footer>
    </div>
  </div>
</body>

</html>