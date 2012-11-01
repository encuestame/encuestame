<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script data-main="<%=request.getContextPath()%>/resources/js/mobile/main"
        type="text/javascript" 
        src="<%=request.getContextPath()%>/resources/js/mobile/lib/require/require.js"></script>
<script>          
require.config( {
    paths: {
        'backbone':         'lib/backbone/backbone',
        'underscore':       'lib/backbone/underscore',
        'json2':            'lib/json2'        
    },
    baseUrl: '<%=request.getContextPath()%>/resources/js/mobile/'
} );  

//simtple module test
define('test', ['backbone'], function(foo, bar) {
    var myModule = {
        doStuff : function() {
        }
    };
    return myModule;
});
//simple module required test
require(['require', 'underscore', 'backbone', 'test' ],
                function( require, $, _ , Backbone, t) {    
                    console.log("required", arguments);
                    console.log("required", t.doStuff());
});
        
//FUTURE: after migrate to Dojo 1.8, requirejs it's included and should be removed

</script>
