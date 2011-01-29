dojo.provide("encuestame.org.core.commons");

encuestame.service = {};
encuestame.service.timeout = 20000;
encuestame.contextDefault = "/encuestame";
/**
 * Json Get Call.
 */
encuestame.service.xhrGet = function(url, params, load, error){
    var defaultError = function(error, ioargs){
        console.debug("default error ", error);
    };
    if(error == null){
      error = defaultError;
      console.debug("default error");
    }
    if(load == null || url == null || params == null){
        console.error("error params required.")
    } else {
        dojo.xhrGet({
            url : url,
            handleAs : "json",
            failOk : true, //Indicates whether a request should be allowed to fail
            //(and therefore no console error message in the event of a failure)
            timeout : encuestame.service.timeout,
            content: params,
            load: load,
            preventCache: true,
            error: error,
            handle: function(error, ioargs) {
                var message = "";
                switch (ioargs.xhr.status) {
                case 200:
                    message = "Good request.";
                    break;
                case 404:
                    message = "The requested page was not found";
                    console.debug(message);
                case 500:
                    break;
                    message = "The server reported an error.";
                    console.debug(message);
                    break;
                case 407:
                    message = "You need to authenticate with a proxy.";
                    console.debug(message);
                    break;
                default:
                    console.debug("error", dijit.byId("errorConexionHandler"));
                    dijit.byId("errorConexionHandler").show();
                    message = "Unknown error.";
                    console.debug(message);
                }
              }
          });
    }
};

encuestame.filter = {};

encuestame.filter.response = function(load){

}

/**
 * Json Get Call.
 */
encuestame.service.xhrPost = function(url, form, load, error){
    var defaultError = function(error, ioargs){
        console.debug("default error ", error);
    };
    if(error == null){
      error = defaultError;
    }
    if(load == null || url == null || form == null){
        console.error("error params required.");
    } else {
        var xhrArgs = {
                url: url,
                form: form,
                timeout : encuestame.service.timeout,
                handleAs: "json",
                ailOk : true, //Indicates whether a request should be allowed to fail
                //(and therefore no console error message in the event of a failure)
                load: load,
                preventCache: true,
                error: error
            }
        var deferred = dojo.xhrPost(xhrArgs);
    }
};

encuestame.contextWidget = function(){
    var contextWidget2 = dijit.byId("contextWidget");
    //console.debug("Context Widget: ", contextWidget2)
    if(contextWidget2){
        //console.debug("Found Context Path");
        return contextWidget2.contextPath;
    } else {
        //console.debug("Not found, default context");
        return encuestame.contextDefault;
    }
};

encuestame.service.list = {};
encuestame.service.list.userList = encuestame.contextWidget()+"/api/admon/users.json";
encuestame.service.list.getNotifications = encuestame.contextWidget()+"/api/notifications.json";
encuestame.service.list.getStatusNotifications = encuestame.contextWidget()+"/api/status-notifications.json"
encuestame.service.list.changeStatusNotification = encuestame.contextWidget()+"/api/change-status-notifications.json"
encuestame.service.list.removeNotification = encuestame.contextWidget()+"/api/remove-notification.json"
encuestame.service.list.userInfo = encuestame.contextWidget()+"/api/admon/user-info.json";
encuestame.service.list.createUser = encuestame.contextWidget()+"/api/admon/create-user.json";
encuestame.service.list.upgradeProfile = encuestame.contextWidget()+"/api/user/profile/upgrade.json";

encuestame.service.list.listPermissions = encuestame.contextWidget()+"/api/admon/list-permissions.json";
encuestame.service.list.listUserPermissions = encuestame.contextWidget()+"/api/admon/list-user-permissions.json";
encuestame.service.list.addPermission = encuestame.contextWidget()+"/api/admon/add-permission.json";
encuestame.service.list.removePermission = encuestame.contextWidget()+"/api/admon/remove-permission.json";
encuestame.service.list.hashtags = encuestame.contextWidget()+"/api/common/hashtags.json";
encuestame.service.list.twitterAccount = encuestame.contextWidget()+"/api/common/twitter/valid-accounts.json";

//tweetpoll service
encuestame.service.list.publishTweetPoll = encuestame.contextWidget()+"/api/survey/tweetpoll/publish.json";
encuestame.service.list.listTweetPoll = encuestame.contextWidget()+"/api/survey/tweetpoll/search.json";
encuestame.service.list.changeTweetPollStatus = encuestame.contextWidget()+"/api/survey/tweetpoll/change-open-status-tweetpoll.json";
encuestame.service.list.resumeliveResultsTweetPoll = encuestame.contextWidget()+"/api/survey/tweetpoll/resumeliveResults-tweetpoll.json";
encuestame.service.list.captchaTweetPoll = encuestame.contextWidget()+"/api/survey/tweetpoll/captcha-tweetpoll.json";
encuestame.service.list.favouriteTweetPoll = encuestame.contextWidget()+"/api/survey/tweetpoll/favourite-tweetpoll.json";
encuestame.service.list.liveResultsTweetPoll = encuestame.contextWidget()+"/api/survey/tweetpoll/liveResults-tweetpoll.json";
encuestame.service.list.notificationTweetPoll = encuestame.contextWidget()+"/api/survey/tweetpoll/notification-tweetpoll.json";
encuestame.service.list.repeatedTweetPoll = encuestame.contextWidget()+"/api/survey/tweetpoll/repeated-tweetpoll.json";
encuestame.service.list.VotesTweetPoll = encuestame.contextWidget()+"/api/chart/tweetPoll/votes.json";

//group services
encuestame.service.list.groupCreate = encuestame.contextWidget()+"/api/groups/createGroup.json";
encuestame.service.list.updateCreate = encuestame.contextWidget()+"/api/groups/updateGroup.json";
encuestame.service.list.updateCreate = encuestame.contextWidget()+"/api/groups/removeGroup.json";
encuestame.service.list.loadGroups = encuestame.contextWidget()+"/api/groups/groups.json";
encuestame.service.list.assingGroups = encuestame.contextWidget()+"/api/admon/groups/assingToUser.json";