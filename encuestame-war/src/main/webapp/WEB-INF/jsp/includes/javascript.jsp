<script src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"
        djConfig="parseOnLoad: true, useCommentedJson:true, isDebug: false"></script>
<script type="text/javascript">
        var config = {
            contextPath: '<%=request.getContextPath()%>',
            delay : 60000,
            activity : { levelDebug :"info", maxConnections : 2, maxNetworkDelay : 50000},
            notification : { delay : 60000, limit : 8},
            tp : {a : 2, hr : false, minsoa : 1 }
        };
</script>
<script src="<%=request.getContextPath()%>/resources/js/dojo/encuestame-commons.js"></script>