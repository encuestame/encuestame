define([
    'intern!object',
    'intern/chai!assert',
    'me/web/widget/stream/FrontEnd',
    'me/web/widget/stream/FrontEndItem',
    'me/web/widget/stream/HashTagInfo'
], function (
    registerSuite,
    assert,
    FrontEnd,
    FrontEndItem,
    HashTagInfo) {
    registerSuite({
        name: 'Stream Widgets',
        
        'default data': function () {
            var hashTagInfo = new HashTagInfo({

            });            
        },

        'FrontEnd Widget': function () {
            var frontEnd = new FrontEnd({

            });
            assert.isObject(frontEnd, 'FrontEnd should be an object');
        },

        'FrontEndItem Widget': function () {
            var frontEndItem = new FrontEndItem({
                item : {
                    total_votes : 10,
                    hits : 3,
                    item_type : "TWEETPOLL",
                    id : 1,
                    vote_up : 1,
                    relative_time : "Two minutes ago",
                    total_comments : 10
                }

            });
            assert.isObject(frontEndItem, 'FrontEndItem should be an object');
        }
    });
});
