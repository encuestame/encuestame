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
import java.util.Collections;
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
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.stats.HashTagDetailStats;
import org.encuestame.utils.web.stats.ItemStatDetail;
import org.joda.time.DateTime;
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
    
    /**
     *
     */
    private HashTagDetailStats hashTagItemDetailedStats = new HashTagDetailStats();

    /**
     *
     */
    private List<HashTagDetailStats> tagStatsDetail = new ArrayList<HashTagDetailStats>();

    /**
     *
     */
    private Long counterItemsbyMonth = 0L;
    
    /** **/
    private List<HashTagDetailStats> tagDetailStats = new ArrayList<HashTagDetailStats>();
     
    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IStatisticsService#getTotalVotesbyHashTagUsageAndDateRange(java.lang.String, java.lang.Integer)ItemStatDetail
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
	     * Counter items by HashTag.
	     *
	     * @param month
	     * @param counter
	     * @return
	     */
		private Long counterItemsbyHashTag(final int month, Long counter) {
	        switch (month) {
	        case 1:
	            counter++;
	            break;

	        case 2:
	            counter++;
	            break;

	        case 3:
	            counter++;
	            break;

	        case 4:
	            counter++;
	            break;

	        case 5:
	            counter++;
	            break;

	        case 6:
	            counter++;
	            break;

	        case 7:
	            counter++;
	            break;

	        case 8:
	            counter++;
	            break;

	        case 9:
	            counter++;
	            break;

	        case 10:
	            counter++;
	            break;

	        case 11:
	            counter++;
	            break;

	        case 12:
	            counter++;
	            break;

	        default:
	            log.debug("Month not found");
	        }

	        return counter;
	    }

	    /**
	     * Get item creation date value
	     *
	     * @param tpolls
	     * @param polls
	     * @param surveys
	     * @param counter
	     * @return
	     */
	    private DateTime getItemCreationDate(final List<TweetPoll> tpolls,
	            final List<Poll> polls, final List<Survey> surveys,
	            final int counter) {
	        DateTime monthValue;
	        if (tpolls != null) {
	            monthValue = new DateTime(tpolls.get(counter).getCreateDate());

	        } else if (polls != null) {
	            monthValue = new DateTime(polls.get(counter).getCreatedAt());

	        } else {
	            monthValue = new DateTime(surveys.get(counter).getCreatedAt());
	        }
	        return monthValue;
	    }

	    /**
	     *
	     * @param totalList
	     * @param tpolls
	     * @param polls
	     * @param surveys
	     * @param iValue
	     * @return
	     */
	    private List<HashTagDetailStats> addHashTagDetailedStatsbyItem(
	            final int totalList, final List<TweetPoll> tpolls,
	            final List<Poll> polls, final List<Survey> surveys, final int iValue) {
	        int month = 0;

	        int afterMonthValue = 0;
	        int afterMonthIndexValue = 0;

	        DateTime currentMonthDate = this.getItemCreationDate(tpolls, polls,
	                surveys, iValue);
	        month = currentMonthDate.getMonthOfYear();

	        if (iValue < totalList - 1) {
	            afterMonthIndexValue = iValue + 1;
	            DateTime dt2 = this.getItemCreationDate(tpolls, polls, surveys,
	                    afterMonthIndexValue);
	            afterMonthValue = dt2.getMonthOfYear();

	        } else {
	            afterMonthIndexValue = iValue;
	            afterMonthValue = 0;
	        }
	        counterItemsbyMonth = this.counterItemsbyHashTag(month,
	                counterItemsbyMonth);
	        if (month != afterMonthValue) {
	            hashTagItemDetailedStats = this.createTagDetailsStats(
	                    String.valueOf(month), counterItemsbyMonth);
	            tagStatsDetail.add(hashTagItemDetailedStats);

	            counterItemsbyMonth = 0L;
	        }
	        return tagStatsDetail;
	    }


	    private List<HashTagDetailStats> getHashTagItemUsageDetailedByDateRange(final int totalList, final List<TweetPoll> tpolls, final List<Survey> surveys, final List<Poll> polls ){
	        List<HashTagDetailStats> statDetail = new ArrayList<HashTagDetailStats>();
	        if (totalList > 0) {
	            log.debug(" Total items by hashTag  ---> " + totalList);
	            for (int i = 0; i < totalList; i++) {
	                statDetail = this.addHashTagDetailedStatsbyItem(totalList,
	                        tpolls, polls, surveys, i);
	            }
	        } else
	            log.error("Items by HashTag not found");
	        return statDetail;
	    }


	    /**
	     *
	     * @param tpolls
	     * @param surveys
	     * @param polls
	     * @return
	     */
	    private List<HashTagDetailStats> getTotalPoll(final List<TweetPoll> tpolls,
	            final List<Survey> surveys, final List<Poll> polls) {
	        int totalList = 0;
	        List<HashTagDetailStats> itemStatDetail = new ArrayList<HashTagDetailStats>();
	        if (tpolls.size() > 0) {
	            totalList = tpolls.size();
	            itemStatDetail = this.getHashTagItemUsageDetailedByDateRange(totalList, tpolls, null, null);
	        }
	        if (polls.size() > 0) {
	            totalList = polls.size();
	            itemStatDetail = this.getHashTagItemUsageDetailedByDateRange(totalList, null, null, polls);

	        }
	        if (surveys.size() > 0) {
	            totalList = surveys.size();
	            itemStatDetail = this.getHashTagItemUsageDetailedByDateRange(totalList, null, surveys, null);
	        } else {
	            log.error("Items by HashTag not found");
	        }

	        return itemStatDetail;
	    }


	    /**
	     * Get total tweetpoll usage stats by hastag and date range.
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

	 

	/**
	* Get hashtag stats detailed list orderly.
	* @param totalHashTagStatsbyItem
	* @return
	*/
	private List<HashTagDetailStats> getHashTagStatsDetailedtList(
			final List<HashTagDetailStats> totalHashTagStatsbyItem) {
 
	        List<HashTagDetailStats> hashTagStatDetailedList = new ArrayList<HashTagDetailStats>();
	        String previousItemValue = "0";
	        Long actualLabelValue;
	        Long previousLabelValue;
	        Long newItemValue;
	        String hashTagStatActualLabel;
	        Collections.sort(totalHashTagStatsbyItem);
	        for (int i = 0; i < totalHashTagStatsbyItem.size(); i++) {

	            if (totalHashTagStatsbyItem.get(i).getLabel().equals(previousItemValue)) {
	                actualLabelValue = totalHashTagStatsbyItem.get(i).getValue();
	                hashTagStatActualLabel = totalHashTagStatsbyItem.get(i).getLabel();

	                for (int j = 0; j < hashTagStatDetailedList.size(); j++) {
	                    if (hashTagStatDetailedList.get(j).getLabel()
	                            .equals(hashTagStatActualLabel)) {
	                        previousLabelValue = hashTagStatDetailedList.get(j)
	                                .getValue();
	                        newItemValue = actualLabelValue + previousLabelValue;
	                        hashTagStatDetailedList.get(j).setValue(newItemValue);
	                    }
	                }
	            } else {

	                hashTagStatDetailedList.add(totalHashTagStatsbyItem.get(i));
	            }

	            previousItemValue = totalHashTagStatsbyItem.get(i).getLabel();
	        }

	        return hashTagStatDetailedList;
	    }
	     
	 /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#getTweetPollSocialNetworkLinksbyTagAndDateRange(java.lang.String, java.lang.Integer, java.lang.Integer, org.encuestame.utils.enums.TypeSearchResult, java.lang.Integer)
     */
    public List<HashTagDetailStats> getTweetPollSocialNetworkLinksbyTagAndDateRange(
            final String tagName, final Integer initResults,
            final Integer maxResults, final TypeSearchResult filter,
            final Integer period) {
        Long linksbyItem = 0L;

        final List<HashTagDetailStats> tpollListByLink = new ArrayList<HashTagDetailStats>();
        List<HashTagDetailStats> hashTagDetailedStatisticsListbyTweetPoll = new ArrayList<HashTagDetailStats>();
        HashTagDetailStats detailItem = new HashTagDetailStats();

        final List<TweetPoll> tpolls =  getTweetPollsByHashTag(tagName,
                initResults, maxResults, filter);
        int monthValue = 0;

        for (TweetPoll tweetPoll : tpolls) {
            // Get total value by links
            linksbyItem = getTweetPollDao().getSocialLinksByTypeAndDateRange(
                    tweetPoll, null, null, filter, period, initResults,
                    initResults);

            DateTime dt = new DateTime(tweetPoll.getCreateDate());
            monthValue = dt.getMonthOfYear();

            if (linksbyItem > 0) {
                detailItem = createTagDetailsStats(String.valueOf(monthValue),
                        linksbyItem);
                tpollListByLink.add(detailItem);
            }
        }
        hashTagDetailedStatisticsListbyTweetPoll = this
                .getHashTagStatsDetailedtList(tpollListByLink);
        return hashTagDetailedStatisticsListbyTweetPoll;
    }
    
    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.imp.IFrontEndService#
     * getTotalUsagebyHashTagAndDateRange(java.lang.String, java.lang.Integer,
     * java.lang.Integer, java.lang.Integer)
     */
    public List<HashTagDetailStats> getTotalUsagebyHashTagAndDateRange(
            final String hashTagName, final Integer period,
            final Integer startResults, final Integer maxResults)
            throws EnMeNoResultsFoundException {
        // Check if the hashtag exists
        final HashTag tag = this.getHashTag(hashTagName, Boolean.TRUE);

        List<HashTagDetailStats> hashTagDetailedStatisticsList = new ArrayList<HashTagDetailStats>();

        List<HashTagDetailStats> hashTagUsagebyItemAndDateRange = new ArrayList<HashTagDetailStats>();

        List<TweetPoll> tweetPollsByDateRange = new ArrayList<TweetPoll>();
        List<Poll> pollsByDateRange = new ArrayList<Poll>();
        List<Survey> surveysByDateRange = new ArrayList<Survey>();
        // If the tag exists then obtains the total

        if (tag != null) {
            tweetPollsByDateRange = this
                    .getTotalTweetPollUsageByHashTagAndDateRange(hashTagName,
                            period, startResults, maxResults);

            pollsByDateRange = this.getTotalPollUsageByHashTagAndDateRange(
                    hashTagName, period, startResults, maxResults);

            surveysByDateRange = this.getTotalSurveyUsageByHashTagAndDateRange(
                    hashTagName, period, startResults, maxResults);
            hashTagUsagebyItemAndDateRange = this
                    .getTotalPoll(tweetPollsByDateRange, surveysByDateRange,
                            pollsByDateRange);

        }
        hashTagDetailedStatisticsList = this.getHashTagStatsDetailedtList(hashTagUsagebyItemAndDateRange);
        return hashTagDetailedStatisticsList;
    }
    
    
}
