//dojo.provide("encuestame.org.core.commons");
//
//dojo.require("encuestame.org.core.commons.error.ErrorConexionHandler");
//dojo.require("encuestame.org.core.commons.error.ErrorHandler");
//dojo.require("dijit.Dialog");
//dojo.require("dojo.cookie");

//
var ENME = {
		config : function() {
			return "";
		},
		getMessage : function(){
			return "text";
		}
};

//

encuestame = {};
encuestame.activity = {};
encuestame.service = {};
encuestame.service.offline = false;
encuestame.service.timeout = ENME.config('delay');
encuestame.contextDefault = ENME.config('contextPath');
encuestame.signin = encuestame.contextDefault+"/signin";

/**
 * JSON GET call.
 * @param {String} url
 * @param {Object} params
 * @param {Function} load
 * @param {Function} error
 * @param {Boolean} logginHandler
 */
encuestame.service.xhrGet = function(url, params, load, error, logginHandler) {
    if (logginHandler == null) {
        logginHandler = true;
    }
    var defaultError = function(error, ioargs){
        console.debug("default error ", error);
    };
    if(error == null){
      error = defaultError;
      console.error("default error");
    }
    if (load == null || url == null || params == null) {
        console.error("error params required.");
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
            error: function(error, ioargs) {
                console.info("error function", ioargs);
                var message = "";
                console.info(ioargs.xhr.status, error);
                //if dialog is missing or is hide.
                if (encuestame.error.dialog == null || !encuestame.error.dialog.open) {
                    switch (ioargs.xhr.status) {
                    case 403:
                        var jsonError = dojo.fromJson(ioargs.xhr.responseText);
                        //console.info("queryObject", jsonError);
                        message = "Application does not have permission for this action";
                        if(!logginHandler){
                            encuestame.error.denied(message);
                        } else {
                            if (!jsonError.session || jsonERror.anonymousUser) {
                                console.info("session is expired");
                                encuestame.error.session(encuestame.error.messages.session);
                            }
                        }
                        break;
                    case 0:
                        message = "A network error occurred. Check that you are connected to the internet.";
                        encuestame.error.conexion(message);
                        break;
                    default:
                        message = "An unknown error occurred";
                        encuestame.error.unknown(message, ioargs.xhr.status);
                    }
                }
              },
            handle: function(response, ioargs) {
                //encuestame.filter.response(response);
                var message = "";
                //console.info(ioargs.xhr.status, error);
                switch (ioargs.xhr.status) {
                case 200:
                    message = "Good request.";
                    //if (encuestame.error.dialog != null) {
                     //   encuestame.error.clear();
                    //}
                    break;
                case 404:
                    message = "The page you requested was not found.";
                    //encuestame.error.createDialog(message, message);
                    break;
                case 400:
                    message = "Bad Request";
                    //encuestame.error.createDialog(message, message);
                    break;
                case 500:
                    break;
                    message = "Service temporarily unavailable.";
                    //encuestame.error.createDialog(message, message);
                    break;
                case 407:
                    message = "You need to authenticate with a proxy.";
                    //encuestame.error.createDialog(message, message);
                    break;
                case 0:
                    message = "A network error occurred. Check that you are connected to the internet.";
                    //encuestame.error.conexion(message);
                    break;
                default:
                    message = "An unknown error occurred";
                    //encuestame.error.unknown(message, ioargs.xhr.status);
                }
              }
          });
    }
};	
	
/**
 * JSON GET call.
 * @param {String} url
 * @param {Object} params
 * @param {Function} load
 * @param {Function} error
 * @param {Boolean} logginHandler
 */
encuestame.service.xhrGet = function(url, params, load, error, logginHandler) {
    if (logginHandler == null) {
        logginHandler = true;
    }
    var defaultError = function(error, ioargs){
        console.debug("default error ", error);
    };
    if(error == null){
      error = defaultError;
      console.error("default error");
    }
    if (load == null || url == null || params == null) {
        console.error("error params required.");
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
            error: function(error, ioargs) {
                console.info("error function", ioargs);
                var message = "";
                console.info(ioargs.xhr.status, error);
                //if dialog is missing or is hide.
                if (encuestame.error.dialog == null || !encuestame.error.dialog.open) {
                    switch (ioargs.xhr.status) {
                    case 403:
                        var jsonError = dojo.fromJson(ioargs.xhr.responseText);
                        console.info("queryObject", jsonError);
                        message = "Application does not have permission for this action";
                        if(!logginHandler){
                            encuestame.error.denied(message);
                        } else {
                            if (!jsonError.session || jsonERror.anonymousUser) {
                                console.info("session is expired");
                                encuestame.error.session(encuestame.error.messages.session);
                            }
                        }
                        break;
                    case 0:
                        message = "A network error occurred. Check that you are connected to the internet.";
                        encuestame.error.conexion(message);
                        break;
                    default:
                        message = "An unknown error occurred";
                        encuestame.error.unknown(message, ioargs.xhr.status);
                    }
                }
              },
            handle: function(response, ioargs) {
                //encuestame.filter.response(response);
                var message = "";
                console.info(ioargs.xhr.status, error);
                switch (ioargs.xhr.status) {
                case 200:
                    message = "Good request.";
                    //if (encuestame.error.dialog != null) {
                     //   encuestame.error.clear();
                    //}
                    break;
                case 404:
                    message = "The page you requested was not found.";
                    //encuestame.error.createDialog(message, message);
                    break;
                case 400:
                    message = "Bad Request";
                    //encuestame.error.createDialog(message, message);
                    break;
                case 500:
                    break;
                    message = "Service temporarily unavailable.";
                    //encuestame.error.createDialog(message, message);
                    break;
                case 407:
                    message = "You need to authenticate with a proxy.";
                    //encuestame.error.createDialog(message, message);
                    break;
                case 0:
                    message = "A network error occurred. Check that you are connected to the internet.";
                    //encuestame.error.conexion(message);
                    break;
                default:
                    message = "An unknown error occurred";
                    //encuestame.error.unknown(message, ioargs.xhr.status);
                }
              }
          });
    }
};

encuestame.shortUrlProvider = [
                    {code:"googl",label:"http://googl.com"},
                    {code:"tinyurl",label:"http://tinyurl.com"},
                    {code:"bitly",label:"http://bit.ly/"}
                   ];

encuestame.error = {};
encuestame.error.debug = true;
encuestame.error.dialog = null;

encuestame.utilities = {};
encuestame.utilities.vote = 1;
encuestame.utilities.GRADINENT_CLASS = "gradient-gray";
encuestame.utilities.HIDDEN_CLASS = "hidden";

/*
 * create a username profile link.
 */
encuestame.utilities.usernameLink = function(username) {
    var url = encuestame.contextDefault;
    if (username) {
        url = url.concat("/profile/");
        url = url.concat(username);
        return url;
    } else {
        url = url.concat("/404");
        return url;
    }
};

/**
 * Create a no results message node.
 */
encuestame.utilities.noResults = function(classCSS) {
    var norR = dojo.create("div");
    dojo.addClass(norR, "no-results");
    if (classCSS != null) {
        dojo.addClass(norR, classCSS);
    }
    norR.innerHTML = encuestame.constants.messageCodes["024"];
    return norR;
};

encuestame.utilities.randomString = function() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for( var i=0; i < 5; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    return text;
};

/**
 * Checking if a string is empty, null or undefined.
 * @param string value
 */
encuestame.utilities.isEmpty = function(str) {
     return (!str || 0 === str.length);
};

encuestame.utilities.url = {};

/*
 * summary :: build hashtag url
 *    hashtagName : hashtag name;
 */
encuestame.utilities.url.hashtag  = function(hashtagName) {
    if (hashtagName) {
        var url = encuestame.contextDefault;
        url = url.concat("/tag/");
        url = url.concat(hashtagName);
        url = url.concat("/");
        return url;
    } else {
        throw new Error("hashtag name is required");
    }
};

/*
 * clear dialog.
 */
encuestame.error.clear = function(){
    console.info("clean", encuestame.error.dialog);
    if (encuestame.error.dialog != null){
        console.info("hidding dialog");
        encuestame.error.dialog.hide();
    }
};

/*
 * denied dialog.
 */
encuestame.error.denied = function(error){
    var div = dojo.doc.createElement('div');
    var h3 = dojo.doc.createElement('h3');
        h3.innerHTML = error;
        var p = dojo.doc.createElement('p');
        p.innerHTML = status;
        div.appendChild(h3);
        div.appendChild(p);
    encuestame.error.createDialog("Opps, What's happening?", div);
};

/*
 * unknow error dialog.
 */
encuestame.error.unknown = function(error, status){
    var div = dojo.doc.createElement('div');
    var h3 = dojo.doc.createElement('h3');
        h3.innerHTML = error;
        var p = dojo.doc.createElement('p');
        p.innerHTML = status;
        div.appendChild(h3);
        div.appendChild(p);
    encuestame.error.createDialog("Opps, What's happening?", div, true);
};

/*
 * conexion error.
 */
encuestame.error.conexion = function(message){
    var div = dojo.doc.createElement('div');
    var h3 = dojo.doc.createElement('h3');
    h3.innerHTML = message;
    div.appendChild(h3);
    encuestame.error.createDialog("Network Issues", div, true);
};

/*
 * expired sesion dialog.
 */
encuestame.error.session = function(message){
    var div = dojo.doc.createElement('div');
    var h3 = dojo.doc.createElement('h3');
    h3.innerHTML = message;
    var widgetButton = new dijit.form.Button({
        label: "Sign In",
        onClick: dojo.hitch(this, function(event) {
            dojo.stopEvent(event);
            document.location.href = encuestame.signin;
        })
    });
    div.appendChild(h3);
    div.appendChild(widgetButton.domNode);
    encuestame.error.createDialog("Not logged in", div, true);
};

/*
 * Create New Error Dialog.
 */
encuestame.error.createDialog = function(title, content, addcloseButton){
    //var node = dojo.byId("errorHandler");
    console.error("error dialog "+title, content);
//    if(node != null){
//        if (encuestame.error.dialog != null){
//             encuestame.error.dialog.open ? encuestame.error.dialog.hide() : "";
//        } else {
//        }
//        dojo.empty(node);
//        //close button validation
//        addcloseButton = addcloseButton == null ? false : addcloseButton;
//        encuestame.error.dialog = new dijit.Dialog({
//              title: title,
//              content: content,
//              style: "width: 480px; height: 100px;"
//          });
//        if(addcloseButton){
//            var widgetButton = new dijit.form.Button({
//                label: "Close",
//                onClick: dojo.hitch(this, function(event) {
//                    dojo.stopEvent(event);
//                    encuestame.error.dialog.hide();
//                })
//            });
//            var content = encuestame.error.dialog.content;
//            content.appendChild(widgetButton.domNode);
//        }
//        console.debug("dialog", encuestame.error.dialog);
//        node.appendChild(encuestame.error.dialog.domNode);
//        encuestame.error.dialog.show();
//    } else {
//        console.error("no error handler dialog found");
//    }
};

encuestame.error.messages = {};
encuestame.error.messages.denied = "Application does not have permission for this action";
encuestame.error.messages.session = "Your session has expired, please sign in to contiue.";

encuestame.filter = {};

/**
 * {"error":{"message":"Access is denied"}}
 */
encuestame.filter.response = function(response){
    console.info("encuestame.filter.response", response);
    //no permissions or session
    if(response == undefined){
        //no response

    }else if (response.error.message != undefined && response.success == undefined ) {
        encuestame.session.getSession();
    } else if(response.success == undefined ) {
        console.info("sucess response no existe");
    }
};

encuestame.session = {};
encuestame.session.getSession = function(){
    //JSESSIONID=dh3u2xvj7fwd1llbddl33dhcq; path=/encuestame; domain=demo2.encuestame.org
    var sessionCookie = dojo.cookie("JSESSIONID");
    if(sessionCookie == undefined){
        encuestame.error.session(encuestame.error.messages.denied);
    } else {
        console.info("session is valid");
    }
};

encuestame.status = ['SUCCESS','FAILED', 'STAND_BY', 'RE_SCHEDULED', 'RE_SEND'];

encuestame.surveys = ['TWEETPOLL', 'POLl', 'SURVEY', 'HASHTAG'];

encuestame.social = {};

encuestame.social.shortPicture = function(provider){
     var url = encuestame.contextDefault + "/resources/images/social/"+provider.toLowerCase()
               +"/enme_icon_"+provider.toLowerCase()+".png";
     return url;
};

encuestame.notification = {};
encuestame.notification.load = {};
encuestame.notification.load.limit = 100;

encuestame.notification.buildURLDescription = function(type, description, url) {
    var multi = dojo.doc.createElement("div");
    var a = dojo.doc.createElement("a");
    a.target = "_blank";
    if (type == "TWEETPOLL_PUBLISHED") {
        multi.innerHTML = description+ "<br/> ";
        a.href = encuestame.contextDefault + url;
        a.innerHTML = "See details here.";
    } else if (type == "SOCIAL_MESSAGE_PUBLISHED") {
        multi.innerHTML = "";
        a.href = url;
        a.innerHTML = description;
    }
    multi.appendChild(a);
    return multi;
};

encuestame.session.activity = {};

encuestame.session.activity.name = "enme-ac";
encuestame.session.activity.notification = {t:0,n:0};

/*
 * Return activity cookie.
 */
encuestame.session.activity.cookie = function(){
    var sessionCookie = dojo.cookie(encuestame.session.activity.name);
    if(!sessionCookie) {
        //new cookie.
        encuestame.session.update(encuestame.session.activity.name,
                encuestame.session.activity.notification);
        sessionCookie = dojo.cookie(encuestame.session.activity.name);
    }
    return dojo.fromJson(sessionCookie);
};

/*
 * Update notification cookie info.
 */
encuestame.session.activity.updateNot = function(t,n) {
    var cokienotification = encuestame.session.activity.cookie();
    if (cokienotification) {
        cokienotification.t = t;
        cokienotification.n = n;
        encuestame.session.update(encuestame.session.activity.name, cokienotification);
    }
};

/*
 *  Creates the cookie with default values.
 */
encuestame.session.update = function(name, data){
    dojo.cookie(
        name,
        dojo.toJson(data),
        {path:'/'}
    );
};


encuestame.date = {};
encuestame.date.timeFormat = "hh:mm:ss";
encuestame.date.dateFormat = "M/d/yy";
encuestame.date.getFormatTime = function(date, fmt){
    return dojo.date.locale.format(date, {
        selector: "date",
        datePattern: fmt
    });
}

/**
 * Json Post Call.
 */
encuestame.service.xhrPost = function(url, form, load, error, formEnabled) {
    //validate form param.
    formEnabled = formEnabled == null ? true : formEnabled;
    //default error.
    var defaultError = function(error, ioargs){
        console.error("default error ", error);
    };
    if(error == null){
      error = defaultError;
    }
    console.debug("Form POST ", form);
    if(load == null || url == null || form == null){
        console.error("error params required.");
    } else {
        var xhrArgs = {
                url: url,
                timeout : encuestame.service.timeout,
                handleAs: "json",
                ailOk : true, //Indicates whether a request should be allowed to fail
                //(and therefore no console error message in the event of a failure)
                load: load,
                preventCache: true,
                error: error
            };
        if (formEnabled) {
            dojo.mixin(xhrArgs, { form: form });
        } else {
            dojo.mixin(xhrArgs, { postData: form });
        }
        var deferred = dojo.xhrPost(xhrArgs);
    }
};

encuestame.service.handler = {};	
//encuestame.service.handler.serviceHander = dojo.hitch(this, function(response, ioargs) {
//    //encuestame.filter.response(response);
//    //console.info(ioargs.xhr.status, error);
//    var message = "";
//    switch (ioargs.xhr.status) {
//    case 200:
//        message = "Good request.";
//        break;
//    case 404:
//        message = "The page you requested was not found.";
//        //encuestame.error.createDialog(message, message);
//        break;
//    case 400:
//        message = "Bad Request";
//        //encuestame.error.createDialog(message, message);
//        break;
//    case 500:
//        break;
//        message = "Service temporarily unavailable.";
//        //encuestame.error.createDialog(message, message);
//        break;
//    case 407:
//        message = "You need to authenticate with a proxy.";
//        //encuestame.error.createDialog(message, message);
//        break;
//    case 0:
//        message = "A network error occurred. Check that you are connected to the internet.";
//        //encuestame.error.conexion(message);
//        break;
//    default:
//        message = "An unknown error occurred";
//        //encuestame.error.unknown(message, ioargs.xhr.status);
//    }
//});


/**
 * JSON GET call.
 * @param {String} url
 * @param {Object} params
 * @param {Function} load
 * @param {Function} error
 * @param {Boolean} logginHandler
 */
encuestame.service.GET = function(url, params, load, error, loadingFunction) {
    	var innerLoad = dojo.hitch(this, function(data) {
    		loadingFunction == null ? "" : loadingFunction.end();
    		if (dojo.isFunction(load)) {
    			load(data);
    		}
    	});
    	// initialize the loading
        loadingFunction == null ? "" : loadingFunction.init();
        var argsGet = {
                url : url,
		        handleAs : "json",
		        failOk : true, //Indicates whether a request should be allowed to fail
		        // (and therefore no console error message in the event of a failure)
		        timeout : encuestame.service.timeout,
		        content: params,
		        load: innerLoad,
		        preventCache: true,
		        error: error,
		        handle : encuestame.service.handler.serviceHander
       }
       dojo.xhrGet(argsGet);
};		

/**
 * xhr POST param.
 */
encuestame.service.xhrPostParam = function(url, params, load, error, formEnabled, loadingFunction) {
    //validate form param.
    formEnabled = formEnabled == null ? true : formEnabled;
    //default error.
    var defaultError = function(error, ioargs) {
        console.error("default error ", error);
    };
    if (error == null) {
      error = defaultError;
    }
    //get the xhr encapsulated error message.
    errorWrapper = function (errorText, xhrError) {
    	if (typeof(xhrError === "object")){
	    	var responseText = dojo.fromJson(xhrError.xhr.response);
	    	error(responseText.error.message);
    	} else {
    		error(errorText || "undefined error");
    	}
    };
    //console.debug("Form POST ", form);
    if (load == null || url == null || params == null){
        console.error("error params required.");
    } else {
    	var innerLoad = function(data) {
    		loadingFunction == null ? "" : loadingFunction.end();
    		load(data);
    	};
    	//load = innerLoad(load);    	
        var xhrArgs = {
            url: url,
            postData: dojo.objectToQuery(params),
            handleAs: "json",
            //headers: { "Content-Type": "application/json", "Accept": "application/json" },
            load: innerLoad,
            preventCache: true,
            error: errorWrapper
        };
        //initialize the loading
        loadingFunction == null ? "" : loadingFunction.init();
        //make the call
        var deferred = dojo.xhrPost(xhrArgs);
    }
};


/*
 * get context widget.
 */
encuestame.contextWidget = function(){
        return encuestame.contextDefault;
};

encuestame.service.list = {};
encuestame.service.list.userList = encuestame.contextWidget()+"/api/admon/users.json";
encuestame.service.list.getNotifications = encuestame.contextWidget()+"/api/notifications/list.json";
encuestame.service.list.getAllNotifications = encuestame.contextWidget()+"/api/notifications/all/list.json";
encuestame.service.list.changeStatusNotification = encuestame.contextWidget()+"/api/notifications/readed.json";
encuestame.service.list.removeNotification = encuestame.contextWidget()+"/api/remove-notification.json";
encuestame.service.list.userInfo = encuestame.contextWidget()+"/api/admon/user-info.json";
encuestame.service.list.createUser = encuestame.contextWidget()+"/api/admon/create-user.json";
encuestame.service.list.profile = {};
encuestame.service.list.profile.my = encuestame.contextWidget()+"/api/settings/profile/my.json";
encuestame.service.list.upgradeProfile = encuestame.contextWidget()+"/api/user/profile/upgrade.json";
//settings update
encuestame.service.list.updateProfile = encuestame.contextWidget()+"/api/settings/profile/update.json";
encuestame.service.list.updateUsername = encuestame.contextWidget()+"/api/settings/profile/username/update.json";
encuestame.service.list.updateEmail = encuestame.contextWidget()+"/api/settings/profile/email/update.json";
encuestame.service.list.updatePicture = encuestame.contextWidget()+"/api/settings/profile/picture/update.json";

encuestame.service.list.listPermissions = encuestame.contextWidget()+"/api/admon/list-permissions.json";
encuestame.service.list.listUserPermissions = encuestame.contextWidget()+"/api/admon/list-user-permissions.json";
encuestame.service.list.addPermission = encuestame.contextWidget()+"/api/admon/add-permission.json";
encuestame.service.list.removePermission = encuestame.contextWidget()+"/api/admon/remove-permission.json";
//Hashtag Service
encuestame.service.list.hashtags = encuestame.contextWidget()+"/api/common/hashtags.json";
encuestame.service.list.ranking = {};
encuestame.service.list.ranking.hashtag = encuestame.contextWidget()+"/api/common/hashtags/stats/ranking.json";
encuestame.service.list.range = {};
encuestame.service.list.range.hashtag =  encuestame.contextWidget()+"/api/common/hashtags/stats/button/range.json";
/*
 * Get Hashtag action.
 * @param type could be / hashtag, tweetpoll, poll.
 * @param action could be / remove / add
 */
encuestame.service.list.hashtagsAction = {};
encuestame.service.list.hashtagsAction.getAction = function(/* string */ type,  /* string */action) {
    return  encuestame.contextWidget()+"/api/survey/hashtag/"+ type+"/"+action+".json";
};
encuestame.service.list.cloud = encuestame.contextWidget()+"/api/common/hashtags/cloud.json";

//Social Services
encuestame.service.list.allSocialAccount = encuestame.contextWidget()+"/api/common/social/accounts.json";

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
encuestame.service.list.VotesTweetPoll = encuestame.contextWidget()+"/api/chart/tweetpoll/votes.json";

encuestame.service.list.tweetpoll = {};
encuestame.service.list.tweetpoll.answer = {};
encuestame.service.list.tweetpoll.answer.getVotes = function(username, id){
    return  encuestame.contextWidget()+"/api/tweetpoll/"+username+"/answer/"+id+"/votes.json";
};

/*
 * Poll Services
 */
encuestame.service.list.poll = {};
encuestame.service.list.listPoll = encuestame.contextWidget()+"/api/survey/poll/search.json";
encuestame.service.list.poll.create = encuestame.contextWidget()+"/api/survey/poll/create.json";
encuestame.service.list.poll.publish = encuestame.contextWidget()+"/api/survey/poll/publish.json";
encuestame.service.list.poll.detail = encuestame.contextWidget()+"/api/survey/poll/detail.json";
encuestame.service.list.poll.setParameter = function(/* string */ type) {
    return  encuestame.contextWidget()+"/api/survey/poll/"+type+"-poll.json";
};
encuestame.service.list.poll.getVotes = function(username) {
    return  encuestame.contextWidget()+"/api/"+username+"/poll/votes.json";
};

/**
 * Comment Services.
 */
encuestame.service.comments = {};
encuestame.service.comments.list = function(type) {
    return encuestame.contextWidget()+"/api/common/comment/comments/"+ type +".json";
};
encuestame.service.comments.search = encuestame.contextWidget()+"/api/common/comment/search.json";
encuestame.service.comments.like = encuestame.contextWidget()+"/api/common/comment/like_vote.json";
encuestame.service.comments.dislike = encuestame.contextWidget()+"/api/common/comment/dislike_vote.json";
encuestame.service.comments.create = encuestame.contextWidget()+"/api/common/comment/create.json";

encuestame.service.list.rate = {};
encuestame.service.list.rate.comments = encuestame.contextWidget()+"/api/common/comment/rate/top.json";
encuestame.service.list.rate.profile = encuestame.contextWidget()+"/api/common/frontend/topusers.json";
encuestame.service.list.rate.stats = function(type) {
    return  encuestame.contextWidget()+"/api/common/frontend/"+type+"/stats.json";
};
encuestame.service.list.generic = {};
encuestame.service.list.generic.stats = encuestame.contextWidget()+"/api/common/stats/generic.json";
encuestame.service.list.rate.buttons =	encuestame.contextWidget()+"/api/common/hashtags/stats/button.json";

/**
 * Vote services.
 */
encuestame.service.list.votes = {};
encuestame.service.list.getTweetPollVotes = function(username, id){
    return  encuestame.contextWidget()+"/api/"+username+"/tweetpoll/"+id+"/votes.json";
};
/**
 * Vote on Home
 * @param username the source of the vote, could be anonymous
 */
encuestame.service.list.votes.home = encuestame.contextWidget()+"/api/frontend/home/vote.json";
encuestame.service.list.addAnswer = encuestame.contextWidget()+"/api/survey/tweetpoll/answer/add.json";
encuestame.service.list.removeAnswer = encuestame.contextWidget()+"/api/survey/tweetpoll/answer/remove.json";
//group services
encuestame.service.list.groupCreate = encuestame.contextWidget()+"/api/groups/createGroup.json";
encuestame.service.list.updateCreate = encuestame.contextWidget()+"/api/groups/updateGroup.json";
encuestame.service.list.updateCreate = encuestame.contextWidget()+"/api/groups/removeGroup.json";
encuestame.service.list.loadGroups = encuestame.contextWidget()+"/api/groups/groups.json";
encuestame.service.list.assingGroups = encuestame.contextWidget()+"/api/admon/groups/assingToUser.json";

encuestame.service.list.checkProfile = encuestame.contextWidget()+"/api/user/account/validate.json";


encuestame.service.publicService = {};
encuestame.service.publicService.validate = {};
encuestame.service.publicService.validate.username = encuestame.contextWidget()+"/api/public/validator/username.json";
encuestame.service.publicService.validate.email = encuestame.contextWidget()+"/api/public/validator/email.json";
encuestame.service.publicService.validate.realName = encuestame.contextWidget()+"/api/public/validator/realName.json";

//settings social
encuestame.service.social = {};
encuestame.service.social.links = {};
encuestame.service.social.links.loadByType = encuestame.contextWidget()+"/api/public/social/links/published.json";
encuestame.service.social.action = {};

encuestame.service.social.action.defaultState = encuestame.contextWidget()+"/api/social/actions/account/default.json";
encuestame.service.social.action.remove = encuestame.contextWidget()+"/api/social/actions/account/remove.json";

encuestame.service.social.facebook = {};
encuestame.service.social.linkedIn = {};

//search.
encuestame.service.search = {};
encuestame.service.search.suggest = encuestame.contextWidget()+"/api/search/quick-suggest.json";

encuestame.service.stream = {};
encuestame.service.stream = encuestame.contextWidget()+"/api/common/frontend/stream.json";

encuestame.service.folder = {};

encuestame.service.folder.create = function(type) {
    return  encuestame.contextWidget()+"/api/survey/folder/"+type+"/create.json";
};
encuestame.service.folder.update = function(type) {
    return  encuestame.contextWidget()+"/api/survey/folder/"+type+"/update.json";
};
encuestame.service.folder.remove = function(type) {
    return  encuestame.contextWidget()+"/api/survey/folder/"+type+"/remove.json";
};
encuestame.service.folder.move = function(type) {
    return  encuestame.contextWidget()+"/api/survey/folder/"+type+"/move.json";
};

encuestame.service.folder.list = function(type) {
    return  encuestame.contextWidget()+"/api/survey/folder/"+type+"/list.json";
};
encuestame.service.stream = encuestame.contextWidget()+"/api/common/frontend/stream.json";
encuestame.service.dashboard = {};
encuestame.service.dashboard.create = encuestame.contextWidget()+"/api/common/dashboard/create-dashboard.json";
encuestame.service.dashboard.list = encuestame.contextWidget()+"/api/common/dashboard/list.json";
encuestame.service.dashboard.select = encuestame.contextWidget()+"/api/common/dashboard/select.json";
encuestame.service.gadget = {};
encuestame.service.gadget.directory = encuestame.contextWidget()+"/api/common/gadgets/directory.json";
encuestame.service.gadget.list = encuestame.contextWidget()+"/api/common/gadgets/list.json";
encuestame.service.gadget.move = encuestame.contextWidget()+"/api/common/dashboard/move-gadget.json";
encuestame.service.gadget.add = encuestame.contextWidget()+"/api/common/gadgets/add.json";
encuestame.service.gadget.load = encuestame.contextWidget()+"/api/common/dashboard/gadget/load.json";
encuestame.service.gadget.remove = encuestame.contextWidget()+"/api/common/dashboard/gadget/remove.json";

//short url service.
//encuestame.service.short = {};
//encuestame.service.short.google = "/api/short/url/google.json";
//encuestame.service.short.tinyurl = "/api/short/url/tinyurl.json";

encuestame.constants = {};
encuestame.constants.passwordExcludes = [];
encuestame.constants.imageSizes = {
    thumbnail : "thumbnail",
    defaultType : "default",
    profile : "profile",
    preview : "preview",
    web : "web"
};

encuestame.messages = {};

// publish a message on main frame.
/*
 * MESSAGE: "message",
            WARNING: "warning",
            ERROR: "error",
            FATAL: "fatal"
 */
encuestame.messages.pubish = function(message, type, duration) {
    //console.info("encuestame.messages.pubish", message);
    //console.info("encuestame.messages.pubish", type);
    //console.info("encuestame.messages.pubish", duration);
    dojo.publish('/encuestame/message/publish', [{ message: message, type: type, duration: duration}]);
};

encuestame.modalbox = {};


encuestame.constants.errorCodes = {
    "002" : "Enter your first and last name.",
    "003" : "Whats your email address?",
    "006" : "Doesn't look like a valid email.",
    "007" : "An email is required!",
    "008" : "This email is already registered",
    "012" : "Password is too obvious.",
    "013" : "Password is not secure enough.",
    "014" : "Password must be at least 6 characters. No whitespace.",
    "015" : "Password cannot be blank!",
    "017" : "This username is already taken!",
    "018" : "Invalid username!",
    "019" : "A username is required!",
    "020" : "Your Tweet contains more than 140 characters. You will have to be more ingenious.",
    "021" : "Your Tweet no contains required answers, imagine a sky without clouds?",
    "022" : "You need at least 1 social account to publish your beautiful creation.",
    "023" : "Ops, something is wrong.",
    "024" : "Please, save your tweetpoll first"
};

encuestame.constants.warningCodes = {
    "001" : "Warning message to define"
};

encuestame.constants.messageCodes = {
    "001" : "Name looks great",
    "004" : "We will email you a confirmation.",
    "005" : "Validating...",
    "007" : "An email is required!",
    "008" : "This email is already registered",
    "009" : "Password is perfect!",
    "010" : "Password is okay.",
    "011" : "Password could be more secure.",
    "016" : "Don't worry, you can change it later.",
    "020" : "",
    "021" : "Drag your gadget here !!",
    "022" : "Be the first to comment on this publication.",
    "023" : "Updated Successfully",
    "024" : "Sorry, no results found",
    "025" : "Publishing your tweets, please wait ..."
 };

encuestame.constants.version = { version : "1.143"};
