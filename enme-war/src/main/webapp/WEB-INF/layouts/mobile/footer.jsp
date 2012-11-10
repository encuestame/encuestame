<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="mobile-footer">
    <div class="footer-menu">
        <span>
            <a>
                <spring:message code="footer.about" />
            </a>
          </span>
        |
          <span>
            <a>
                <spring:message code="footer.faq" />
            </a>
          </span>
        |
          <span>
            <a href="http://wiki.encuestame.org" target="_blank">
                <spring:message code="footer.blog" />
            </a>
          </span>
        | <span>
            <a>
                <spring:message code="footer.contact" />
            </a>
          </span>
    </div>
    <div class="mobile-footer-version">
        <a href="http://www.encuestame.org" target="_blank">
            <spring:message code="footer.version" />
                <span>
                     <%=EnMePlaceHolderConfigurer.getProperty("app.version")%>
               </span>
        </a>
    </div>
</div>
