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
package org.encuestame.core.cron;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.dao.IFrontEndDao;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Calculate relevance.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 07, 2011
 */
public class CalculateRelevance {

     /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private ITweetPoll tweetPollDao;

    private IFrontEndDao frontEndDao;

    /**
     * Calculate relevance.
     */
    public void calculateRelevance() {
        log.info("************ Start calculate relevance item **************");
        final Calendar dateFrom = Calendar.getInstance();
        dateFrom.add(Calendar.DATE, -5);
        final Calendar datebefore = Calendar.getInstance();
        dateFrom.add(Calendar.DATE, -5);
        final Calendar todayDate = Calendar.getInstance();

        final List<TweetPoll> tweetPolls = getTweetPollDao().getTweetPolls(50,
                0, dateFrom.getTime());
        for (TweetPoll tweetPoll : tweetPolls) {
            long likeVote = tweetPoll.getLikeVote();
            long dislikeVote = tweetPoll.getDislikeVote();
            long hits = tweetPoll.getHits();
            long maxVotebyUser = getTweetPollDao()
                    .getMaxTweetPollLikeVotesbyUser(
                            tweetPoll.getEditorOwner().getUid(),
                            datebefore.getTime(), todayDate.getTime());
            long relevance = EnMeUtils.calculateRelevance(likeVote,
                    dislikeVote, maxVotebyUser, hits);
            tweetPoll.setRelevance(relevance);
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }
    }

    /**
     * @return the frontEndDao
     */
    public IFrontEndDao getFrontEndDao() {
        return frontEndDao;
    }

    /**
     * @param frontEndDao the frontEndDao to set
     */
    public void setFrontEndDao(IFrontEndDao frontEndDao) {
        this.frontEndDao = frontEndDao;
    }


    /**
     * @return the tweetPollDao
     */
    public ITweetPoll getTweetPollDao() {
        return tweetPollDao;
    }


    /**
     * @param tweetPollDao the tweetPollDao to set
     */
    public void setTweetPollDao(final ITweetPoll tweetPollDao) {
        this.tweetPollDao = tweetPollDao;
    }
}
