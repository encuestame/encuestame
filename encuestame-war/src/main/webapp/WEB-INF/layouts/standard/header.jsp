<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div id="publicLineHeader">
        <div class="headerOptions">
                <form method="get" action="<%=request.getContextPath()%>/search.jspx">
                        <span class="link">
                            <c:if test="${!logged}">
                                <a href="<%=request.getContextPath()%>/signin.jspx">Sign In</a>
                           </c:if>
                        </span>
                         <span class="link">
                            <c:if test="${logged}">
                                <div dojoType="encuestame.org.core.commons.dashboard.DashBoardMenu"
                                    contextPath="<%=request.getContextPath()%>"></div>
                            </c:if>
                         </span>
                        <span class="link">
                         <span id="navbar">
                            <c:if test="${logged}">
                                <%@ include file="/WEB-INF/jsp/includes/profile.jsp" %>
                            </c:if>
                         </span>
                        </span>
                    <span class="search">
                        <div dojoType="encuestame.org.core.commons.search.SearchMenu"></div>
                    </span>
                </form>
        </div>
        <div style="float: left;">
            <a href="<%=request.getContextPath()%>/home">
                <span class="logoSmallEncuestame"></span>
            </a>
        </div>
</div>
