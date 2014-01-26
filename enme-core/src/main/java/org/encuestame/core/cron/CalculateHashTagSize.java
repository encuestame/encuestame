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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.dao.IHashTagDao;
import org.encuestame.persistence.dao.ITweetPoll;
import org.encuestame.persistence.dao.imp.HashTagDao;
import org.encuestame.persistence.dao.imp.TweetPollDao;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagRanking;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.SearchPeriods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * This job calculate every day the size of each hashgtag in the database.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 19, 2011
 */
public class CalculateHashTagSize {

    /** Front End Service Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /** Maximum results query. **/
    private Integer MAX_RESULTS = 0;

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
    @Scheduled(cron = "${cron.calculateReindex}")
    public void calculate(){
    	if (EnMePlaceHolderConfigurer.getSystemInitialized()) {
	        log.info("************ Start hashtag calculate job **************");
	
	        double average = 0;
	        int total = 0;
	        double score = 0;
	        double scoreRank = 0;
	        double averageHashTagRanking = 0;
	        Date currentDate = DateUtil.getCurrentCalendarDate();
	        //store the max min values
	        final List<Long> maxMinTotal = new ArrayList<Long>();  
	
	        final List<HashTag> tags = getHashTagDao().getHashTags(null, 0, "");
	
	        log.debug("HashTag to process "+tags.size());
	
	        total = tags.size();
	
	        final List<Object[]> maxMin = getHashTagDao().getMaxMinTagFrecuency();
	        long maxFrecuency = 0;
	        long minFrecuency = 0;
	
	        if (maxMin.get(0) != null) {
	        	maxFrecuency =  (Long) maxMin.get(0)[0]; // Max
	            minFrecuency =  (Long) maxMin.get(0)[1]; // Min
	        }
	        
	         
	        List<HashTagRanking> hashTagRankingList = getHashTagDao()
	                .getHashTagRankStats(currentDate);
	      
	        for (HashTag hashTag : tags) {
	        	final HashTagRanking tagRanking;
	            log.debug("Calculate for: "+hashTag.getHashTag()+" size after calculate: "+hashTag.getSize());
	
	            long tagFrecuency = getHashTagFrecuency(hashTag.getHashTag(), this.INIT_RESULTS, this.MAX_RESULTS);
	            log.debug("-------- tag frecuency: " + tagFrecuency);
	            long relevance = (tagFrecuency + (hashTag.getHits() == null ? 0 : hashTag.getHits()));
	            long logFrecuency = Math.round(EnMeUtils.calculateSizeTag(relevance, maxFrecuency, minFrecuency));
	            
	            score += logFrecuency; 
	              
	            scoreRank= Math.round((double) relevance / (double)total); 
	            averageHashTagRanking  = scoreRank < 1 ? 1 : Math.round(scoreRank); 
	            maxMinTotal.add(logFrecuency);
	            hashTag.setSize(Long.valueOf(logFrecuency));
	
	            log.debug("Calculate for: "+hashTag.getHashTag()+" size before calculate: "+logFrecuency);
	
	            hashTag.setCreatedAt(Calendar.getInstance().getTime());
	            getHashTagDao().saveOrUpdate(hashTag);
	            // Save table
	            if(hashTagRankingList.size() == 0){
	            	 tagRanking = this.createHashTagRanking(averageHashTagRanking, hashTag, currentDate);
	            	 getHashTagDao().saveOrUpdate(tagRanking);
	            } else {
	            	log.debug("Process has been executed today`s date");
	            } 
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
    }
    
    
    /**
     * Create {@link HashTagRanking}.
     * @param average
     * @param tag
     * @param rankingDate
     * @return
     */
    private HashTagRanking createHashTagRanking(final double average, final HashTag tag, final Date rankingDate){
    	final HashTagRanking tagRank = new HashTagRanking();
    	tagRank.setAverage(average);
    	tagRank.setHashTag(tag); 
    	tagRank.setRankingDate(rankingDate);
    	return tagRank; 
    }

    /**
     * Get hashTag counter.
     * @param hashTagId
     * @param limit
     * @return
     */
    public Long getHashTagFrecuency(final String tagName, final Integer initResults, final Integer limit){
        final Integer totalRelTweetPoll;
		final List<TweetPoll> tweetPolls = getTweetPoll()
				.getTweetpollByHashTagName(tagName, initResults, limit, null,
						SearchPeriods.ALLTIME);
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
