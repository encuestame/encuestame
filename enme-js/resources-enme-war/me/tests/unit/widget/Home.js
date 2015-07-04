define([
    'intern!object',
    'intern/chai!assert',
    'me/web/widget/home/votes/ItemVote',
    'me/web/widget/pictures/AccountPicture',
    'me/web/widget/pictures/Avatar',
    'me/web/widget/pictures/Icon',
    'me/web/widget/profile/Profile',
    'me/web/widget/profile/ProfileMenu',
    'me/web/widget/profile/UploadProfilePicture',
    'me/web/widget/hashtags/Cloud'
], function (
    registerSuite,
    assert,
    ItemVote,
    AccountPicture,
    Avatar,
    Icon,    
    Profile,
    ProfileMenu,
    UploadProfilePicture,
    Cloud) {
    registerSuite({
        name: 'Home Widgets',
        
        'default data': function () {
            var cloud = new Cloud({

            });
        } ,

        'ItemVote Widget': function () {
            var itemVote = new ItemVote({

            });
            assert.isObject(itemVote, 'ItemVote should be an object');
        },

        'AccountPicture Widget': function () {
            var accountPicture = new AccountPicture({

            });
            assert.isObject(accountPicture, 'AccountPicture should be an object');
        },

        'Avatar Widget': function () {
            var avatar = new Avatar({

            });
            assert.isObject(avatar, 'Avatar should be an object');
        },

        'Icon Widget': function () {
            var icon = new Icon({

            });
            assert.isObject(icon, 'Icon should be an object');
        },

        'Profile Widget': function () {
            var profile = new Profile({
	            username : "test",
	            email : "test@test.com",
	            complete_name : "test complete name",
	            language : "en",

	            contextDefaultPath : '../../tests/resources'
            });
            assert.isObject(profile, 'Profile should be an object');
        },

        'ProfileMenu Widget': function () {
            var profileMenu = new ProfileMenu({
	            completeName : "test",
	            username : "test",
	            contextDefaultPath : '../../tests/resources'
            });
            assert.isObject(profileMenu, 'ProfileMenu should be an object');
        },

        'UploadProfilePicture Widget': function () {
            var uploadProfile = new UploadProfilePicture({

            });
            assert.isObject(uploadProfile, 'UploadProfilePicture should be an object');
        }
    });
});