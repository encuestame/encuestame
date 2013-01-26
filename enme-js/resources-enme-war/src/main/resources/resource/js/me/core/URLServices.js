define(
    [ "dojo",
      "dojo/request",
      "dojo/_base/lang",
      'dojo/_base/json',
      "dojo/request/notify",
      "dojo/_base/array",
      "me/core/enme" ],
    function(dojo,
             request,
              lang,
              _json,
              notify,
              _array,
              _ENME) {

      // config context path
      var context_path = _ENME.config("contextPath"),
      // json-comment-optional, json-comment-filtered
      _handleAs = "json",
      // Indicates whether a request should be allowed to
      _failOk = true,
      _timeout = ENME.config('delay'),
      //_cross_browsing = false,
      _preventCache = true;
      /*
       * Append the context to each service @param
       */
      var _appendContext = function(service) {
        var url = context_path;
        url = url.concat("/");
        url = url.concat(service);
        return url;
      };

      /**
       *
       */
      var _service_store = {
        "encuestame.service.list.userList" : _appendContext("api/admon/users.json"),
        "encuestame.service.list.getNotifications" : _appendContext("api/notifications/list.json"),
        "encuestame.service.list.getAllNotifications" : _appendContext("api/notifications/all/list.json"),
        "encuestame.service.list.changeStatusNotification" : _appendContext("api/notifications/readed.json"),
        "encuestame.service.list.removeNotification" : _appendContext("api/remove-notification.json"),
        "encuestame.service.list.userInfo" : _appendContext("api/admon/user-info.json"),
        "encuestame.poll.publish.social" : _appendContext("/api/survey/poll/social/publish.json"),
        "encuestame.service.list.createUser" : _appendContext("api/admon/create-user.json"),
        "encuestame.service.list.profile.my" : _appendContext("api/settings/profile/my.json"),
        "encuestame.service.list.upgradeProfile" : _appendContext("api/user/profile/upgrade.json"),
        "encuestame.service.list.updateProfile" : _appendContext("api/settings/profile/update.json"),
        "encuestame.service.list.updateUsername" : _appendContext("api/settings/profile/username/update.json"),
        "encuestame.service.list.updateEmail" : _appendContext("api/settings/profile/email/update.json"),
        "encuestame.service.list.updatePicture" : _appendContext("api/settings/profile/picture/update.json"),
        "encuestame.service.list.listPermissions" : _appendContext("api/admon/list-permissions.json"),
        "encuestame.service.list.listUserPermissions" : _appendContext("api/admon/list-user-permissions.json"),
        "encuestame.service.list.addPermission" : _appendContext("api/admon/add-permission.json"),
        "encuestame.service.list.removePermission" : _appendContext("api/admon/remove-permission.json"),
        "encuestame.service.list.hashtags" : _appendContext("api/common/hashtags.json"),
        "encuestame.service.list.ranking.hashtag" : _appendContext("api/common/hashtags/stats/ranking.json"),
        "encuestame.service.list.range.hashtag" : _appendContext("api/common/hashtags/stats/button/range.json"),
        "encuestame.service.list.hashtagsAction.getAction" : _appendContext("api/survey/hashtag/$0/$1.json"),
        "encuestame.service.list.cloud" : _appendContext("api/common/hashtags/cloud.json"),
        "encuestame.service.list.allSocialAccount" : _appendContext("api/common/social/accounts.json"),
        "encuestame.service.list.publishTweetPoll" : _appendContext("/api/survey/tweetpoll/publish.json"),
        "encuestame.service.list.listTweetPoll" : _appendContext("api/survey/tweetpoll/search.json"),
        "encuestame.service.list.changeTweetPollStatus" : _appendContext("api/survey/tweetpoll/change-open-status-tweetpoll.json"),
        "encuestame.service.list.resumeliveResultsTweetPoll" : _appendContext("api/survey/tweetpoll/resumeliveResults-tweetpoll.json"),
        "encuestame.service.list.captchaTweetPoll" : _appendContext("api/survey/tweetpoll/captcha-tweetpoll.json"),
        "encuestame.service.list.favouriteTweetPoll" : _appendContext("api/survey/tweetpoll/favourite-tweetpoll.json"),
        "encuestame.service.list.liveResultsTweetPoll" : _appendContext("api/survey/tweetpoll/liveResults-tweetpoll.json"),
        "encuestame.service.list.notificationTweetPoll" : _appendContext("api/survey/tweetpoll/notification-tweetpoll.json"),
        "encuestame.service.list.repeatedTweetPoll" : _appendContext("api/survey/tweetpoll/repeated-tweetpoll.json"),
        "encuestame.service.list.VotesTweetPoll" : _appendContext("api/chart/tweetpoll/votes.json"),
        "encuestame.service.list.tweetpoll.answer.getVotes" : _appendContext("api/tweetpoll/$0/answer/$1/votes.json"),
        "encuestame.service.list.listPoll" : _appendContext("api/survey/poll/search.json"),
        "encuestame.service.list.poll.create" : _appendContext("api/survey/poll/create.json"),
        "encuestame.service.list.poll.publish" : _appendContext("api/survey/poll/publish.json"),
        "encuestame.service.list.poll.remove" : _appendContext("/api/survey/poll/remove.json"),
        "encuestame.service.list.poll.detail" : _appendContext("api/survey/poll/detail.json"),
        "encuestame.service.list.poll.setParameter" : _appendContext("api/survey/poll/$0-poll.json"),
        "encuestame.service.list.poll.getVotes" : _appendContext("api/$0/poll/votes.json"),
        "encuestame.service.comments.list" : _appendContext("api/common/comment/comments/$0.json"),
        "encuestame.service.comments.search" : _appendContext("api/common/comment/search.json"),
        "encuestame.service.comments.like" : _appendContext("api/common/comment/like_vote.json"),
        "encuestame.service.comments.dislike" : _appendContext("api/common/comment/dislike_vote.json"),
        "encuestame.service.comments.create" : _appendContext("api/common/comment/create.json"),
        "encuestame.service.list.rate.comments" : _appendContext("api/common/comment/rate/top.json"),
        "encuestame.service.list.rate.profile" : _appendContext("api/common/frontend/topusers.json"),
        "encuestame.service.list.rate.stats" : _appendContext("api/common/frontend/$0/stats.json"),
        "encuestame.service.list.generic.stats" : _appendContext("api/common/stats/generic.json"),
        "encuestame.service.list.rate.buttons" : _appendContext("api/common/hashtags/stats/button.json"),
        "encuestame.service.list.getTweetPollVotes" : _appendContext("api/$0/tweetpoll/$1/votes.json"),
        "encuestame.service.list.votes.home" : _appendContext("api/frontend/home/vote.json"),
        "encuestame.service.list.addAnswer" : _appendContext("api/survey/tweetpoll/answer/add.json"),
        "encuestame.service.list.removeAnswer" : _appendContext("api/survey/tweetpoll/answer/remove.json"),
        "encuestame.service.list.groupCreate" : _appendContext("api/groups/createGroup.json"),
        "encuestame.service.list.updateCreate" : _appendContext("api/groups/updateGroup.json"),
        "encuestame.service.list.removeGroup" : _appendContext("api/groups/removeGroup.json"),
        "encuestame.service.list.loadGroups" : _appendContext("api/groups/groups.json"),
        "encuestame.service.list.assingGroups" : _appendContext("api/admon/groups/assingToUser.json"),
        "encuestame.service.list.checkProfile" : _appendContext("api/user/account/validate.json"),
        "encuestame.service.publicService.validate.username" : _appendContext("api/public/validator/username.json"),
        "encuestame.service.publicService.validate.email" : _appendContext("api/public/validator/email.json"),
        "encuestame.service.publicService.validate.realName" : _appendContext("api/public/validator/realName.json"),
        "encuestame.service.social.links.loadByType" : _appendContext("api/public/social/links/published.json"),
        "encuestame.service.social.action.defaultState" : _appendContext("api/social/actions/account/default.json"),
        "encuestame.service.social.action.remove" : _appendContext("api/social/actions/account/remove.json"),
        "encuestame.service.search.suggest" : _appendContext("api/search/quick-suggest.json"),
        "encuestame.service.stream" : _appendContext("api/common/frontend/stream.json"),
        "encuestame.service.folder.create" : _appendContext("api/survey/folder/$0/create.json"),
        "encuestame.service.folder.update" : _appendContext("api/survey/folder/$0/update.json"),
        "encuestame.service.folder.remove" : _appendContext("api/survey/folder/$0/remove.json"),
        "encuestame.service.folder.move" : _appendContext("api/survey/folder/$0/move.json"),
        "encuestame.service.folder.list" : _appendContext("api/survey/folder/$0/list.json"),
        "encuestame.service.dashboard.create" : _appendContext("api/common/dashboard/create-dashboard.json"),
        "encuestame.service.dashboard.list" : _appendContext("api/common/dashboard/list.json"),
        "encuestame.service.dashboard.select" : _appendContext("api/common/dashboard/select.json"),
        "encuestame.service.gadget.directory" : _appendContext("api/common/gadgets/directory.json"),
        "encuestame.service.gadget.list" : _appendContext("api/common/gadgets/list.json"),
        "encuestame.service.gadget.move" : _appendContext("api/common/dashboard/move-gadget.json"),
        "encuestame.service.gadget.add" : _appendContext("api/common/gadgets/add.json"),
        "encuestame.service.gadget.load" : _appendContext("api/common/dashboard/gadget/load.json"),
        "encuestame.service.gadget.remove" : _appendContext("api/common/dashboard/gadget/remove.json"),
        "encuestame.service.tweetpoll.autosave" : _appendContext("api/survey/tweetpoll/autosave.json")
      };

      /**
       *
       */
      var _services = {
              service : function(key, params) {
                if (!params) {
                  return _service_store[key];
                } else {
                  var url = _service_store[key];
                  _array.forEach(params, function(entry, i) {
                    if (entry != "undefined" && entry != "") {
                      url = url.replace("$" + i, entry);
                    }
                  });
                  return url;
                }
              },

              /*
               *
               */
               get : function(url, params, load, error, loaderHandler) {
                    _makeCall(url, params, 'GET', load, error, loaderHandler);
              },

              /*
               *
               */
              post : function(url, params, load, error, loaderHandler) {
                    _makeCall(url, params, 'POST', load, error, loaderHandler);
              },

              /*
               *
               */
              del : function(url, params, load, error, loaderHandler) {
                   _makeCall(url, params, 'DELETE', load, error, loaderHandler);
              },

              /*
               *
               */
              put : function(url, params, load, error, loaderHandler) {
                   _makeCall(url, params, 'PUT', load, error, loaderHandler);
              }
      };

      /**
       *
       */
      var _makeCall = function(url , params, method, response, error, loaderHandler) {
          var _load = response;
          var _error = error;
          if (loaderHandler != 'undefined' && typeof loaderHandler === 'function') {
              _load = function(r) {
                  try{
                    if (typeof loaderHandler === 'function') {
                       loaderHandler();
                    }
                    response(r);
                  } catch(error) {
                      _ENME.log(error);
                  }
              };
              _error = function(e) {
                  try{
                      if (typeof loaderHandler === 'function') {
                         loaderHandler();
                      }
                      error(e);
                  } catch(error) {
                      _ENME.log(error);
                  }
              };
          }

          // defaultt params
          var _params = {
             handleAs : _handleAs,
               failOk : _failOk,
               timeout : _timeout,
               method : method,
               preventCache : _preventCache
           };

           // on dependes the method, the way to send the data it's different

           if (method === 'POST' || method === 'PUT') {
                _params = lang.mixin(_params, {
                    data : params
                });
           } else if (method === 'GET' || method === 'DELETE') {
                _params = lang.mixin(_params, {
                    query : params,
                    headers: { "Content-Type": "application/json"},
                });
           }

           // resolve complex url
           var _service_url = null;
           if (typeof url === 'string') {
               _service_url = _services.service(url);
           } else if (lang.isArray(url) && url.length === 2) {
               _service_url = _services.service(url[0], url[1]);
           } else if (lang.isArray(url) && url.length === 1) {
               _service_url = _services.service(url[0]);
           }
           //_ENME.log("url to call -->", _service_url);
           if (_service_url !== null ) {
                // make the request
                request(_service_url, _params).then(_load, _error,
                         function(evt) {});
              }
     };
    return _services;
});

require(["dojo", "dojo/request/notify", "me/core/enme"], function(dojo, notify, _ENME) {

  notify("start", function(){
    // Do something when the request queue has started
    // This event won't fire again until "stop" has fired
      //console.log("NOTIFYYYY start", arguments);
      dojo.subscribe("/encuestame/status/start", this, dojo.hitch(this, function(_f) {
          _f();
      }));
  });

  notify("send", function(response, cancel) {
    // Do something before a request has been sent
    // Calling cancel() will prevent the request from
    // being sent
      //console.log("NOTIFYYYY send", arguments);
      dojo.subscribe("/encuestame/status/sent", this, dojo.hitch(this, function(_f) {
          _f();
      }));
  });

  notify("load", function(response) {
    // Do something when a request has succeeded
      //console.log("NOTIFYYYY load", arguments);
      dojo.subscribe("/encuestame/status/load", this, dojo.hitch(this, function(_f) {
          _f();
      }));
  });

  notify("error", function(error){
    // Do something when a request has failed
      //console.log("NOTIFYYYY error", arguments);
      dojo.subscribe("/encuestame/status/error", this, dojo.hitch(this, function(_f) {
          _f();
      }));
  });

  notify("done", function(responseOrError) {
    // Do something whether a request has succeeded or failed
      //console.log("NOTIFYYYY done", arguments);
      dojo.subscribe("/encuestame/status/done", this, dojo.hitch(this, function(_f) {
          _f();
      }));
    // if (responseOrError instanceof Error) {
    //   // Do something when a request has failed
    // } else {
    //   // Do something when a request has succeeded
    // }
  });

  notify("stop", function() {
     // console.log("NOTIFYYYY stop", arguments);
      dojo.subscribe("/encuestame/status/stop", this, dojo.hitch(this, function(_f) {
          _f();
      }));
    // Do something when all in-flight requests have finished
  });
});
