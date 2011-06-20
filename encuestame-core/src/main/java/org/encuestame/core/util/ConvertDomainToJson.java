/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.util;

import java.util.ArrayList;
import java.util.List;

import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.json.TweetPollStatus;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since May 10, 2011
 */
public class ConvertDomainToJson {


    /**
     *
     * @param savedPublishedStatus
     * @return
     */
    public static final List<TweetPollStatus> convertTweetPollStatusToJson(
            final List<TweetPollSavedPublishedStatus> savedPublishedStatus) {
        final List<TweetPollStatus> list = new ArrayList<TweetPollStatus>();
        for (TweetPollSavedPublishedStatus tweetPollSavedPublishedStatus : savedPublishedStatus) {
            list.add(convertTweetPollStatusToJson(tweetPollSavedPublishedStatus));
        }
        return list;
    }

    /**
     *
     * @param savedPublishedStatus
     * @return
     */
    public static final TweetPollStatus convertTweetPollStatusToJson(
            final TweetPollSavedPublishedStatus savedPublishedStatus) {
        final TweetPollStatus pollStatus = new TweetPollStatus();
        pollStatus.datePublished = DateUtil.getFormatDate(savedPublishedStatus.getPublicationDateTweet());
        pollStatus.textTweeted = savedPublishedStatus.getTweetContent();
        pollStatus.statusTweet = savedPublishedStatus.getStatus().name();
        pollStatus.statusDescriptionTweet = savedPublishedStatus.getDescriptionStatus();
        pollStatus.socialAccountId = savedPublishedStatus.getTwitterAccount().getId();
        pollStatus.sourceTweet = savedPublishedStatus.getApiType().name();
        return pollStatus;
    }
}
