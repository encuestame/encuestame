<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>dashboard mobile</h2>
<div>
<a href="<%=request.getContextPath()%>/user/tweetpoll/list">TweetPolls</a>
<a href="<%=request.getContextPath()%>/user/notifications">Notifications</a>
</div>
