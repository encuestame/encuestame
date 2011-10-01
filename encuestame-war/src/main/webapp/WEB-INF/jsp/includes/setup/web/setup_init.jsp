<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-setup-wrapper">
    <div class="web-setup-header-into">
            <div class="web-setup-header">
                <div class="web-setup-title">
                    <spring:message code="setup.header"></spring:message>
                </div>
            </div>
            <div class="web-header-description">
                <spring:message code="setup.header.des"></spring:message>
                <%=EnMePlaceHolderConfigurer.getProperty("app.version")%>
            </div>
        </div>
    <div id="enme-content" class="enme-auto-center web-setup-content">
