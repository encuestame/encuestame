<script type="text/javascript">
    dojo.require("encuestame.org.core.commons.poll.PollNavigate");
</script>
<div id="web-poll-wrapper">
    <div class="web-poll-menu default-background-dark-color">
        <a href="<%=request.getContextPath()%>/user/poll/new">
        <img src="<%=request.getContextPath()%>/resources/images/icons/enme-add.png" alt="+" /> New Poll</a>
    </div>
    <div dojoType="encuestame.org.core.commons.poll.PollNavigate"></div>
</div>
