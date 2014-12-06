<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>



<div class="web-profile-wrapper web-wrapper-detail">
  <div class="container">
    <div class="row">
      <div class="profile-header">
          <div class="picture">
                 <img width="110" height="110" alt="${profile.username}" src="<%=request.getContextPath()%>/picture/profile/${profile.username}/profile">
          </div>
          <h3>
            ${profile.username}
          </h3>
      </div>
    </div>
    <div>
            <div>
               <article class="emne-box">
                  <header>
                      <div class="rss-right">

                      </div>
                  </header>
                   <div class="web-pup-wrapper emne-box-gray">
                   <c:forEach items="${lastItems}" var="item">
                       <%@ include file="detail_item.jsp"%>
                  </c:forEach>
                  <c:if test="${empty lastItems}">
                     <h5 class="no-results">
                      <spring:message code="results.noresults" />
                    </h5>
                  </c:if>
                  </div>
              </article>
            </div>
        </div>
        <div class="emne-box">
                  <small>
                      <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                      <a href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss">
                          <spring:message code="home.type.tweetpoll" /> </a>
                      </a>
                  </small>
                  <small>
                      <img src="<%=request.getContextPath()%>/resources/images/icons/enme_rss.png">
                      <a href="<%=request.getContextPath()%>/feed/${profile.username}/poll.rss">
                          <spring:message code="home.type.poll" />
                      </a>
                  </small>
              </li>
          </ul>
      </div>

  </div>
</div>