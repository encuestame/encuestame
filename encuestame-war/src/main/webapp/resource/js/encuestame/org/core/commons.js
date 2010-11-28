dojo.provide("encuestame.org.core.commons");

encuestame.service = {};
encuestame.service.timeout = 5000;
encuestame.contextDefault = "/encuestame";
/**
 * Json Get Call.
 */
encuestame.service.xhrGet = function(url, params, load, error){
    //console.debug("url ", url);
    //console.debug("params ", params);
    //console.debug("load ", load);
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
                    message = "Unknown error.";
                    console.debug(message);
                }

              }
          });
    }
};

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
                load: load,
                preventCache: true,
                error: error
            }
        var deferred = dojo.xhrPost(xhrArgs);
    }
};

encuestame.contextWidget = function(){
    var contextWidget2 = dijit.byId("contextWidget");
    console.debug("Context Widget: ", contextWidget2)
    if(contextWidget2){
        console.debug("Found Context Path");
        return contextWidget2.contextPath;
    } else {
        console.debug("Not found, default context");
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
encuestame.service.list.listPermissions = encuestame.contextWidget()+"/api/admon/list-permissions.json";
encuestame.service.list.listUserPermissions = encuestame.contextWidget()+"/api/admon/list-user-permissions.json";
encuestame.service.list.addPermission = encuestame.contextWidget()+"/api/admon/add-permission.json";
encuestame.service.list.removePermission = encuestame.contextWidget()+"/api/admon/remove-permission.json";
encuestame.service.list.hashtags = encuestame.contextWidget()+"/api/common/hashtags.json";
