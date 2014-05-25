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
	    // useCommentedJson : false,
	    parseOnLoad : false,
	    isDebug : 0,
	    tlmSiblingOfDojo : false,
	    async : true
	    };
</script>