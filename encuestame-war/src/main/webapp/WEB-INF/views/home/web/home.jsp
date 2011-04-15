<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
    <div id="mainPublicLineWrapper">
                   <div style="float: right; border: 0px solid rgb(0, 0, 0); width: 20%;">
                               In progress...  ${logged}
                  </div>
                  <div class="itemWrapper" style="border: 0px solid #FFF; width: 79%;">

          <div class="categoryMenu">
                <div style="clear: both;">
                <div style="float: right;">
                    filter by date
                </div>
                <div style="float: left;">
                    <span><a href="<%=request.getContextPath()%>/home?view=survey">All Surveys</a></span>
                    <span><a href="<%=request.getContextPath()%>/home?view=tweetpoll">All TweetPoll</a></span>
                    <span><a href="<%=request.getContextPath()%>/home?view=poll">All Poll</a></span>
                </div>
                </div>
            </div>
    <c:forEach items="${items}" var="item">
     <div class="item">
          ${item}
     </div>
   </c:forEach>
             <div class="item">
                <div class="img">
                        <div class="stats">
                            <div class="vote">
                                <div>
                                    <span class="count"><strong>150</strong></span>
                                    <div class="viewcount">answers</div>
                                </div>
                            </div>
                            <div class="views">
                               50 views
                            </div>
                        </div>
                </div>
                <div class="content">
                    <h3>
                         <a href="/survey/12345">Ever take silverware, glasses or other serving ware from a restaurant
                            becouse Nicaragua is the country more poor of the region?</a>
                    </h3>
                    <div style="padding: 2px;">

                    </div>
                    <div class="bottom">
                       <div class="share">
                                <span class="title">Share on:</span><br/>
                                <img src="resource/images/icons/enme_twitter.png" alt="Twitter" />
                                <img src="resource/images/icons/enme_facebook.png" alt="Facebook" />
                       </div>
                       <div class="options">
                            <div class="image">
                                    <img width="32" height="32" alt=""
                                    src="http://www.gravatar.com/avatar/6757caf55de0413ae0994293e001d465?s=32&amp;d=identicon&amp;r=PG"/>
                            </div>
                            <div>(Submited By <strong><a href="#">Jota</a></strong>) added <strong>45 minutes</strong> ago</div>
                            <div><img src="/resource/images/icons/enme_comment_reply.png"/> 25 Comments</div>

                       </div>
                       <div class="tags">
                                <a href="#" class="tag">Nicaragua</a>
                                <a href="#" class="tag">Venezuela</a>
                                <a href="#" class="tag">Hugo Chavez</a>
                                <a href="#" class="tag">Estados Unidos</a>
                                <a href="#" class="tag">Guerra</a>
                       </div>
                    </div>
                </div>
                <p class="clear"></p>
            </div>
    </div>
</div>
