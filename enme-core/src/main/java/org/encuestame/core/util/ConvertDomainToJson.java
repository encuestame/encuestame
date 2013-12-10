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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.json.TweetItemPublishedResponse;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since May 10, 2011
 */
public class ConvertDomainToJson {
	
	/**
     * Log.
     */
    private static Log log = LogFactory.getLog(ConvertDomainToJson.class);


    /**
     *
     * @param savedPublishedStatus
     * @return
     */
    public static final List<TweetItemPublishedResponse> convertTweetPollStatusToJson(
            final List<TweetPollSavedPublishedStatus> savedPublishedStatus) {
        final List<TweetItemPublishedResponse> list = new ArrayList<TweetItemPublishedResponse>();
        for (TweetPollSavedPublishedStatus tweetPollSavedPublishedStatus : savedPublishedStatus) {
        	log.debug("*******************************************************************************");
        	log.debug(savedPublishedStatus.toString());
        	log.debug("*******************************************************************************");
            list.add(convertTweetPollStatusToJson(tweetPollSavedPublishedStatus));
        }
        return list;
    }

    /**
     *
     * @param savedPublishedStatus
     * @return
     */
    public static final TweetItemPublishedResponse convertTweetPollStatusToJson(
            final TweetPollSavedPublishedStatus savedPublishedStatus) {
        final TweetItemPublishedResponse tweetResponse = new TweetItemPublishedResponse();
		tweetResponse.datePublished = savedPublishedStatus
				.getPublicationDateTweet() == null ? null : DateUtil
				.getFormatDate(savedPublishedStatus.getPublicationDateTweet());
		tweetResponse.setId(savedPublishedStatus.getId());
        tweetResponse.textTweeted = savedPublishedStatus.getTweetContent();
        tweetResponse.statusTweet = savedPublishedStatus.getStatus().name();
        tweetResponse.statusDescriptionTweet = savedPublishedStatus.getDescriptionStatus();
        tweetResponse.socialAccountId = savedPublishedStatus.getSocialAccount().getId();
        tweetResponse.sourceTweet = savedPublishedStatus.getApiType().name();
        tweetResponse.tweetId = savedPublishedStatus.getTweetId() == null ? "" : savedPublishedStatus.getTweetId();
		tweetResponse.tweetUrl = savedPublishedStatus.getTweetId() == null ? ""
				: SocialUtils.getSocialTweetPublishedUrl(savedPublishedStatus
						.getTweetId(),
                savedPublishedStatus.getSocialAccount().getSocialAccountName(),
                savedPublishedStatus.getSocialAccount().getAccounType());
        tweetResponse.socialAccountName = savedPublishedStatus.getSocialAccount().getSocialAccountName();
        return tweetResponse;
    }
}
