<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<div class="mobile-footer">
    <div class="footer-menu">
        <span>
            <a href="<%=EnMePlaceHolderConfigurer.getProperty("application.official.about")%>" target="_blank">
                <spring:message code="footer.about" />
            </a>
          </span>
        |
          <span>
            <a href="<%=EnMePlaceHolderConfigurer.getProperty("application.official.wiki")%>" target="_blank">
                <spring:message code="footer.faq" />
            </a>
          </span>
        |
          <span>
            <a href="<%=EnMePlaceHolderConfigurer.getProperty("application.official.wiki")%>" target="_blank">
                <spring:message code="footer.blog" />
            </a>
          </span>
        | <span>
            <a href="<%=EnMePlaceHolderConfigurer.getProperty("application.official.contact")%>" target="_blank">
                <spring:message code="footer.contact" />
            </a>
          </span>
    </div>
    <div class="mobile-footer-version">
        <spring:message code="footer.version" />
        <span>
             <%=EnMePlaceHolderConfigurer.getProperty("app.version")%>
       </span>
    </div>
</div>
