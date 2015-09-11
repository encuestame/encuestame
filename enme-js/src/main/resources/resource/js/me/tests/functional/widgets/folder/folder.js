define([
	'intern!object',
	'intern/chai!assert',
	'dojo/Deferred',
	'require',
	'intern/dojo/node!leadfoot/helpers/pollUntil'
], function (
	registerSuite,
	assert,
	Deferred,
	require,
	pollUntil) {
	registerSuite({

		name: 'Folder Widget Test',

		'folder action tweetpoll': function () {
			var url = require.toUrl('./folderTP.html');
			return this.remote.get(url)
				.setFindTimeout(88000)
				.setPageLoadTimeout(88000)
				.setExecuteAsyncTimeout(10000)
				.then(pollUntil('return window.ready', 88000))
				.find('css selector', '.new-option')
				.click(function(el, session) {
					session.find('css selector', '.folder-name')
						.click(function(el, session) {
							return session.find('input')
								   .pressKeys('folder5');
						})
						.then(function(el, session) {
						session.findAllByClassName('web-folder-item')
							.then(function (items) {
								//alert(1);
								assert.equal(3, items.length);
							})
							.end();
					});
				});
		},
		//'folder action poll': function () {
		//	var url = require.toUrl('./folderPoll.html');
		//	return this.remote.get(url)
		//		.setFindTimeout(88000)
		//		.setPageLoadTimeout(88000)
		//		.setExecuteAsyncTimeout(10000)
		//		.then(pollUntil('return window.ready', 88000))
		//		.find('css selector', '.new-option')
		//		.click(function(el, session){
		//			session.findAllByClassName('web-folder-item')
		//				.then(function (items) {
		//					//alert(1);
		//					assert.equal(3, items.length);
		//				})
		//				.end();
		//		});
		//},
		//'folder action survey': function () {
		//	var url = require.toUrl('./folderSurvey.html');
		//	return this.remote.get(url)
		//		.setFindTimeout(88000)
		//		.setPageLoadTimeout(88000)
		//		.setExecuteAsyncTimeout(10000)
		//		.then(pollUntil('return window.ready', 88000))
		//		.find('css selector', '.new-option')
		//		.click(function(el, session){
		//			session.findAllByClassName('web-folder-item')
		//				.then(function (items) {
		//					//alert(1);
		//					assert.equal(3, items.length);
		//				})
		//				.end();
		//		});
		//}
	});
});
