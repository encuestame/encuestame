<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-header">
    <div class="web-header-wrapper enme-auto-center">
        <div class="logo">
            <a href="<%=request.getContextPath()%>/"> <img alt="logo"
                src="<%=request.getContextPath()%>/resources/${logo}"> </a>
        </div>
        <div class="web-header-options">
            <c:if test="${!logged}">
                <span class="link"> <a
                    href="<%=request.getContextPath()%>/user/signin"> <spring:message
                            code="header.signin" /> </a> </span>
            </c:if>
            <c:if test="${logged}">
                <span class="link"> <a
                    href="<%=request.getContextPath()%>/home"> <spring:message
                            code="header.public.line" /> </a> </span>
            </c:if>
            <c:if test="${logged}">
                <span class="link">
                    <div dojoType="encuestame.org.core.commons.dashboard.DashBoardMenu"
                        contextPath="<%=request.getContextPath()%>"></div> </span>
            </c:if>
            <span class="link web-search-wrapper">
                <div dojoType="encuestame.org.core.commons.search.SearchMenu"></div>
            </span>
        </div>
    </div>
</div>
