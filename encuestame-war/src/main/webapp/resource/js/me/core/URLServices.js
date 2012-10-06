define([ "dojo",
         "me/core/enme",
         "dojo/_base/array" ],
    function(dojo, _ENME, _array) {

      // config context path
      var context_path = _ENME.config("contextPath");

      //
      var _appendContext = function(service) {
        var url = context_path;
        url = url.concat("/");
        url = url.concat(service);
        return url;
      };

      var _service_store = {
        "encuestame.service.list.userList" : _appendContext("api/admon/users.json"),
        "encuestame.service.list.getNotifications" : _appendContext("api/notifications/list.json"),
        "encuestame.service.list.getAllNotifications" : _appendContext("api/notifications/all/list.json"),
        "encuestame.service.list.changeStatusNotification" : _appendContext("api/notifications/readed.json"),
        "encuestame.service.list.removeNotification" : _appendContext("api/remove-notification.json"),
        "encuestame.service.list.userInfo" : _appendContext("api/admon/user-info.json"),
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
        "encuestame.service.list.publishTweetPoll" : _appendContext("api/admon/users.json"),
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
        "encuestame.service.gadget.remove" : _appendContext("api/common/dashboard/gadget/remove.json")
      };

      return {
        service : function(key, params) {
          if (!params) {
            return _service_store[key];
          } else {
             var url = _service_store[key];
             _array.forEach(params, function(entry, i) {
               if (entry != "undefined" && entry != "") {
                  url = url.replace("$"+ i, entry);
               }
             });
            return url;
          }
        }
      };
    });