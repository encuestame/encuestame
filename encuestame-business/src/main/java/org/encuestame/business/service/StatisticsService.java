/*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2012
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service;

import java.util.ArrayList; 
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.IStatisticsService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.encuestame.utils.web.stats.ItemStatDetail; 
import org.springframework.stereotype.Service;

/**
 * Statistics Service.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since April 25, 2012
 * @version $Id$
 */
@Service
public class StatisticsService extends AbstractBaseService implements IStatisticsService {  
	
	 /** Statistics Service Log. **/
    private Logger log = Logger.getLogger(this.getClass()); 
 
    
    /** **/
    private List<HashTagDetailStats> tagDetailStats = new ArrayList<HashTagDetailStats>();
     
   /*
    *  (non-Javadoc)
    * @see org.encuestame.core.service.imp.IStatisticsService#getTotalSocialLinksbyHashTagUsageAndDateRange(java.lang.String, java.lang.String)
    */
	public List<HashTagDetailStats> getTotalSocialLinksbyHashTagUsageAndDateRange(
			final String tagName, final String period)
			throws EnMeSearchException {

		List<ItemStatDetail> tpSocialSavePublishedDetail = new ArrayList<ItemStatDetail>();
		List<TweetPollSavedPublishedStatus> tpSavedPublished = new ArrayList<TweetPollSavedPublishedStatus>();
		Integer periodValue = null;
		if (period == null) {
			throw new EnMeSearchException("search params required.");
		} else {

			final List<TweetPoll> tpolls = getTweetPollsByHashTag(tagName, 0,
					100, TypeSearchResult.HASHTAG); 
			for (TweetPoll tweetPoll : tpolls) {
				tpSavedPublished = getTweetPollDao()
						.getSocialLinksByTypeAndDateRange(tweetPoll, null,
								null, TypeSearchResult.TWEETPOLL);
				tpSocialSavePublishedDetail
						.addAll(ConvertDomainBean
								.convertTweetPollSavedPublishedStatusListToItemDetailBean(tpSavedPublished));
			}  
			periodValue = Integer.parseInt(period);
			this.removeDuplicatleItemOutOfRange(tpSocialSavePublishedDetail,
					periodValue);
			tagDetailStats = this.compareList(tpSocialSavePublishedDetail,
					periodValue); 
			return tagDetailStats;
		}
	}
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.encuestame.core.service.imp.IStatisticsService#
	 * getTotalVotesbyHashTagUsageAndDateRange(java.lang.String,
	 * java.lang.Integer)ItemStatDetail
	 */
	public List<HashTagDetailStats> getTotalVotesbyHashTagUsageAndDateRange(final String tagName, final String period) throws EnMeSearchException{  
		Integer periodValue = null;
		List<ItemStatDetail> tpResultsBean = new ArrayList<ItemStatDetail>(); 
		List<TweetPollResult> tpollResults = new ArrayList<TweetPollResult>();
		List<TweetPollSwitch> tpollsSwitch = new ArrayList<TweetPollSwitch>(); 
		if (period == null) {
			throw new EnMeSearchException("search params required.");
		} else {  
			
			final List<TweetPoll> tpolls = getTweetPollsByHashTag(tagName, 0,
					100, TypeSearchResult.HASHTAG);
			log.debug("Total Tweetpolls by hashtagName" + tpolls.size());
			for (TweetPoll tweetPoll : tpolls) {
				tpollsSwitch = getTweetPollDao()
						.getListAnswersByTweetPollAndDateRange(tweetPoll);
				log.debug("Total TweetpollSwitch by tweetPoll -->"
						+ tpollsSwitch.size());
				for (TweetPollSwitch tweetPollSwitch : tpollsSwitch) {
					tpollResults = getTweetPollDao()
							.getTweetPollResultsByTweetPollSwitch(
									tweetPollSwitch);
					log.debug("Total TweetPollResults by tweetPollSwitch -->"
							+ tpollResults.size());
					tpResultsBean
							.addAll(ConvertDomainBean
									.convertTweetPollResultListToItemDetailBean(tpollResults));
				}
			}

		}

		periodValue = Integer.parseInt(period); 
		this.removeDuplicatleItemOutOfRange(tpResultsBean, periodValue);  
		tagDetailStats = this.compareList(tpResultsBean, periodValue);   
		return tagDetailStats;
	}
	
	 
	/**
	 * Remove duplicate item  from {@link TweetPollResult} vote. 
	 * @param itemList
	 * @param period
	 */
	private void removeDuplicatleItemOutOfRange(
			final List<ItemStatDetail> itemList, final Integer period) { 
		Boolean check; 
		for (int i = 0; i < itemList.size(); i++) {
			check = DateUtil.checkDatedWithinAllowableRange(period, itemList
					.get(i).getDate()); 
			if (!check) { 
				itemList.remove(i);
			}
		} 
	}
		 
	/**
	 * 
	 * @param itemList
	 * @param period
	 * @return
	 * @throws EnMeSearchException 
	 */
	private List<HashTagDetailStats> compareList(
			final List<ItemStatDetail> itemList, final Integer period) throws EnMeSearchException {
		Long countItems = 0L;
		Integer month = 0;
		Integer monthB;
		Boolean existItemStatDetailLabel = Boolean.FALSE; 
		for (int i = 0; i < itemList.size(); i++) { 
			monthB = this.getLabelValue(period.toString(), itemList.get(i).getDate());
			 
			for (int j = 0; j < itemList.size(); j++) {
				month =  this.getLabelValue(period.toString(), itemList.get(j).getDate()); 
				if (monthB == month) {
					countItems++;  
				}

			}
			existItemStatDetailLabel = checkLabelExistsHashTagDetailStat(monthB
					.toString());
			if (!existItemStatDetailLabel) { 
				tagDetailStats.add(this.createTagDetailsStats(monthB.toString(),
						countItems, null));
			}
			countItems = 0L; 
		}
		return tagDetailStats;
	}  
	
	/**
	 * 
	 * @param label
	 * @param stat
	 * @param tagList
	 */
	public Boolean checkLabelExistsHashTagDetailStat(final String label) { 
	    Boolean existLabel = Boolean.FALSE;
		if (tagDetailStats.size() > 0) {
			for (HashTagDetailStats hashTagDetailStats : tagDetailStats) {
				if (hashTagDetailStats.getLabel().equals(label)) {
					existLabel = Boolean.TRUE;
				}
			}
		}
		return existLabel; 
	}  
	
	/**
	 * 
	 * @param period
	 * @param pubDate
	 * @return
	 */
	private Integer getLabelValue(final String period, final Date pubDate) {
		Integer labelValue = null;
		final SearchPeriods periodSelected = SearchPeriods
				.getPeriodString(period);

		if (periodSelected.equals(SearchPeriods.ONEYEAR)) {
			labelValue = DateUtil.getValueCurrentMonthOfTheYear(pubDate);

		} else if (periodSelected.equals(SearchPeriods.THIRTYDAYS)) {
			labelValue = DateUtil.getValueCurrentDateOfTheMonths(pubDate);
		} else if (periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) {
			labelValue = DateUtil.getValueHourOfTheDay(pubDate);
		}
		else if (periodSelected.equals(SearchPeriods.SEVENDAYS)) {
			labelValue = DateUtil.getValueCurrentDayOfTheWeek(pubDate);
		}
		return labelValue;
	} 
 
	/**
	 * Get total tweetpoll usage stats by hastag and date range.
	 * 
	 * @param tagName
	 * @param period
	 * @param startResults
	 * @param maxResults
	 * @return
	 */
	private List<TweetPoll> getTotalTweetPollUsageByHashTagAndDateRange(
			final String tagName, final Integer period) {
		List<TweetPoll> tweetPollsByHashTag = new ArrayList<TweetPoll>();
		// Gets the tweetpolls by hashtag
		tweetPollsByHashTag = getTweetPollDao()
				.getTweetPollsbyHashTagNameAndDateRange(tagName, period);
		// Gets the stats detail of hashtags by tweetpoll.
		return tweetPollsByHashTag;
	}

	/**
	 * Get total poll usage stats by hastag and date range.
	 * 
	 * @param tagName
	 * @param period
	 * @param startResults
	 * @param maxResults
	 * @return
	 */
	private List<Poll> getTotalPollUsageByHashTagAndDateRange(
			final String tagName, final Integer period) {
		List<Poll> pollsByHashTag = new ArrayList<Poll>();
		pollsByHashTag = getPollDao().getPollsbyHashTagNameAndDateRange(
				tagName, period);
		return pollsByHashTag;
	}

	/**
	 * Get total survey usage by HashTag name and date range.
	 * 
	 * @param tagName
	 * @param period
	 * @param startResults
	 * @param maxResults
	 * @return
	 */
	private List<Survey> getTotalSurveyUsageByHashTagAndDateRange(
			final String tagName, final Integer period) {
		List<Survey> surveysByHashTag = new ArrayList<Survey>();
		surveysByHashTag = getSurveyDaoImp()
				.getSurveysbyHashTagNameAndDateRange(tagName, period);
		return surveysByHashTag;
	} 
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.encuestame.core.service.imp.IStatisticsService#
	 * getTotalUsagebyHashTagAndDateRange(java.lang.String, java.lang.Integer,
	 * java.lang.Integer, java.lang.Integer)
	 */
    public List<HashTagDetailStats> getTotalUsagebyHashTagAndDateRange(
    		final String hashTagName, final Integer period)
            throws EnMeNoResultsFoundException, EnMeSearchException {
        // Check if the hashtag exists
    	final HashTag tag = this.getHashTag(hashTagName, Boolean.TRUE); 
     
        List<TweetPoll> tweetPollsByDateRange = new ArrayList<TweetPoll>();
        List<Poll> pollsByDateRange = new ArrayList<Poll>();
        List<Survey> surveysByDateRange = new ArrayList<Survey>();
        
        List<ItemStatDetail> itemStatDetail = new ArrayList<ItemStatDetail>();
        // If the tag exists then obtains the total

        if (tag != null) {
            tweetPollsByDateRange = this
                    .getTotalTweetPollUsageByHashTagAndDateRange(hashTagName,
                            period); 
            pollsByDateRange = this.getTotalPollUsageByHashTagAndDateRange(
                    hashTagName, period);
           
            surveysByDateRange = this.getTotalSurveyUsageByHashTagAndDateRange(
                    hashTagName, period); 
        }
         
        itemStatDetail.addAll(ConvertDomainBean.convertTweetPollListToItemDetailBean(tweetPollsByDateRange));
        itemStatDetail.addAll(ConvertDomainBean.convertPollListToItemDetailBean(pollsByDateRange)); 
        itemStatDetail.addAll(ConvertDomainBean.convertSurveyListToItemDetailBean(surveysByDateRange));  
        tagDetailStats = this.compareList(itemStatDetail,
        		period); 
		return tagDetailStats; 
    }  
    
    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IStatisticsService#getTotalHitsUsagebyHashTagAndDateRange(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
     */
	public List<HashTagDetailStats> getTotalHitsUsagebyHashTagAndDateRange(
			final String hashTagName, final Integer period)
			throws EnMeNoResultsFoundException, EnMeSearchException {
		List<Hit> hashTagHits = new ArrayList<Hit>();
		final HashTag tag = this.getHashTag(hashTagName, Boolean.TRUE);
		List<ItemStatDetail> itemStatDetail = new ArrayList<ItemStatDetail>();
		if (tag != null) {
			hashTagHits = getFrontEndDao().getHashTagHitsbyDateRange(
					tag.getHashTagId(), period);
			System.out.println("  Total Hits --> " + hashTagHits.size());

		}

		itemStatDetail.addAll(ConvertDomainBean
				.convertHitListToItemDetailBean(hashTagHits));

		tagDetailStats = this.compareList(itemStatDetail, period);
		return tagDetailStats;

	} 
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.encuestame.core.service.imp.IStatisticsService#getTotalUsageByHashTag
	 * (java.lang.String, java.lang.Integer, java.lang.Integer,
	 * org.encuestame.utils.enums.TypeSearchResult)
	 */
    public HashTagDetailStats getTotalUsageByHashTag(
    		final String tagName,
            final Integer initResults, 
            final Integer maxResults,
            final TypeSearchResult filter) {
        // Validate if tag belongs to hashtag and filter isn't empty.
        Long totalUsagebyHashTag = 0L; 
        final HashTag tag = getHashTagDao().getHashTagByName(tagName);
        if (tag != null) {
            final List<TweetPoll> tweetsbyTag = this.getTweetPollsByHashTag(
                    tagName, initResults, maxResults, filter);
            final int totatTweetPolls = tweetsbyTag.size();
            final List<Poll> pollsbyTag = this.getPollsByHashTag(tagName,
                    initResults, maxResults, filter);
            final int totalPolls = pollsbyTag.size();
            final List<Survey> surveysbyTag = this.getSurveysByHashTag(tagName,
                    initResults, maxResults, filter);
            final int totalSurveys = surveysbyTag.size();
            totalUsagebyHashTag = (long) (totatTweetPolls + totalPolls + totalSurveys);

        }
        final HashTagDetailStats detailStatItem4 = this.createTagDetailsStats("Usage", totalUsagebyHashTag, "times");
        //totalUsagebyHashTag
        return detailStatItem4;
    }
    
    

    public HashTagDetailStats getSocialNetworkUseByHashTag(final String tagName,
            final Integer initResults, final Integer maxResults) {
        // 1- Get tweetPoll, Polls o Survey
        Long linksbyTweetPoll = 0L;
        Long linksbyPoll = 0L;
        Long totalSocialLinks = 0L; 
        linksbyTweetPoll = this.getTweetPollSocialNetworkLinksbyTag(tagName,
                initResults, maxResults, TypeSearchResult.TWEETPOLL);
        linksbyPoll = this.getPollsSocialNetworkLinksByTag(tagName,
                initResults, maxResults, TypeSearchResult.POLL);
        totalSocialLinks = linksbyTweetPoll + linksbyPoll;
        //totalSocialLinks 
        final HashTagDetailStats detailStatItem5 = this.createTagDetailsStats("Social Link xxx", totalSocialLinks, "Tweets");
        return detailStatItem5;
    }
    
    public HashTagDetailStats getHashTagHitsbyName(final String tagName,
            final TypeSearchResult filterBy) { 
        final HashTag tag = getHashTagDao().getHashTagByName(tagName);
        final Long hits = this.getHashTagHits(tag.getHashTagId(),
                TypeSearchResult.HASHTAG);
        // hits
        final HashTagDetailStats detailStatItem3 = this.createTagDetailsStats("Visited", hits, "times");
        return detailStatItem3;
    }
    

    
    public HashTagDetailStats getHashTagUsedOnItemsVoted(final String tagName,
            final Integer initResults, final Integer maxResults) {
        Long totalVotesbyTweetPoll = 0L;
        Long total = 0L;
         
        final List<TweetPoll> tp = this.getTweetPollsByHashTag(tagName, 0, 100,
                TypeSearchResult.HASHTAG);
        for (TweetPoll tweetPoll : tp) {
            totalVotesbyTweetPoll = getTweetPollDao()
                    .getTotalVotesByTweetPollId(tweetPoll.getTweetPollId());
            total = total + totalVotesbyTweetPoll;
        }
        log.debug("Total HashTag used by Tweetpoll voted: " + total);
        final HashTagDetailStats detailStatItem2 = this.createTagDetailsStats("Used on", total, "times");
        // total
        return detailStatItem2;
    }
    

    /**
     * Get Polls by HashTag
     *
     * @param tagName
     * @param initResults
     * @param maxResults
     * @param filter
     * @return
     */
    private List<Poll> getPollsByHashTag(final String tagName,
            final Integer initResults, final Integer maxResults,
            final TypeSearchResult filter) {
        final List<Poll> pollsByTag = getPollDao().getPollByHashTagName(
                tagName, initResults, maxResults, filter);
        return pollsByTag;
    }

	/**
     * Get polls social network links by tag.
     *
     * @param tagName
     * @param initResults
     * @param maxResults
     * @param filter
     * @return
     */
	private Long getPollsSocialNetworkLinksByTag(final String tagName,
            final Integer initResults, final Integer maxResults,
            final TypeSearchResult filter) {
        Long linksbyItem = 0L;
        Long totalLinksByPoll = 0L;

        final List<Poll> polls = this.getPollsByHashTag(tagName, initResults,
                maxResults, filter);
        for (Poll poll : polls) {
            linksbyItem = getTweetPollDao().getSocialLinksByType(null, null,
                    poll, TypeSearchResult.POLL);
            totalLinksByPoll = totalLinksByPoll + linksbyItem;
        }
        return totalLinksByPoll;
    }
	
    /**
     * Get surveys by HashTag.
     *
     * @param tagName
     * @param initResults
     * @param maxResults
     * @param filter
     * @return
     */
    private List<Survey> getSurveysByHashTag(final String tagName,
            final Integer initResults, final Integer maxResults,
            final TypeSearchResult filter) {
        final List<Survey> surveysByTag = getSurveyDaoImp()
                .getSurveysByHashTagName(tagName, initResults, maxResults,
                        filter);
        return surveysByTag;
    } 
    

    /**
     * Get tweetPolls social network links by tag.
     * @param tagName
     * @param initResults
     * @param maxResults
     * @param filter
     * @return
     */
	private Long getTweetPollSocialNetworkLinksbyTag(final String tagName,
            final Integer initResults, final Integer maxResults,
            final TypeSearchResult filter) {
        Long linksbyItem = 0L;
        Long totalLinksByTweetPoll = 0L;
        final List<TweetPoll> tp = this.getTweetPollsByHashTag(tagName,
                initResults, maxResults, filter);
        for (TweetPoll tweetPoll : tp) {
            // Get total value by links
            linksbyItem = getTweetPollDao().getSocialLinksByType(tweetPoll,
                    null, null, TypeSearchResult.TWEETPOLL);
            totalLinksByTweetPoll = totalLinksByTweetPoll + linksbyItem;
        }
        return totalLinksByTweetPoll;
    }  

}
