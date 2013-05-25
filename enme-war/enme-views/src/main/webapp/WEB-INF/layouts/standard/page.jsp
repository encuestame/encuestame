<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!DOCTYPE html>
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
<%@ include file="decorators/web-meta.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/web/css.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/javascript.jsp"%>
<tiles:insertAttribute name="rss" ignore="true" />
</head>
<body class="enme-web-context">
  <c:if test="${logged}">
      <div data-dojo-type="me/web/widget/ui/UpgradeBar" validate="${isActivated}">
          <div class="up-message">
              <span>
                  <spring:message code="singup.account.not.validated" />
              </span>
              <a href="<%=request.getContextPath()%>/user/confirm/email/refresh/code">
                     <spring:message code="singup.account.send.code" />
              </a>
          </div>
      </div>
  </c:if>
  <div class="container">
      <tiles:insertAttribute name="header" ignore="true" />
  </div>
  <div id="mainWrapper" class="page">
    <header id="header" class="header_input_hidden">
          <c:forEach items="${i18n}" var="entry">
                <input type="hidden" name="${entry.key}" value="${entry.value}"/>
          </c:forEach>
          <input type="hidden" name="not_view_all" value="<spring:message code="not_view_all" />"/>
    </header>
    <tiles:insertAttribute name="menu" ignore="true" />
    <div id="content-container">
      <div id="enme-content">
         <c:if test="${logged}">
             <div data-dojo-type="me/web/widget/ui/Toaster"
               duration="<%=EnMePlaceHolderConfigurer.getProperty("not.toaster.duration")%>"
               messageTopic="/encuestame/message/publish"
               positionDirection="<%=EnMePlaceHolderConfigurer.getProperty("not.toaster.position")%>"
               id="toasted_message">
            </div>
        </c:if>
        <tiles:insertAttribute name="content" />
      </div>
    </div>
    <footer id="footer" class="">

    </footer>
  </div>
  <div id="footer-f">
     <tiles:insertAttribute name="footer" />
  </div>
  <!-- Insert additional javascript  -->
  <tiles:insertAttribute name="extra-js" ignore="true" />
  <c:if test="${logged}">
     <div id="modal-box"></div>
     <div id="loading"></div>
  </c:if>
</body>

</html>