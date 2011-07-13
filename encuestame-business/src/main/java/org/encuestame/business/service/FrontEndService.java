/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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
import org.encuestame.business.service.imp.IFrontEndService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.dao.SearchPeriods;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.HashTagHits;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.TweetPollBean;
import org.encuestame.utils.web.UnitPoll;
import org.springframework.stereotype.Service;

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
            if(periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast24(start, maxResults));
            } else if(periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast24(start, maxResults));
            } else if(periodSelected.equals(SearchPeriods.SEVENDAYS)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast7Days(start, maxResults));
            } else if(periodSelected.equals(SearchPeriods.THIRTYDAYS)){
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast30Days(start, maxResults));
            } else if(periodSelected.equals(SearchPeriods.ALLTIME)){
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

    /**
     * Search items by poll.
     * @param period
     * @param maxResults
     * @return
     * @throws EnMeSearchException
     */
    public List<UnitPoll> searchItemsByPoll(
            final String period,
            final Integer start,
            Integer maxResults)
            throws EnMeSearchException{
    final List<UnitPoll> results = new ArrayList<UnitPoll>();
    if(maxResults == null){
        maxResults = this.MAX_RESULTS;
    }
    log.debug("Max Results "+maxResults);
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
        log.debug("Max Results HashTag -----> "+maxResults);
        List<HashTag> tags = new ArrayList<HashTag>();
        tags.addAll(getHashTagDao().getHashTags(maxResults, start, tagCriteria));
        log.debug("Hashtag total size ---> "+tags.size());
        hashBean.addAll(ConvertDomainBean.convertListHashTagsToBean(tags));

        return hashBean;
    }

    /**
     * Get hashTag item.
     * @param tagName
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public HashTagBean getHashTagItem(final String tagName) throws EnMeNoResultsFoundException {
        final HashTag tag = getHashTagDao().getHashTagByName(tagName);
        if (tag == null){
            throw new EnMeNoResultsFoundException("hashtag not found");
        }
        return ConvertDomainBean.convertHashTagDomain(tag);
    }

    /**
     * Get TweetPolls by hashTag id.
     * @param hashTagId
     * @param limit
     * @return
     */
    @SuppressWarnings("unchecked")
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

    /**
     * Get tweetPoll by top rated.
     * @param hashTagId
     * @param limit
     * @param request
     * @return
     */
  /*  public List<TweetPollBean> getTweetPollsbyTopRated(
            final Long hashTagId,
            final Integer limit,
            final HttpServletRequest request){
        final List<TweetPoll> tweetPolls = getTweetPollDao().getTweetpollByTopRated(hashTagId, limit);
        log.debug("TweetPoll by TopRated total size ---> "+tweetPolls.size());
        final List<TweetPollBean> tweetPollBean = ConvertDomainBean.convertListToTweetPollBean(tweetPolls);
        for (TweetPollBean tweetPoll : tweetPollBean) {
            tweetPoll = convertTweetPollRelativeTime(tweetPoll, request);
        }
        return tweetPollBean;
    }
*/
    /**
     * Get hashTag relevance.
     * @param hashTagId
     * @param limit
     * @return
     */
    public Integer getHashTagRelevance(final Long hashTagId, final Integer limit){
        final Integer totalRelTweetPoll;
        final Integer relevance;
        final List<TweetPoll> tweetPolls = getTweetPollDao().getTweetpollByHashTagId(hashTagId, limit, "");
        totalRelTweetPoll = tweetPolls.size();
        relevance = totalRelTweetPoll;
        //TODO:Pending count relevance hashtags for polls and surveys.
        return relevance;
    }

    /**
     * Check previous hash tag hit.
     * @param ipAddress
     * @return
     */
    public Boolean checkPreviousHashTagHit(final String ipAddress){
        boolean tagHit = false;
        final List<HashTagHits> hashTag = getFrontEndDao().getHashTagsHitByIp(ipAddress);
        try {
            if(hashTag.size() == 1){
                if(hashTag.get(0).getIpAddress().equals(ipAddress)){
                    tagHit = true;
                }
             }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return tagHit;
    }


    public Boolean registerHashTagHit(final String tagName, final String ip, final String username){
        final HashTagHits hashHit ;
        Long hitCount = 1L;
        Boolean register = false;
        try {
            if((ip!=null) || (tagName!=null) ){
                hashHit = this.newHashTagHit(tagName, new Date(), ip);
                if (hashHit!=null){
                    final HashTag tag = getHashTagDao().getHashTagByName(tagName);
                    hitCount = tag.getHits()+hitCount;
                    tag.setHits(hitCount);
                    register = true;
                }
            }
        } catch (Exception e) {
            log.debug(e);
            e.printStackTrace();
            // TODO: handle exception
        }
        return register;
    }

    /**
     * New hash tag hit.
     * @param tagName
     * @param hitDate
     * @param ipAddress
     * @return
     */
    private HashTagHits newHashTagHit(final String tagName, final Date hitDate, final String ipAddress){
        final HashTagHits tagHitsDomain = new HashTagHits();
        tagHitsDomain.setHitDate(hitDate);
        tagHitsDomain.setHashTagId(getHashTagDao().getHashTagByName(tagName));
        tagHitsDomain.setIpAddress(ipAddress);
        tagHitsDomain.setUserAccount(getUserAccountLogged());
        this.getFrontEndDao().saveOrUpdate(tagHitsDomain);
        return tagHitsDomain;
    }
}
