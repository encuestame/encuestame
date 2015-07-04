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

define([
    'intern!object',
    'intern/chai!assert',
    'dojo/dom-construct',
    'dojo/on',
    'dojo/request/registry',
    'dojo/when',
    '../../Helper',
    '../../support/requestMocker',
    'me/web/widget/tweetpoll/TweetPollList',
    'me/web/widget/tweetpoll/TweetPoll',
    'me/web/widget/tweetpoll/TweetPollCore',
    'me/web/widget/tweetpoll/TweetPollListDetail',
    'me/web/widget/tweetpoll/TweetPollListItem',
    'me/web/widget/tweetpoll/TweetPollPreview',
    'me/web/widget/tweetpoll/AnswerItem',
    'me/web/widget/tweetpoll/Answers',
    'me/web/widget/tweetpoll/detail/TweetPollAnswer',
    'me/web/widget/tweetpoll/detail/TweetPollChartDetail',
    'me/web/widget/tweetpoll/detail/TweetPollInfoDetail',
    'me/web/widget/tweetpoll/HashTags',
    'me/web/widget/tweetpoll/HashTagsItem',
    'me/web/widget/tweetpoll/TweetPollPublishInfo',
    'me/web/widget/tweetpoll/TweetPollPublishItemAbstractStatus',
    'me/web/widget/tweetpoll/TweetPollPublishItemFAILUREStatus',
    'me/web/widget/tweetpoll/TweetPollPublishItemStatus',
    'me/web/widget/tweetpoll/TweetPollPublishItemSUCCESStatus'
], function (
    registerSuite,
    assert,
    domConstruct,
    on,
    registry,
    when,
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
    TweetPollPublishItemSUCCESStatus) {

    'use strict';

    var tp, tp2;
    var handle, handleAddAnswer, handleSocial;

    registerSuite({
        name: 'TweetPoll Widgets',

        setup: function () {
            Helper.init();
            tp = Helper.createElement('mainWrapper');
            tp2 = Helper.createElement('previewWrapperFixed');
            Helper.addCss(5, "../../tests/resources/resources/css/dev/tweetpoll.css");
        },

        before : function() {
            //Helper.createFakeServer('../../tests/resources' + Helper.url('encuestame.service.list.publishTweetPoll'), {hola:1});
            handleSocial = registry.register(require.toUrl('../../tests/resources' + Helper.url('encuestame.service.list.allSocialAccount')), function () {
                return when(Helper.buildSuccessResponse({
                    "items": [
                        {
                            "account": "facebookUserNickname",
                            "type": null,
                            "email": null,
                            "id": 2,
                            "type_account": "FACEBOOK",
                            "description": null,
                            "default_selected": true,
                            "date_created": 1400524376311,
                            "picture_url": "http:\/\/localhost:3000\/tests\/resources\/facebookUserNickname\/picture",
                            "profile_picture_url": "http:\/\/localhost:3000\/tests\/resources\/facebookUserNickname\/picture",
                            "profile_thumbnail_picture": "http:\/\/localhost:3000\/tests\/resources\/facebookUserNickname\/picture",
                            "real_name": "My TestUser",
                            "social_username": "facebookUserNickname",
                            "url": "",
                            "tweetpoll_stats": 0,
                            "poll_stats": 0,
                            "survey_stats": 0
                        },
                        {
                            "account": "twitterUserNickname",
                            "type": null,
                            "email": null,
                            "id": 1,
                            "type_account": "TWITTER",
                            "description": null,
                            "default_selected": true,
                            "date_created": 1400524376311,
                            "picture_url": "http:\/\/localhost:3000\/tests\/resources\/facebookUserNickname\/picture",
                            "profile_picture_url": "http:\/\/localhost:3000\/tests\/resources\/facebookUserNickname\/picture",
                            "profile_thumbnail_picture": "http:\/\/localhost:3000\/tests\/resources\/facebookUserNickname\/picture",
                            "real_name": "My TestUser",
                            "social_username": "twitterUserNickname",
                            "url": "",
                            "tweetpoll_stats": 0,
                            "poll_stats": 0,
                            "survey_stats": 0
                        }
                    ],
                    "label": "socialAccounts",
                    "identifier": "id"
                }));
            });
            // handler publish
            handle = registry.register(require.toUrl('../../tests/resources' + Helper.url('encuestame.service.list.publishTweetPoll')), function () {
                return when(Helper.buildSuccessResponse({
                    "socialPublish":[
                        {
                            "id":764,
                            "tweet_id":"510140987986161664",
                            "textTweeted":"dsadsa dsadsa http://tinyurl.com/ldcx54j dsa http://tinyurl.com/ofsa2fc #dsa #dsada",
                            "date_published":"2014-09-11",
                            "social_account":"testEncuesta",
                            "status_tweet":"SUCCESS",
                            "status_description_tweet":null,
                            "source_tweet":"TWITTER",
                            "tweet_url":"https://twitter.com/#!/testEncuesta/status/510140987986161664",
                            "social_account_id":1,
                            "type_item":"TWEETPOLL"
                        }
                    ]
                }));
            });
            // handler social
            handleAddAnswer = registry.register(require.toUrl('../../tests/resources' + Helper.url('encuestame.service.list.addAnswer')), function () {
                console.log("add answer handler");
                return when(Helper.buildSuccessResponse({
                    "newAnswer":{
                        "id": 161,
                        "tweet_poll_id": 80,
                        "answer":{
                            "answers": "dsa",
                            "url":null,
                            "color": "#71A051",
                            "answer_id": 265,
                            "short_url":"tinyurl",
                            "qid": 106,
                            "provider": null
                        },
                        "short_url":"http://tinyurl.com/nc43vv2",
                        "relative_url":"http://www.test.com",
                        "results":null
                    }
                }));
            });
        },

        after: function () {
            // Step 1.2
            if (handle) {
                handleAddAnswer.remove();
                handle.remove();
                handleSocial.remove();
            }
        },

        'TweetPoll Widget': function () {
            var tweetPoll = new TweetPoll({
                _scroll : false
            });
            Helper.place(tweetPoll.domNode, 'mainWrapper');
            tweetPoll.cancelUnLoadSupport();
            //fake tp id
            var aW = tweetPoll.answerWidget;
            var hW = tweetPoll.hashTagWidget;
            var tweetPollId = 12345;
            tweetPoll.tweetPollId = tweetPollId;
            aW.tweetPollId = tweetPollId;
            hW.tweetPollId = tweetPollId;
            // set the question name

            tweetPoll.questionWidget.setValue('test');
            // set answers
            aW._suggest.setValue('answer1');
            aW._suggest.setValue('answer2');
            // simulating click
            var event = Helper.createEvent();
            //create an answer 1
            aW.buttonWidget.onClick(event);
            //create an answer 2
            aW.buttonWidget.onClick(event);
            //hashtag
            hW.hashTagWidget._suggest.setValue("hastahg1");
            hW.hashTagWidget._suggest.onKeyUp(event);
            // emit a click event in the hashtag widget
            on.emit(hW.hashTagWidget.buttonWidget.domNode, "click", {
                bubbles: true,
                cancelable: true
            });

            //deselect all social networks
            tweetPoll.socialWidget._selectAllItems(event);
            //select all social networks
            tweetPoll.socialWidget._selectAllItems(event);

            hW.hashTagWidget.buttonWidget.onClick(event);
            hW.hashTagWidget._suggest.setValue('');
            // remove all
            //Helper.removeElement(tweetPoll);
            //Helper.removeElement(tp);
            //Helper.removeElement(tp2);
            tweetPoll.liveResultsWidget.set('checked', false);
            tweetPoll.captchaWidget.set('checked', false);
            tweetPoll.limitVotesWidget.set('checked', false);
            tweetPoll.ipWidget.set('checked', false);
            tweetPoll.resumeWidget.set('checked', false);
            tweetPoll.dashboardWidget.set('checked', false);
            tweetPoll.scheduleWidget.set('checked', false);
            tweetPoll.liveResultsWidget.set('checked', true);
            tweetPoll.captchaWidget.set('checked', true);
            tweetPoll.limitVotesWidget.set('checked', true);
            tweetPoll.ipWidget.set('checked', true);
            tweetPoll.resumeWidget.set('checked', true);
            tweetPoll.dashboardWidget.set('checked', true);
            tweetPoll.scheduleWidget.set('checked', true);
            var that = this;
            var dfd = that.async(7000);
            setTimeout(function(){
                tweetPoll._checkPublish();
                tweetPoll._publishTweet().then(dfd.callback(function(){
                    console.log("callback publish tweetpoll");
                }));
            }, 6000);

        },

        teardown: function () {

        }
    });
});
