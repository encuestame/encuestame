<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div id="publicLineHeader">
        <div class="logo">
            <a href="<%=request.getContextPath()%>">
                <img alt="logo" src="<%=request.getContextPath()%>/resource/${logo}">
            </a>
        </div>
        <div class="headerOptions">
            <c:if test="${!logged}">
               <span class="link">
                   <a href="<%=request.getContextPath()%>/signin.jspx">Sign In</a>
               </span>
             </c:if>
             <c:if test="${logged}">
                <span class="link">
                    <div dojoType="encuestame.org.core.commons.dashboard.DashBoardMenu"
                         contextPath="<%=request.getContextPath()%>"></div>
                </span>
             </c:if>
             <c:if test="${logged}">
                  <span class="link">
                     <span id="navbar">
                           <%@ include file="/WEB-INF/jsp/includes/profile.jsp" %>
                     </span>
                 </span>
             </c:if>
             <span class="link">
                   <div dojoType="encuestame.org.core.commons.search.SearchMenu"></div>
             </span>
        </div>
</div>
