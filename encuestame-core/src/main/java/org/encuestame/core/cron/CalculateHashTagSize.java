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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.dao.IHashTagDao;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.dao.imp.HashTagDao;
import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This job calculate every day the size of each hashgtag in the database.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 19, 2011
 */
public class CalculateHashTagSize {

    /** Front End Service Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /** Maximum results query. **/
    private Integer MAX_RESULTS = 10;

    /** Init results query. **/
    private Integer INIT_RESULTS = 0;
    
    /**
     * {@link HashTagDao}.
     */
    @Autowired
    private IHashTagDao hashTagDao;

    /**
     * {@link TweetPollDao}.
     */
    @Autowired
    private ITweetPoll tweetPoll;

    /**
     * Calculate all hashtag size.
     */
    public void calculate(){
        log.info("************ Start hashtag calculate job **************");

        double average = 0;
        int total = 0;
        double score = 0;

        List<Long> maxMinTotal = new ArrayList<Long>();

        final List<HashTag> tags = getHashTagDao().getHashTags(null, 0, "");
        log.debug("HashTag to process "+tags.size());
        total = tags.size();

        final List<Object[]> maxMin = getHashTagDao().getMaxMinTagFrecuency();
        long maxFrecuency = 0;
        long minFrecuency = 0;

        if (maxMin.get(0) != null) {
            minFrecuency =  (Long) maxMin.get(0)[0];
            minFrecuency =  (Long) maxMin.get(0)[1];
        }

        for (HashTag hashTag : tags) {
            log.debug("Calculate for: "+hashTag.getHashTag()+" size after calculate: "+hashTag.getSize());
            long tagFrecuency = getHashTagFrecuency(hashTag.getHashTag(), this.INIT_RESULTS, this.MAX_RESULTS);
            long relevance = (tagFrecuency + (hashTag.getHits() == null ? 0 : hashTag.getHits()));
            long logFrecuency = Math.round(EnMeUtils.calculateSizeTag(relevance, maxFrecuency, minFrecuency));
            log.debug("-------- log frecuency: "+logFrecuency);
            score += logFrecuency;
            maxMinTotal.add(logFrecuency);
            hashTag.setSize(Long.valueOf(logFrecuency));
            log.debug("Calculate for: "+hashTag.getHashTag()+" size before calculate: "+logFrecuency);
            hashTag.setUpdatedDate(Calendar.getInstance().getTime());
            getHashTagDao().saveOrUpdate(hashTag);
        }
        average = (double) score / (double)total;
        log.info("*******************************");
        log.info("******* Resume of Process *****");
        log.info("-------------------------------");
        log.info("|  Max Frec : "+maxFrecuency+"            |");
        log.info("|  Min Frec : "+minFrecuency+"            |");
        log.info("|  Total : "+total+"            |");
        log.info("|  Score : "+Math.round(score)+"           |");
        log.info("|  Average : "+Math.round(average)+"       |");
        log.info("|  Max : "+Collections.max(maxMinTotal)+"               |");
        log.info("|  Min : "+Collections.min(maxMinTotal)+"               |");
        log.info("-------------------------------");
        log.info("*******************************");
        log.info("************ Finished Start hashtag calculate job **************");
    }

    /**
     * Get hashTag counter.
     * @param hashTagId
     * @param limit
     * @return
     */
    public Long getHashTagFrecuency(final String tagName, final Integer initResults, final Integer limit){
        final Integer totalRelTweetPoll;
        final List<TweetPoll> tweetPolls = getTweetPoll().getTweetpollByHashTagName(tagName, initResults, limit, null);
        totalRelTweetPoll = tweetPolls.size();
        //TODO:Pending count relevance hashtags for polls and surveys.
        return totalRelTweetPoll.longValue();
    }

    /**
     * @return the hashTagDao
     */
    public IHashTagDao getHashTagDao() {
        return hashTagDao;
    }

    /**
     * @param hashTagDao
     *            the hashTagDao to set
     */
    public void setHashTagDao(final IHashTagDao hashTagDao) {
        this.hashTagDao = hashTagDao;
    }

    /**
     * @return the tweetPoll
     */
    public ITweetPoll getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll the tweetPoll to set
     */
    public void setTweetPoll(final ITweetPoll tweetPoll) {
        this.tweetPoll = tweetPoll;
    }
}
