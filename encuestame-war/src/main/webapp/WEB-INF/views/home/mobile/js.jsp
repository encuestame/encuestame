<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script type="text/javascript">
	define('test', ['jquery'], function(foo, bar) {
		var myModule = {
			doStuff : function() {
				console.log('Yay! Stuff');
			}
		};
		return myModule;
	});
</script>