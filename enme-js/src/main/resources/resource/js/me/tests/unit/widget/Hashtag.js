define([
    'intern!object',
    'intern/chai!assert',
    '../Helper',
    'me/web/widget/hashtags/HashTagGraph',
    'me/web/widget/stream/HashTagInfo',
    'me/web/widget/hashtags/HashTagGraphStatsButton',
    'me/web/widget/hashtags/HashTagGraphStatsUsageHandler',
    'me/web/widget/hashtags/Cloud'
], function (
    registerSuite,
    assert,
    Helper,
    HashTagGraph,
    HashTagInfo,
    HashTagGraphStatsButton,
    HashTagGraphStatsUsageHandler,
    Cloud) {

    'use strict';

    var tp, tp2;

    registerSuite({
        name: 'Hashtag Widget',

        setup: function () {
            Helper.init();
            tp = Helper.createElement('mainWrapper');
            tp2 = Helper.createElement('previewWrapperFixed');
            Helper.addCss(1, "../../tests/resources/resources/css/dev/tweetpoll.css");
        },

        'Cloud Widget': function () {
            var cloud = new Cloud({

            });
            assert.isObject(cloud, 'cloud should be an object');
        },

        'HashtagGraph Widget': function () {
            var graphCloud = new HashTagGraph({

            });
            assert.isObject(graphCloud, 'HashTag graph should be an object');
        },


        'HashtagGraphButton Widget': function () {
            var hashTagGraphButton = new HashTagGraphStatsButton({

            });
            assert.isObject(hashTagGraphButton, 'HashTag graph button should be an object');
        },


        'HashtagGraphStatsUsage Widget': function () {
            var hashTagGraphUsage = new HashTagGraphStatsUsageHandler({

            });
            assert.isObject(hashTagGraphUsage, 'HashTag graph usage should be an object');
        },



        'HashTagInfo Widget': function () {
            var h1 = new HashTagInfo({
                url : '',
                hashTagName : 'hola1'
            });
            Helper.place(h1.domNode, 'mainWrapper');
            var h2 = new HashTagInfo({
                url : '',
                hashTagName : 'hola2'
            });
            Helper.place(h2.domNode, 'mainWrapper');
	        var h3 = new HashTagInfo({
		        autoCreateUrl : true,
		        hashTagName : 'hola2'
	        });
	        Helper.place(h2.domNode, 'mainWrapper');
        },
        teardown: function () {
            Helper.removeElement(tp);
            Helper.removeElement(tp2);
            //Helper.removeCss(1);
        }
    });
});