<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-tweetpoll-detail web-wrapper-detail">
   <section class="web-hashtag-header web-detail-header">
        <div class="title">
            <h2>
                ${q}
            </h2>
        </div>
    </section>
   <article class="emne-box">
       <article class="search-items-wrapper">
            <article class="search-items">
                 <header>
                     <h4>
                        Questions
                    </h4>
                 </header>
                 <c:if test="${not empty results.questions}">
                    <c:forEach items="${results.questions}" var="item">
                          <section class="search-item">
                            <div class="post-title">
                                Question added at ${item.dateCreated}
                            </div>
                            <a href="<%=request.getContextPath()%>${item.urlLocation}">
                               <h3>
                                    ${item.itemSearchTitle}
                               </h3>
                            </a>
                          </section>
                    </c:forEach>
                </c:if>
                <!-- No results -->
                <c:if test="${empty results.questions}">
                     <div class="search-item no-results">
                        <h3>
                            No Results
                        </h3>
                     </div>
                </c:if>
            </article>
            <aside>
                <section class="search-hashtag">
                    <header>
                        <h4>
                            Hashtags
                        </h4>
                    </header>
                     <c:if test="${not empty results.tags}">
                        <div>
                            <c:forEach items="${results.tags}" var="item">
                                  <section class="search-item">
                                     <a class="item" dojoTypeE="encuestame.org.core.commons.stream.HashTagInfo"
                                            url="<%=request.getContextPath()%>/tag/${item.itemSearchTitle}/"
                                            hashTagName="${item.itemSearchTitle}"></a>
                                  </section>
                            </c:forEach>
                        </div>
                    </c:if>
                    <!-- No results -->
                    <c:if test="${empty results.tags}">
                          <div class="no-results">
                            <h3>
                                No Results
                            </h3>
                         </div>
                    </c:if>
                </section>
                <section class="search-profiles">
                    <header>
                        <h4>Profiles</h4>
                    </header>
                     <c:if test="${not empty results.profiles}">
                        <c:forEach items="${results.profiles}" var="item">
                              <section class="search-item">
                                ${item.itemSearchTitle}
                              </section>
                        </c:forEach>
                    </c:if>
                    <!-- No results -->
                    <c:if test="${empty results.profiles}">
                          <div class="no-results">
                            <h3>No Results</h3>
                         </div>
                    </c:if>
                </section>
            </aside>
       </article>
   </article>
</article>