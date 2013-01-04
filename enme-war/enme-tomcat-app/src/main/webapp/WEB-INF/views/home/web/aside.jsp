<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="section emne-box">
   <div class="sectionTitle ">
      <spring:message code="home.aside.popular.tags" />
   </div>
   <div data-dojo-type="me/web/widget/hashtags/Cloud" class="web-aside-section"></div>
   <div class="link">
     <a href="<%=request.getContextPath()%>/cloud">
         <spring:message code="home.aside.view.all" />
     </a>
   </div>
</section>
<section class="section emne-box">
   <div class="sectionTitle ">
           <spring:message code="home.aside.rated.comments" />
   </div>
   <div data-dojo-type="me/web/widget/rated/Comments" comments="5" class="web-aside-section"></div>
</section>
<section class="section emne-box">
   <div class="sectionTitle ">
           <spring:message code="home.aside.rated.hashtag" />
       </div>

</section>
<section class="section emne-box">
    <div class="sectionTitle ">
         <spring:message code="home.aside.rated.users" />
     </div>
     <div data-dojo-type="me/web/widget/rated/RatedProfile" class="web-aside-section"></div>
</section>