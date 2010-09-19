window.onload=function(){
    var live = $("livePanel");
        if(live != null){

            var notifications = new Notifications.EnMe('livePanel');
            notifications.loadNotifications();

            /**$$(".notificationItem").each(function(item) {
           Event.observe(item, 'mousemove', function(event){
               //alert(event);
              // console.debug(this);
               //console.debug(event);
           });
        }.bind(this));**/

        $$(".notificationItem").each(function(item) {
            Event.observe(item, 'click', function(event){
                console.debug("click");
            });
         }.bind(this));

    }
}

function callMyClass(event){
    var not = new Notification("Jota");
    not.speak();
}

function removeChildrens(){
     $$(".notificationItem").each(function(item) {
         console.debug(item);
     });
}

