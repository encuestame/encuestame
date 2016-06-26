define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/notifications/Notification",
    "me/web/widget/notifications/NotificationItem",
    "me/web/widget/notifications/NotificationList",
    "me/web/widget/notifications/NotificationListItem"
], function(
    registerSuite,
    assert,
    Notification,
    NotificationItem,
    NotificationList,
    NotificationListItem ) {
    registerSuite({
        name: "Notification Widgets",

        "default data": function() {
            var notificationList = new NotificationList({

            });
        },

        "Notification Widget": function() {
            var notification = new Notification({

            });
            assert.isObject( notification, "Notification should be an object" );
        },

        "NotificationItem Widget": function() {
            var notificationItem = new NotificationItem({
				item: {
					icon: "",
					description:  "description",
					date: "1997-12-10",
					additionalDescription: "additionalDescription"
				}
            });
            assert.isObject( notificationItem, "NotificationItem should be an object" );
        },

        "NotificationListItem Widget": function() {
            var notificationListItem = new NotificationListItem({
	            item: {
		            date: "1997-12-10",
		            description: "test"
	            }
            });
            assert.isObject( notificationListItem, "NotificationListItem should be an object" );
        }
    });
});
