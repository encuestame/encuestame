<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<meta name="HandheldFriendly" content="True">
<meta name="MobileOptimized" content="320">
<meta name="viewport" content="width=device-width">
<meta http-equiv="cleartype" content="on">

<link rel="apple-touch-icon"
    href="<%=request.getContextPath()%>/resources/mobile/icons/apple-touch-icon-iphone.png" />
<link rel="apple-touch-icon" sizes="72x72"
    href="<%=request.getContextPath()%>/resources/mobile/icons/apple-touch-icon-ipad.png" />
<link rel="apple-touch-icon" sizes="114x114"
    href="<%=request.getContextPath()%>/resources/mobile/icons/apple-touch-icon-iphone4.png" />
<link rel="apple-touch-icon" sizes="144x144"
    href="<%=request.getContextPath()%>/resources/mobile/icons/apple-touch-icon-ipad3.png" />

<link rel="shortcut icon" href="img/touch/apple-touch-icon.png">

<!-- For iOS web apps. Delete if not needed. https://github.com/h5bp/mobile-boilerplate/issues/94 -->
<!--
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        -->

<!-- This script prevents links from opening in Mobile Safari. https://gist.github.com/1042026 -->
<!--
        <script>(function(a,b,c){if(c in b&&b[c]){var d,e=a.location,f=/^(a|html)$/i;a.addEventListener("click",function(a){d=a.target;while(!f.test(d.nodeName))d=d.parentNode;"href"in d&&(d.href.indexOf("http")||~d.href.indexOf(e.host))&&(a.preventDefault(),e.href=d.href)},!1)}})(document,window.navigator,"standalone")</script>
        -->