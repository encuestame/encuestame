<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-tweetpoll-wrapper" class="enme-main-section web-wrapper-detail">
    <div class="admon-table-options panel-header">
       <div class="tb-left">
            <h3>
                <spring:message code="search.title" /> Search Results for <b>${q}</b>
            </h3>
            <p>
                <b>${total}</b> <spring:message code="search.total.found" />
            </p>
       </div>
    </div>
</div>

<article class="web-tweetpoll-detail web-wrapper-detail">
   <article class="emne-box">
       <article class="search-items-wrapper">
            <c:if test="${not empty results.questions}">
                <article class="search-items">
                        <c:forEach items="${results.questions}" var="item">
                              <section class="search-item">
                                <a href="<%=request.getContextPath()%>${item.urlLocation}">
                                   <h4 class="enme">
                                        ${item.itemSearchTitle}
                                   </h4>
                                </a>
                                <div data-dojo-type="me/web/widget/search/SearchStats"
                                      url="<%=request.getContextPath()%>${item.urlLocation}"
                                      type="${item.typeSearchResult}"
                                      dateCreated="${item.dateCreated}"
                                      itemId="${item.id}"></div>
                              </section>
                        </c:forEach>
                </article>
            </c:if>
            <aside>
                <c:if test="${not empty results.tags}">
                    <section class="search-hashtag">
                            <div>
                                <c:forEach items="${results.tags}" var="item">
                                      <c:if test="${not empty item.itemSearchTitle}">
                                          <span data-dojo-type="me/web/widget/stream/HashTagInfo"
                                                url="<%=request.getContextPath()%>/tag/${item.itemSearchTitle}/"
                                                hashTagName="${item.itemSearchTitle}">
                                          </span>
                                     </c:if>
                                </c:forEach>
                            </div>
                    </section>
                </c:if>
                <c:if test="${not empty results.profiles}">
                    <section class="search-profiles">

                            <c:forEach items="${results.profiles}" var="item">
                                  <section class="search-item">
                                    ${item.itemSearchTitle}
                                  </section>
                            </c:forEach>
                    </section>
                </c:if>
            </aside>
       </article>
   </article>
</article>