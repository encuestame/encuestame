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
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.dao.SearchPeriods;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.TypeSearchResult;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMePollNotFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.SurveyBean;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Front End Service.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 11:29:38 AM
 * @version $Id:$
 */
@Service
public class FrontEndService extends AbstractBaseService implements IFrontEndService {

    /** Front End Service Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /** Max Results. **/
    private final Integer MAX_RESULTS = 15;

    /** **/
    @Autowired
    private TweetPollService tweetPollService;

    /** **/
    @Autowired
    private PollService pollService;

    @Autowired
    private SurveyService surveyService;

    /**
     * Search Items By tweetPoll.
     * @param maxResults limit of results to return.
     * @return result of the search.
     * @throws EnMeSearchException search exception.
     */
    public List<TweetPollBean> searchItemsByTweetPoll(
                final String period,
                final Integer start,
                Integer maxResults,
                final HttpServletRequest request)
                throws EnMeSearchException{
        final List<TweetPollBean> results = new ArrayList<TweetPollBean>();
        if(maxResults == null){
            maxResults = this.MAX_RESULTS;
        }
        log.debug("Max Results "+maxResults);
        final List<TweetPoll> items = new ArrayList<TweetPoll>();
        if (period == null ) {
            throw new EnMeSearchException("search params required.");
        } else {
            final SearchPeriods periodSelected = SearchPeriods.getPeriodString(period);
            if (periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast24(start, maxResults));
            } else if(periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast24(start, maxResults));
            } else if(periodSelected.equals(SearchPeriods.SEVENDAYS)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast7Days(start, maxResults));
            } else if(periodSelected.equals(SearchPeriods.THIRTYDAYS)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast30Days(start, maxResults));
            } else if(periodSelected.equals(SearchPeriods.ALLTIME)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndAllTime(start, maxResults));
            }
            log.debug("TweetPoll "+items.size());
            results.addAll(ConvertDomainBean.convertListToTweetPollBean(items));
            for (TweetPollBean tweetPoll : results) {
                tweetPoll = convertTweetPollRelativeTime(tweetPoll, request);
            }

        }
        return results;
    }

    public List<SurveyBean> searchItemsBySurvey(final String period,
            final Integer start, Integer maxResults,
            final HttpServletRequest request) throws EnMeSearchException {
        final List<SurveyBean> results = new ArrayList<SurveyBean>();
        if (maxResults == null) {
            maxResults = this.MAX_RESULTS;
        }
        log.debug("Max Results " + maxResults);
        final List<Survey> items = new ArrayList<Survey>();
        if (period == null) {
            throw new EnMeSearchException("search params required.");
        } else {
            final SearchPeriods periodSelected = SearchPeriods
                    .getPeriodString(period);
            if (periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) {
                items.addAll(getFrontEndDao().getSurveyFrontEndLast24(start,
                        maxResults));
            } else if (periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) {
                items.addAll(getFrontEndDao().getSurveyFrontEndLast24(start,
                        maxResults));
            } else if (periodSelected.equals(SearchPeriods.SEVENDAYS)) {
                items.addAll(getFrontEndDao().getSurveyFrontEndLast7Days(start,
                        maxResults));
            } else if (periodSelected.equals(SearchPeriods.THIRTYDAYS)) {
                items.addAll(getFrontEndDao().getSurveyFrontEndLast30Days(
                        start, maxResults));
            } else if (periodSelected.equals(SearchPeriods.ALLTIME)) {
                items.addAll(getFrontEndDao().getSurveyFrontEndAllTime(start,
                        maxResults));
            }
            log.debug("TweetPoll " + items.size());
            results.addAll(ConvertDomainBean.convertListSurveyToBean(items));
        }
        return results;
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#getFrontEndItems(java.lang.String, java.lang.Integer, java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    public Set<HomeBean> getFrontEndItems(final String period,
            final Integer start, Integer maxResults,
            final HttpServletRequest request) throws EnMeSearchException {
        final Set<HomeBean> allItems = new TreeSet<HomeBean>();
        final List<TweetPollBean> tweetPollItems = this.searchItemsByTweetPoll(
                period, start, maxResults, request);
        allItems.addAll(ConvertDomainBean
                .convertTweetPollListToHomeBean(tweetPollItems));
        final List<PollBean> pollItems = this.searchItemsByPoll(period, start,
                maxResults);
        allItems.addAll(ConvertDomainBean.convertPollListToHomeBean(pollItems));
        final List<SurveyBean> surveyItems = this.searchItemsBySurvey(period,
                start, maxResults, request);
        allItems.addAll(ConvertDomainBean.convertSurveyListToHomeBean(surveyItems));
        return allItems;
    }

    /**
     * Search items by poll.
     * @param period
     * @param maxResults
     * @return
     * @throws EnMeSearchException
     */
    public List<PollBean> searchItemsByPoll(
            final String period,
            final Integer start,
            Integer maxResults)
            throws EnMeSearchException{
    final List<PollBean> results = new ArrayList<PollBean>();
    if(maxResults == null){
        maxResults = this.MAX_RESULTS;
    }
    final List<Poll> items = new ArrayList<Poll>();
    if(period == null ){
        throw new EnMeSearchException("search params required.");
    } else {
        final SearchPeriods periodSelected = SearchPeriods.getPeriodString(period);
        if(periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)){
            items.addAll(getFrontEndDao().getPollFrontEndLast24(start, maxResults));
        } else if(periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)){
            items.addAll(getFrontEndDao().getPollFrontEndLast24(start, maxResults));
        } else if(periodSelected.equals(SearchPeriods.SEVENDAYS)){
            items.addAll(getFrontEndDao().getPollFrontEndLast7Days(start, maxResults));
        } else if(periodSelected.equals(SearchPeriods.THIRTYDAYS)){
            items.addAll(getFrontEndDao().getPollFrontEndLast30Days(start, maxResults));
        } else if(periodSelected.equals(SearchPeriods.ALLTIME)){
            items.addAll(getFrontEndDao().getPollFrontEndAllTime(start, maxResults));
        }
        log.debug("Poll "+items.size());
        results.addAll(ConvertDomainBean.convertListToPollBean((items)));
    }

    return results;
    }

    /**
     * Get hashTags
     * @param maxResults
     * @param start
     * @return
     */
    public List<HashTagBean> getHashTags(
              Integer maxResults,
              final Integer start,
              final String tagCriteria){
        final List<HashTagBean> hashBean = new ArrayList<HashTagBean>();
        if(maxResults == null){
            maxResults = this.MAX_RESULTS;
        }
        final List<HashTag> tags = getHashTagDao().getHashTags(maxResults, start, tagCriteria);
        hashBean.addAll(ConvertDomainBean.convertListHashTagsToBean(tags));
        return hashBean;
    }

    /**
     * Get hashTag item.
     * @param tagName
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public HashTag getHashTagItem(final String tagName) throws EnMeNoResultsFoundException {
        final HashTag tag = getHashTagDao().getHashTagByName(tagName);
        if (tag == null){
            throw new EnMeNoResultsFoundException("hashtag not found");
        }
        return tag;
    }

    /**
     * Get TweetPolls by hashTag id.
     * @param hashTagId
     * @param limit
     * @return
     */
    public List<TweetPollBean> getTweetPollsbyHashTagId(
            final Long hashTagId,
            final Integer limit,
            final String filter,
            final HttpServletRequest request){
        final List<TweetPoll> tweetPolls = getTweetPollDao().getTweetpollByHashTagId(hashTagId, limit, filter);
        log.debug("TweetPoll by HashTagId total size ---> "+tweetPolls.size());
        final List<TweetPollBean> tweetPollBean = ConvertDomainBean.convertListToTweetPollBean(tweetPolls);
        for (TweetPollBean tweetPoll : tweetPollBean) {
            tweetPoll = convertTweetPollRelativeTime(tweetPoll, request);
        }
        return tweetPollBean;
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#checkPreviousHit(java.lang.String, java.lang.Long, java.lang.String)
     */
    public Boolean checkPreviousHit(final String ipAddress, final Long id, final TypeSearchResult searchHitby){
        boolean hit = false;
        final List<Hit> hitList = getFrontEndDao().getHitsByIpAndType(ipAddress, id, searchHitby);
        try {
            if(hitList.size() == 1){
                if(hitList.get(0).getIpAddress().equals(ipAddress)){
                    hit = true;
                }
             }
            else if(hitList.size() > 1){
                log.error("List cant'be greater than one");
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e);
        }
        return hit;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#registerHit(org.encuestame.persistence.domain.tweetpoll.TweetPoll, org.encuestame.persistence.domain.survey.Poll, org.encuestame.persistence.domain.survey.Survey, org.encuestame.persistence.domain.HashTag, java.lang.String)
     */
    public Boolean registerHit(final TweetPoll tweetPoll, final Poll poll, final Survey survey, final HashTag tag,
            final String ip) throws EnMeNoResultsFoundException{
        final Hit hit ;
        Long hitCount = 1L;
        Boolean register = false;
        // HashTag
        if (ip != null){
            if (tag != null){
                hit = this.newHashTagHit(tag, ip);
                hitCount = tag.getHits() + hitCount;
                tag.setHits(hitCount);
                getFrontEndDao().saveOrUpdate(tag);
                register = true;
            }
            else if(tweetPoll != null){
                hit = this.newTweetPollHit(tweetPoll, ip);
                hitCount = tweetPoll.getHits() + hitCount;
                tweetPoll.setHits(hitCount);
                getFrontEndDao().saveOrUpdate(tweetPoll);
                register = true;
            }
            else if(poll != null){
                 hit = this.newPollHit(poll, ip);
                 hitCount = poll.getHits() + hitCount;
                 poll.setHits(hitCount);
                 getFrontEndDao().saveOrUpdate(poll);
                 register = true;
            }
            else if(survey != null ){
                 hit = this.newSurveyHit(survey, ip);
                 hitCount = survey.getHits() + hitCount;
                 survey.setHits(hitCount);
                 getFrontEndDao().saveOrUpdate(survey);
                 register = true;
            }
        }
        return register;
    }

    /**
     * New hit item.
     * @param tweetPoll
     * @param poll
     * @param survey
     * @param tag
     * @param ipAddress
     * @return
     */

    @Transactional(readOnly = false)
    private Hit newHitItem(final TweetPoll tweetPoll, final Poll poll, final Survey survey, final HashTag tag,
            final String ipAddress) {
        final Hit hitItem = new Hit();
        hitItem.setHitDate(Calendar.getInstance().getTime());
        hitItem.setHashTag(tag);
        hitItem.setIpAddress(ipAddress);
        hitItem.setTweetPoll(tweetPoll);
        hitItem.setPoll(poll);
        hitItem.setSurvey(survey);
        getFrontEndDao().saveOrUpdate(hitItem);
        return hitItem;
    }

    /**
     * New tweet poll hit item.
     * @param tweetPoll
     * @param ipAddress
     * @return
     */
    private Hit newTweetPollHit(final TweetPoll tweetPoll, final String ipAddress){
        return this.newHitItem(tweetPoll, null, null, null, ipAddress);
    }

    /**
     * New poll hit item.
     * @param poll
     * @param ipAddress
     * @return
     */
    private Hit newPollHit(final Poll poll, final String ipAddress){
        return this.newHitItem(null, poll, null, null, ipAddress);
    }

    /**
     * New hash tag hit item.
     * @param tag
     * @param ipAddress
     * @return
     */
    private Hit newHashTagHit(final HashTag tag, final String ipAddress){
        return this.newHitItem(null, null, null, tag, ipAddress);
    }

    /**
     * New survey hit item.
     * @param survey
     * @param ipAddress
     * @return
     */
    private Hit newSurveyHit(final Survey survey, final String ipAddress){
        return this.newHitItem(null, null, survey, null, ipAddress);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#registerAccessRate(org.encuestame.persistence.domain.TypeSearchResult, java.lang.Long, java.lang.String, java.lang.Boolean)
     */
    public AccessRate registerAccessRate(final TypeSearchResult type,
            final Long itemId, final String ipAddress, final Boolean rate)
            throws EnMeExpcetion {
        AccessRate recordAccessRate = new AccessRate();
        if (ipAddress != null) {
            if (type.equals(TypeSearchResult.TWEETPOLL)) {
                // Find tweetPoll by itemId.
                final TweetPoll tweetPoll = this.getTweetPoll(itemId);
                Assert.assertNotNull(tweetPoll);
                // Check if exist a previous tweetpoll access record.
                recordAccessRate = this.checkExistTweetPollPreviousRecord(tweetPoll, ipAddress,
                        rate);
            }
            // Poll Acces rate item.
            if (type.equals(TypeSearchResult.POLL)) {
                // Find poll by itemId.
                final Poll poll = this.getPoll(itemId);
                Assert.assertNotNull(poll);
                // Check if exist a previous poll access record.
                recordAccessRate = this.checkExistPollPreviousRecord(poll, ipAddress,
                        rate);
            }
            // Survey Access rate item.
            if (type.equals(TypeSearchResult.SURVEY)) {
                // Find survey by itemId.
                final Survey survey = this.getSurvey(itemId);
                Assert.assertNotNull(survey);
                // Check if exist a previous survey access record.
                recordAccessRate = this.checkExistSurveyPreviousRecord(survey, ipAddress,
                        rate);
            }
        }
        return recordAccessRate;
    }

    /**
     * Check exist tweetPoll previous record.
     * @param tpoll
     * @param ipAddress
     * @param option
     * @throws EnMeExpcetion
     */
    private AccessRate checkExistTweetPollPreviousRecord(final TweetPoll tpoll,
            final String ipAddress, final Boolean option) throws EnMeExpcetion {
        // Search record by tweetPpll in access Rate domain.
        List<AccessRate> rateList = this.getAccessRateItem(ipAddress,
                tpoll.getTweetPollId(), TypeSearchResult.TWEETPOLL);
        final AccessRate accessRate;
        if (rateList.size() > 1) {
            throw new EnMeExpcetion("Access rate list found coudn't be greater than one ");
        } else if (rateList.size() == 1) {
            // Get first element from access rate list
            accessRate = rateList.get(0);
            //Check if the option selected is the same that you have registered
            if (accessRate.getRate() == option) {
                log.warn("The option was previously selected " + accessRate.getRate());
            } else {
                // We proceed to update the record in the table access Rate.
                accessRate.setRate(option);
                // Update the value in the fields of TweetPoll
                final TweetPoll tp = this.setTweetPollSocialOption(option,
                        tpoll);
                // Save access rate record.
                getFrontEndDao().saveOrUpdate(accessRate);
            }

        } else {
            // Otherwise, create access rate record.
            accessRate = this.newTweetPollAccessRate(tpoll, ipAddress, option);
            // update tweetPoll record.
            final TweetPoll tp = this.setTweetPollSocialOption(option,
                    tpoll);
        }
        return accessRate;
    }

    /**
     * Check exist Poll previous record.
     * @param poll
     * @param ipAddress
     * @param option
     * @return
     * @throws EnMeExpcetion
     */
    private AccessRate checkExistPollPreviousRecord(final Poll poll,
            final String ipAddress, final Boolean option) throws EnMeExpcetion {
        // Search record by poll in access Rate domain.
        List<AccessRate> rateList = this.getAccessRateItem(ipAddress,
                poll.getPollId(), TypeSearchResult.POLL);
        final AccessRate accessRate;
        if (rateList.size() > 1) {
            throw new EnMeExpcetion("Access rate list found coudn't be greater than one ");
        } else if (rateList.size() == 1) {
            // Get first element from access rate list
            accessRate = rateList.get(0);
            //Check if the option selected is the same that you have registered
            if (accessRate.getRate() == option) {
                log.warn("The option was previously selected " + accessRate.getRate());
            } else {
                // We proceed to update the record in the table access Rate.
                accessRate.setRate(option);
                // Update the value in the fields of TweetPoll
                final Poll pollItem = this.setPollSocialOption(option,
                        poll);
                // Save access rate record.
                getFrontEndDao().saveOrUpdate(accessRate);
            }

        } else {
            // Otherwise, create access rate record.
            accessRate = this.newPollAccessRate(poll, ipAddress, option);
            // update poll record.
            final Poll pollItem = this.setPollSocialOption(option,
                    poll);
        }
        return accessRate;
    }

    /**
     * Check exist Survey previous record.
     * @param survey
     * @param ipAddress
     * @param option
     * @return
     * @throws EnMeExpcetion
     */
    private AccessRate checkExistSurveyPreviousRecord(final Survey survey,
            final String ipAddress, final Boolean option) throws EnMeExpcetion {
        // Search record by survey in access Rate domain.
        List<AccessRate> rateList = this.getAccessRateItem(ipAddress,
                survey.getSid(), TypeSearchResult.SURVEY);
        final AccessRate accessRate;
        if (rateList.size() > 1) {
            throw new EnMeExpcetion("Access rate list found coudn't be greater than one ");
        } else if (rateList.size() == 1) {
            // Get first element from access rate list
            accessRate = rateList.get(0);
            //Check if the option selected is the same that you have registered
            if (accessRate.getRate() == option) {
                log.warn("The option was previously selected " + accessRate.getRate());
            } else {
                // We proceed to update the record in the table access Rate.
                accessRate.setRate(option);
                // Update the value in the fields of survey
                final Survey surveyItem = this.setSurveySocialOption(option, survey);
                // Save access rate record.
                getFrontEndDao().saveOrUpdate(accessRate);
            }

        } else {
            // Otherwise, create access rate record.
            accessRate = this.newSurveyAccessRate(survey, ipAddress, option);
            // update poll record.
            final Survey surveyItem = this.setSurveySocialOption(option,
                    survey);
        }
        return accessRate;
    }

    /**
     * Set tweetpoll social options.
     * @param socialOption
     * @param tpoll
     * @return
     */
    private TweetPoll setTweetPollSocialOption(final Boolean socialOption,
            final TweetPoll tpoll) {
        long valueSocialVote = 1L;
        long optionValue;
        // If the user has voted like.
        if (socialOption) {
            valueSocialVote = tpoll.getLikeVote() + valueSocialVote;
            tpoll.setLikeVote(valueSocialVote);
            optionValue = tpoll.getLikeVote() - valueSocialVote;
            tpoll.setDislikeVote(tpoll.getDislikeVote() == 0 ? 0 : optionValue);
            getTweetPollDao().saveOrUpdate(tpoll);
        } else {
            valueSocialVote = tpoll.getDislikeVote() + valueSocialVote;
            optionValue = tpoll.getLikeVote() - valueSocialVote;
            tpoll.setLikeVote(tpoll.getLikeVote() == 0 ? 0 : optionValue);
            tpoll.setDislikeVote(valueSocialVote);
            getTweetPollDao().saveOrUpdate(tpoll);
        }
        return tpoll;
    }

    /**
     * Set Poll social option.
     * @param socialOption
     * @param poll
     * @return
     */
    private Poll setPollSocialOption(final Boolean socialOption, final Poll poll) {
        long valueSocialVote = 1L;
        long optionValue;
        // If the user has voted like.
        if (socialOption) {
            valueSocialVote = poll.getLikeVote() + valueSocialVote;
            poll.setLikeVote(valueSocialVote);
            optionValue = poll.getLikeVote() - valueSocialVote;
            poll.setDislikeVote(poll.getDislikeVote() == 0 ? 0 : optionValue);
            getTweetPollDao().saveOrUpdate(poll);
        } else {
            valueSocialVote = poll.getDislikeVote() + valueSocialVote;
            optionValue = poll.getLikeVote() - valueSocialVote;
            poll.setLikeVote(poll.getLikeVote() == 0 ? 0 : optionValue);
            poll.setDislikeVote(valueSocialVote);
            getTweetPollDao().saveOrUpdate(poll);
        }
        return poll;
    }

    /**
     * Set Survey social option.
     * @param socialOption
     * @param survey
     * @return
     */
    private Survey setSurveySocialOption(final Boolean socialOption,
            final Survey survey) {
        long valueSocialVote = 1L;
        long optionValue;
        // If the user has voted like.
        if (socialOption) {
            valueSocialVote = survey.getLikeVote() + valueSocialVote;
            survey.setLikeVote(valueSocialVote);
            optionValue = survey.getLikeVote() - valueSocialVote;
            survey.setDislikeVote(survey.getDislikeVote() == 0 ? 0
                    : optionValue);
            getTweetPollDao().saveOrUpdate(survey);
        } else {
            valueSocialVote = survey.getDislikeVote() + valueSocialVote;
            optionValue = survey.getLikeVote() - valueSocialVote;
            survey.setLikeVote(survey.getLikeVote() == 0 ? 0 : optionValue);
            survey.setDislikeVote(valueSocialVote);
            getTweetPollDao().saveOrUpdate(survey);
        }
        return survey;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#getAccessRateItem(java.lang.String, java.lang.Long, org.encuestame.persistence.domain.TypeSearchResult)
     */
    private List<AccessRate> getAccessRateItem(final String ipAddress,
            final Long itemId, final TypeSearchResult searchby)
            throws EnMeExpcetion {
        final List<AccessRate> itemAccessList = getFrontEndDao()
                .getAccessRatebyItem(ipAddress, itemId, searchby);
        return itemAccessList;
    }

    /**
     * New access rate item.
     *
     * @param tweetPoll
     * @param poll
     * @param survey
     * @param ipAddress
     * @param rate
     * @return
     */
    @Transactional(readOnly = false)
    private AccessRate newAccessRateItem(final TweetPoll tweetPoll,
            final Poll poll, final Survey survey, final String ipAddress,
            final Boolean rate) {
        final AccessRate itemRate = new AccessRate();
        itemRate.setTweetPoll(tweetPoll);
        itemRate.setPoll(poll);
        itemRate.setSurvey(survey);
        itemRate.setRate(rate);
        itemRate.setUser(null);
        itemRate.setIpAddress(ipAddress);
        getTweetPollDao().saveOrUpdate(itemRate);
        return itemRate;
    }

    /**
     * New TweetPoll access rate.
     *
     * @param tweetPoll
     * @param ipAddress
     * @param rate
     * @return
     */
    private AccessRate newTweetPollAccessRate(final TweetPoll tweetPoll,
            final String ipAddress, final Boolean rate) {
        return this.newAccessRateItem(tweetPoll, null, null, ipAddress, rate);
    }

    /**
     * New Poll access rate.
     *
     * @param poll
     * @param ipAddress
     * @param rate
     * @return
     */
    private AccessRate newPollAccessRate(final Poll poll,
            final String ipAddress, final Boolean rate) {
        return this.newAccessRateItem(null, poll, null, ipAddress, rate);
    }

    /**
     * New Survey access rate.
     * @param survey
     * @param ipAddress
     * @param rate
     * @return
     */
    private AccessRate newSurveyAccessRate(final Survey survey,
            final String ipAddress, final Boolean rate) {
        return this.newAccessRateItem(null, null, survey, ipAddress, rate);
    }

    /**
     *
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private TweetPoll getTweetPoll(final Long id) throws EnMeNoResultsFoundException{
        return getTweetPollService().getTweetPollById(id);
    }

    /**
     * Get survey by id.
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private Survey getSurvey(final Long id) throws EnMeNoResultsFoundException{
        return getSurveyService().getSurveyById(id);
    }

    /**
     * Get Poll by id.
     * @param id
     * @return
     * @throws EnMePollNotFoundException
     */
    private Poll getPoll(final Long id) throws EnMePollNotFoundException{
        return getPollService().getPollById(id);
    }

    /**
     * @return the tweetPollService
     */
    public TweetPollService getTweetPollService() {
        return tweetPollService;
    }

    /**
     * @param tweetPollService the tweetPollService to set
     */
    public void setTweetPollService(TweetPollService tweetPollService) {
        this.tweetPollService = tweetPollService;
    }

    /**
     * @return the pollService
     */
    public PollService getPollService() {
        return pollService;
    }

    /**
     * @param pollService the pollService to set
     */
    public void setPollService(final PollService pollService) {
        this.pollService = pollService;
    }

    /**
     * @return the surveyService
     */
    public SurveyService getSurveyService() {
        return surveyService;
    }

    /**
     * @param surveyService the surveyService to set
     */
    public void setSurveyService(final SurveyService surveyService) {
        this.surveyService = surveyService;
    }
}