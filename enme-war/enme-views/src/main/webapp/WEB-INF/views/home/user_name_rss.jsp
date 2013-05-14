<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<link href="<%=request.getContextPath()%>/feed/${profile.username}/poll.atom" rel="alternate" type="application/atom+xml" title="<spring:message code="syndicate.poll" />" />
<link href="<%=request.getContextPath()%>/feed/${profile.username}/poll.rss" rel="alternate" type="application/rss+xml" title="<spring:message code="syndicate.poll" />" />
<link href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.atom" rel="alternate" type="application/atom+xml" title="<spring:message code="syndicate.tweetpoll" />" />
<link href="<%=request.getContextPath()%>/feed/${profile.username}/tweetpoll.rss" rel="alternate" type="application/rss+xml" title="<spring:message code="syndicate.tweetpoll" />" />
<link href="<%=request.getContextPath()%>/feed/${profile.username}/profile.atom" rel="alternate" type="application/atom+xml" title="<spring:message code="syndicate.profiles" />" />
<link href="<%=request.getContextPath()%>/feed/${profile.username}/profile.rss" rel="alternate" type="application/rss+xml" title="<spring:message code="syndicate.profiles" />" />
