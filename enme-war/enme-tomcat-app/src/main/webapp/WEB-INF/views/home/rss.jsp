<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<link href="<%=request.getContextPath()%>/feed/home.rss" rel="alternate" type="application/rss+xml" title="<spring:message code="syndicate.home" />" />
<link href="<%=request.getContextPath()%>/feed/home.atom" rel="alternate" type="application/atom+xml" title="<spring:message code="syndicate.home" />" />
<link href="<%=request.getContextPath()%>/feed/poll.atom" rel="alternate" type="application/atom+xml" title="<spring:message code="syndicate.poll" />" />
<link href="<%=request.getContextPath()%>/feed/poll.rss" rel="alternate" type="application/rss+xml" title="<spring:message code="syndicate.poll" />" />
<link href="<%=request.getContextPath()%>/feed/projects.atom" rel="alternate" type="application/atom+xml" title="<spring:message code="syndicate.project" />" />
<link href="<%=request.getContextPath()%>/feed/projects.rss" rel="alternate" type="application/rss+xml" title="<spring:message code="syndicate.project" />" />
<link href="<%=request.getContextPath()%>/feed/survey.rss" rel="alternate" type="application/rss+xml" title="<spring:message code="syndicate.survey" />" />
<link href="<%=request.getContextPath()%>/feed/survey.atom" rel="alternate" type="application/atom+xml" title="<spring:message code="syndicate.survey" />" />
<link href="<%=request.getContextPath()%>/feed/tweetpoll.atom" rel="alternate" type="application/atom+xml" title="<spring:message code="syndicate.tweetpoll" />" />
<link href="<%=request.getContextPath()%>/feed/tweetpoll.rss" rel="alternate" type="application/rss+xml" title="<spring:message code="syndicate.tweetpoll" />" />