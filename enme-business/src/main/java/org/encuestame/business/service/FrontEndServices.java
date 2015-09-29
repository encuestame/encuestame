/**
 *
 */
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
//import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.imp.*;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.core.util.RecentItemsComparator;
import org.encuestame.persistence.domain.*;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.enums.HitCategory;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.HomeBean;
import org.encuestame.utils.json.LinksSocialBean;
import org.encuestame.utils.json.TweetPollBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.ProfileRatedTopBean;
import org.encuestame.utils.web.SurveyBean;
import org.encuestame.utils.web.stats.GenericStatsBean;
import org.encuestame.utils.web.stats.HashTagRankingBean;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Front End Service.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 11:29:38 AM
 */
@Service
@Transactional
public class FrontEndServices  extends AbstractBaseService implements IFrontEndService {

     /** Front End Service Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /** Max Results. **/
    private final Integer MAX_RESULTS = 15;

    /** **/
    private ITweetPollService tweetPollService;

    /** {@link PollService} **/
    private IPollService pollService;

    /** {@link SurveyService} **/
    private ISurveyService surveyService;

    /** {@link SecurityOperations} **/
    private SecurityOperations securityService;

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#searchItemsByTweetPoll(java.lang.String, java.lang.Integer, java.lang.Integer, javax.servlet.http.HttpServletRequest)
     */
    public List<TweetPollBean> searchItemsByTweetPoll(
            final String period,
            final Integer start,
            Integer maxResults,
            final HttpServletRequest request) throws EnMeSearchException {
        return this.searchItemsByTweetPoll(period, start, maxResults, request, false);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#searchItemsByTweetPoll(java.lang.String, java.lang.Integer, java.lang.Integer, javax.servlet.http.HttpServletRequest, java.lang.Boolean)
     */
    public List<TweetPollBean> searchItemsByTweetPoll(
            final String period,
            final Integer start,
            Integer maxResults,
            final HttpServletRequest request,
            final Boolean addResults) throws EnMeSearchException {
        final List<TweetPollBean> results = new ArrayList<TweetPollBean>();
        if (maxResults == null) {
            maxResults = this.MAX_RESULTS;
        }
        log.debug("Max Results: " + maxResults);
        log.debug("Period Results: " + period);
        final List<TweetPoll> items = new ArrayList<TweetPoll>();
        if (period == null) {
            throw new EnMeSearchException("search params required.");
        } else {
            final SearchPeriods periodSelected = SearchPeriods
                    .getPeriodString(period);
            if (periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast24(start,
                        maxResults));
            } else if (periodSelected.equals(SearchPeriods.SEVENDAYS)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast7Days(
                        start, maxResults));
            } else if (periodSelected.equals(SearchPeriods.THIRTYDAYS)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndLast30Days(
                        start, maxResults));
            } else if (periodSelected.equals(SearchPeriods.ALLTIME)) {
                items.addAll(getFrontEndDao().getTweetPollFrontEndAllTime(
                        start, maxResults));
            }
            if (addResults) {
                results.addAll(getTweetPollService().setTweetPollListAnswers(items, true, request));
            } else {
                results.addAll(ConvertDomainBean.convertListToTweetPollBean(items));
            }
            for (TweetPollBean tweetPoll : results) {
                // log.debug("Iterate Home TweetPoll id: "+tweetPoll.getId());
                // log.debug("Iterate Home Tweetpoll Hashtag Size: "+tweetPoll.getHashTags().size());
                tweetPoll = convertTweetPollRelativeTime(tweetPoll, request);
                tweetPoll.setTotalComments(this.getTotalCommentsbyType(tweetPoll.getId(), TypeSearchResult.TWEETPOLL));
                if (this.isWellAuthenticated()) {
                    //FIXME: ENCUESTAME-530 is not an optimal solution
                    final TweetPoll tp = getTweetPollDao().getTweetPollById(tweetPoll.getId());
                    final List<Hit> pollItems = getFrontEndDao().getVotesByType(TypeSearchResult.TWEETPOLL, getUserAccountonSecurityContext(), tp.getQuestion());
                    tweetPoll.setVoteUp(!(pollItems.size() > 0));
                } else {
                    tweetPoll.setVoteUp(Boolean.TRUE);
                }
            }
        }
        log.debug("Search Items by TweetPoll: " + results.size());
        return results;
    }

    /**
     *
     */
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
            for (SurveyBean surveyBean : results) {
                surveyBean.setTotalComments(this.getTotalCommentsbyType(
                        surveyBean.getSid(), TypeSearchResult.SURVEY));
            }
        }
        return results;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#getFrontEndItems(java
     * .lang.String, java.lang.Integer, java.lang.Integer,
     * javax.servlet.http.HttpServletRequest)
     */
    public List<HomeBean> getFrontEndItems(
            final String period,
            final Integer start,
            final Integer maxResults,
            final HttpServletRequest request) throws EnMeSearchException, EnMeNoResultsFoundException {
       return this.getFrontEndItems(period, start, maxResults, false, request);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#getFrontEndItems(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Boolean, javax.servlet.http.HttpServletRequest)
     */
    public List<HomeBean> getFrontEndItems(
            final String period,
            final Integer start,
            final Integer maxResults,
            final Boolean addResults,
            final HttpServletRequest request) throws EnMeSearchException, EnMeNoResultsFoundException {
          // Sorted list based comparable interface
        List<HomeBean> allItems = new ArrayList<HomeBean>();
        final List<TweetPollBean> tweetPollItems = this.searchItemsByTweetPoll(
                period, start, maxResults, request, addResults);
        log.debug("FrontEnd TweetPoll items size  :" + tweetPollItems.size());
        allItems.addAll(ConvertDomainBean
                .convertTweetPollListToHomeBean(tweetPollItems));
        final List<PollBean> pollItems = this.searchItemsByPoll(period, start,
                maxResults, request, true);
        log.debug("FrontEnd Poll items size  :" + pollItems.size());
        allItems.addAll(ConvertDomainBean.convertPollListToHomeBean(pollItems));
        final List<SurveyBean> surveyItems = this.searchItemsBySurvey(period,
                start, maxResults, request);
        log.debug("FrontEnd Survey items size  :" + surveyItems.size());
        allItems.addAll(ConvertDomainBean
                .convertSurveyListToHomeBean(surveyItems));
        log.debug("Home bean list size :" + allItems.size());
        Collections.sort(allItems);
        // limit the total resuls to maxResults requested
        if (maxResults != null && allItems.size() > maxResults) {
            allItems = allItems.subList(0, maxResults);
        }
        return allItems;
    }

   /*
    * (non-Javadoc)
    * @see org.encuestame.core.service.imp.IFrontEndService#getLastItemsPublishedFromUserAccount(java.lang.String, java.lang.Integer, java.lang.Boolean, javax.servlet.http.HttpServletRequest)
    */
    public List<HomeBean> getLastItemsPublishedFromUserAccount(
            final String username,
            final Integer maxResults,
            final Boolean showUnSharedItems,
            final HttpServletRequest request) throws EnMeNoResultsFoundException {
            //get tweetpolls
        // TODO: parameter showUnSharedItems not used
        final UserAccount user = getUserAccount(username);
        log.debug("getLastItemsPublishedFromUserAccount: "+user.getUsername());
        final List<TweetPoll> lastTp = getTweetPollDao().getTweetPollByUsername(maxResults, user);
        log.debug("getLastItemsPublishedFromUserAccount lastTp: "+lastTp.size());
        final List<Poll> lastPoll = getPollDao().getPollByUserIdDate(null, user, maxResults, EnMeUtils.DEFAULT_START);
        log.debug("getLastItemsPublishedFromUserAccount lastPoll: "+lastPoll.size());
        final List<HomeBean> totalItems = new ArrayList<HomeBean>();
        //convert poll to home beans
        totalItems.addAll(ConvertDomainBean.convertPollListToHomeBean(ConvertDomainBean.convertListToPollBean(lastPoll)));
        //convert TweetPoll to home beans
        totalItems.addAll(ConvertDomainBean.convertTweetPollListToHomeBean(ConvertDomainBean.convertListToTweetPollBean(lastTp)));
        //TODO: add survey Items.

        //order items
        Collections.sort(totalItems, new RecentItemsComparator());
        //fill the relative time.
        if (request != null) {
            fillHomeBeanRelativeTime(totalItems, request);
        }
        log.debug("getLastItemsPublishedFromUserAccount after sort: "+totalItems.size());
        //ListUtils.
        return totalItems;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#searchItemsByPoll(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<PollBean> searchItemsByPoll(
            final String period,
            final Integer start,
            Integer maxResults,
            final HttpServletRequest request) throws EnMeSearchException{
        return this.searchItemsByPoll(period, start, maxResults, request, false);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#searchItemsByPoll(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Boolean)
     */
    public List<PollBean> searchItemsByPoll(
            final String period,
            final Integer start,
            Integer maxResults,
            final HttpServletRequest request,
            final Boolean addResults) throws EnMeSearchException {
        final List<PollBean> results = new ArrayList<PollBean>();
        log.debug("searchItemsByPoll period " + period);
        log.debug("searchItemsByPoll start " + period);
        log.debug("searchItemsByPoll maxResults " + maxResults);
        //avoid null values
        maxResults = maxResults == null ? this.MAX_RESULTS : maxResults;
        final List<Poll> items = new ArrayList<Poll>();
        if (period == null) {
            throw new EnMeSearchException("search params required");
        } else {
            final SearchPeriods periodSelected = SearchPeriods
                    .getPeriodString(period);
            if (periodSelected.equals(SearchPeriods.TWENTYFOURHOURS)) {
                items.addAll(getFrontEndDao().getPollFrontEndLast24(start,
                        maxResults));
            } else if (periodSelected.equals(SearchPeriods.SEVENDAYS)) {
                items.addAll(getFrontEndDao().getPollFrontEndLast7Days(start,
                        maxResults));
            } else if (periodSelected.equals(SearchPeriods.THIRTYDAYS)) {
                items.addAll(getFrontEndDao().getPollFrontEndLast30Days(start,
                        maxResults));
            } else if (periodSelected.equals(SearchPeriods.ALLTIME)) {
                items.addAll(getFrontEndDao().getPollFrontEndAllTime(start,
                        maxResults));
            }
            // if is required add results for each poll
            if (addResults) {
                // here the conversion to bean is made it inside the method
                results.addAll(getPollService().getAnswersVotesByPoll(items));
            } else {
                results.addAll(ConvertDomainBean.convertListToPollBean((items)));
            }
            // add comments info
            for (PollBean pollbean : results) {
                  if (this.isWellAuthenticated()) {
                      //FIXME: ENCUESTAME-530 is not an optimal solution
                      final Poll poll = getPollDao().getPollById(pollbean.getId());
                      final List<Hit> pollItems = getFrontEndDao().getVotesByType(TypeSearchResult.POLL, getUserAccountonSecurityContext(), poll.getQuestion());
                      pollbean.setVoteUp(!(pollItems.size() > 0));
                  } else {
                      pollbean.setVoteUp(Boolean.TRUE);
                  }

                //
                pollbean.setTotalComments(this.getTotalCommentsbyType(
                        pollbean.getId(), TypeSearchResult.POLL));
            }
        }
        log.debug("Poll results:--> " + results.size());
        return results;
    }

    public void search() {

    }

    /**
     * Get hashTags
     *
     * @param maxResults
     *            the max results to display
     * @param start
     *            to pagination propose.
     * @return List of {@link HashTagBean}
     */
    public List<HashTagBean> getHashTags(Integer maxResults,
            final Integer start, final String tagCriteria) {
        final List<HashTagBean> hashBean = new ArrayList<HashTagBean>();
        if (maxResults == null) {
            maxResults = this.MAX_RESULTS;
        }
        final List<HashTag> tags = getHashTagDao().getHashTags(maxResults,
                start, tagCriteria);
        hashBean.addAll(ConvertDomainBean.convertListHashTagsToBean(tags));
        return hashBean;
    }

    /**
     * Get hashTag item.
     *
     * @param tagName
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public HashTag getHashTagItem(final String tagName)
            throws EnMeNoResultsFoundException {
        return getHashTag(tagName, true);
    }

    /**
     * Get TweetPolls by hashTag id.
     * @param limit
     * @return
     */
    public List<TweetPollBean> getTweetPollsbyHashTagName(
            final String tagName,
            final Integer initResults,
            final Integer limit,
            final String filter,
            final HttpServletRequest request,
            final SearchPeriods searchPeriods) {
        final List<TweetPoll> tweetPolls = getTweetPollDao()
                .getTweetpollByHashTagName(tagName, initResults, limit,
                        TypeSearchResult.getTypeSearchResult(filter), searchPeriods);
        log.debug("TweetPoll by HashTagId total size ---> " + tweetPolls.size());
        final List<TweetPollBean> tweetPollBean = ConvertDomainBean
                .convertListToTweetPollBean(tweetPolls);
        for (TweetPollBean tweetPoll : tweetPollBean) {
            tweetPoll = convertTweetPollRelativeTime(tweetPoll, request);
        }
        return tweetPollBean;
    }

    /**
     * Search the last publications by {@link HashTag}.
     */
    public List<HomeBean> searchLastPublicationsbyHashTag(
            final HashTag hashTag, final String keyword,
            final Integer initResults, final Integer limit,
            final String filter, final HttpServletRequest request) {
        final List<HomeBean> allItems = new ArrayList<HomeBean>();
        final List<TweetPollBean> tweetPollItems = this
                .getTweetPollsbyHashTagName(hashTag.getHashTag(), initResults,
                        limit, filter, request, SearchPeriods.ALLTIME);
        log.debug("FrontEnd TweetPoll items size  :" + tweetPollItems.size());
        allItems.addAll(ConvertDomainBean
                .convertTweetPollListToHomeBean(tweetPollItems));
        Collections.sort(allItems);
        return allItems;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#checkPreviousHit(java
     * .lang.String, java.lang.Long, java.lang.String)
     */
    public Boolean checkPreviousHit(final String ipAddress, final Long id,
            final TypeSearchResult searchHitby) {
        boolean hit = false;
        final List<Hit> hitList = getFrontEndDao().getHitsByIpAndType(
                ipAddress, id, searchHitby);
        try {
            if (hitList.size() == 1) {
                if (hitList.get(0).getIpAddress().equals(ipAddress)) {
                    hit = true;
                }
            } else if (hitList.size() > 1) {
                log.error("List cant'be greater than one");
            }
        } catch (Exception e) {
            log.error(e);
        }
        return hit;
    }

    /**
     * Delete all user votes.
     * @param userVotes
     */
    private void deleteHits(final List<Hit> userVotes) {
        for (Hit userVote : userVotes) {
            getFrontEndDao().delete(userVote);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IFrontEndService#registerVote()
     */
    public Status registerVote(
            final Long itemId,
            final TypeSearchResult searchResult,
            final String ipAddress) throws EnMeExpcetion {
        UserAccount user = getUserAccount(getUserPrincipalUsername());
        Status status = Status.ACTIVE;
        final Long INCREASE_VOTES = 1L;
        final String userVote = user.getUsername();
            try {
                //vote process
                if (searchResult.equals(TypeSearchResult.TWEETPOLL)) {
                    final TweetPoll tp = getTweetPollService().getTweetPollPublishedById(itemId);
                    List<Hit> vote = getFrontEndDao().getVotesByType(TypeSearchResult.TWEETPOLL, user, tp.getQuestion());
                    if (vote.size() == 0) {
                        final Long votes = tp.getNumbervotes() + INCREASE_VOTES;
                        tp.setNumbervotes(votes);
                        getTweetPollDao().saveOrUpdate(tp);
                        newHitItem(TypeSearchResult.TWEETPOLL, ipAddress, tp.getQuestion(),user, HitCategory.VOTE);
                    } else {
                        status = Status.INACTIVE;
                        tp.setNumbervotes(tp.getNumbervotes() - 1);
                        getTweetPollDao().saveOrUpdate(tp);
                        deleteHits(vote);
                    }
                } else if (searchResult.equals(TypeSearchResult.POLL)) {
                    final Poll poll = getPollService().getPollById(itemId);
                    List<Hit> vote = getFrontEndDao().getVotesByType(TypeSearchResult.POLL, user, poll.getQuestion());
                    if (vote.size() == 0) {
                        final Long votes = poll.getNumbervotes() + INCREASE_VOTES;
                        poll.setNumbervotes(votes);
                        getPollDao().saveOrUpdate(poll);
                        newHitItem(TypeSearchResult.POLL, ipAddress, poll.getQuestion(), user, HitCategory.VOTE);
                    } else {
                        status = Status.INACTIVE;
                        poll.setNumbervotes(poll.getNumbervotes() - 1);
                        getPollDao().saveOrUpdate(poll);
                        deleteHits(vote);
                    }
                } else if (searchResult.equals(TypeSearchResult.SURVEY)) {
                    //TODO: Vote a Survey.
                    throw new EnMeExpcetion("is not possible to vote surveys yet");
                }
                //register the vote.
                // NOTE: no anonymous votes anymore
                if (EnMeUtils.ANONYMOUS_USER.equals(userVote)) {
                    throw new EnMeExpcetion("you must be logged to vote");
                }
            } catch (EnMeNoResultsFoundException e) {
                log.error(e);
                status = Status.FAILED;
            }
        return status;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#registerHit(org.encuestame
     * .persistence.domain.tweetpoll.TweetPoll,
     * org.encuestame.persistence.domain.survey.Poll,
     * org.encuestame.persistence.domain.survey.Survey,
     * org.encuestame.persistence.domain.HashTag, java.lang.String)
     */
    public Boolean registerHit(final TweetPoll tweetPoll, final Poll poll,
            final Survey survey, final HashTag tag, final String ip, final HitCategory hitCategory)
            throws EnMeNoResultsFoundException {
        final Hit hit;
        Long hitCount = 1L;
        Boolean register = false;
        // HashTag
        if (ip != null) {
            if (tag != null) {
                hit = this.newHashTagHit(tag, ip, hitCategory);
                hitCount = tag.getHits() == null ? 0L : tag.getHits()
                        + hitCount;
                tag.setHits(hitCount);
                getFrontEndDao().saveOrUpdate(tag);
                register = true;
            } else if (tweetPoll != null) {
                hit = this.newTweetPollHit(tweetPoll, ip, hitCategory);
                hitCount = tweetPoll.getHits() + hitCount;
                tweetPoll.setHits(hitCount);
                getFrontEndDao().saveOrUpdate(tweetPoll);
                register = true;
            } else if (poll != null) {
                hit = this.newPollHit(poll, ip, hitCategory);
                hitCount = poll.getHits() + hitCount;
                poll.setHits(hitCount);
                getFrontEndDao().saveOrUpdate(poll);
                register = true;
            } else if (survey != null) {
                hit = this.newSurveyHit(survey, ip, hitCategory);
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
     *
     * @param tweetPoll
     * @param poll
     * @param survey
     * @param tag
     * @param ipAddress
     * @return
     */

    @Transactional(readOnly = false)
    private Hit newHitItem(
            final TweetPoll tweetPoll,
            final Poll poll,
            final Survey survey,
            final HashTag tag,
            final String ipAddress,
            final HitCategory hitCategory) {
        final Hit hitItem = new Hit();
        hitItem.setHitDate(Calendar.getInstance().getTime());
        hitItem.setHashTag(tag);
        hitItem.setIpAddress(ipAddress);
        hitItem.setTweetPoll(tweetPoll);
        hitItem.setPoll(poll);
        hitItem.setSurvey(survey);
        hitItem.setHitCategory(hitCategory);
        getFrontEndDao().saveOrUpdate(hitItem);
        return hitItem;
    }

    /**
     *
     * @param typeSearchResult
     * @param ipAddress
     * @param question
     * @param hitCategory
     * @return
     */
    private Hit newHitItem(
            final TypeSearchResult typeSearchResult,
            final String ipAddress,
            final Question question,
            final UserAccount userAccount,
            final HitCategory hitCategory) {
        final Hit hitItem = new Hit();
        hitItem.setHitDate(Calendar.getInstance().getTime());
        hitItem.setQuestion(question);
        hitItem.setUserAccount(userAccount);
        hitItem.setIpAddress(ipAddress);
        hitItem.setTypeSearchResult(typeSearchResult);
        hitItem.setHitCategory(hitCategory);
        getFrontEndDao().saveOrUpdate(hitItem);
        return hitItem;
    }

        /**
         * New tweet poll hit item.
         *
         * @param tweetPoll
         * @param ipAddress
         * @return
         */
    private Hit newTweetPollHit(final TweetPoll tweetPoll,
            final String ipAddress, final HitCategory hitCategory) {
        return this.newHitItem(tweetPoll, null, null, null, ipAddress, hitCategory);
    }

    /**
     * New poll hit item.
     *
     * @param poll
     * @param ipAddress
     * @return
     */
    private Hit newPollHit(final Poll poll, final String ipAddress, final HitCategory hitCategory) {
        return this.newHitItem(null, poll, null, null, ipAddress, hitCategory);
    }

    /**
     * New hash tag hit item.
     *
     * @param tag
     * @param ipAddress
     * @return
     */
    private Hit newHashTagHit(final HashTag tag, final String ipAddress, final HitCategory hitCategory) {
        return this.newHitItem(null, null, null, tag, ipAddress, hitCategory);
    }

    /**
     * New survey hit item.
     *
     * @param survey
     * @param ipAddress
     * @return
     */
    private Hit newSurveyHit(final Survey survey, final String ipAddress, final HitCategory hitCategory) {
        return this.newHitItem(null, null, survey, null, ipAddress, hitCategory);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#registerAccessRate(org
     * .encuestame.persistence.domain.TypeSearchResult, java.lang.Long,
     * java.lang.String, java.lang.Boolean)
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
                recordAccessRate = this.checkExistTweetPollPreviousRecord(
                        tweetPoll, ipAddress, rate);
            }
            // Poll Acces rate item.
            if (type.equals(TypeSearchResult.POLL)) {
                // Find poll by itemId.
                final Poll poll = this.getPoll(itemId);
                Assert.assertNotNull(poll);
                // Check if exist a previous poll access record.
                recordAccessRate = this.checkExistPollPreviousRecord(poll,
                        ipAddress, rate);
            }
            // Survey Access rate item.
            if (type.equals(TypeSearchResult.SURVEY)) {
                // Find survey by itemId.
                final Survey survey = this.getSurvey(itemId);
                Assert.assertNotNull(survey);
                // Check if exist a previous survey access record.
                recordAccessRate = this.checkExistSurveyPreviousRecord(survey,
                        ipAddress, rate);
            }
        }
        return recordAccessRate;
    }

    /**
     * Check exist tweetPoll previous record.
     *
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
            throw new EnMeExpcetion(
                    "Access rate list found coudn't be greater than one ");
        } else if (rateList.size() == 1) {
            // Get first element from access rate list
            accessRate = rateList.get(0);
            // Check if the option selected is the same that you have registered
            if (accessRate.getRate() == option) {
                log.warn("The option was previously selected "
                        + accessRate.getRate());
            } else {
                // We proceed to update the record in the table access Rate.
                accessRate.setRate(option);
                // Update the value in the fields of TweetPoll
                this.setTweetPollSocialOption(option, tpoll);
                // Save access rate record.
                getFrontEndDao().saveOrUpdate(accessRate);
            }

        } else {
            // Otherwise, create access rate record.
            accessRate = this.newTweetPollAccessRate(tpoll, ipAddress, option);
            // update tweetPoll record.
            this.setTweetPollSocialOption(option, tpoll);
        }
        return accessRate;
    }

    /**
     * Check exist Poll previous record.
     *
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
            throw new EnMeExpcetion(
                    "Access rate list found coudn't be greater than one ");
        } else if (rateList.size() == 1) {
            // Get first element from access rate list
            accessRate = rateList.get(0);
            // Check if the option selected is the same that you have registered
            if (accessRate.getRate() == option) {
                log.warn("The option was previously selected "
                        + accessRate.getRate());
            } else {
                // We proceed to update the record in the table access Rate.
                accessRate.setRate(option);
                // Update the value in the fields of TweetPoll
                this.setPollSocialOption(option, poll);
                // Save access rate record.
                getFrontEndDao().saveOrUpdate(accessRate);
            }

        } else {
            // Otherwise, create access rate record.
            accessRate = this.newPollAccessRate(poll, ipAddress, option);
            // update poll record.
            this.setPollSocialOption(option, poll);
        }
        return accessRate;
    }

    /**
     * Check exist Survey previous record.
     *
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
            throw new EnMeExpcetion(
                    "Access rate list found coudn't be greater than one ");
        } else if (rateList.size() == 1) {
            // Get first element from access rate list
            accessRate = rateList.get(0);
            // Check if the option selected is the same that you have registered
            if (accessRate.getRate() == option) {
                log.warn("The option was previously selected "
                        + accessRate.getRate());
            } else {
                // We proceed to update the record in the table access Rate.
                accessRate.setRate(option);
                // Update the value in the fields of survey
                this.setSurveySocialOption(option, survey);
                // Save access rate record.
                getFrontEndDao().saveOrUpdate(accessRate);
            }

        } else {
            // Otherwise, create access rate record.
            accessRate = this.newSurveyAccessRate(survey, ipAddress, option);
            // update poll record.
            this.setSurveySocialOption(option, survey);
        }
        return accessRate;
    }

    /**
     * Set tweetpoll social options.
     *
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
     *
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
     *
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
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#getAccessRateItem(java
     * .lang.String, java.lang.Long,
     * org.encuestame.persistence.domain.TypeSearchResult)
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
     *
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
    private TweetPoll getTweetPoll(final Long id)
            throws EnMeNoResultsFoundException {
        return getTweetPollService().getTweetPollById(id);
    }

    private Integer getSocialAccountsLinksByItem(final TweetPoll tpoll,
            final Survey survey, final Poll poll,
            final TypeSearchResult itemType) {
        final List<TweetPollSavedPublishedStatus> totalAccounts = getTweetPollDao()
                .getLinksByTweetPoll(tpoll, survey, poll, itemType);
        return totalAccounts.size();

    }

    /**
     * Get Relevance value by item.
     *
     * @param likeVote
     * @param dislikeVote
     * @param hits
     * @param totalComments
     * @param totalSocialAccounts
     * @param totalNumberVotes
     * @param totalHashTagHits
     * @return
     */
    private long getRelevanceValue(final long likeVote, final long dislikeVote,
            final long hits, final long totalComments,
            final long totalSocialAccounts, final long totalNumberVotes,
            final long totalHashTagHits) {
        final long relevanceValue = EnMeUtils.calculateRelevance(likeVote,
                dislikeVote, hits, totalComments, totalSocialAccounts,
                totalNumberVotes, totalHashTagHits);
        log.info("*******************************");
        log.info("******* Resume of Process *****");
        log.info("-------------------------------");
        log.info("|  Total like votes : " + likeVote + "            |");
        log.info("|  Total dislike votes : " + dislikeVote + "            |");
        log.info("|  Total hits : " + hits + "            |");
        log.info("|  Total Comments : " + totalComments + "            |");
        log.info("|  Total Social Network : " + totalSocialAccounts
                + "            |");
        log.info("|  Total Votes : " + totalNumberVotes + "            |");
        log.info("|  Total HashTag hits : " + totalHashTagHits
                + "            |");
        log.info("-------------------------------");
        log.info("*******************************");
        log.info("************ Finished Start Relevance calculate job **************");
        return relevanceValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.imp.IFrontEndService#
     * processItemstoCalculateRelevance(java.util.List, java.util.List,
     * java.util.List, java.util.Calendar, java.util.Calendar)
     */
    public void processItemstoCalculateRelevance(
            final List<TweetPoll> tweetPollList,
            final List<Poll> pollList,
            final List<Survey> surveyList,
            final SearchPeriods periods) {
        long likeVote;
        long dislikeVote;
        long hits;
        long relevance;
        long comments;
        long socialAccounts;
        long numberVotes;
        long hashTagHits;

        for (TweetPoll tweetPoll : tweetPollList) {
            likeVote = tweetPoll.getLikeVote() == null ? 0 : tweetPoll.getLikeVote();
            dislikeVote = tweetPoll.getDislikeVote() == null ? 0 : tweetPoll.getDislikeVote();
            hits = tweetPoll.getHits() == null ? 0 : tweetPoll.getHits();
            // final Long userId = tweetPoll.getEditorOwner().getUid();
            socialAccounts = this.getSocialAccountsLinksByItem(tweetPoll, null, null, TypeSearchResult.TWEETPOLL);
            numberVotes = tweetPoll.getNumbervotes();
            comments = getTotalCommentsbyType(tweetPoll.getTweetPollId(), TypeSearchResult.TWEETPOLL);
            log.debug("Total comments by TweetPoll ---->" + comments);
            hashTagHits = this.getTotalHits(tweetPoll.getTweetPollId(), TypeSearchResult.HASHTAG, periods);
            relevance = this.getRelevanceValue(likeVote, dislikeVote, hits, comments, socialAccounts, numberVotes, hashTagHits);
            tweetPoll.setRelevance(relevance);
            getTweetPollDao().merge(tweetPoll);
        }

        for (Poll poll : pollList) {
            likeVote = poll.getLikeVote() == null ? 0 : poll.getLikeVote();
            dislikeVote = poll.getDislikeVote() == null ? 0 : poll.getDislikeVote();
            hits = poll.getHits() == null ? 0 : poll.getHits();
            socialAccounts = this.getSocialAccountsLinksByItem(null, null, poll, TypeSearchResult.POLL);
            numberVotes = poll.getNumbervotes() == null ? 0 : poll.getNumbervotes();
            comments = getTotalCommentsbyType(poll.getPollId(), TypeSearchResult.POLL);
            log.debug("Total Comments by Poll ---->" + comments);
            hashTagHits = this.getTotalHits(poll.getPollId(), TypeSearchResult.HASHTAG, periods);
            relevance = this.getRelevanceValue(likeVote, dislikeVote, hits, comments, socialAccounts, numberVotes, hashTagHits);
            poll.setRelevance(relevance);
            getPollDao().merge(poll);
        }

    }

    /**
     * Get last {@link HashTagRanking} position
     * @param maxDate
     * @param tagName
     * @return
     */
    private Integer getHashTagLastPosition(final Date maxDate,
            final String tagName) {
        Integer lastPosValue = null;
        final List<HashTagRanking> tagRanking = getHashTagDao().getHashTagRankingLastPosition(maxDate);
        if (tagRanking.size() > 0) {
            for (int i = 0; i < tagRanking.size(); i++) {
                if (tagRanking.get(i).getHashTag().getHashTag().equals(tagName)) {
                    lastPosValue = i+1;
                }
            }
        }
        return lastPosValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#getHashTagRanking(java
     * .lang.String)
     */
    public List<HashTagRankingBean> getHashTagRanking(final String tagName) {
        final Date maxRankingDate;
        maxRankingDate = getHashTagDao().getMaxHashTagRankingDate();

        List<HashTagRanking> hashTagRankingList = getHashTagDao()
                .getHashTagRankStats(maxRankingDate);
        log.debug("Hashtag ranking list --->" + hashTagRankingList.size());
        final Integer value = 1;
        Integer position = 0;
        Integer lastRankPosition;

        final List<HashTagRankingBean> tagRankingBeanList = new ArrayList<HashTagRankingBean>();

        HashTagRankingBean ranking = new HashTagRankingBean();
        final Integer hashTagRankListSize = hashTagRankingList.size() - value;
        Integer positionBefore;
        Integer positionAfter;
        log.debug("Hashtag ranking list --->" + hashTagRankListSize);
        for (int i = 0; i < hashTagRankingList.size(); i++) {
            if (hashTagRankingList.get(i).getHashTag().getHashTag()
                    .equals(tagName)) {
                // Retrieve hashtag main.
                position =i;
                positionBefore = position - value;
                positionAfter = position + value;
                lastRankPosition = this.getHashTagLastPosition(maxRankingDate, hashTagRankingList.get(i).getHashTag().getHashTag());
                ranking = this.createHashTagRankingBean(
                        hashTagRankingList.get(i).getRankId(),
                        hashTagRankingList.get(i).getAverage(), positionAfter, tagName,
                        lastRankPosition);

                tagRankingBeanList.add(ranking);
                log.debug("HashTag ranking main ---> "
                        + hashTagRankingList.get(i).getHashTag().getHashTag());
                log.debug("HashTag ranking main position---> " + position);

                if ((position > 0) && (position < hashTagRankListSize)) {
                    log.debug(" --- HashTag ranking first option ---");
                    // Save hashTag before item
                    lastRankPosition = this.getHashTagLastPosition(maxRankingDate, hashTagRankingList.get(positionBefore).getHashTag().getHashTag());
                    ranking = this
                            .createHashTagRankingBean(
                                    hashTagRankingList.get(positionBefore)
                                            .getRankId(), hashTagRankingList
                                            .get(positionBefore).getAverage(),
                                            position,
                                    hashTagRankingList.get(positionBefore)
                                            .getHashTag().getHashTag(),
                                    lastRankPosition);
                    tagRankingBeanList.add(ranking);

                    // Save hashTag after item
                    lastRankPosition = this.getHashTagLastPosition(maxRankingDate, hashTagRankingList.get(positionAfter).getHashTag().getHashTag());
                    ranking = this.createHashTagRankingBean(hashTagRankingList
                            .get(positionAfter).getRankId(), hashTagRankingList
                            .get(positionAfter).getAverage(), position +2,
                            hashTagRankingList.get(positionAfter).getHashTag()
                                    .getHashTag(), lastRankPosition);

                    tagRankingBeanList.add(ranking);
                } else if ((position > 0) && (position == hashTagRankListSize)) {
                    log.debug(" --- HashTag ranking second option --- ");
                    // Save hashTag before item
                    lastRankPosition = this.getHashTagLastPosition(maxRankingDate, hashTagRankingList.get(positionBefore).getHashTag().getHashTag());
                    ranking = this
                            .createHashTagRankingBean(
                                    hashTagRankingList.get(positionBefore)
                                            .getRankId(), hashTagRankingList
                                            .get(positionBefore).getAverage(),
                                            position,
                                    hashTagRankingList.get(positionBefore)
                                            .getHashTag().getHashTag(),
                                    lastRankPosition);
                    tagRankingBeanList.add(ranking);
                } else if ((position == 0)) {
                    log.debug(" --- HashTag ranking second option --- ");
                    // Save hashTag after item
                    lastRankPosition = this.getHashTagLastPosition(maxRankingDate, hashTagRankingList.get(positionAfter).getHashTag().getHashTag());
                    ranking = this.createHashTagRankingBean(hashTagRankingList
                            .get(positionAfter).getRankId(), hashTagRankingList
                            .get(positionAfter).getAverage(), i+2,
                            hashTagRankingList.get(positionAfter).getHashTag()
                                    .getHashTag(), lastRankPosition);
                    tagRankingBeanList.add(ranking);
                }
            }
        }
        Collections.sort(tagRankingBeanList);
        return tagRankingBeanList;
    }

    /**
     *
     * @param id
     * @param average
     * @param position
     * @param tagName
     * @param lastPos
     * @return
     */
    private HashTagRankingBean createHashTagRankingBean(final Long id,
            final Double average, final Integer position, final String tagName,
            final Integer lastPos) {
        final HashTagRankingBean tagItemRanking = new HashTagRankingBean();
        tagItemRanking.setPosition(position);
        tagItemRanking.setTagName(tagName);
        tagItemRanking.setLastPosition(lastPos == null ? 0 : lastPos);
        return tagItemRanking;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#retrieveGenericStats
     * (java.lang.String, org.encuestame.utils.enums.TypeSearchResult)
     */
    public GenericStatsBean retrieveGenericStats(final String itemId,
            final TypeSearchResult itemType, HttpServletRequest request)
            throws EnMeNoResultsFoundException {
        GenericStatsBean genericBean = new GenericStatsBean();
        long totalHits = 0L;
        String createdBy = " ";
        String createdAt = null;
        double average = 0;
        long likeDislikeRate = 0L;
        long likeVotes;
        long dislikeVotes;
        long id;
        if (itemType.equals(TypeSearchResult.TWEETPOLL)) {
            id = new Long(Long.parseLong(itemId));
            final TweetPoll tweetPoll = this.getTweetPoll(id);
            totalHits = tweetPoll.getHits() == null ? 0 : tweetPoll.getHits();
            createdBy = tweetPoll.getEditorOwner().getUsername() == null ? ""
                    : tweetPoll.getEditorOwner().getUsername();
            createdAt = convertRelativeTimeToString(tweetPoll.getCreateDate(), request);
            likeVotes = tweetPoll.getLikeVote() == null ? 0L : tweetPoll
                    .getLikeVote();
            dislikeVotes = tweetPoll.getDislikeVote() == null ? 0L : tweetPoll
                    .getDislikeVote();
            // Like/Dislike Rate = Total Like votes minus total dislike votes.
            likeDislikeRate = (likeVotes - dislikeVotes);
            genericBean = ConvertDomainBean.createGenericStatsBean(likeDislikeRate, totalHits, createdBy, average, createdAt);
        } else if (itemType.equals(TypeSearchResult.POLL)) {
            id = new Long(Long.parseLong(itemId));
            final Poll poll = this.getPoll(id);
            totalHits = poll.getHits() == null ? 0 : poll.getHits();
            createdBy = poll.getEditorOwner().getUsername();
            createdAt = convertRelativeTimeToString(poll.getCreateDate(), request);
            likeVotes = poll.getLikeVote() == null ? 0L : poll.getLikeVote();
            dislikeVotes = poll.getDislikeVote() == null ? 0L : poll
                    .getDislikeVote();
            likeDislikeRate = (likeVotes - dislikeVotes);
            genericBean = ConvertDomainBean.createGenericStatsBean(likeDislikeRate, totalHits, createdBy, average, createdAt);
        } else if (itemType.equals(TypeSearchResult.SURVEY)) {
            id = new Long(Long.parseLong(itemId));
            final Survey survey = this.getSurvey(id);
            totalHits = survey.getHits();
            createdBy = survey.getEditorOwner().getUsername() == null ? " "
                    : survey.getEditorOwner().getUsername();
            createdAt = convertRelativeTimeToString(survey.getCreateDate(), request);
            likeVotes = survey.getLikeVote();
            dislikeVotes = survey.getDislikeVote();
            likeDislikeRate = (likeVotes - dislikeVotes);
            genericBean = ConvertDomainBean.createGenericStatsBean(likeDislikeRate, totalHits, createdBy, average, createdAt);
        } else if (itemType.equals(TypeSearchResult.HASHTAG)) {
            final HashTag tag = getHashTagItem(itemId);
            totalHits = tag.getHits();
            createdAt = convertRelativeTimeToString(tag.getUpdatedDate(), request);
            likeDislikeRate = 0L;
            genericBean = ConvertDomainBean.createGenericStatsBean(likeDislikeRate, totalHits, null, average, createdAt);
        }
        return genericBean;
    }


    /**
     * Get survey by id.
     *
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private Survey getSurvey(final Long id) throws EnMeNoResultsFoundException {
        return getSurveyService().getSurveyById(id);
    }

    /**
     * Get Poll by id.
     *
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private Poll getPoll(final Long id) throws EnMeNoResultsFoundException {
        return getPollService().getPollById(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#getTopRatedProfile(java
     * .lang.Boolean)
     */
    public List<ProfileRatedTopBean> getTopRatedProfile(final Boolean status)
            throws EnMeNoResultsFoundException {
        Long topValue = 0L;
        Long totalPublications;
        Long publishedTweetPolls;
        Long publishedPolls;

        final List<ProfileRatedTopBean> profileItems = new ArrayList<ProfileRatedTopBean>();

        final List<UserAccount> users = getSecurityService()
                .getUserAccountsAvailable(status);

        for (UserAccount userAccount : users) {
            publishedTweetPolls = this.getTotalTweetPollPublished(userAccount,
                    status);

            publishedPolls = this.getTotalPollPublished(userAccount, status);
            totalPublications = publishedTweetPolls + publishedPolls;
            topValue = topValue + totalPublications;
            profileItems.add(this.createProfileTopBean(0, 0L, 0L, 0, topValue,
                    totalPublications, "myurl", userAccount.getUsername()));
        }
        Collections.sort(profileItems);
        return profileItems;
    }

    /**
     * Create {@link ProfileRatedTopBean}.
     * @param currentPos
     * @param dislike
     * @param likeVotes
     * @param lastPos
     * @param topValue
     * @param total
     * @param url
     * @param username
     * @return
     */
    private ProfileRatedTopBean createProfileTopBean(final Integer currentPos,
            final Long dislike, final Long likeVotes, final Integer lastPos,
            final Long topValue, final Long total, final String url, final String username) {
        ProfileRatedTopBean profile = new ProfileRatedTopBean();
        profile.setCurrentPos(currentPos);
        profile.setDisLikeVotes(dislike);
        profile.setLastPos(lastPos);
        profile.setLikeVotes(likeVotes);
        profile.setTopValue(topValue);
        profile.setTotalbyItems(total);
        profile.setUrl(url);
        profile.setUsername(username);

        return profile;

    }

    private Long getTotalPollPublished(final UserAccount user, final Boolean status){
          final Long totalPollPublished;
          totalPollPublished = getPollDao().getTotalPollsbyUser(user, status);
        return totalPollPublished;
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#getTotalItemsPublishedByType
     * (org.encuestame.persistence.domain.security.UserAccount,
     * java.lang.Boolean, org.encuestame.utils.enums.TypeSearchResult)
     */
    public Long getTotalItemsPublishedByType(
            final UserAccount user,
            final Boolean status,
            final TypeSearchResult typeSearch)
            throws EnMeSearchException {
        Long totalBy = 0L;
        if(typeSearch.equals(TypeSearchResult.TWEETPOLL)){
            totalBy = getTotalTweetPollPublished(user, status);
        } else if (typeSearch.equals(TypeSearchResult.POLL)){
            totalBy = getTotalPollPublished(user, status);
        } else if (typeSearch.equals(TypeSearchResult.SURVEY)){
            // TODO: Create method to retrieve survey by user
            totalBy = 1L;
        } else {
            throw new EnMeSearchException("Type search parameter not valid: ");
        }
        return totalBy;
    }

    /**
     * Retrieve total tweetpolls published by user.
     * @param user
     * @param status
     * @return
     */
    private Long getTotalTweetPollPublished(final UserAccount user,
            final Boolean status) {
        final Long totalTweetPollPublished;
        totalTweetPollPublished = getTweetPollDao().getTotalTweetPoll(user,
                status);
        log.trace("total tweetPolss published by -->" + totalTweetPollPublished);
        return totalTweetPollPublished;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IFrontEndService#getHashTagLinks(org.
     * encuestame.persistence.domain.HashTag)
     */
    public List<LinksSocialBean> getHashTagLinks(final HashTag hash, final Integer start,
            final Integer max) {
        final List<TweetPoll> tpollByHashtag = getTweetPollDao().getTweetpollByHashTagName(hash.getHashTag(), null, null, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
        final List<Poll> pollsByHashtag = getPollDao().getPollByHashTagName(hash.getHashTag(), null, null, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
        final List<TweetPollSavedPublishedStatus> links = getFrontEndDao()
                .getLinksByHomeItem(hash, null, null, null, null,
                        TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME, start, max, tpollByHashtag, pollsByHashtag);
        log.debug("getTweetPollLinks getHashTagLinks HASHTAG: " + links.size());
        return ConvertDomainBean.convertTweetPollSavedPublishedStatus(links);
    }

    /**
     * Total Usage by item.
     */
    public void getTotalUsagebyHashTagAndDateRange() {}




    /**
     * @return the tweetPollService
     */
    public ITweetPollService getTweetPollService() {
        return tweetPollService;
    }

    /**
     * @param tweetPollService
     *            the tweetPollService to set
     */
    @Autowired
    public void setTweetPollService(ITweetPollService tweetPollService) {
        this.tweetPollService = tweetPollService;

    }

    /**
     * @return the pollService
     */
    public IPollService getPollService() {
        return pollService;
    }

    /**
     * @param pollService
     *            the pollService to set
     */
    @Autowired
    public void setPollService(final IPollService pollService) {
        this.pollService = pollService;
    }

    /**
     * @return the surveyService
     */
    public ISurveyService getSurveyService() {
        return surveyService;
    }

    /**
     * @param surveyService
     *            the surveyService to set
     */
    @Autowired
    public void setSurveyService(final ISurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * @return the securityService
     */
    public SecurityOperations getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService
     *            the securityService to set
     */
    @Autowired
    public void setSecurityService(SecurityOperations securityService) {
        this.securityService = securityService;
    }

}
