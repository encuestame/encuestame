<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="web-pup-item">
     <!-- general information -->
     <div   data-dojo-type="me/web/widget/publications/Publications"
            username="${item.ownerUsername}"
            url="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}"
            title="${item.questionBean.questionName}"
            itemId="${item.id}"
            displayImage="false"
            comments="${item.totalComments}"
            submited="<spring:message code="submited_by" />"
            added="<spring:message code="added" />"
            relativeTime="${item.relativeTime}"
            ht="${item.hashtagAsString}">
      </div>
</section>