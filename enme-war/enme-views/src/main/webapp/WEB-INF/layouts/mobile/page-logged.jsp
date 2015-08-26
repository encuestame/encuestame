<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame mobile" />
    </title>
    <%@ include file="decorators/mobile-meta.jsp"%>
    <%@ include file="/WEB-INF/jsp/includes/web/css.jsp"%>
    <%@ include file="/WEB-INF/jsp/includes/mobile/css.jsp"%>
    <%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/js/delite-build/themes/defaultapp.css" />" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/commons/dev-mobile.css" />" />
</head>
<body class="mobil">


<d-side-pane id="sp" class="blue">
    <ul class="side-ul">
        <li class="side-li">
            <a onclick="sp.hide()" class=" side-bar-btn side-bar-icon-delete" href="#" />
                Close
            </a>
        </li>
        <menu-link url="<c:url value="/user/dashboard" />" name="Dashboard"></menu-link>
        <menu-link url="<c:url value="/user/tweetpoll/list" />" name="TweetPoll Manage"></menu-link>
        <menu-link url="<c:url value="/user/tweetpoll/new" />" name="Create Tweetpoll"></menu-link>
        <menu-link url="<c:url value="/user/poll" />" name="Manage Poll"></menu-link>
        <menu-link url="<c:url value="/user/poll/new" />" name="Create Poll"></menu-link>
        <menu-link url="<c:url value="/admon/members" />" name="Members"></menu-link>
        <menu-link url="<c:url value="/settings/configuration" />" name="Settings"></menu-link>
        <menu-link url="<c:url value="/settings/social" />" name="Social Accounts"></menu-link>
        <menu-link url="<c:url value="/user/logout" />" name="Logout"></menu-link>
    </ul>
</d-side-pane>
<div id="page" class="panel-content" style="">
    <%@ include file="/WEB-INF/jsp/includes/decorators/mobile-header.jsp"%>
    <header class="header_input_hidden">
        <%@ include file="/WEB-INF/layouts/decorators/i18n-input.jsp"%>
        <tiles:insertAttribute name="header" ignore="true" />
    </header>
    <article class="content">
        <tiles:insertAttribute name="content"/>
        <menu-link url="<c:url value="/user/tweetpoll/list" />" name="TweetPoll Manage">
            <button onclick="sp.show()">Show</button>
        </menu-link>

    </article>
    <footer>
        <tiles:insertAttribute name="footer" />
    </footer>

</div>


<%@ include file="/WEB-INF/layouts/mobile/decorators/bottom-logged-common.jsp"%>
</body>

</html>