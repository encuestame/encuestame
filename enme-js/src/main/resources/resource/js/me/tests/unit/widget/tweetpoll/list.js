/**
 * Copyright 2014 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

define( [
	"intern!object",
	"intern/chai!assert",
	"dojo/dom-construct",
	"dojo/on",
	"../../Helper",
	"../../support/requestMocker",
	"me/web/widget/tweetpoll/TweetPollList",
	"me/web/widget/tweetpoll/TweetPoll",
	"me/web/widget/tweetpoll/TweetPollCore",
	"me/web/widget/tweetpoll/TweetPollListDetail",
	"me/web/widget/tweetpoll/TweetPollListItem",
	"me/web/widget/tweetpoll/TweetPollPreview",
	"me/web/widget/tweetpoll/AnswerItem",
	"me/web/widget/tweetpoll/Answers",
	"me/web/widget/tweetpoll/detail/TweetPollAnswer",
	"me/web/widget/tweetpoll/detail/TweetPollChartDetail",
	"me/web/widget/tweetpoll/detail/TweetPollInfoDetail",
	"me/web/widget/tweetpoll/HashTags",
	"me/web/widget/tweetpoll/HashTagsItem",
	"me/web/widget/tweetpoll/TweetPollPublishInfo",
	"me/web/widget/tweetpoll/TweetPollPublishItemAbstractStatus",
	"me/web/widget/tweetpoll/TweetPollPublishItemFAILUREStatus",
	"me/web/widget/tweetpoll/TweetPollPublishItemStatus",
	"me/web/widget/tweetpoll/TweetPollPublishItemSUCCESStatus"
], function(
	registerSuite,
	assert,
	domConstruct,
	on,
	Helper,
	requestMocker,
	TweetPollList,
	TweetPoll,
	TweetPollCore,
	TweetPollListDetail,
	TweetPollListItem,
	TweetPollPreview,
	AnswerItem,
	Answers,
	TweetPollAnswer,
	TweetPollChartDetail,
	TweetPollInfoDetail,
	HashTags,
	HashTagsItem,
	TweetPollPublishInfo,
	TweetPollPublishItemAbstractStatus,
	TweetPollPublishItemFAILUREStatus,
	TweetPollPublishItemStatus,
	TweetPollPublishItemSUCCESStatus ) {

	"use strict";

	var tp, tp2;

	registerSuite({
		name: "TweetPoll List Widgets",
		setup: function() {
			Helper.init();
			tp = Helper.createElement( "mainWrapper" );
			tp2 = Helper.createElement( "previewWrapperFixed" );
			Helper.addCss( 5, "../../tests/resources/resources/css/dev/tweetpoll.css");
		},

		"Instanciate TweetPoll List Detail": function() {

			//Helper.clean();
			var tp1 = Helper.createElement( "mainWrapper" );
			var tweetPollList = new TweetPollListDetail({
				data: {
					folder_id: 1
				}
			});
			Helper.place( tweetPollList.domNode, "mainWrapper" );
			Helper.removeElement( tweetPollList.domNode );
			Helper.removeElement( tp1 );
		},

		teardown: function() {
			Helper.removeElement( tp );
			Helper.removeElement( tp2 );

			//Helper.removeCss(1);
		},

		"TweetPollList Widget": function() {
			var tpollList = new TweetPollList({

			});
			assert.isObject( tpollList, "TweetPollList should be an object" );
		},

		"TweetPollCore Widget": function() {
			var tpollCore = new TweetPollCore({

			});
			assert.isObject( tpollCore, "TweetPollCore should be an object" );
		},

		"TweetPollListItem Widget": function() {
			var tpollListItem = new TweetPollListItem({
				data: {
					favourites: true,
					id: 12345,
					question: {
						question_name: "test"
					}
				}
			});
			assert.isObject( tpollListItem, "TweetPollListItem should be an object" );
		},

//        'TweetPollPreview Widget': function () {
//            var tpollPreview = new TweetPollPreview({
//
//            });
//            assert.isObject(tpollPreview, 'TweetPollPreview should be an object');
//        },

		"TweetPollInfoDetail Widget": function() {
			var tpollInfoDetail = new TweetPollInfoDetail({

			});
			assert.isObject( tpollInfoDetail, "TweetPollInfoDetail should be an object" );
		},

//        'HashTags Widget': function () {
//            var hashtags = new HashTags({
//
//            });
//            assert.isObject(hashtags, 'HashTags should be an object');
//        },

		"HashTagsItem Widget": function() {
			var hashTagsItem = new HashTagsItem({

			});
			assert.isObject( hashTagsItem, "HashTagsItem should be an object" );
		}
	});
});
