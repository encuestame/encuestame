<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<div id="header" class="header_color ">
    <div id="mobile-header-wrapper">
        <div class="logo">
            <a href="<%=request.getContextPath()%>"> <img alt="logo"
                src="<%=request.getContextPath()%>/resources/${logo}"> </a>
        </div>
        <%-- <div class="mobile-header-options">
            <c:if test="${!logged}">
                <span class="link"> <a
                    href="<%=request.getContextPath()%>/user/signin">Sign In</a> </span>
            </c:if>
            <c:if test="${logged}">
                <!--  <img alt="logo" src="<%=request.getContextPath()%>/resources/${logo}">-->
                <a href="<%=request.getContextPath()%>/user/dashboard">Dashboard</a>
            </c:if>
        </div> --%>
    </div>
</div>