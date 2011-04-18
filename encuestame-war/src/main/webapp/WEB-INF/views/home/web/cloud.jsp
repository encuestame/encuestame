<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div>Cloud Page</div>
 <c:forEach items="${hashtags}" var="cloud">
     <span class="item" style="font-size: 12px;">
         <a href="<%=request.getContextPath()%>/tag/${cloud.hashTagName}/">${cloud.hashTagName} --- ${cloud.size} </a>
     </span>
   </c:forEach>