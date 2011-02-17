 <div class="menuWrapper">
    <ul>
        <li>
            <a style="margin-left: 5px;">
                <img src="<%=request.getContextPath()%>/resource/images/icons/enme_home.png" alt="Home" />
            </a>
        </li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/members.jspx">Accounts Members</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/location.jspx">Locations</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/project.jspx">Projects</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/tweetpoll.jspx">TweetPolls</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/poll.jspx">Polls</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/survey.jspx">Surveys</a></li>
    </ul>
    <div style="float: right;">
        <div dojoType="encuestame.org.core.commons.notifications.Notification" delay="40000"></div>
    </div>
    <br style="clear: left" />
    </div>