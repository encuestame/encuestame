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

import javax.servlet.http.HttpServletRequest;

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
import org.encuestame.utils.enums.HashTagRate;
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

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.imp.IStatisticsService#
     * getTotalSocialLinksbyHashTagUsageAndDateRange(java.lang.String,
     * java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    public List<HashTagDetailStats> getTotalSocialLinksbyHashTagUsageAndDateRange(
            final String tagName, final SearchPeriods period, final HttpServletRequest request)
            throws EnMeSearchException {
        List<HashTagDetailStats> tagDetailStatsBySocial = new ArrayList<HashTagDetailStats>();
        List<ItemStatDetail> tpSocialSavePublishedDetail = new ArrayList<ItemStatDetail>();
        List<TweetPollSavedPublishedStatus> tpSavedPublished = new ArrayList<TweetPollSavedPublishedStatus>();
        if (period == null) {
            throw new EnMeSearchException("search params required.");
        } else {
            final List<TweetPoll> tpolls = getTweetPollsByHashTag(tagName, null, null, TypeSearchResult.HASHTAG,
                    period);
            for (TweetPoll tweetPoll : tpolls) {
                tpSavedPublished = getTweetPollDao()
                        .getSocialLinksByTypeAndDateRange(tweetPoll, null,
                                null, TypeSearchResult.TWEETPOLL);
                tpSocialSavePublishedDetail
                        .addAll(ConvertDomainBean
                                .convertTweetPollSavedPublishedStatusListToItemDetailBean(tpSavedPublished));
            }
            this.removeDuplicatleItemOutOfRange(tpSocialSavePublishedDetail,
                    period.toDays());
            tagDetailStatsBySocial = this.compareList(tpSocialSavePublishedDetail,
                    period, request);
            return tagDetailStatsBySocial;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.imp.IStatisticsService#
     * getTotalVotesbyHashTagUsageAndDateRange(java.lang.String,
     * java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    public List<HashTagDetailStats> getTotalVotesbyHashTagUsageAndDateRange(
            final String tagName, final SearchPeriods period,
            final HttpServletRequest request) throws EnMeSearchException {
        List<ItemStatDetail> tpResultsBean = new ArrayList<ItemStatDetail>();
        List<TweetPollResult> tpollResults = new ArrayList<TweetPollResult>();
        List<TweetPollSwitch> tpollsSwitch = new ArrayList<TweetPollSwitch>();
        List<HashTagDetailStats> tagDetailStatsByVotes = new ArrayList<HashTagDetailStats>();
        if (period == null) {
            throw new EnMeSearchException("search params required.");
        } else {

            final List<TweetPoll> tpolls = getTweetPollsByHashTag(tagName, null, null, TypeSearchResult.HASHTAG, period);
            log.debug("Total Tweetpolls by hashtagName" + tpolls.size());
            for (TweetPoll tweetPoll : tpolls) {
                tpollsSwitch = getTweetPollDao()
                        .getListAnswersByTweetPollAndDateRange(tweetPoll);
                log.trace("Total TweetpollSwitch by tweetPoll -->"
                        + tpollsSwitch.size());
                for (TweetPollSwitch tweetPollSwitch : tpollsSwitch) {
                    tpollResults = getTweetPollDao()
                            .getTweetPollResultsByTweetPollSwitch(
                                    tweetPollSwitch);
                    log.trace("Total TweetPollResults by tweetPollSwitch -->"
                            + tpollResults.size());
                    tpResultsBean
                            .addAll(ConvertDomainBean
                                    .convertTweetPollResultListToItemDetailBean(tpollResults));
                }
            }

        }
        this.removeDuplicatleItemOutOfRange(tpResultsBean, period.toDays());
        tagDetailStatsByVotes = this.compareList(tpResultsBean, period, request);
        return tagDetailStatsByVotes;
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
     * @param request
     * @return
     * @throws EnMeSearchException
     */
    private List<HashTagDetailStats> compareList(
            final List<ItemStatDetail> itemList,
            final SearchPeriods period,
            final HttpServletRequest request) throws EnMeSearchException {
        Integer month = 0;
        Integer monthB;
        String rangeSubLabel = null;
        boolean existItemStatDetailLabel = false;
        List<HashTagDetailStats> tagDetailStatsCompare = new ArrayList<HashTagDetailStats>();
        //FIXME: It's limited only for dates in the same year, upgrade to accept dates for different year.
        for (int i = 0; i < itemList.size(); i++) {
            Long countItems = 0L;
            monthB = this.getLabelDateValue(period, itemList.get(i).getDate());
            for (int j = 0; j < itemList.size(); j++) {
                month =  this.getLabelDateValue(period, itemList.get(j).getDate());
                if (monthB == month) {
                    countItems++;
                }
            }
            //check if the label exist previously
            existItemStatDetailLabel = checkLabelExistsHashTagDetailStat(
                    tagDetailStatsCompare,
                    monthB.toString());
            if (!existItemStatDetailLabel) {
                rangeSubLabel = this.getHashTagStatsDataRangeLabel(period.toString(), monthB, request);
                tagDetailStatsCompare.add(this.createTagDetailsStats(monthB.toString(),
                        countItems, rangeSubLabel));
            }
        }
        return tagDetailStatsCompare;
    }

    /**
     * Check if the label exist previously.
     * @param detail List of hashtag details.
     * @param label
     * @return
     */
    private boolean checkLabelExistsHashTagDetailStat(final List<HashTagDetailStats> detail, final String label) {
        boolean existLabel = false;
        if (detail.size() > 0) {
            for (HashTagDetailStats hashTagDetailStats : detail) {
                if (hashTagDetailStats.getLabel().equals(label)) {
                    existLabel = true;
                }
            }
        }
        return existLabel;
    }

    /**
     * Convert a number to correct i18n label.
     * eg: 12 == December / ONEYEAR
     *     6  == Saturday / SEVENDAYS
     *     24 == 24       / TWENTYFOURHOURS
     * @param period the period
     * @param label the label to be translated
     * @param request {@link HttpServletRequest}.
     * @return
     */
    private String getHashTagStatsDataRangeLabel(
            final String period,
            final Integer label,
            final HttpServletRequest request) {
        String dataRangeLabel = null;
        HashTagRate tagRateLabel;
        final SearchPeriods periodSelected = SearchPeriods
                .getPeriodString(period);

        if (periodSelected.equals(SearchPeriods.ONEYEAR)) {
            tagRateLabel = HashTagRate.getHashTagMonthLabel(Integer
                    .toString(label));
            dataRangeLabel = this.convertHashTagDataRangeLabelMessage(
                    tagRateLabel, request, new Object[] {});
        } else if (periodSelected.equals(SearchPeriods.ALLTIME)) {
            dataRangeLabel = String.valueOf(label);
        } else if (periodSelected.equals(SearchPeriods.THIRTYDAYS)) {
            dataRangeLabel = String.valueOf(label);
        } else if (periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) {
            dataRangeLabel = String.valueOf(label);
        } else if (periodSelected.equals(SearchPeriods.SEVENDAYS)) {
            tagRateLabel = HashTagRate.getHashTagWeekDayLabel(
                    Integer
                    .toString(label));
            dataRangeLabel = this.convertHashTagDataRangeLabelMessage(tagRateLabel,
                    request, new Object[] {});
        }
        return dataRangeLabel;
    }

    /**
     * Return the label value of the period based on {@link Date}.
     *   eg :: 22/12/2012 - Period : ONEYEAR
     *   return 12;
     * @param period {@link SearchPeriods}
     * @param pubDate {@link Date}
     * @return
     */
    private Integer getLabelDateValue(
            final SearchPeriods periodSelected,
            final Date pubDate) {
        Integer labelValue = null;
        if (periodSelected.equals(SearchPeriods.ONEYEAR)) { //return motnhs
            labelValue = DateUtil.getValueCurrentMonthOfTheYear(pubDate);
        } else if (periodSelected.equals(SearchPeriods.THIRTYDAYS)) { //return days
            labelValue = DateUtil.getValueCurrentDateOfTheMonths(pubDate);
        } else if (periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) { //return hours
            labelValue = DateUtil.getValueHourOfTheDay(pubDate);
        } else if (periodSelected.equals(SearchPeriods.SEVENDAYS)) { //return days
            labelValue = DateUtil.getValueCurrentDayOfTheWeek(pubDate);
        } else if (periodSelected.equals(SearchPeriods.ALLTIME)) { //return years
            labelValue = DateUtil.getValueCurrentYear(pubDate);
        }
        return labelValue;
    }

    /**
     * Get total tweetpoll usage stats by hastag and date range.
     *
     * @param tagName
     * @param period
     * @return
     */
    private List<TweetPoll> getTotalTweetPollUsageByHashTagAndDateRange(
            final String tagName,
            final SearchPeriods period) {
        List<TweetPoll> tweetPollsByHashTag = new ArrayList<TweetPoll>();
        // Gets the tweetpolls by hashtag
        tweetPollsByHashTag = getTweetPollDao().getTweetPollsbyHashTagNameAndDateRange(tagName, period);
        // Gets the stats detail of hashtags by tweetpoll.
        return tweetPollsByHashTag;
    }

    /**
     *
     * @param tagName
     * @param period
     * @return
     */
    private List<Object[]> getTweetPollsRangeStats(
            final String tagName,
            final SearchPeriods period) {
        List<Object[]> tweetPollsByHashTag = getTweetPollDao().getTweetPollsRangeStats(tagName, period);
        return tweetPollsByHashTag;
    }

    /**
     *
     * @param tagName
     * @param period
     * @return
     */
	private List<Object[]> getPollsRangeStats(final String tagName,
			final SearchPeriods period) {
		List<Object[]> pollsByHashTag = new ArrayList<Object[]>();
		pollsByHashTag = getPollDao().getPollsRangeStats(tagName, period);
		return pollsByHashTag;
	}

	/**
	 *
	 * @param tagName
	 * @param period
	 * @return
	 */
	private List<Object[]> getSurveysRangeStats(
			final String tagName, final SearchPeriods period) {
		List<Object[]> surveysByHashTag = new ArrayList<Object[]>();
		surveysByHashTag = getSurveyDaoImp()
				.getSurveysRangeStats(tagName, period);
		return surveysByHashTag;
	}



    /**
     * Get total poll usage stats by hastag and date range.
     *
     * @param tagName
     * @param period
     * @return
     */
    private List<Poll> getTotalPollUsageByHashTagAndDateRange(
            final String tagName, final SearchPeriods period) {
        List<Poll> pollsByHashTag = new ArrayList<Poll>();
        pollsByHashTag = getPollDao().getPollsbyHashTagNameAndDateRange(
                tagName, period);
        return pollsByHashTag;
    }

    /**
     * Get total survey usage by HashTag name and date range.
     * @param tagName
     * @param period
     * @return
     */
    private List<Survey> getTotalSurveyUsageByHashTagAndDateRange(
            final String tagName, final SearchPeriods period) {
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
     * javax.servlet.http.HttpServletRequest)
     */
    public List<HashTagDetailStats> getTotalUsagebyHashTagAndDateRange(
            final String hashTagName,
            final SearchPeriods period,
            final HttpServletRequest request)
            throws EnMeNoResultsFoundException, EnMeSearchException {
        // Check if the hashtag exists
        final HashTag tag = this.getHashTag(hashTagName, true);
        List<TweetPoll> tweetPollsByDateRange = new ArrayList<TweetPoll>();
        List<Poll> pollsByDateRange = new ArrayList<Poll>();
        List<Survey> surveysByDateRange = new ArrayList<Survey>();
        List<ItemStatDetail> itemStatDetailByUsage = new ArrayList<ItemStatDetail>();
        // If the tag exists then obtains the total
        List<HashTagDetailStats> tagDetailStatsByTagName = new ArrayList<HashTagDetailStats>();
            tweetPollsByDateRange = this.getTotalTweetPollUsageByHashTagAndDateRange(tag.getHashTag(),
                            period);
            pollsByDateRange = this.getTotalPollUsageByHashTagAndDateRange(
                    tag.getHashTag(), period);
            surveysByDateRange = this.getTotalSurveyUsageByHashTagAndDateRange(
                    tag.getHashTag(), period);
        itemStatDetailByUsage.addAll(ConvertDomainBean.convertTweetPollListToItemDetailBean(tweetPollsByDateRange));
        itemStatDetailByUsage.addAll(ConvertDomainBean.convertPollListToItemDetailBean(pollsByDateRange));
        itemStatDetailByUsage.addAll(ConvertDomainBean.convertSurveyListToItemDetailBean(surveysByDateRange));
        tagDetailStatsByTagName = this.compareList(itemStatDetailByUsage,
                period, request);
        return tagDetailStatsByTagName;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.imp.IStatisticsService#
     * getTotalHitsUsagebyHashTagAndDateRange(java.lang.String,
     * java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    public List<HashTagDetailStats> getTotalHitsUsagebyHashTagAndDateRange(
            final String hashTagName, final SearchPeriods period, final HttpServletRequest request)
            throws EnMeNoResultsFoundException, EnMeSearchException {
        List<Hit> hashTagHits = new ArrayList<Hit>();
        List<HashTagDetailStats> tagDetailStatsByHits = new ArrayList<HashTagDetailStats>();
        final HashTag tag = this.getHashTag(hashTagName, Boolean.TRUE);
        List<ItemStatDetail> itemStatDetailByHits = new ArrayList<ItemStatDetail>();
        if (tag != null) {
            hashTagHits = getFrontEndDao().getHashTagHitsbyDateRange(
                    tag.getHashTagId(), period.toDays());
        }
        itemStatDetailByHits.addAll(ConvertDomainBean
                .convertHitListToItemDetailBean(hashTagHits));
        tagDetailStatsByHits = this.compareList(itemStatDetailByHits, period, request);
        return tagDetailStatsByHits;
        //return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IStatisticsService#getTotalUsageByHashTag
     * (java.lang.String, java.lang.Integer, java.lang.Integer,
     * org.encuestame.utils.enums.TypeSearchResult,
     * javax.servlet.http.HttpServletRequest)
     */
    public HashTagDetailStats getTotalUsageByHashTag(
            final String tagName,
            final Integer initResults,
            final Integer maxResults,
            final TypeSearchResult filter,
            final HttpServletRequest request,
            final SearchPeriods periods) throws EnMeNoResultsFoundException {
        // Validate if tag belongs to hashtag and filter isn't empty.
        Long totalUsagebyHashTag = 0L;
        final HashTag tag = getHashTag(tagName, true);
        if (tag != null) {
            final List<TweetPoll> tweetsbyTag = this.getTweetPollsByHashTag(
                    tagName, initResults, maxResults, filter, periods);
            final int totatTweetPolls = tweetsbyTag.size();
            final List<Poll> pollsbyTag = this.getPollsByHashTag(tagName,
                    initResults, maxResults, filter, periods);
            final int totalPolls = pollsbyTag.size();
            final List<Survey> surveysbyTag = this.getSurveysByHashTag(tagName,
                    initResults, maxResults, filter, periods);
            final int totalSurveys = surveysbyTag.size();
            totalUsagebyHashTag = (long) (totatTweetPolls + totalPolls + totalSurveys);

        }
        final HashTagDetailStats detailStatItem = this
                .createHashTagDetailButtonStats(HashTagRate.LBL_USAGE,
                        totalUsagebyHashTag, HashTagRate.SUB_LBL_TIMES, request);
        return detailStatItem;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.imp.IStatisticsService#
     * getSocialNetworkUseByHashTag(java.lang.String, java.lang.Integer,
     * java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    public HashTagDetailStats getSocialNetworkUseByHashTag(
            final String tagName,
            final Integer initResults,
            final Integer maxResults,
            final HttpServletRequest request,
            final SearchPeriods searchPeriods) {
        // 1- Get tweetPoll, Polls o Survey
        Long linksbyTweetPoll = 0L;
        Long linksbyPoll = 0L;
        Long totalSocialLinks = 0L;
        linksbyTweetPoll = this.getTweetPollSocialNetworkLinksbyTag(tagName,
                initResults, maxResults, TypeSearchResult.TWEETPOLL, searchPeriods);
        linksbyPoll = this.getPollsSocialNetworkLinksByTag(tagName,
                initResults, maxResults, TypeSearchResult.POLL, searchPeriods);
        totalSocialLinks = linksbyTweetPoll + linksbyPoll;
        //TODO: add Survey support
        final HashTagDetailStats detailStatItem = this
                .createHashTagDetailButtonStats(HashTagRate.LBL_SOCIAL_NETWORK,
                        totalSocialLinks, HashTagRate.SUB_LBL_TWEETS, request);
        return detailStatItem;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IStatisticsService#getHashTagHitsbyName
     * (java.lang.String, org.encuestame.utils.enums.TypeSearchResult,
     * javax.servlet.http.HttpServletRequest)
     */
    public HashTagDetailStats getHashTagHitsbyName(
            final String tagName,
            final TypeSearchResult filterBy,
            final HttpServletRequest request,
            final SearchPeriods periods) throws EnMeNoResultsFoundException {
        final HashTag tag = getHashTag(tagName, true);
        final Long hits = this.getTotalHits(tag.getHashTagId(),
                TypeSearchResult.HASHTAG, periods);
        final HashTagDetailStats detailStatItem = this
                .createHashTagDetailButtonStats(HashTagRate.LBL_HITS, hits,
                        HashTagRate.SUB_LBL_TIMES, request);
        return detailStatItem;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IStatisticsService#getHashTagUsedOnItemsVoted
     * (java.lang.String, java.lang.Integer, java.lang.Integer,
     * javax.servlet.http.HttpServletRequest)
     */
    public HashTagDetailStats getHashTagUsedOnItemsVoted(final String tagName,
            final Integer initResults, final Integer maxResults,
            final HttpServletRequest request, final SearchPeriods periods) {
        Long totalVotesbyTweetPoll = 0L;
        Long totalVotesbyPoll = 0L;
        Long totalHashTagUsedOnItemsVoted = 0L;

        totalVotesbyTweetPoll = this.retrieveTweetPollTotalVotesByHashTag(
                tagName, periods);
        log.debug("Total tweetPoll votes by hashtag name : " + totalVotesbyTweetPoll);
        totalVotesbyPoll = this.retrievePollTotalVotesByHashTag(tagName,
                periods);
        log.debug("Total poll votes by hashtag name : " + totalVotesbyPoll);
        totalHashTagUsedOnItemsVoted = totalVotesbyTweetPoll + totalVotesbyPoll;
        log.debug("Total HashTag used votes : " + totalHashTagUsedOnItemsVoted);
        final HashTagDetailStats detailStatItem = this
                .createHashTagDetailButtonStats(HashTagRate.LBL_VOTES,
                        totalHashTagUsedOnItemsVoted,
                        HashTagRate.SUB_LBL_VOTES, request);
        return detailStatItem;
    }

    /**
     *
     * @param tagName
     * @param period
     * @return
     */
    private Long retrievePollTotalVotesByHashTag(final String tagName,
            final SearchPeriods period) {
        Long totalPollVotes = 0L;
        final List<Poll> polls = this.getPollsByHashTag(tagName, null, null,
                TypeSearchResult.HASHTAG, period);
        for (Poll poll : polls) {
            totalPollVotes = totalPollVotes
                    + getPollDao().retrievePollResults(poll).size();

        }
        return totalPollVotes;
    }

    /**
     *
     * @param tagName
     * @param period
     * @return
     */
    private Long retrieveTweetPollTotalVotesByHashTag(final String tagName,
            final SearchPeriods period) {
        Long totalTweetPollVotes = 0L;
        final List<TweetPoll> tweetPolls = this.getTweetPollsByHashTag(tagName,
                null, null, TypeSearchResult.HASHTAG, period);

        log.debug("TweetPolls by HashTag ****************************************** " + tweetPolls.size() + "Period ***********************" + period.toString());
        for (TweetPoll tweetPoll : tweetPolls) {

            totalTweetPollVotes = totalTweetPollVotes
                    + getTweetPollDao().getTotalVotesByTweetPollId(
                            tweetPoll.getTweetPollId());
        }
         log.debug("Total Votes by Tweetpoll ******************************************" + totalTweetPollVotes);
        return totalTweetPollVotes;
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
            final TypeSearchResult filter, final SearchPeriods searchPeriods) {
        final List<Poll> pollsByTag = getPollDao().getPollByHashTagName(
                tagName, initResults, maxResults, filter, searchPeriods);
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
    private Long getPollsSocialNetworkLinksByTag(
            final String tagName,
            final Integer initResults,
            final Integer maxResults,
            final TypeSearchResult filter,
            final SearchPeriods searchPeriods) {
        Long linksbyItem = 0L;
        Long totalLinksByPoll = 0L;
        final List<Poll> polls = this.getPollsByHashTag(tagName, initResults,
                maxResults, filter, searchPeriods);
        for (Poll poll : polls) {
            linksbyItem = getTweetPollDao().getSocialLinksByType(null, null,
                    poll, TypeSearchResult.POLL);
            totalLinksByPoll = totalLinksByPoll + linksbyItem;
        }
        return totalLinksByPoll;
    }

    /**
     * Get tweetPolls social network links by tag.
     * @param tagName
     * @param initResults
     * @param maxResults
     * @param filter
     * @return
     */
    private Long getTweetPollSocialNetworkLinksbyTag(
            final String tagName,
            final Integer initResults,
            final Integer maxResults,
            final TypeSearchResult filter,
            final SearchPeriods searchPeriods) {
        Long linksbyItem = 0L;
        Long totalLinksByTweetPoll = 0L;
        final List<TweetPoll> tp = this.getTweetPollsByHashTag(tagName,
                initResults, maxResults, filter, searchPeriods);
        for (TweetPoll tweetPoll : tp) {
            // Get total value by links
            linksbyItem = getTweetPollDao().getSocialLinksByType(tweetPoll,
                    null, null, TypeSearchResult.TWEETPOLL);
            totalLinksByTweetPoll = totalLinksByTweetPoll + linksbyItem;
        }
        return totalLinksByTweetPoll;
    }

    /**
     * Create {@link HashTagDetailStats} for {@link HashTag} stats button.
     * @param label
     * @param value
     * @param subLabel
     * @param request
     * @return
     */
    private HashTagDetailStats createHashTagDetailButtonStats(
            final HashTagRate label, final Long value,
            final HashTagRate subLabel, final HttpServletRequest request) {
        final HashTagDetailStats tagDetails = new HashTagDetailStats();
        tagDetails.setLabel(this.convertHashTagButtonStatsLabel(label, request,
                new Object[] {}));
        tagDetails.setValue(value);
        tagDetails.setSubLabel(this.convertHashTagButtonStatsLabel(subLabel,
                request, new Object[] {}));

        return tagDetails;
    }

	/*
	 * (non-Javadoc)
	 *
	 * @see org.encuestame.core.service.imp.IStatisticsService#
	 * getTotalUsagebyHashtagAndDateRangeGraph(java.lang.String,
	 * org.encuestame.utils.enums.SearchPeriods,
	 * javax.servlet.http.HttpServletRequest)
	 */
	public List<HashTagDetailStats> getTotalUsagebyHashtagAndDateRangeGraph(
			final String hashTagName, final SearchPeriods period,
			final HttpServletRequest request)
			throws EnMeNoResultsFoundException, EnMeSearchException {
		// Check if the hashtag exists
		final HashTag tag = this.getHashTag(hashTagName, true);
		List<Object[]> tweetPollsByDateRange = new ArrayList<Object[]>();
		List<Object[]> pollsByDateRange = new ArrayList<Object[]>();
		List<Object[]> surveysByDateRange = new ArrayList<Object[]>();

		List<ItemStatDetail> itemStatDetailByUsage = new ArrayList<ItemStatDetail>();
		// If the tag exists then obtains the total
		List<HashTagDetailStats> tagDetailStatsByTagName = new ArrayList<HashTagDetailStats>();
		tweetPollsByDateRange = this.getTweetPollsRangeStats(tag.getHashTag(),
				period);
		System.out.println("TweetPolls Service -->" + tweetPollsByDateRange.size());
		pollsByDateRange = this.getPollsRangeStats(tag.getHashTag(), period);
		System.out.println("POLLS Service -->" + pollsByDateRange.size());

		surveysByDateRange = this.getSurveysRangeStats(tag.getHashTag(), period);

 		itemStatDetailByUsage.addAll(ConvertDomainBean
 				.convertObjectListToItemDetailBean(tweetPollsByDateRange));
		itemStatDetailByUsage.addAll(ConvertDomainBean
				.convertObjectListToItemDetailBean(pollsByDateRange));
 		itemStatDetailByUsage.addAll(ConvertDomainBean
 				.convertObjectListToItemDetailBean(surveysByDateRange));

		tagDetailStatsByTagName = this.compareHashtagListGraph(
				itemStatDetailByUsage, period, request);
		return tagDetailStatsByTagName;
	}


   public List<HashTagDetailStats> getTotalHitsUsagebyHashTagAndDateRangeGraph(
           final String hashTagName, final SearchPeriods period, final HttpServletRequest request)
           throws EnMeNoResultsFoundException, EnMeSearchException {
       List<Hit> hashTagHits = new ArrayList<Hit>();
       List<HashTagDetailStats> tagDetailStatsByHits = new ArrayList<HashTagDetailStats>();
       final HashTag tag = this.getHashTag(hashTagName, Boolean.TRUE);
       List<ItemStatDetail> itemStatDetailByHits = new ArrayList<ItemStatDetail>();
       if (tag != null) {
           hashTagHits = getFrontEndDao().getHashTagHitsbyDateRange(
                   tag.getHashTagId(), period.toDays());
       }
       itemStatDetailByHits.addAll(ConvertDomainBean
               .convertHitListToItemDetailBean(hashTagHits));
       tagDetailStatsByHits = this.compareList(itemStatDetailByHits, period, request);
       return tagDetailStatsByHits;
       //return null;
   }



    /**
     *
     * @param itemList
     * @param period
     * @param request
     * @return
     * @throws EnMeSearchException
     */
    public List<HashTagDetailStats> compareHashtagListGraph(
            final List<ItemStatDetail> itemList,
            final SearchPeriods period,
            final HttpServletRequest request) throws EnMeSearchException {
        Integer month = 0;
        Integer monthB;
        Integer dayA=0;
        Integer dayB=0;

        Integer yearA=0;
        Integer yearB=0;

        boolean existItemStatDetailLabel = false;
        List<HashTagDetailStats> tagDetailStatsCompare = new ArrayList<HashTagDetailStats>();
        //FIXME: It's limited only for dates in the same year, upgrade to accept dates for different year.

        for (int i = 0; i < itemList.size(); i++) {
            Long countItems = 0L;
           // Get the date values.
            monthB = DateUtil.getValueCurrentMonthOfTheYear(itemList.get(i).getDate());
            dayB = DateUtil.getValueCurrentDateOfTheMonths(itemList.get(i).getDate());
            yearB = DateUtil.getValueCurrentYear(itemList.get(i).getDate());

            for (int j = 0; j < itemList.size(); j++) {
                month = DateUtil.getValueCurrentMonthOfTheYear(itemList.get(j).getDate());
                dayA = DateUtil.getValueCurrentDateOfTheMonths(itemList.get(j).getDate());
                yearA = DateUtil.getValueCurrentYear(itemList.get(j).getDate());
                if ((monthB.equals(month)) && (dayB.equals(dayA))  && (yearB.equals(yearA)) ) {
                    countItems++;
                }
            }
            //check if the label exist previously

            final DateTime myDate = new DateTime(itemList.get(i).getDate());
            existItemStatDetailLabel = checkLabelExistsHashTagDetailStatGraph(
                    tagDetailStatsCompare,
                    myDate);
            if (!existItemStatDetailLabel) {

                tagDetailStatsCompare.add(this.createHastagItemDetailGraph(monthB.toString(),
                        countItems, "Compare", myDate.getMillis(), myDate  ));
            }// createTagDetailsStats2
        }

        return tagDetailStatsCompare;
    }

    /**
     *
     * @param detail
     * @param dayLabel
     * @return
     */
    private boolean checkLabelExistsHashTagDetailStatGraph(
            final List<HashTagDetailStats> detail,
            final DateTime dayLabel) {
        boolean existLabel = false;

        if (detail.size() > 0) {
            for (HashTagDetailStats hashTagDetailStats : detail) {

                final int detailStat = hashTagDetailStats.getDateValue().toLocalDate().compareTo(dayLabel.toLocalDate());
                // Values:  0 = Exist 1 = No exists
                if(detailStat ==0){
                    existLabel = true;
                }
            }
        }
        return existLabel;
    }
}
