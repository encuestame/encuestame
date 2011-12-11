<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>


<article class="web-hashtag-wrapper">

    <section class="web-hashtag-header">
        <div class="web-hashtagName">
            <h2>
                ${tagName.hashTagName}
            </h2>
        </div>
        <div class="web-hashtag-options">
             Viewing: All Time
        </div>
    </section>

    <section class="web-ht-graph-wrapper emne-box">
        <div class="web-ht-button-wrapper">
            <div class="web-ht-button-item">

            </div>
            <div class="web-ht-button-item">

            </div>
            <div class="web-ht-button-item">

            </div>
        </div>
        <div id="holder" class="web-ht-graph"></div>
    </section>

    <div class="web-ht-detail-wrapper">
       <div class="web-ht-wrapper-mainline">
           <article class="emne-box">
                <header>
                   Last 50 Publications
                </header>
                <div class="web-pup-wrapper">
                <c:forEach items="${tweetPolls}" var="item">
                    <%@ include file="detail_item.jsp"%>
                </c:forEach>
                </div>
            </article>
             <article class="emne-box">
                     <header>
                        Tweets
                     </header>
                <section>
                    Pub #1
                </section>
                 <section>
                    Pub #2
                </section>
                 <section>
                    Pub #3
                </section>
                 <section>
                    Pub #1
                </section>
            </article>
        </div>
        <aside class="web-ht-wrapper-top">
            <article class="emne-box">
                 <header>
                    Stats
                 </header>
                 <table>
                    <tr>
                        <td>
                            Created by
                        </td>
                        <td>
                            Admin
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Created
                        </td>
                        <td>
                            4 years ago
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Hits
                        </td>
                        <td>
                            1,534
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Likes / Dislike Rate
                        </td>
                        <td>
                            34
                        </td>
                    <tr>
                        <td>
                            Average
                        </td>
                        <td>
                            454
                        </td>
                    </tr>
                 </table>
             </article>
            <article class="emne-box">
                 <header>
                    Rate
                 </header>
                 <div>Current Position</div>
                <table>
                    <tr>
                        <td>
                           USA
                        </td>
                        <td>
                            4
                        </td>
                        <td>
                            <img src="<%=request.getContextPath()%>/resources/images/icons/enme_down.png"/>
                        </td>
                    <tr>
                    <tr>
                        <td>
                           ${tagName.hashTagName}
                        </td>
                        <td>
                            5
                        </td>
                        <td>
                            <img src="<%=request.getContextPath()%>/resources/images/icons/enme_up.png"/>
                        </td>
                    <tr>
                </table>
            </article>
            <article class="emne-box">
                 <header>
                    Top 50 Profiles
                 </header>
                <table>
                    <tr>
                        <td>
                           Admin
                        </td>
                        <td>
                            1,400
                        </td>
                    <tr>
                    <tr>
                        <td>
                           Jota
                        </td>
                        <td>
                            1,200
                        </td>
                    <tr>
                </table>
            </article>
        </aside>
    </div>

<!--    <div class="web-tags-tweetpolls"> -->
<!--          <div> Last 10 TweetPolls</div> -->
<%--          <c:forEach items="${tweetPolls}" var="tweets"> --%>
<%--             <div class="web-tweetpolls"> <a href="<%=request.getContextPath()%>/tweetpoll/${tweets.id}/test">  ${tweets.questionBean.questionName} </a> | --%>
<%--             <span class="web-tweetpolls-time">${tweets.relativeTime}</span> --%>
<!--             </div> -->
<%--         </c:forEach> --%>
<!--         <div> Last 10 rated</div> -->
<%--         <c:forEach items="${tweetPollrated}" var="rated"> --%>
<%--             <div class="web-tweetpolls"> <a href="<%=request.getContextPath()%>/tweetpoll/${rated.id}/test">  ${rated.questionBean.questionName} </a> --%>
<%--             <span class="web-tweetpolls-time">${rated.relativeTime}</span> |  <span class="web-tweetpolls-time">${rated.totalVotes}</span> --%>
<!--             </div> -->
<%--         </c:forEach> --%>
<!--    </div> -->


</article>

