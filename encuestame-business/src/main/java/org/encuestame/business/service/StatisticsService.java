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
				tagDetailStats.add(createTagDetailsStats(monthB.toString(),
						countItems));
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
	 * Create hashTag details stats.
	 *
	 * @param label
	 * @param value
	 * @return
	*/
	private HashTagDetailStats createTagDetailsStats(final String label,
	            final Long value) {
		final HashTagDetailStats tagDetails = new HashTagDetailStats();
		tagDetails.setLabel(label);
		tagDetails.setValue(value);
	return tagDetails;
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
			final String tagName, final Integer period,
			final Integer startResults, final Integer maxResults) {
		List<TweetPoll> tweetPollsByHashTag = new ArrayList<TweetPoll>();
		// Gets the tweetpolls by hashtag
		tweetPollsByHashTag = getTweetPollDao()
				.getTweetPollsbyHashTagNameAndDateRange(tagName, period,
						startResults, maxResults);
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
			final String tagName, final Integer period,
			final Integer startResults, final Integer maxResults) {
		List<Poll> pollsByHashTag = new ArrayList<Poll>();
		pollsByHashTag = getPollDao().getPollsbyHashTagNameAndDateRange(
				tagName, period, startResults, maxResults);
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
			final String tagName, final Integer period,
			final Integer startResults, final Integer maxResults) {
		List<Survey> surveysByHashTag = new ArrayList<Survey>();
		surveysByHashTag = getSurveyDaoImp()
				.getSurveysbyHashTagNameAndDateRange(tagName, period,
						startResults, maxResults);
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
    		final String hashTagName, final Integer period,
            final Integer startResults, final Integer maxResults)
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
                            period, startResults, maxResults);
            System.out.println("  Total TweetPolls--> "+ tweetPollsByDateRange.size());
            pollsByDateRange = this.getTotalPollUsageByHashTagAndDateRange(
                    hashTagName, period, startResults, maxResults);
            System.out.println("  Total  Polls--> "+ pollsByDateRange.size());
            surveysByDateRange = this.getTotalSurveyUsageByHashTagAndDateRange(
                    hashTagName, period, startResults, maxResults); 
        }
         
        itemStatDetail.addAll(ConvertDomainBean.convertTweetPollListToItemDetailBean(tweetPollsByDateRange));
        itemStatDetail.addAll(ConvertDomainBean.convertPollListToItemDetailBean(pollsByDateRange)); 
        itemStatDetail.addAll(ConvertDomainBean.convertSurveyListToItemDetailBean(surveysByDateRange));  
        tagDetailStats = this.compareList(itemStatDetail,
        		period); 
		return tagDetailStats; 
    }  
}
