/**
 * Copyright 2014 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.147
 *  @module ModuleName
 *  @namespace Widget
 *  @class FileName
 */

define(function() {

	/**
	 * List of services
	 * @property SERVICES
	 */
	var SERVICES = {
		"encuestame.service.list.userList" : "/api/admon/users.json",
		"encuestame.user.invite" : "/api/user/invite.json",
		"encuestame.user.login" : "/api/user/login",
		"encuestame.admon.status" : "/api/user/status",
		"encuestame.service.list.getNotifications" : "/api/notifications/list.json",
		"encuestame.service.list.getAllNotifications" : "/api/notifications/all/list.json",
		"encuestame.service.list.changeStatusNotification" : "/api/notifications/readed.json",
		"encuestame.service.list.removeNotification" : "/api/notification/remove.json",
		"encuestame.service.list.userInfo" : "/api/admon/user-info.json",
		"encuestame.poll.publish.social" : "//api/survey/poll/social/publish.json",
		"encuestame.service.list.createUser" : "/api/admon/create-user.json",
		"encuestame.service.list.profile.my" : "/api/settings/profile/my",
		"encuestame.service.list.upgradeProfile" : "/api/user/profile/upgrade.json",
		"encuestame.service.list.updateProfile" : "/api/settings/profile/update.json",
		"encuestame.service.list.updateUsername" : "/api/settings/profile/username/update.json",
		"encuestame.service.list.updateEmail" : "/api/settings/profile/email/update.json",
		"encuestame.service.list.updatePicture" : "/api/settings/profile/picture/update.json",
		"encuestame.service.list.listPermissions" : "/api/admon/list-permissions.json",
		"encuestame.service.list.listUserPermissions" : "/api/admon/list-user-permissions.json",
		"encuestame.service.list.addPermission" : "/api/admon/add-permission.json",
		"encuestame.service.list.removePermission" : "/api/admon/remove-permission.json",
		"encuestame.service.list.hashtags" : "/api/common/hashtags.json",
		"encuestame.service.list.ranking.hashtag" : "/api/common/hashtags/stats/ranking.json",
		"encuestame.service.list.range.hashtag" : "/api/common/hashtags/stats/button/range.json",
        //v2 hastahg graph
        "encuestame.service.list.range.hashtag2" : "/api/2/common/hashtags/stats/button/$0",
        //v2 hastahg graph
		"encuestame.service.list.hashtagsAction.getAction" : "/api/survey/hashtag/$0/$1.json",
		"encuestame.service.list.cloud" : "/api/common/hashtags/cloud.json",
		"encuestame.service.list.allSocialAccount" : "/api/common/social/accounts.json",
		"encuestame.service.list.publishTweetPoll" : "/api/survey/tweetpoll/publish",
		"encuestame.service.list.republishTweetPoll" : "/api/survey/tweetpoll/publish/$0", //FIXME: new publish API service, use the old one meantime.
		"encuestame.service.list.scheduleTweetPoll" : "/api/survey/$0/schedule/$1",
		"encuestame.service.list.scheduleUnPublishedTweetPoll" : "/api/survey/$0/schedule",
		"encuestame.service.list.listTweetPoll" : "/api/survey/tweetpoll/search.json",
		"encuestame.service.list.changeTweetPollStatus" : "/api/survey/tweetpoll/change-open-status-tweetpoll.json",
		"encuestame.service.list.resumeliveResultsTweetPoll" : "/api/survey/tweetpoll/resumeliveResults-tweetpoll.json",
		"encuestame.service.list.captchaTweetPoll" : "/api/survey/tweetpoll/captcha-tweetpoll.json",
		"encuestame.service.list.favouriteTweetPoll" : "/api/survey/tweetpoll/favourite-tweetpoll.json",
		"encuestame.service.list.liveResultsTweetPoll" : "/api/survey/tweetpoll/liveResults-tweetpoll.json",
		"encuestame.service.list.notificationTweetPoll" : "/api/survey/tweetpoll/notification-tweetpoll.json",
		"encuestame.service.list.repeatedTweetPoll" : "/api/survey/tweetpoll/repeated-tweetpoll.json",
		"encuestame.service.list.commentTweetPoll" : "/api/survey/tweetpoll/comment-tweetpoll.json",
		"encuestame.service.list.VotesTweetPoll" : "/api/chart/tweetpoll/votes.json",
		"encuestame.service.list.tweetpoll.answer.getVotes" : "/api/tweetpoll/$0/answer/$1/votes.json",
		"encuestame.service.list.tweetpoll.delete" : "/api/survey/tweetpoll/$0",
		"encuestame.service.list.listPoll" : "/api/survey/poll/search.json",
		"encuestame.service.list.poll.create" : "/api/survey/poll",
		"encuestame.service.list.poll.publish" : "/api/survey/poll/publish.json",
		"encuestame.service.list.poll.remove" : "/api/survey/poll",
		"encuestame.service.list.poll.detail" : "/api/survey/poll/detail.json",
		"encuestame.service.list.poll.setParameter" : "/api/survey/poll/$0-poll.json",
		"encuestame.service.list.poll.getVotes" : "/api/$0/poll/votes.json",
		"encuestame.service.comments.list" : "/api/common/comment/comments/$0.json",
		"encuestame.service.commentPoPols.search" : "/api/common/comment/search.json",
		"encuestame.service.comments.like" : "/api/common/comment/vote/like",
		"encuestame.service.comments.dislike" : "/api/common/comment/vote/unlike",
		"encuestame.service.comments.create" : "/api/common/comment/$0/create.json",
		"encuestame.service.comments.admon.create" : "/api/admon/comment/$0/create.json",
		"encuestame.service.comments.admon.status" : "/api/admon/comment/$0/$1.json",
		"encuestame.service.comments.my" : "/api/common/comments",
		"encuestame.service.list.rate.comments" : "/api/common/comment/rate/top.json",
		"encuestame.service.list.rate.profile" : "/api/common/frontend/topusers.json",
		"encuestame.service.list.rate.stats" : "/api/common/frontend/$0/stats.json",
		"encuestame.service.list.generic.stats" : "/api/common/stats/generic.json",
		"encuestame.service.list.rate.buttons" : "/api/common/hashtags/stats/button.json",
		"encuestame.service.list.getTweetPollVotes" : "/api/$0/tweetpoll/$1/votes.json",
		"encuestame.service.list.votes.home" : "/api/frontend/home/$0/vote",
		"encuestame.service.list.addAnswer" : "/api/survey/tweetpoll/answer/add.json",
		"encuestame.service.list.removeAnswer" : "/api/survey/tweetpoll/answer/remove.json",
		"encuestame.service.list.groupCreate" : "/api/groups/createGroup.json",
		"encuestame.service.list.updateCreate" : "/api/groups/updateGroup.json",
		"encuestame.service.list.removeGroup" : "/api/groups/removeGroup.json",
		"encuestame.service.list.loadGroups" : "/api/groups/groups.json",
		"encuestame.service.list.assingGroups" : "/api/admon/groups/assingToUser.json",
		"encuestame.service.list.checkProfile" : "/api/user/account/validate.json",
		"encuestame.service.publicService.validate.username" : "/api/public/validator/username.json",
		"encuestame.service.publicService.validate.email" : "/api/public/validator/email.json",
		"encuestame.service.publicService.validate.realName" : "/api/public/validator/realName.json",
		"encuestame.service.social.links.loadByType" : "/api/public/social/links/published.json",
		"encuestame.service.social.action.defaultState" : "/api/social/actions/account/default.json",
		"encuestame.service.social.action.remove" : "/api/social/actions/account/remove.json",
		"encuestame.service.search.suggest" : "/api/search/quick-suggest.json",
		"encuestame.service.stream" : "/api/common/frontend/stream.json",
		"encuestame.service.folder.create" : "/api/survey/folder/$0",
		"encuestame.service.folder.update" : "/api/survey/folder/$0",
		"encuestame.service.folder.remove" : "/api/survey/folder/$0",
		"encuestame.service.folder.move" : "/api/survey/folder/$0/move",
		"encuestame.service.folder.list" : "/api/survey/folder/$0/list",
		"encuestame.service.folder.items" : "/api/survey/folder/$0/items",
		//
		"encuestame.service.dashboard" : "/api/common/dashboard",
		// GET LIST
		"encuestame.service.dashboard.list" : "/api/common/dashboard",

		"encuestame.service.dashboard.select" : "/api/common/dashboard/select.json",
		"encuestame.service.gadget.directory" : "/api/common/gadgets/directory/list.json",
		"encuestame.service.gadget.list" : "/api/common/gadgets/list.json",
    "encuestame.service.help.status" : '/api/help/status',

		//gadget services
		//deprecated
		//"encuestame.service.gadget.move" : "/api/common/gadgets/move.json",
		//"encuestame.service.gadget.add" : "/api/common/gadgets/add.json",
		//"encuestame.service.gadget.remove" : "/api/common/dashboard/gadget/remove.json",
		//"encuestame.service.gadget.load" : "/api/common/dashboard/gadget/load.json",

		"encuestame.service.gadget" : "/api/common/$0/gadget.json",

		"encuestame.service.tweetpoll.autosave" : "/api/survey/tweetpoll/autosave"
	};

	return SERVICES;
});