/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.search;

import java.util.ArrayList;
import java.util.List;

import org.encuestame.core.search.GlobalSearchItem;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.domain.Comment;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.enums.TypeSearchResult;

/**
 * Utils to convert items to {@link GlobalSearchItem}.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 26, 2011
 */
public class UtilConvertToSearchItems {

    /**
     *
     * @param question
     * @return
     */
    public static GlobalSearchItem convertQuestionToSearchItem(
            final Question question) {
        final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
        globalSearchItem.setUrlLocation("/question/detail/" + question.getQid() + "/" + RestFullUtil.slugify(question.getQuestion()));
        globalSearchItem.setHits(question.getHits());
        globalSearchItem.setId(question.getQid());
        globalSearchItem.setItemSearchTitle(question.getQuestion());
        globalSearchItem.setDateCreated(question.getCreateDate());
        globalSearchItem.setItemPattern(question.getQuestionPattern().name());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.QUESTION);
        globalSearchItem.setScore(100L); //FIXME: fixed number?
        return globalSearchItem;
    }

    /**
     *
     * @param item
     * @return
     */
    public static GlobalSearchItem convertAttachmentSearchToSearchItem(
            final AttachmentSearchItem item) {
        final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
        globalSearchItem
                .setUrlLocation("/resource/detail/" + item.getAttachId()+"/"+RestFullUtil.slugify(item.getDescription()));
        globalSearchItem.setHits(200L); //FIXME: fixed number?
        globalSearchItem.setId(item.getAttachId());
        globalSearchItem.setItemSearchTitle(item.getDescription());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.ATTACHMENT);
        globalSearchItem.setScore(100L); //FIXME: fixed number?
        return globalSearchItem;
    }

    /**
     *
     * @param items
     * @return
     */
    public static List<GlobalSearchItem> convertAttachmentSearchToSearchItem(
            final List<AttachmentSearchItem> items) {
        final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
        for (AttachmentSearchItem item : items) {
            globalSearchItems.add(UtilConvertToSearchItems
                    .convertAttachmentSearchToSearchItem(item));
        }
        return globalSearchItems;
    }

    /**
     *
     * @param tag
     * @return
     */
    public static GlobalSearchItem convertHashTagToSearchItem(final HashTag tag) {
        final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
        globalSearchItem.setUrlLocation("/tag/"
                + RestFullUtil.formatHasgTag(tag.getHashTag()));
        globalSearchItem.setHits(tag.getHits());
        globalSearchItem.setId(tag.getHashTagId());
        globalSearchItem.setItemSearchTitle(tag.getHashTag());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.HASHTAG);
        globalSearchItem.setScore(100L); //FIXME: fixed number?
        return globalSearchItem;
    }

    /**
     *
     * @param profile
     * @return
     */
    public static GlobalSearchItem convertProfileToSearchItem(
            final UserAccount profile) {
        final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
        globalSearchItem.setUrlLocation("/profile/" + profile.getUsername());
        globalSearchItem.setHits(0L);
        globalSearchItem.setId(profile.getUid());
        globalSearchItem.setItemSearchTitle(profile.getCompleteName());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.PROFILE);
        globalSearchItem.setScore(100L); //FIXME: fixed number?
        return globalSearchItem;
    }

    /**
     *
     * @param profiles
     * @return
     */
    public static List<GlobalSearchItem> convertProfileToSearchItem(
            final List<UserAccount> profiles) {
        final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
        for (UserAccount profile : profiles) {
            globalSearchItems.add(UtilConvertToSearchItems
                    .convertProfileToSearchItem(profile));
        }
        return globalSearchItems;
    }

    /**
     *
     * @param questions
     * @return
     */
    public static List<GlobalSearchItem> convertQuestionToSearchItem(
            final List<Question> questions) {
        final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
        for (Question question : questions) {
            globalSearchItems.add(UtilConvertToSearchItems
                    .convertQuestionToSearchItem(question));
        }
        return globalSearchItems;
    }

    /**
     *
     * @param tags
     * @return
     */
    public static List<GlobalSearchItem> convertHashTagToSearchItem(
            final List<HashTag> tags) {
        final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
        for (HashTag tag : tags) {
            globalSearchItems.add(UtilConvertToSearchItems
                    .convertHashTagToSearchItem(tag));
        }
        return globalSearchItems;
    }

    /**
     * Convert a {@link Comment} Array to {@link GlobalSearchItem} list.
     * @param comments
     * @return
     */
	public static List<GlobalSearchItem> convertCommentToSearchItem(
			final List<Comment> comments) {
		final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
		for (Comment comment : comments) {
			globalSearchItems.add(UtilConvertToSearchItems
					.convertCommentToSearchItem(comment));
		}
		return globalSearchItems;
	}

    /**
     * Convert {@link Comment} to {@link GlobalSearchItem}
     * @param comment
     * @return
     */
	public static GlobalSearchItem convertCommentToSearchItem(
			final Comment comment) {
		final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
		globalSearchItem.setDateCreated(comment.getCreatedAt());
		globalSearchItem.setId(comment.getCommentId());
		globalSearchItem.setTypeSearchResult(TypeSearchResult.COMMENT);
		globalSearchItem.setScore(100l);
		globalSearchItem.setItemSearchTitle(comment.getComment());
		return globalSearchItem;
	}

	/**
	 * Convert a list of {@link Poll} to {@link GlobalSearchItem} list.
	 * @param polls
	 * @return
	 */
	public static List<GlobalSearchItem> convertPollToSearchItem(
			final List<Poll> polls) {

		final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
		for (Poll poll : polls) {
			globalSearchItems.add(UtilConvertToSearchItems
					.convertPollToSearchItem(poll));
		}
		return globalSearchItems;
	}

	/**
	 * Convert Poll to Search item
	 * @param question
	 * @return
	 */
    public static GlobalSearchItem convertPollToSearchItem(
            final Poll poll) {
        final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
        globalSearchItem.setUrlLocation(EnMeUtils.createUrlPollAccess("", poll));
        globalSearchItem.setHits(poll.getHits());
        globalSearchItem.setId(poll.getPollId());
        globalSearchItem.setItemSearchTitle(poll.getQuestion().getQuestion());
        globalSearchItem.setDateCreated(poll.getCreateDate());
        globalSearchItem.setItemPattern(poll.getQuestion().getQuestionPattern().name());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.POLL);
        return globalSearchItem;
    }

    /**
     * Convert a list of {@link TweetPoll} to {@link GlobalSearchItem} list.
     * @param tpoll
     * @return
     */
	public static List<GlobalSearchItem> convertTweetPollToSearchItem(
			final List<TweetPoll> tpoll) {

		final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
		for (TweetPoll tweetPoll : tpoll) {
			globalSearchItems.add(UtilConvertToSearchItems
					.convertTweetPollToSearchItem(tweetPoll));
		}
		return globalSearchItems;
	}

	/**
	 * Convert {@link TweetPoll} to {@link GlobalSearchItem}
	 * @param question
	 * @return
	 */
    public static GlobalSearchItem convertTweetPollToSearchItem(
            final TweetPoll tweetPoll) {
        final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
        globalSearchItem.setUrlLocation(EnMeUtils.createTweetPollUrlAccess("", tweetPoll));
        globalSearchItem.setHits(tweetPoll.getHits());
        globalSearchItem.setId(tweetPoll.getTweetPollId());
        globalSearchItem.setItemSearchTitle(tweetPoll.getQuestion().getQuestion());
        globalSearchItem.setDateCreated(tweetPoll.getCreateDate());
        globalSearchItem.setItemPattern(tweetPoll.getQuestion().getQuestionPattern().name());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.TWEETPOLL);
        return globalSearchItem;
    }

    /**
     * Convert a list of {@link Survey} to {@link GlobalSearchItem} list.
     * @param surveys
     * @return
     */
	public static List<GlobalSearchItem> convertSurveyToSearchItem(
			final List<Survey> surveys) {

		final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
		for (Survey survey : surveys) {
			globalSearchItems.add(UtilConvertToSearchItems
					.convertSurveyToSearchItem(survey));
		}
		return globalSearchItems;
	}

	/**
	 * Convert {@link Survey} to {@link GlobalSearchItem}
	 * @param question
	 * @return
	 */
    public static GlobalSearchItem convertSurveyToSearchItem(
            final Survey survey) {
        final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
        globalSearchItem.setUrlLocation(null);
        globalSearchItem.setHits(survey.getHits());
        globalSearchItem.setId(survey.getSid());
       // globalSearchItem.setItemSearchTitle(null);
        globalSearchItem.setDateCreated(survey.getCreateDate());
       // globalSearchItem.setItemPattern(null);
        globalSearchItem.setTypeSearchResult(TypeSearchResult.SURVEY);
        return globalSearchItem;
    }

}
