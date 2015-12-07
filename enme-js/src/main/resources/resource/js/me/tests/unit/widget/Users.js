define( [
    "intern!object",
    "intern/chai!assert",
	"../Helper",
    "me/web/widget/admon/user/UserEdit",
    "me/web/widget/admon/user/UserGroup",
    "me/web/widget/admon/user/UserPermissions",
    "me/web/widget/admon/user/Users",
    "me/web/widget/admon/user/UserTableRow"
], function(
    registerSuite,
    assert,
    Helper,
    UserEdit,
    UserGroup,
    UserPermissions,
    Users,
    UserTableRow ) {
    registerSuite({
        name: "User Widgets",

	    setup: function() {
		    Helper.init();
		    Helper.addCss( 1, "../../tests/resources/resources/css/dev/admon.css");
	    },

        "default data": function() {
            var users = new Users({
                i18n: {
                    admon_members_title: "title",
                    admon_members_subtitle: "subtitle",
                    admon_create_user: "create user",
                    admon_username: "admon_username",
                    admon_group: "admon_group",
                    admon_user_from: "admon_user_from",
                    admon_status: "admon_status",
                    admon_tweetpoll_count: "admon_tweetpoll_count",
                    admon_poll_count: "admon_poll_count",
                    admon_survey_count: "admon_survey_count",
                    admon_last_logged: "admon_last_logged",
                    followers: "followers",
                    table_first: "table_first",
                    table_previous: "table_previous",
                    table_next: "table_next",
                    table_last: "table_last",
                    admon_users_new_title: "admon_users_new_title",
                    admon_users_new_option1_title: "admon_users_new_option1_title",
                    admon_users_new_user_subtitle: "admon_users_new_user_subtitle",
                    placeholder_username: "placeholder_username",
                    placeholder_email: "placeholder_email",
                    admon_users_new_button: "admon_users_new_button",
                    admon_users_new_invite_title: "admon_users_new_invite_title",
                    admon_users_invite_subtitle: "admon_users_invite_subtitle",
                    admon_users_invite_button: "admon_users_invite_button",
                    button_close: "button_close"
                }
            });
        },

        "UserEdit Widget": function() {
            var usrEdit = new UserEdit({
					i18n: {
						button_close: "button_close"
					},
		            user: {
			            listPermission: [
				            { permission: "USER_ADMON" },
				            { permission: "USER_ADMON" }
			            ],
			            username: "test",
			            name: "name"
		            }
            });
            assert.isObject( usrEdit, "UserEdit should be an object" );
        },

        "UserGroup Widget": function() {
            var usrGroup = new UserGroup({

            });
            assert.isObject( usrGroup, "UserGroup should be an object" );
        },

        "UserPermissions Widget": function() {
            var usrPermission = new UserPermissions({
	            user: {
		            listPermission: [
			            { permission: "USER_ADMON" },
			            { permission: "USER_ADMON" }
		            ],
		            username: "test"
	            }
            });
            assert.isObject( usrPermission, "UserPermissions should be an object" );
        },

        "UserTableRow Widget": function() {
	        var a = Helper.createElement( "mainWrapper" );
	        var b = Helper.createElement( "previewWrapperFixed" );
            var usrTableRow = new UserTableRow({
	            data: {
		            username: "test",
		            user: {
			            listPermission: [
				            { permission: "USER_ADMON" },
				            { permission: "USER_ADMON" }
			            ],
			            username: "test2",
			            relateTimeEnjoy: "",
			            status: true,
			            poll: 1,
			            tweetPoll:1,
			            survey: 1,
			            lastTimeLogged: null,
			            followers: 100
		            }
	            }
            });
	        Helper.place( usrTableRow.domNode, "mainWrapper" );
	        Helper.removeElement( usrTableRow.domNode );
	        Helper.removeElement( a );
	        Helper.removeElement( b );
            assert.isObject( usrTableRow, "UserTableRow should be an object" );
        }
    });
});
