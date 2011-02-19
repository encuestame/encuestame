 <div class="menuWrapper">
    <ul>
        <li>
            <a style="margin-left: 5px;">
                <img src="<%=request.getContextPath()%>/resource/images/icons/enme_home.png" alt="Home" />
            </a>
        </li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/members">Accounts Members</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/location">Locations</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/project">Projects</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/tweetpoll">TweetPolls</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/poll">Polls</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/survey">Surveys</a></li>
    </ul>
    <div style="float: right;">
        <div dojoType="encuestame.org.core.commons.notifications.Notification" delay="40000"></div>
    </div>
    <br style="clear: left" />
    </div>
