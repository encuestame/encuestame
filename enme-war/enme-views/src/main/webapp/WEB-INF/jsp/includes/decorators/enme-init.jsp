<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
_ENME.init({
        contextPath: '<%=request.getContextPath()%>',
        domain : '<%=WidgetUtil.getDomain(request)%>',
        suggest_limit : 10,
        delay : 1800000,
        logged : ${logged},
        helpLinks : ${help_links},
        isMobile: ${detectedDevice},
        currentPath: '${mappedPath}',
        locale: '${user_locale}',
        debug : <%=EnMePlaceHolderConfigurer.getProperty("encuestame.error.debug")%>,
        message_delay : 5000,
        activity : {
            url : "<%=WidgetUtil.getDomain(request)%>/activity",
            logLevel : "<%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.levelDebug")%>",
            maxConnections : <%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.maxConnections")%>,
            maxNetworkDelay : <%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.maxNetworkDelay")%>,
            delay : <%=EnMePlaceHolderConfigurer.getProperty("not.main.delay")%>,
            limit : <%=EnMePlaceHolderConfigurer.getProperty("not.main.limit")%>
        },
        tp_a : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.allowed")%>,
        tp_hr : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.hr")%>,
        tp_minsoa : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.minsoa")%>
    });