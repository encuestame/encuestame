dojo.provide("encuestame.org.class.commons");

encuestame.service = {};
encuestame.service.timeout = 5000;

/**
 * Json Get Call.
 */
encuestame.service.xhrGet = function(url, params, load, error){
    console.debug("url ", url);
    console.debug("params ", params);
    console.debug("load ", load);
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
}

encuestame.service.list = {};
encuestame.service.list.userList = "/encuestame/api/admon/users.json";
encuestame.service.list.getNotifications = "/encuestame/api/notifications.json";