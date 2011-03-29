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

import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.RestFullUtil;

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
        globalSearchItem
                .setUrlLocation("/question/detail/" + question.getQid()+"/"+RestFullUtil.slugify(question.getQuestion()));
        globalSearchItem.setHits(question.getHits());
        globalSearchItem.setItemSearchTitle(question.getQuestion());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.QUESTION);
        globalSearchItem.setScore(100L);
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
        globalSearchItem.setHits(200L);
        globalSearchItem.setItemSearchTitle(item.getDescription());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.ATTACHMENT);
        globalSearchItem.setScore(100L);
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
        globalSearchItem.setUrlLocation("/hashtag/"
                + RestFullUtil.formatHasgTag(tag.getHashTag()));
        globalSearchItem.setHits(tag.getHits());
        globalSearchItem.setItemSearchTitle(tag.getHashTag());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.HASHTAG);
        globalSearchItem.setScore(100L);
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
        globalSearchItem.setUrlLocation("/users/profile/"
                + profile.getUsername());
        globalSearchItem.setHits(0L);
        globalSearchItem.setItemSearchTitle(profile.getCompleteName());
        globalSearchItem.setTypeSearchResult(TypeSearchResult.PROFILE);
        globalSearchItem.setScore(100L);
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
}
