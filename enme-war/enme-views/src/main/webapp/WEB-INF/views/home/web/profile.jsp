
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>


<style>
.web-profile-wrapper {

}

</style>

<div class="web-profile-wrapper web-wrapper-detail">
  <div class="container">
    <div class="row">
      <div class="span12 profile-header">
          <div class="picture">
                 <img width="110" height="110" alt="${profile.username}" src="<%=request.getContextPath()%>/picture/profile/${profile.username}/profile">
          </div>
          <h3>
            ${profile.username}
          </h3>
      </div>
    </div>
    <div class="row">
        <div class="span2">

              <ul class="rss">
                    <li>
                        <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                        <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">
                            <spring:message code="home.type.tweetpoll" /> </a>
                        </a>
                    </li>
                     <li>
                        <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                         <a href="<%=request.getContextPath()%>/feed/${profile.username}/poll.rss">
                             <spring:message code="home.type.poll" />
                         </a>
                    </li>
                    <li>
                        <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                        <a href="<%=request.getContextPath()%>/feed/${profile.username}/project.rss">
                            Projects
                        </a>
                    </li>
              </ul>
            </div>
            <div class="span10">
               <article class="enme-box">
                      <header>
                          <h3>
                             <spring:message code="home.profile.lastpub" />
                          </h3>
                          <div class="rss-right">
                            <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">
                              <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                              </a>
                          </div>
                      </header>
               <div class="web-pup-wrapper emne-box-gray">
               <c:forEach items="${lastItems}" var="item">
                   <%@ include file="detail_item.jsp"%>
              </c:forEach>
              <c:if test="${empty lastItems}">
                 <h3 class="no-results">
                  <spring:message code="results.noresults" />
                </h3>
              </c:if>
              </div>
              </article>
            </div>
        </div>
  </div>
</div>