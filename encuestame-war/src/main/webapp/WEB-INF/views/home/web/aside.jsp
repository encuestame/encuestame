<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="section">
   <div class="sectionTitle">
      <spring:message code="home.aside.popular.tags" />
   </div>
   <div dojoType="encuestame.org.core.commons.hashtags.Cloud"></div>
   <div class="link">
     <a href="<%=request.getContextPath()%>/cloud">
         <spring:message code="home.aside.view.all" />
     </a>
   </div>
</section>
<section class="section">
   <div class="sectionTitle">
           <spring:message code="home.aside.rated.comments" />
       </div>
   <div dojoType="encuestame.org.core.commons.rated.Comments"></div>
</section>
<section class="section">
   <div class="sectionTitle">
           <spring:message code="home.aside.rated.hashtag" />
       </div>
   <div dojoType="encuestame.org.core.commons.rated.HashTags"></div>
</section>
<section class="section">
   <div class="sectionTitle">
           <spring:message code="home.aside.rated.users" />
       </div>
   <div dojoType="encuestame.org.core.commons.rated.Users"></div>
</section>