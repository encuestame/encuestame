<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="section">
   <div class="sectionTitle">
      <spring:message code="home.aside.popular.tags" />
   </div>
   <div dojoType="encuestame.org.core.commons.hashtags.Cloud" class="web-aside-section"></div>
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
   <div dojoType="encuestame.org.core.commons.rated.Comments" class="web-aside-section"></div>
</section>
<section class="section">
   <div class="sectionTitle">
           <spring:message code="home.aside.rated.hashtag" />
       </div>

</section>
<section class="section">
    <div class="sectionTitle">
         <spring:message code="home.aside.rated.users" />
     </div>
     <div dojoType="encuestame.org.core.commons.rated.RatedProfile" class="web-aside-section"></div>
</section>