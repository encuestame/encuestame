dojo.provide("encuestame.org.class.commons");

encuestame.service = {};
encuestame.service.timeout = 5000;

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
                case 500:
                    break;
                    message = "The server reported an error.";
                    break;
                case 407:
                    message = "You need to authenticate with a proxy.";
                    break;
                default:
                    message = "Unknown error.";
                }
                console.debug(message);
              }
          });
    }
};

/**
 * Json Get Call.
 */
encuestame.service.xhrPost = function(url, form, load, error){
    console.debug("default url ", url);
    console.debug("default form ", form);
    console.debug("default load ", load);
    console.debug("default error ", error);
    var defaultError = function(error, ioargs){
        console.debug("default error ", error);
    };
    if(error == null){
      error = defaultError;
    }
    if(load == null || url == null || form == null){
        console.error("error params required.")
    } else {
        var xhrArgs = {
                url: url,
                form: form,
                handleAs: "text",
                load: load,
                error: error
            }
        var deferred = dojo.xhrPost(xhrArgs);
    }
}

encuestame.service.list = {};
encuestame.service.list.userList = "/encuestame/api/admon/users.json";
encuestame.service.list.getNotifications = "/encuestame/api/notifications.json";
encuestame.service.list.userInfo = "/encuestame/api/admon/user-info.json";
encuestame.service.list.createUser = "/encuestame/api/admon/create-user.json";