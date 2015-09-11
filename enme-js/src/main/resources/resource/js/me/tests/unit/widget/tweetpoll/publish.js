///**
// * Copyright 2014 encuestame
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *       http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//
//define([
//	'intern!object',
//	'intern/chai!assert',
//	'dojo/dom-construct',
//	'dojo/on',
//	'../../Helper',
//	'../../support/requestMocker',
//	'me/web/widget/tweetpoll/TweetPollList',
//	'me/web/widget/tweetpoll/TweetPoll',
//	'me/web/widget/tweetpoll/TweetPollCore',
//	'me/web/widget/tweetpoll/TweetPollListDetail',
//	'me/web/widget/tweetpoll/TweetPollListItem',
//	'me/web/widget/tweetpoll/TweetPollPreview',
//	'me/web/widget/tweetpoll/AnswerItem',
//	'me/web/widget/tweetpoll/Answers',
//	'me/web/widget/tweetpoll/detail/TweetPollAnswer',
//	'me/web/widget/tweetpoll/detail/TweetPollChartDetail',
//	'me/web/widget/tweetpoll/detail/TweetPollInfoDetail',
//	'me/web/widget/tweetpoll/HashTags',
//	'me/web/widget/tweetpoll/HashTagsItem',
//	'me/web/widget/tweetpoll/TweetPollPublishInfo',
//	'me/web/widget/tweetpoll/TweetPollPublishItemAbstractStatus',
//	'me/web/widget/tweetpoll/TweetPollPublishItemFAILUREStatus',
//	'me/web/widget/tweetpoll/TweetPollPublishItemStatus',
//	'me/web/widget/tweetpoll/TweetPollPublishItemSUCCESStatus'
//], function (
//	registerSuite,
//	assert,
//	domConstruct,
//	on,
//	Helper,
//	requestMocker,
//	TweetPollList,
//	TweetPoll,
//	TweetPollCore,
//	TweetPollListDetail,
//	TweetPollListItem,
//	TweetPollPreview,
//	AnswerItem,
//	Answers,
//	TweetPollAnswer,
//	TweetPollChartDetail,
//	TweetPollInfoDetail,
//	HashTags,
//	HashTagsItem,
//	TweetPollPublishInfo,
//	TweetPollPublishItemAbstractStatus,
//	TweetPollPublishItemFAILUREStatus,
//	TweetPollPublishItemStatus,
//	TweetPollPublishItemSUCCESStatus) {
//
//	'use strict';
//
//	var tp, tp2;
//
//	registerSuite({
//		name: 'TweetPoll Publish Widgets',
//		setup: function () {
//			Helper.init();
//			tp = Helper.createElement('mainWrapper');
//			tp2 = Helper.createElement('previewWrapperFixed');
//			Helper.addCss(5, "../../tests/resources/resources/css/dev/tweetpoll.css");
//		},
//
//		teardown: function () {
//			Helper.removeElement(tp);
//			Helper.removeElement(tp2);
//			//Helper.removeCss(1);
//		},
//
//
//		'TweetPollPublishInfo Widget': function () {
//			var tpollPublishInfo = new TweetPollPublishInfo({
//
//			});
//			assert.isObject(tpollPublishInfo, 'TweetPollPublishInfo should be an object');
//		},
//
////        'TweetPollPublishItemAbstractStatus Widget': function () {
////            var tpollPublishItemAbstract = new TweetPollPublishItemAbstractStatus({
////
////            });
////            assert.isObject(tpollPublishItemAbstract, 'TweetPollPublishItemAbstractStatus should be an object');
////        },
////
////        'TweetPollPublishItemFAILUREStatus Widget': function () {
////            var tpollPublishItemFailureStatus = new TweetPollPublishItemFAILUREStatus({
////
////            });
////            assert.isObject(tpollPublishItemFailureStatus, 'TweetPollPublishItemFAILUREStatus should be an object');
////        },
//
////        'TweetPollPublishItemStatus Widget': function () {
////            var tpollPublishItemStatus = new TweetPollPublishItemStatus({
////
////            });
////            assert.isObject(tpollPublishItemStatus, 'TweetPollPublishItemStatus should be an object');
////        },
//
//		'TweetPollPublishItemSUCCESStatus Widget': function () {
//			var tpollPublishItemSuccess = new TweetPollPublishItemSUCCESStatus({
//				metadata : {
//					tweet_url : '#'
//				}
//			});
//			assert.isObject(tpollPublishItemSuccess, 'TweetPollPublishItemSUCCESStatus should be an object');
//		}
//	});
//});
