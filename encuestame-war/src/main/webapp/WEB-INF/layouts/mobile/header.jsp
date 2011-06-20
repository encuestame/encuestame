<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div id="publicLineHeader">
        <div class="logo">
            <a href="<%=request.getContextPath()%>">
                <img alt="logo" src="<%=request.getContextPath()%>/resources/${logo}">
            </a>
        </div>
        <div class="headerOptions">
            <c:if test="${!logged}">
               <span class="link">
                   <a href="<%=request.getContextPath()%>/user/signin">Sign In</a>
               </span>
             </c:if>
        </div>
</div>