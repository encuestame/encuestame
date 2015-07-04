define([
    'intern!object',
    'intern/chai!assert',
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
    registerSuite({
        name: 'Survey Widgets',
        
        'default data': function () {
            var tweetPollList = new TweetPollList({

            });
        },

        'TweetPoll Widget': function () {
            var tpoll = new TweetPoll({

            });
            assert.isObject(tpoll, 'TweetPoll should be an object');
        },

        'TweetPollCore Widget': function () {
            var tpollCore = new TweetPollCore({

            });
            assert.isObject(tpollCore, 'TweetPollCore should be an object');
        },

        'TweetPollListDetail Widget': function () {
            var tpollListDetail = new TweetPollListDetail({

            });
            assert.isObject(tpollListDetail, 'TweetPollListDetail should be an object');
        },

        'TweetPollListItem Widget': function () {
            var tpollListItem = new TweetPollListItem({

            });
            assert.isObject(tpollListItem, 'TweetPollListItem should be an object');
        },

        'TweetPollPreview Widget': function () {
            var tpollPreview = new TweetPollPreview({

            });
            assert.isObject(tpollPreview, 'TweetPollPreview should be an object');
        },

        'AnswerItem Widget': function () {
            var answerItem = new AnswerItem({

            });
            assert.isObject(answerItem, 'AnswerItem should be an object');
        },

        'Answers Widget': function () {
            var answers = new Answers({

            });
            assert.isObject(answers, 'Answers should be an object');
        },

        'TweetPollAnswer Widget': function () {
            var tpollAnswer = new TweetPollAnswer({

            });
            assert.isObject(tpollAnswer, 'TweetPollAnswer should be an object');
        },

        'TweetPollChartDetail Widget': function () {
            var tpollChartDetail = new TweetPollChartDetail({

            });
            assert.isObject(tpollChartDetail, 'TweetPollChartDetail should be an object');
        },

        'TweetPollInfoDetail Widget': function () {
            var tpollInfoDetail = new TweetPollInfoDetail({

            });
            assert.isObject(tpollInfoDetail, 'TweetPollInfoDetail should be an object');
        },

        'HashTags Widget': function () {
            var hashtags = new HashTags({

            });
            assert.isObject(hashtags, 'HashTags should be an object');
        },

        'HashTagsItem Widget': function () {
            var hashtagsItem = new HashTagsItem({

            });
            assert.isObject(hashtagsItem, 'HashTagsItem should be an object');
        },

        'TweetPollPublishInfo Widget': function () {
            var tpollPublishInfo = new TweetPollPublishInfo({

            });
            assert.isObject(tpollPublishInfo, 'TweetPollPublishInfo should be an object');
        },

        'TweetPollPublishItemFAILUREStatus Widget': function () {
            var tpollPublishItemFailure = new TweetPollPublishItemFAILUREStatus({

            });
            assert.isObject(tpollPublishItemFailure, 'TweetPollPublishItemFAILUREStatus should be an object');
        },

        'TweetPollPublishItemStatus Widget': function () {
            var tpollPublishItemStatus = new TweetPollPublishItemStatus({

            });
            assert.isObject(tpollPublishItemStatus, 'TweetPollPublishItemStatus should be an object');
        },

        'TweetPollPublishItemSUCCESStatus Widget': function () {
            var tpollPublishItemSuccess = new TweetPollPublishItemSUCCESStatus({

            });
            assert.isObject(tpollPublishItemSuccess, 'TweetPollPublishItemSUCCESStatus should be an object');
        }
    });
});