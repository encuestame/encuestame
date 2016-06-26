define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/social/LinksPublished",
    "me/web/widget/social/LinksPublishedItem",
    "me/web/widget/social/SocialAccountDetail",
    "me/web/widget/social/SocialAccountPicker",
    "me/web/widget/social/SocialAccountRow",
    "me/web/widget/social/SocialAccounts",
    "me/web/widget/social/SocialButton",
    "me/web/widget/social/SocialPickerAccount",
    "me/web/widget/social/SocialAccountsSupport"
], function(
    registerSuite,
    assert,
    LinksPublished,
    LinksPublishedItem,
    SocialAccountDetail,
    SocialAccountPicker,
    SocialAccountRow,
    SocialAccounts,
    SocialButton,
    SocialPickerAccount,
    SocialAccountsSupport ) {
    registerSuite({
        name: "Social Widgets",

        "default data": function() {
            var linksPublished = new LinksPublished({

            });
        },

        "LinksPublishedItem Widget": function() {

            var linksPublishItem = new LinksPublishedItem({
                social:  "TWITTER",
                date: " Two days ago ",
                text: "Tweet ",
                link: " twitter.com"

            });
            assert.isObject( linksPublishItem, "LinksPublishedItem should be an object" );
        },

        "SocialAccountDetail Widget": function() {
            var socialAccountDetail = new SocialAccountDetail({
                socialProvider: "TWITTER"

            });
            assert.isObject( socialAccountDetail, "SocialAccountDetail should be an object" );
        },

        "SocialAccountPicker Widget": function() {
            var socialAccountPicker = new SocialAccountPicker({

            });
            assert.isObject( socialAccountPicker, "SocialAccountPicker should be an object" );
        },

        "SocialAccountRow Widget": function() {
            var socialAccountRow = new SocialAccountRow({
                account: {
                    id: "",
                    tweetpoll_stats: "10",
                    poll_stats: "3",
                    survey_stats: "5",
                    default_selected: "true",
                    profile_thumbnail_picture: config.contextPath + "/resources/images/social/facebook/enme_icon_facebook.png",
                    account: "user",
                    email: "dadad@gmail.com",
                    real_name: "real",
                    url: "google.es"

                }

            });
            assert.isObject( socialAccountRow, "SocialAccountRow should be an object" );
        },

        "SocialAccounts Widget": function() {
            var socialAccounts = new SocialAccounts({

            });
            assert.isObject( socialAccounts, "SocialAccounts should be an object" );
        },

        "SocialButton Widget": function() {
            var socialButton = new SocialButton({
                label: "label"

            });
            assert.isObject( socialButton, "SocialButton should be an object" );
        },

        "SocialPickerAccount Widget": function() {
            var socialPickerAcc = new SocialPickerAccount({
                account: {
                    id: "",
                    tweetpoll_stats: "10",
                    poll_stats: "3",
                    type_account: "TWITTER",
                    survey_stats: "5",
                    default_selected: "true",
                    picture_url: config.contextPath + "/resources/images/social/facebook/enme_icon_facebook.png",
                    profile_thumbnail_picture: config.contextPath + "/resources/images/social/facebook/enme_icon_facebook.png",
                    account: "user",
                    email: "dadad@gmail.com",
                    real_name: "real",
                    url: "google.es"
                },
                parentPicker: ""

            });
            assert.isObject( socialPickerAcc, "SocialPickerAccount should be an object" );
        },

        "SocialAccountsSupport Widget": function() {
            var socialAccountSupport = new SocialAccountsSupport({

            });
            assert.isObject( socialAccountSupport, "SocialAccountsSupport should be an object" );
        }
    });
});
