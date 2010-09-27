if(!Notifications) var Notifications = {};

Notifications.EnMe = Class.create( {

    // constructor
    initialize : function(nodeName) {
        this.nodeName = nodeName;
        // new Ajax.PeriodicalUpdater(nodeName, '/notifications.json', {
        // method: 'get', frequency: 3, decay: 2
        // });
        new PeriodicalExecuter(this.loadNotifications.bind(this), 20);
    },

    // load notifications
    loadNotifications : function() {
        this.cleanNodeName();
        var url = '/encuestame/notifications.json';
        var json =
        new Ajax.Request(url, {
            method : 'get',
            parameters : {
                limit : 7
            },
            onSuccess : function(transport) {
                var tran = transport;
                if(transport.status == 200){
                    console.debug("success", tran.responseJSON);
                    if(tran.responseText.isJSON()){
                        json = tran.responseJSON;
                        if(json.error.message == undefined){
                            json.success.notifications.each(function(item) {
                                this.createNotification(item);
                            }.bind(this));
                        } else {
                            console.debug("error", json.error.message)
                            this.createNetworkError(json.error.message, json.error.message);
                        }
                        this.addEvents();
                     }
                } else {
                    if(transport.status == 0){
                         this.createNetworkError("You are lost Internet Connection", "Connection time out");
                    } else {
                        console.error("other error", transport.status);
                    }
                }
            }.bind(this),

            onComplete : function(complete) {
                //Remove error messages or something.
            }.bind(this),

            onLoading : function(loading) {
              console.debug("loading", loading);
            }.bind(this),

            onFailure : function(resp) {
                console.debug("Oops, there's been an error.", resp);
                this.createNetworkError("Error", resp);
            }.bind(this)
        });
    },

    /*
     * Remove Notification
     */
    removeNotification : function(notificationId){
        var url = '/encuestame/remove-notification.json';
        var json =
        new Ajax.Request(url, {
            method : 'get',
            parameters : {
            notificationId : notificationId
            },
            onSuccess : function(transport) {
                var tran = transport;
                if(transport.status == 200){
                   console.debug("ok");
                } else {
                    if(transport.status == 0){
                         this.createNetworkError("You are lost Internet Connection", "Connection time out");
                    } else {
                        console.error("other error", transport.status);
                    }
                }
            }.bind(this),

            onComplete : function(complete) {
                //Remove error messages or something.
            }.bind(this),

            onLoading : function(loading) {
              console.debug("loading", loading);
            }.bind(this),

            onFailure : function(resp) {
                console.debug("Oops, there's been an error.", resp);
                this.createNetworkError("Error", resp);
            }.bind(this)
        });
    },

    cleanNodeName : function(){
         console.debug("cleanNodeName");
         var name = $(this.nodeName);
         if(name != null){
            name.innerHTML = '';
         }
    },

    addEvents : function(){
         $$(".notificationItem").each(function(item) {
             Event.observe(item, 'click', function(event){
                 console.debug("click", item.getAttribute("notificationId"));
                 this.removeNotification(item.getAttribute("notificationId"));
                 this.loadNotifications();
             }.bind(this));
          }.bind(this));
    },

    /*
     * Create Network Error.
     */
    createNetworkError : function(error, additional){
        var item = {
            type : "",
            description : error,
            additionalDescription: additional,
            icon : "netWorkErrorImage"
        };
        this.createNotification(item);
    },

    /*
     * Create Notification.
     */
    createNotification : function(item){
        var livePanel = $(this.nodeName);
        var div = new Element('div', { 'class': 'notificationItem', "notificationId" : item.id });
        var divLeft = new Element('div', { 'class': 'left' });
        var span = new Element('span', { 'class': 'image '+item.icon });
        divLeft.update(span);
        div.appendChild(divLeft)
        var divRight = new Element('div', { 'class': 'right' });
        var description = new Element('div', { 'class': 'title' }).update(item.description);
        var question = new Element('div', { 'class': 'question' });
        if(item.type == "TWEETPOLL_PUBLISHED"){
            var a = new Element('a', { 'href': item.additionalDescription, "target": "_blank" }).update(item.additionalDescription);
            question.update(a);
        } else {
            question.update(item.additionalDescription);
        }
        divRight.insert(description)
        divRight.insert(question)
        div.appendChild(divRight)
        livePanel.appendChild(div);
    }
});
