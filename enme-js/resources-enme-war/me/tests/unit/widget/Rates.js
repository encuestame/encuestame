define([
    'intern!object',
    'intern/chai!assert',
    'me/web/widget/rated/Comment',
    'me/web/widget/rated/Comments',
    'me/web/widget/rated/HashTags',
    'me/web/widget/rated/LikeRate',
    'me/web/widget/rated/RatedOperations',
    'me/web/widget/rated/RatedProfile',
    'me/web/widget/stats/GenericStats',
    'me/web/widget/stats/RatePosition',
    'me/web/widget/stats/TopProfiles',
    'me/web/widget/rated/UsersProfile'
], function (
    registerSuite,
    assert,
    Comment,
    Comments,
    HashTags,
    LikeRate,
    RatedOperations,
    RatedProfile,
    GenericStats,
    RatePosition,
    TopProfiles,    
    UsersProfile) {
    registerSuite({
        name: 'Rate Widgets',
        
        'default data': function () {
            var comments = new Comments({

            });            
        },

        'Comments Widget': function () {
            var comments = new Comments({

            });
            assert.isObject(comments, 'Comments should be an object');
        },

        'HashTags Widget': function () {
            var hashtags = new HashTags({

            });
            assert.isObject(hashtags, 'HashTags should be an object');
        },

        'LikeRate Widget': function () {
            var likeRate = new LikeRate({

            });
            assert.isObject(likeRate, 'LikeRate should be an object');
        },

        'RatedOperations Widget': function () {
            var ratedOperations = new RatedOperations({

            });
            assert.isObject(ratedOperations, 'RatedOperations should be an object');
        },

        'RatedProfile Widget': function () {
            var ratedProfile = new RatedProfile({

            });
            assert.isObject(ratedProfile, 'RatedProfile should be an object');
        },

        'GenericStats Widget': function () {
            var genericStats = new GenericStats({

            });
            assert.isObject(genericStats, 'GenericStats should be an object');
        },

        'RatePosition Widget': function () {
            var ratePosition = new RatePosition({

            });
            assert.isObject(ratePosition, 'RatePosition should be an object');
        },

        'TopProfiles Widget': function () {
            var topProfiles = new TopProfiles({

            });
            assert.isObject(topProfiles, 'TopProfiles should be an object');
        },

        'UsersProfile Widget': function () {
            var usersProfile = new UsersProfile({
	            data : {
		            username : 'test'
	            }
            });
            assert.isObject(usersProfile, 'UsersProfile should be an object');
        }
    });
});