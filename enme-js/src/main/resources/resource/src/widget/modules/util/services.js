/*global __enme_widget:false*/

"use strict";

var services = function(){
    var domain = global.__enme_widget.host;
    return {
        poll_form : domain + "api/jsonp/poll/embedded",
        tp_poll_form : domain + "api/jsonp/tweetpoll/embedded",
        tp_poll_votes : domain + "api/jsonp/tweetpollresult/embedded",
        hashtag_profile : domain + "api/jsonp/hashtag/embedded",
        user_profile : domain + "api/jsonp/profile/embedded",
        poll_results : domain + "api/jsonp/pollresult/embedded",
        tp_poll_results : domain + "api/jsonp/poll/embedded"
    }
};

module.exports = services;

