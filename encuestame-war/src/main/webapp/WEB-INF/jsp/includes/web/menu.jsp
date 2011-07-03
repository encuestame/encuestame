 <div class="menuWrapper enme-auto-center">
    <ul>
        <li>
            <a style="margin-left: 5px;">
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_home.png" alt="Home" />
            </a>
        </li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/members">Accounts Members</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/location">Locations</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/project">Projects</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/user/tweetpoll/list">TweetPolls</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/user/poll">Polls</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/user/survey">Surveys</a></li>
    </ul>
    <div style="float: right;">
        <div dojoType="encuestame.org.core.commons.notifications.Notification"></div>
    </div>
    <br style="clear: left" />
    </div>
