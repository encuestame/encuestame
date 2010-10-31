 <script type="text/javascript">
   dojo.require("encuestame.org.class.commons.notifications.Notification");
</script>
 <div class="menuWrapper">
    <ul>
        <li>
            <a style="margin-left: 5px;">
                <img src="<%=request.getContextPath()%>/resource/images/icons/enme_home.png" alt="Home" />
            </a>
        </li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/users.html">Users</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/groups.html">Groups</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/location.html">Locations</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/project.html">Projects</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/tweetpoll.html">TweetPolls</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/poll.html">Polls</a></li>
        <li class="item"><a href="<%=request.getContextPath()%>//survey.html">Surveys</a></li>
    </ul>
    <div style="float: right; padding: 12px;" >
        <div dojoType="encuestame.org.class.commons.notifications.Notification" delay="10000"></div>
    </div>
    <br style="clear: left" />
    </div>