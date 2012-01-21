<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="web-pup-item">
     <!-- general information -->
     <div dojoType="encuestame.org.core.shared.publications.Publications"
            username="${item.ownerUsername}"
            url="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}"
            title="${item.questionBean.questionName}"
            itemId="${item.id}"
            comments="${item.totalComments}"
            submited="<spring:message code="submited.by" />"
            added="<spring:message code="added" />"
            relativeTime="${item.relativeTime}"
            ht="${item.hashtagAsString}">
      </div>
</section>