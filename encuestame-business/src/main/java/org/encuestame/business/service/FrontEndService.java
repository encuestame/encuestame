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
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.encuestame.core.search.TypeSearchResult;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.IFrontEndService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.dao.SearchPeriods;
import org.encuestame.persistence.domain.AccessRate;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
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

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#getFrontEndItems(java.lang.String, java.lang.Integer, java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    public List<HomeBean> getFrontEndItems(final String period,
            final Integer start,
            Integer maxResults,
            final HttpServletRequest request) throws EnMeSearchException{
        final List<HomeBean> allItems = new ArrayList<HomeBean>();
        final List<TweetPollBean> tweetPollItems = this.searchItemsByTweetPoll(period, start, maxResults, request);
        allItems.addAll(ConvertDomainBean.convertTweetPollListToHomeBean(tweetPollItems));
        final List<PollBean> pollItems = this.searchItemsByPoll(period, start, maxResults);
        allItems.addAll(ConvertDomainBean.convertPollListToHomeBean(pollItems));
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
     * @see org.encuestame.core.service.imp.IFrontEndService#checkPreviousHashTagHit(java.lang.String)
     */
    @Deprecated
    public Boolean checkPreviousHashTagHit(final String ipAddress){
        boolean tagHit = false;
        final List<HashTagHits> hashTag = getFrontEndDao().getHashTagsHitByIp(ipAddress);
        try {
            if(hashTag.size() == 1){
                if(hashTag.get(0).getIpAddress().equals(ipAddress)){
                    tagHit = true;
                }
             }
            else if(hashTag.size() > 1){
                log.warn("");
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e);
        }
        return tagHit;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#checkPreviousHit(java.lang.String, java.lang.Long, java.lang.String)
     */
    public Boolean checkPreviousHit(final String ipAddress, final Long id, final String searchHitby){
        boolean hit = false;
        final List<Hit> hitList = getFrontEndDao().getHitsByIpAndType(ipAddress, id, searchHitby);
        try {
            if(hitList.size() == 1){
                if(hitList.get(0).getIpAddress().equals(ipAddress)){
                    hit = true;
                }
             }
            else if(hitList.size() > 1){
                log.warn("");
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

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#registerHashTagHit(org.encuestame.persistence.domain.HashTag, java.lang.String)
     */
    @Deprecated
    public Boolean registerHashTagHit(final HashTag tag, final String ip) throws EnMeNoResultsFoundException{
        final HashTagHits hashHit ;
        Long hitCount = 1L;
        Boolean register = false;
        if ((ip != null) || (tag != null)) {
            hashHit = this.newHashTagHit(tag, Calendar.getInstance().getTime(), ip);
            if (hashHit != null) {
                hitCount = tag.getHits() + hitCount;
                tag.setHits(hitCount);
                getFrontEndDao().saveOrUpdate(tag);
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
    /**
     * New hash tag hit.
     * @param tagName
     * @param hitDate
     * @param ipAddress
     * @return
     * @throws EnMeNoResultsFoundException
     */
    @Deprecated
    @Transactional(readOnly = false)
    private HashTagHits newHashTagHit(
            final HashTag tag,
            final Date hitDate,
            final String ipAddress) throws EnMeNoResultsFoundException {
        final HashTagHits tagHitsDomain = new HashTagHits();
        tagHitsDomain.setHitDate(hitDate);
        tagHitsDomain.setHashTag(tag);
        tagHitsDomain.setIpAddress(ipAddress);
        getFrontEndDao().saveOrUpdate(tagHitsDomain);
        return tagHitsDomain;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#registerAccessRate(org.encuestame.core.search.TypeSearchResult, java.lang.Long, java.lang.String, java.lang.Boolean)
     */
    public void registerAccessRate(final TypeSearchResult type,
            final Long itemId, final String ipAddress, final Boolean rate)
            throws EnMeNoResultsFoundException {
        final AccessRate itemRate;
        Long rateCount = 1L;
        Boolean register = false;
        if (ipAddress != null) {
            if (type.equals(TypeSearchResult.TWEETPOLL)) {
                final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(
                        itemId);
                itemRate = this.newTweetPollAccessRate(tweetPoll, ipAddress,
                        rate);
                // tpoll.getLikeVote();
                // rateCount = tweetPoll.getHits() + rateCount;
                // tweetPoll.setHits(rateCount);
                getFrontEndDao().saveOrUpdate(tweetPoll);
                register = true;
            } else if (type.equals(TypeSearchResult.POLL)) {
                final Poll poll = getPollDao().getPollById(itemId);
                itemRate = this.newPollAccessRate(poll, ipAddress, rate);
                rateCount = poll.getHits() + rateCount;
                poll.setHits(rateCount);
                getFrontEndDao().saveOrUpdate(poll);
                register = true;
            } else if (type.equals(TypeSearchResult.SURVEY)) {
                final Survey survey = getSurveyDaoImp().getSurveyByIdandUserId(
                        itemId, null);
                itemRate = this.newSurveyAccessRate(survey, ipAddress, rate);
                rateCount = survey.getHits() + rateCount;
                survey.setHits(rateCount);
                getFrontEndDao().saveOrUpdate(survey);
                register = true;
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#checkItemRate(java.lang
     * .Long, java.lang.String, java.lang.String)
     */
    public Boolean checkItemRate(final Long itemId, final String ipAddress,
            final String searchby) {
        boolean votehit = false;
        final List<AccessRate> voteList = getFrontEndDao().getAccessRatebyItem(
                ipAddress, itemId, searchby);
        try {
            if (voteList.size() == 1) {
                if (voteList.get(0).getIpAddress().equals(ipAddress)) {
                    votehit = true;
                }
            } else if (voteList.size() > 1) {
                log.warn("");
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e);
        }
        return votehit;
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
}