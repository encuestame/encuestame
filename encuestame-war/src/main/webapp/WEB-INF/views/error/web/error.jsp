<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div>
      <c:if test="${not empty message}">
           <div class="${message.infoType.css}">
                <div>${message.message}</div>
                <div class="trac">Report this bug on <a href="<%=EnMePlaceHolderConfigurer.getProperty("application.official.bugtrac")%>">
                    <%=EnMePlaceHolderConfigurer.getProperty("application.official.bugtrac")%></a></div>
                </div>
           <div class="extra">
                 ${message.description}
           </div>
      </c:if>
</div>