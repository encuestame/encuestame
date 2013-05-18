<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<!DOCTYPE html>
<!--[if IEMobile 7 ]>    <html class="no-js iem7"> <![endif]-->
<!--[if (gt IEMobile 7)|!(IEMobile)]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <title>
            <tiles:insertAttribute name="title" defaultValue="encuestame mobile" />
        </title>
        <%@ include file="decorators/mobile-meta.jsp"%>
        <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp"%>
        <%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
    </head>
    <body class="mobile">


      <div class="viewport">
      <div class="frame">
        <div id="menu" class="menu nav-collapse collapse width">
          <div class="collapse-inner">
            <div class="navbar navbar-inverse">
              <div class="navbar-inner">
                Menu
              </div>
            </div>
            <ul class="nav nav-tabs nav-stacked">
              <li><a>Futurama</a></li>
              <li><a>Star Wars</a></li>
              <li><a>Doctor Who</a></li>
            </ul>
          </div>
        </div>

        <div class="view">
          <div class="navbar navbar-inverse">
            <div class="navbar-inner">
              <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target="#menu">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
            </div>
          </div>
          <div id="content">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce et lacus at justo facilisis vehicula ut et felis. Phasellus ut libero pretium nibh sollicitudin vulputate a sed urna. Ut dictum odio a est sodales blandit. Integer vitae nisl nisi, quis vehicula diam. Sed nec erat dictum nisi suscipit placerat. Quisque ligula enim, porttitor at sollicitudin eu, aliquet et justo. Ut eu dolor metus. Etiam vitae justo at metus auctor egestas ac in dolor. Etiam facilisis urna ipsum. Nulla facilisi. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Integer vulputate, sapien sit amet suscipit pellentesque, est ipsum tempor erat, eu molestie risus dolor sit amet risus. Vestibulum adipiscing gravida tincidunt. Integer eu quam et lacus luctus molestie.
          </div>
        </div>
      </div>
    </div>

        <%@ include file="/WEB-INF/jsp/includes/javascript-mobile.jsp"%>
        <tiles:insertAttribute name="extra-js" ignore="true" />
    </body>
</html>