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

// encuestame.shortUrlProvider = [
//                     {code:"googl",label:"http://googl.com"},
//                     {code:"tinyurl",label:"http://tinyurl.com"},
//                     {code:"bitly",label:"http://bit.ly/"}
//                    ];

encuestame.error = {};
encuestame.error.debug = true;
encuestame.error.dialog = null;

encuestame.utilities = {};
encuestame.utilities.vote = 1;
encuestame.utilities.GRADINENT_CLASS = "gradient-gray";
encuestame.utilities.HIDDEN_CLASS = "hidden";


/**
 * Create a no results message node.
 */
encuestame.utilities.noResults = function(classCSS) {
    var norR = dojo.create("div");
    dojo.addClass(norR, "no-results");
    if (classCSS !== null) {
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


encuestame.session.activity = {};

encuestame.session.activity.name = "enme-ac";
encuestame.session.activity.notification = {t:0,n:0};


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


encuestame.service.handler = {};



/*
 * get context widget.
 */
encuestame.contextWidget = function() {
        //return encuestame.contextDefault;
  return "encuestame/";
};

encuestame.contextDefault = "encuestame/";

//short url service.
//encuestame.service.short = {};
//encuestame.service.short.google = "/api/short/url/google.json";
//encuestame.service.short.tinyurl = "/api/short/url/tinyurl.json";

encuestame.constants = {};
// encuestame.constants.passwordExcludes = [];
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
