dojo.provide("encuestame.org.core.commons");

dojo.require("encuestame.org.core.commons.error.ErrorConexionHandler");
dojo.require("encuestame.org.core.commons.error.ErrorHandler");
dojo.require("encuestame.org.core.commons.dashboard.Dashboard");
dojo.require("dijit.Dialog");

encuestame.activity = {};
encuestame.service = {};
encuestame.service.offline = false;
encuestame.service.timeout = config.delay;
encuestame.contextDefault = config.contextPath;
encuestame.signin = encuestame.contextDefault+"/signin";

/**
 * JSON GET call.
 */
encuestame.service.xhrGet = function(url, params, load, error, logginHandler){
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
                    if (encuestame.error.dialog != null) {
                        encuestame.error.clear();
                    }
                    break;
                case 404:
                    message = "The page you requested was not found.";
                    encuestame.error.createDialog(message, message);
                    break;
                case 400:
                    message = "Bad Request";
                    encuestame.error.createDialog(message, message);
                    break;
                case 500:
                    break;
                    message = "Service temporarily unavailable.";
                    encuestame.error.createDialog(message, message);
                    break;
                case 407:
                    message = "You need to authenticate with a proxy.";
                    encuestame.error.createDialog(message, message);
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
          });
    }
};

encuestame.error = {};
encuestame.error.debug = true;
encuestame.error.dialog = null;

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
    var node = dojo.byId("errorHandler");
    console.debug("node", node);
    if(node != null){
        if (encuestame.error.dialog != null){
             encuestame.error.dialog.open ? encuestame.error.dialog.hide() : "";
        } else {
        }
        dojo.empty(node);
        //close button validation
        addcloseButton = addcloseButton == null ? false : addcloseButton;
        encuestame.error.dialog = new dijit.Dialog({
              title: title,
              content: content,
              style: "width: 480px; height: 100px;"
          });
        if(addcloseButton){
            var widgetButton = new dijit.form.Button({
                label: "Close",
                onClick: dojo.hitch(this, function(event) {
                    dojo.stopEvent(event);
                    encuestame.error.dialog.hide();
                })
            });
            var content = encuestame.error.dialog.content;
            content.appendChild(widgetButton.domNode);
        }
        console.debug("dialog", encuestame.error.dialog);
        node.appendChild(encuestame.error.dialog.domNode);
        encuestame.error.dialog.show();
    } else {
        console.error("no error handler dialog found");
    }
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

/**
 * Json Get Call.
 */
encuestame.service.xhrPost = function(url, form, load, error, formEnabled){
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

/*
 * get context widget.
 */
encuestame.contextWidget = function(){
        return encuestame.contextDefault;
};

encuestame.service.list = {};
encuestame.service.list.userList = encuestame.contextWidget()+"/api/admon/users.json";
encuestame.service.list.getNotifications = encuestame.contextWidget()+"/api/notifications.json";
encuestame.service.list.getStatusNotifications = encuestame.contextWidget()+"/api/status-notifications.json";
encuestame.service.list.changeStatusNotification = encuestame.contextWidget()+"/api/change-status-notifications.json";
encuestame.service.list.removeNotification = encuestame.contextWidget()+"/api/remove-notification.json";
encuestame.service.list.userInfo = encuestame.contextWidget()+"/api/admon/user-info.json";
encuestame.service.list.createUser = encuestame.contextWidget()+"/api/admon/create-user.json";
encuestame.service.list.upgradeProfile = encuestame.contextWidget()+"/api/user/profile/upgrade.json";

encuestame.service.list.listPermissions = encuestame.contextWidget()+"/api/admon/list-permissions.json";
encuestame.service.list.listUserPermissions = encuestame.contextWidget()+"/api/admon/list-user-permissions.json";
encuestame.service.list.addPermission = encuestame.contextWidget()+"/api/admon/add-permission.json";
encuestame.service.list.removePermission = encuestame.contextWidget()+"/api/admon/remove-permission.json";
encuestame.service.list.hashtags = encuestame.contextWidget()+"/api/common/hashtags.json";
//TODO: replace twitter encuestame.service.list.socialAccounts
encuestame.service.list.socialAccounts = encuestame.contextWidget()+"/api/common/social/confirmed-accounts.json";
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
encuestame.service.list.VotesTweetPoll = encuestame.contextWidget()+"/api/chart/tweetPoll/votes.json";

//group services
encuestame.service.list.groupCreate = encuestame.contextWidget()+"/api/groups/createGroup.json";
encuestame.service.list.updateCreate = encuestame.contextWidget()+"/api/groups/updateGroup.json";
encuestame.service.list.updateCreate = encuestame.contextWidget()+"/api/groups/removeGroup.json";
encuestame.service.list.loadGroups = encuestame.contextWidget()+"/api/groups/groups.json";
encuestame.service.list.assingGroups = encuestame.contextWidget()+"/api/admon/groups/assingToUser.json";

//settings update
encuestame.service.list.updateProfile = encuestame.contextWidget()+"/api/settings/profile/update.json";
encuestame.service.list.updateUsername = encuestame.contextWidget()+"/api/settings/profile/username/update.json";
encuestame.service.list.updateEmail = encuestame.contextWidget()+"/api/settings/profile/email/update.json";
encuestame.service.list.myProfile = encuestame.contextWidget()+"/api/admon/info-profile.json";

encuestame.service.list.checkProfile = encuestame.contextWidget()+"/api/user/account/validate.json";

//settings social
encuestame.service.social = {};
encuestame.service.social.twitter = {};
encuestame.service.social.twitter.authorize = encuestame.contextWidget()+"/api/social/twitter/authorize/url.json";
encuestame.service.social.twitter.confirm = encuestame.contextWidget()+"/api/social/twitter/authorize/confirm.json";
encuestame.service.social.twitter.create = encuestame.contextWidget()+"/api/social/twitter/account/create.json";

encuestame.service.social.twitter.defaultState = encuestame.contextWidget()+"/api/social/twitter/account/default.json";
encuestame.service.social.twitter.remove = encuestame.contextWidget()+"/api/social/twitter/account/remove.json";
encuestame.service.social.twitter.valid = encuestame.contextWidget()+"/api/social/twitter/account/valid.json";

encuestame.service.social.facebook = {};
encuestame.service.social.linkedIn = {};

//search.
encuestame.service.search = {};
encuestame.service.search.suggest = encuestame.contextWidget()+"/api/search/quick-suggest.json";

//short url service.
//encuestame.service.short = {};
//encuestame.service.short.google = "/api/short/url/google.json";
//encuestame.service.short.tinyurl = "/api/short/url/tinyurl.json";