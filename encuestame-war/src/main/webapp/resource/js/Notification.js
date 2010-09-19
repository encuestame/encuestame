if(!Notifications) var Notifications = {};

Notifications.EnMe = Class.create( {

    // constructor
    initialize : function(nodeName) {
        this.nodeName = nodeName;
        console.debug("nodeName.", nodeName);
        console.debug("this.nodeName.", this.nodeName);
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
                limit : 9
            },
            onSuccess : function(transport) {
                var tran = transport;
                console.debug("success", tran);
                if(tran.responseText.isJSON()){
                    json = tran.responseText.evalJSON();
                    if(json.error.error == undefined){
                        json.success.notifications.each(function(item) {
                            console.debug("responsTExt", item)
                            this.createNotification(item);
                        }.bind(this));
                    } else {
                        console.debug("error", json.error)
                    }
                    this.addEvents();
                 } else {
                    console.error("not json");
                 }
            }.bind(this),

            onComplete : function(complete) {
                console.debug("complete", complete);
            }.bind(this),

            onLoading : function(loading) {
              console.debug("loading", loading);
            }.bind(this),

            onFailure : function(resp) {
                console.debug("Oops, there's been an error.", resp);
                this.createNetworkError(resp);
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
                 console.debug("click");
             });
          }.bind(this));
    },

    /*
     * Create Network Error.
     */
    createNetworkError : function(error){
        var item = {
            type : "",
            description : "",
            additionalDescription: error,
            icon : "netWorkErrorImage"
        };
        this.createNotification(item);
    },

    /*
     * Create Notification.
     */
    createNotification : function(item){
        var livePanel = $(this.nodeName);
        var div = new Element('div', { 'class': 'notificationItem' });
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
