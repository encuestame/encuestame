<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script>
 var dojoConfig= {
    baseUrl: '<%=request.getContextPath()%>/resources/js/',
    packages: [
       { name: "dojo", location: "dojo" },
       { name: "dijit", location: "dijit" },
       { name: "dojox", location: "dojox" },
       { name: "hammer", location: "hammer" },
       { name: "me", location: "me" }
    ],
    has: {
        'dojo-firebug': false,
        'dojo-debug-messages': false
   },
    parseOnLoad : false,
    isDebug : 0,
    locale: '${user_locale}',
    tlmSiblingOfDojo : false,
    async : true
};
</script>