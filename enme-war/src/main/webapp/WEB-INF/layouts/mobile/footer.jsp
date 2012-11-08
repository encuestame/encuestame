<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="mobile-footer">
    <div class="footer-menu">
        <span>
            <a>
                about
            </a>
          </span>
        |
          <span>
            <a>
                faq
            </a>
          </span>
        |
          <span>
            <a>
                blog
            </a>
          </span>
        | <span>
            <a>
                contact us
            </a>
          </span>
    </div>
    <div class="mobile-footer-version">
        <%=EnMePlaceHolderConfigurer.getProperty("app.version")%>
    </div>
</div>
