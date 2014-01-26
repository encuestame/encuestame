<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-profile-wrapper web-wrapper-detail">
        <div class="web-detail-header">
            <div class="mobile-profile-title category_color">
                ${profile.username} (${profile.name})
            </div>
            <div class="emne-box profile-header-resume">
              <div class="header-wrapper">
                  <div class="background-picture">

                  </div>
                  <div class="porfile-image">
                      <div class="cell pi-1"></div>
                      <div class="picture pi-2">
                          <!-- Replace by dojo widget. -->
                             <a href="<%=request.getContextPath()%>/picture/profile/${profile.username}/preview">
                                 <img alt="${profile.username}" src="<%=request.getContextPath()%>/picture/profile/${profile.username}/profile">
                             </a>
                      </div>
                      <div class="cell pi-3"></div> 
                  </div>
               </div>                
              <div class="profile-resume  center txt-df">
                  ${profile.dateNew}
              </div>
            </div>            
        </div>
        <div class="profile-content-wrapper">          
          <div class="emne-box">
            <header class="mobile-home-subtitle category_color">
                  Syndicated Sources
            </header>
            <ul class="rss txt-df">
                 <li>
                     <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                     <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">
                       TweetPoll
                     </a>
                 </li>
                  <li>
                     <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                      <a href="<%=request.getContextPath()%>/feed/${profile.username}/poll.rss">
                         Poll
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

          <article class="emne-box mobile-profile-items-wrapper">
             <header class="mobile-home-subtitle category_color">
                 <span>
                    <spring:message code="home.profile.lastpub" />
                 </span>
                 <div class="rss-icon">
                   <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">
                     <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                     </a>
                 </div>
             </header>
             <section class="web-pup-wrapper emne-box-gray">
                 <c:forEach items="${lastItems}" var="item">
                     <%@ include file="detail_item.jsp"%>
                </c:forEach>
                <c:if test="${empty lastItems}">
                       <h3 class="no-results">
                        <spring:message code="results.noresults" />
                      </h3>
                </c:if>
            </section>
          </article>
        </div>
</div>