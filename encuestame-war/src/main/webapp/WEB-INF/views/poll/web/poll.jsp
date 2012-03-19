<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-tweetpoll-detail web-wrapper-detail">
   <header>
       <h1>${poll.questionBean.questionName}</h1>
   </header>
   <article class="emne-box">
        <section class="web-tweetpoll-info">
            <!--  -->
       </section>
        <section class="web-tweetpoll-answer-wrapper">
            <div class="web-tweetpoll-answer-chart">
                <!-- Poll Chart -->
                <div>
                    <a href="<%=request.getContextPath()%>/poll/vote/${poll.id}/${poll.questionBean.slugName}">Vote</a>
                </div>
            </div>
            <div class="web-tweetpoll-answer-answer">
                <table class="web-tweetpoll-answer-table" cellspacing="0">
                    <thead>
                        <tr class="gradient-black">
                            <th></th>
                            <th>
                                <span class="header-table-answer">Link to Vote</span>
                            </th>
                            <th>
                                <span class="header-table-answer">Count</span>
                            </th>
                            <th>
                                <span class="header-table-answer">Percent</span>
                            </th>
                            <th>
                                <span class="header-table-answer"></span>
                            </th>
                        </tr>
                        <tr>
                            <th class="header-table-answer-emtpy"></th>
                            <th class="header-table-answer-emtpy"></th>
                            <th class="header-table-answer-emtpy"></th>
                            <th class="header-table-answer-emtpy"></th>
                            <th class="header-table-answer-emtpy"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${answers}" var="a">
                       </c:forEach>
                    </tbody>
                </table>
            </div>
       </section>
       <section class="web-button-wrapper gradient-gray">
            <div>
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_pie.png">
            </div>
            <div>
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_bar.png">
            </div>
       </section>
   </article>
   <article class="emne-box">
       <header>
          Social Links Refered
       </header>
       <section>
            <div dojoType="encuestame.org.core.commons.social.LinksPublished"
                 type="POLL" class="web-social-links"
                 ></div>
       </section>
    </article>
   <c:if test="${!empty hashtags}">
       <section class="emne-box">
           <header>HashTag Refered</header>
           <div class="web-tweetpoll-hashtags ">
               <c:forEach items="${hashtags}" var="h">
                       <span dojoType="encuestame.org.core.commons.stream.HashTagInfo"
                        url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                        hashTagName="${h.hashTagName}"></span>
               </c:forEach>
           </div>
       </section>
   </c:if>
   <section class="web-tweetpoll-comments emne-box">
      <header>Comments</header>
      <div dojoType="encuestame.org.core.comments.Comments" type="poll" item_id="${poll.id}"></div>
   </section>
</article>