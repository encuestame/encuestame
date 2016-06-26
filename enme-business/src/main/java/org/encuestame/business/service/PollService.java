/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2010
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

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.IPollService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.Hit;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.domain.survey.PollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.util.exception.EnMeException;
import org.encuestame.util.exception.EnMeNoResultsFoundException;
import org.encuestame.util.exception.EnMePollNotFoundException;
import org.encuestame.util.exception.EnmeFailOperation;
import org.encuestame.utils.*;
import org.encuestame.utils.enums.*;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.SearchBean;
import org.encuestame.utils.social.SocialProvider;
import org.encuestame.utils.web.*;
import org.encuestame.utils.web.search.PollSearchBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Poll Service.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  April 01, 2010
 */
@Service
@Transactional
public class PollService extends AbstractSurveyService implements IPollService{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());


    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#filterPollByItemsByType(org.encuestame.utils.enums.TypeSearch, java.lang.String, java.lang.Integer, java.lang.Integer, org.encuestame.utils.enums.TypeSearchResult)
     */
    public List<PollBean> filterPollByItemsByType(
            final TypeSearch typeSearch,
            String keyword,
            Integer max,
            Integer start)
            throws EnMeNoResultsFoundException, EnMeException {
        final List<PollBean> list = new ArrayList<PollBean>();
        final UserAccount user = getUserAccount(getUserPrincipalUsername());
        if (TypeSearch.KEYWORD.equals(typeSearch)) {
            list.addAll(this.searchPollByKeyword(keyword, max, start));
        } else if (TypeSearch.BYOWNER.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(getPollDao()
                    .findAllPollByEditorOwner(
                            user, max,
                            start)));
        } else if (TypeSearch.LASTDAY.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(this
                    .getPollDao().retrievePollToday(
                            user.getAccount(),
                            max,
                            start,
                            DateUtil.getNextDayMidnightDate())));
        } else if (TypeSearch.LASTWEEK.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(this
                    .getPollDao().retrievePollLastWeek(
                            user.getAccount(), max, start,
                            DateUtil.getNextDayMidnightDate())));
        } else if (TypeSearch.FAVOURITES.equals(typeSearch)) {
            list.addAll(ConvertDomainBean.convertListToPollBean(getPollDao()
                    .retrieveFavouritesPoll(
                            user, max,
                            start)));
        } else if (TypeSearch.ALL.equals(typeSearch)) {
            list.addAll(ConvertDomainBean
                    .convertListToPollBean(getPollDao().retrievePollsByUserId(
                            user, max, start)));
        } else {
            throw new EnMeException("operation not valid");
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.encuestame.core.service.imp.IPollService#filterSearchPollsByType(
     * org.encuestame.utils.web.search.PollSearchBean,
     * javax.servlet.http.HttpServletRequest)
     */
    public List<SearchBean> filterSearchPollsByType(
            final PollSearchBean pollSearch)
            throws EnMeNoResultsFoundException, EnMeException {
        final List<SearchBean> list = new ArrayList<SearchBean>();
        if (TypeSearch.BYOWNER.equals(pollSearch.getTypeSearch())) {
            list.addAll(this.getPollsByUserNameSearch(getUserPrincipalUsername(), pollSearch));
        } else if (TypeSearch.LASTDAY.equals(pollSearch.getTypeSearch())) {
            list.addAll(this.searchPollsToday(getUserPrincipalUsername(),  pollSearch));
        } else if (TypeSearch.LASTWEEK.equals(pollSearch.getTypeSearch())) {
            list.addAll(this.searchPollsLastWeek(getUserPrincipalUsername(),  pollSearch));
        } else if (TypeSearch.FAVOURITES.equals(pollSearch.getTypeSearch())) {
            list.addAll(this.searchPollFavourites(getUserPrincipalUsername(),  pollSearch));
        } else if (TypeSearch.ALL.equals(pollSearch.getTypeSearch())) {
            //list.addAll(this.getPollsByUserNameSearch(getUserPrincipalUsername(), httpServletRequest, pollSearch));
            //FIXME: no advanced search enabled
            list.addAll(this.getAllPollSearch(getUserPrincipalUsername(), pollSearch));
        } else {
            throw new EnMeException("operation not valid");
        }
        log.debug("Poll Search Items : " + list.size());
        return list;
    }

    /**
     *
     * @param pollSearch
     * @return
     * @throws EnMeNoResultsFoundException
     */
//	private List<Poll> getPollByAdvancedSearch(final PollSearchBean pollSearch) throws EnMeNoResultsFoundException {
//		final List<Poll> polls = getPollDao().retrievePollsByUserId(
//				pollSearch.getKeyword(), getUserAccountId(getUserPrincipalUsername()),
//				pollSearch.getMax(), pollSearch.getStart(),
//				pollSearch.getIsComplete(), pollSearch.getIsScheduled(),
//				pollSearch.getIsScheduled(), pollSearch.getIsFavourite(),
//				pollSearch.getPeriod());
//		return polls;
//	}

    private List<SearchBean> getAllPollSearch(
            final String username,
            final PollSearchBean pollSearch) throws EnMeNoResultsFoundException {
        List<Poll> pollsSearchResult = new ArrayList<Poll>();
        final List<Poll> polls = getPollDao().retrievePollsByUserId(pollSearch, getUserAccountId(username));
        pollsSearchResult = this.getPollSearchResult(polls, pollSearch.getProviders(), pollSearch.getSocialAccounts());
        return ConvertDomainBean.convertPollListToSearchBean(pollsSearchResult);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IPollService#getPollsByUserNameSearch
     * (java.lang.String, javax.servlet.http.HttpServletRequest,
     * org.encuestame.utils.web.search.PollSearchBean)
     */
    public List<SearchBean> getPollsByUserNameSearch(
            final String username,
            final PollSearchBean pollSearch) throws EnMeNoResultsFoundException {
        List<Poll> pollsSearchResult = new ArrayList<Poll>();
        final List<Poll> polls = getPollDao().retrievePollsByUserId(pollSearch, getUserAccountId(username));
        pollsSearchResult = this.getPollSearchResult(polls, pollSearch.getProviders(), pollSearch.getSocialAccounts());
        return ConvertDomainBean.convertPollListToSearchBean(pollsSearchResult);
    }

    /**
     * FIXME: Is used?
     *
     * @param pollsResults
     * @param results
     * @param request
     * @return
     */
    // TODO: Retrieve results and set
    @SuppressWarnings("unused")
    private List<SearchBean> setPollAnswersSearch(
            final List<Poll> pollsResults, final Boolean results) {

        for (Poll poll : pollsResults) {
            if (results) {
                final List<PollBeanResult> pollBeanResult = this
                        .getResultVotes(poll);
                // Calculate votes percents
            }
        }

        return null;

    }

    /**
     *
     * @param polls
     * @param socialNetworks
     * @param socialAccounts
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private List<Poll> getPollSearchResult(
            final List<Poll> polls,
            final List<SocialProvider> socialNetworks,
            final List<Long> socialAccounts) throws EnMeNoResultsFoundException {
        List<Poll> tpollsbysocialNetwork = new ArrayList<Poll>();

        if ((socialNetworks.size() > 0) || (socialAccounts.size() > 0)) {
            tpollsbysocialNetwork = this.retrievePollsPostedOnSocialNetworks(
                    polls, socialNetworks, socialAccounts);
         } else {
            tpollsbysocialNetwork = polls;
        }
        log.info("tweetPoll size: " + polls.size());
         return tpollsbysocialNetwork;
    }

    /**
     *
     * @param polls
     * @param providers
     * @param socialAccounts
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private List<Poll> retrievePollsPostedOnSocialNetworks(
            final List<Poll> polls, final List<SocialProvider> providers,
            final List<Long> socialAccounts) throws EnMeNoResultsFoundException {
        final List<Poll> tpollsPostedOnSocialNet = new ArrayList<Poll>();
        List<TweetPollSavedPublishedStatus> tpSavedPublished = new ArrayList<TweetPollSavedPublishedStatus>();
        final List<SocialAccount> socialAccountList = this
                .retrieveSocialAccountsbyId(socialAccounts,
                        getUserPrincipalUsername());
        for (Poll poll : polls) {
            tpSavedPublished = getTweetPollDao().searchSocialLinksbyType(null,
                    poll, TypeSearchResult.POLL, providers, socialAccountList);
            if (tpSavedPublished.size() > 0) {
                tpollsPostedOnSocialNet.add(poll);
            }
        }
        return tpollsPostedOnSocialNet;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IPollService#searchPollsToday(java.lang
     * org.encuestame.utils.web.search.PollSearchBean)
     */
    public List<SearchBean> searchPollsToday(final String username,
            final PollSearchBean pollSearchBean) throws EnMeException {
        final List<Poll> polls = getPollDao().retrievePollsToday(pollSearchBean, getUserAccountId(username));
        return ConvertDomainBean.convertPollListToSearchBean(this.getPollSearchResult(polls,
                pollSearchBean.getProviders(),
                pollSearchBean.getSocialAccounts()));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IPollService#searchPollsLastWeek(java
     * .lang.String, javax.servlet.http.HttpServletRequest,
     * org.encuestame.utils.web.search.PollSearchBean)
     */
    public List<SearchBean> searchPollsLastWeek(final String username,
            final PollSearchBean pollSearchBean) throws EnMeException {
        final List<Poll> polls = getPollDao().retrievePollsLastWeek(pollSearchBean, getUserAccountId(username));
        return ConvertDomainBean.convertPollListToSearchBean(this.getPollSearchResult(polls,
                pollSearchBean.getProviders(),
                pollSearchBean.getSocialAccounts()));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IPollService#searchTweetsPollFavourites
     * (java.lang.String, javax.servlet.http.HttpServletRequest,
     * org.encuestame.utils.web.search.PollSearchBean)
     */
    public List<SearchBean> searchPollFavourites(final String username,
            final PollSearchBean pollSearchBean) throws EnMeException {
        final List<Poll> favouritePolls = getPollDao().retrieveFavouritesPoll(pollSearchBean, getUserAccountId(username));
        return ConvertDomainBean.convertPollListToSearchBean(this.getPollSearchResult(favouritePolls,
                pollSearchBean.getProviders(),
                pollSearchBean.getSocialAccounts()));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IPollService#searchTweetsPollScheduled
     * (java.lang.String, javax.servlet.http.HttpServletRequest,
     * org.encuestame.utils.web.search.PollSearchBean)
     */
    public List<SearchBean> searchPollScheduled(final String username,
            final PollSearchBean pollSearchBean) throws EnMeException {
        final List<Poll> polls = getPollDao().retrieveScheduledPoll(pollSearchBean, getUserAccountId(username));
        return ConvertDomainBean.convertPollListToSearchBean(this.getPollSearchResult(polls,
                pollSearchBean.getProviders(),
                pollSearchBean.getSocialAccounts()));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#createPoll(java.lang.String, java.lang.String[], java.lang.Boolean, java.lang.String, java.lang.Boolean, java.util.List)
     */
    public Poll createPoll(final CreatePollBean createPollBean ) throws EnMeException {
        final UserAccount user = getUserAccount(getUserPrincipalUsername());
        final Poll pollDomain = new Poll();
        try {
            if (log.isInfoEnabled()) {
                log.info("createPollBean->"+createPollBean.toString());
            }
            final QuestionBean questionBean = new QuestionBean();
            questionBean.setQuestionName(createPollBean.getQuestionName());
            final Question question = createQuestion(questionBean, user, QuestionPattern.CUSTOMIZABLE_SELECTION);
            if (question == null) {
                throw new EnMeNoResultsFoundException("Question not valid");
            } else if (createPollBean.getAnswers().length  == 0 ) {
                  throw new EnMeNoResultsFoundException("answers are required to create Poll");
            }
            else{
            final String hashPoll = MD5Utils.md5(RandomStringUtils.randomAlphanumeric(500));
            final CommentOptions commentOpt = EnumerationUtils.getEnumFromString(CommentOptions.class, createPollBean.getShowComments());
            final ShowResultsOptions showResultsOptions =  EnumerationUtils.getEnumFromString(ShowResultsOptions.class, createPollBean.getResults());
            pollDomain.setEditorOwner(user);
            pollDomain.setCreateDate(Calendar.getInstance().getTime());
            pollDomain.setPollHash(hashPoll);
            pollDomain.setQuestion(question);
            pollDomain.setPollCompleted(Boolean.FALSE);
            pollDomain.setHits(EnMeUtils.HIT_DEFAULT);
            pollDomain.setRelevance(EnMeUtils.RATE_DEFAULT);
            pollDomain.setLikeVote(EnMeUtils.LIKE_DEFAULT);
            pollDomain.setDislikeVote(EnMeUtils.DISLIKE_DEFAULT);
            pollDomain.setNumbervotes(EnMeUtils.VOTE_MIN);
            pollDomain.setEditorOwner(user);
            pollDomain.setOwner(user.getAccount());
            // Type of results display
            pollDomain.setShowResults(showResultsOptions);
            // Comments restrictions
            pollDomain.setShowComments(commentOpt);
            pollDomain.setPublish(Boolean.TRUE);
            // multiple votes enabled or not


            //FIXME: ENCUESTAME-673
            //if (createPollBean.getMultiple()) {
                //pollDomain.setMultipleResponse(org.encuestame.persistence.domain.AbstractSurvey.MultipleResponse.MULTIPLE);
            //} else {
                pollDomain.setMultipleResponse(org.encuestame.persistence.domain.AbstractSurvey.MultipleResponse.SINGLE);
            //}

            // set limit of votes by IP (the IP is reviewed in the json service)
            if (createPollBean.getRepeatedVotes() != null) {
                pollDomain.setAllowRepeatedVotes(true);
                pollDomain.setClosedQuota(createPollBean.getRepeatedVotes());
            } else {
                pollDomain.setAllowRepeatedVotes(false);
            }
            // repeated votes in total (eg: if the user defines 5K)
            if(createPollBean.getLimitVote() != null) {
                pollDomain.setLimitVotesEnabled(true);
                pollDomain.setLimitVotes(createPollBean.getLimitVote());
            } else {
                pollDomain.setLimitVotesEnabled(false);
            }
            // define the closed date
            if (createPollBean.getCloseDate() != null) {
                final Date closedDate = new Date(createPollBean.getCloseDate());
                pollDomain.setCloseAfterDate(true);
                pollDomain.setClosedDate(closedDate);
            } else {
                pollDomain.setCloseAfterDate(false);
            }
            // notifications enabled by default
            pollDomain.setNotifications(Boolean.TRUE);
            // published on create the poll
            pollDomain.setPublish(Boolean.TRUE);
            final List<HashTagBean> hashtags = EnMeUtils.createHashTagBeansList(createPollBean.hashtags);
            if (hashtags.size() > 0) {
                //http://issues.encuestame.org/browse/ENCUESTAME-504
                pollDomain.getHashTags().addAll(retrieveListOfHashTags(hashtags));
            }
            // Add answers
            this.createQuestionAnswers(createPollBean.getAnswers(), question);

            // property to define Privacy or if the poll is hidden
            pollDomain.setIsHidden(createPollBean.getIsHidden());
            // Properties to vote a poll only with password
            if ((createPollBean.getIsPasswordProtected()!= null) && (createPollBean.getIsPasswordProtected()))
            {
            	pollDomain.setIsPasswordProtected(createPollBean.getIsPasswordProtected());
            	pollDomain.setPassword(RandomStringUtils.randomAlphanumeric(5));
            }
            this.getPollDao().saveOrUpdate(pollDomain);
            // Create email Poll Notification
            this.createPollNotification(pollDomain);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            log.equals(e);
            throw new EnMeException(e);
        }
        return pollDomain;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#updatePoll(org.encuestame.utils.web.PollBean)
     */
    public Poll updatePoll(final PollBean pollBean)
            throws EnMeNoResultsFoundException {
        final Poll poll = getPollById(pollBean.getId(),
                getUserPrincipalUsername());
        Assert.notNull(poll);
        final Question questionDomain = poll.getQuestion();
        questionDomain
                .setQuestion(pollBean.getQuestionBean().getQuestionName());
        questionDomain.setSlugQuestion(RestFullUtil.slugify(pollBean
                .getQuestionBean().getQuestionName()));
        questionDomain.setCreateDate(DateUtil.getCurrentCalendarDate());
        getQuestionDao().saveOrUpdate(questionDomain);
        Assert.notNull(questionDomain);

        poll.setPollCompleted(pollBean.getCompletedPoll());
        poll.setShowResults(EnumerationUtils.getEnumFromString(ShowResultsOptions.class, pollBean.getShowResults()));
        poll.setShowComments(EnumerationUtils.getEnumFromString(CommentOptions.class, pollBean
                .getShowComments()));
        poll.setPublish(pollBean.getPublishPoll());
        poll.setNotifications(pollBean.getCloseNotification());
        poll.getHashTags().addAll(retrieveListOfHashTags(pollBean.getHashTags()));
        poll.setIsHidden(pollBean.getIsHidden());
        if(pollBean.getIsPasswordProtected()){
        	poll.setIsPasswordProtected(pollBean.getIsPasswordProtected());
            poll.setPassword(pollBean.getPassword());
        }
        return poll;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IPollService#addHashTagToPoll(org.encuestame
     * .persistence.domain.survey.Poll, org.encuestame.utils.web.HashTagBean)
     */
    public HashTag addHashTagToPoll(final Poll poll, final HashTagBean tagBean)
            throws EnMeNoResultsFoundException {
        log.debug("Adding hashtag to Poll --->" + poll.getPollId());
        log.debug("Adding hashTagBean to Poll ---> " + tagBean.getHashTagName());
        tagBean.setHashTagName(ValidationUtils
                .removeNonAlphanumericCharacters(tagBean.getHashTagName()));
        HashTag tag = getHashTag(tagBean.getHashTagName(), Boolean.FALSE);
        if (tag == null) {
            tag = createHashTag(tagBean.getHashTagName().toLowerCase());
            poll.getHashTags().add(tag);
            getPollDao().saveOrUpdate(poll);
            log.debug("Added new hashtag done :" + tag.getHashTagId());
            return tag;
        } else {
            poll.getHashTags().add(tag);
            getPollDao().saveOrUpdate(poll);
            log.debug("Added previous hashtag done :" + tag.getHashTagId());
            return tag;
        }
    }

    /**
     * Get Poll by Id without user session.
     * @param pollId poll id.
     * @return
     * @throws EnMeNoResultsFoundException
     */
     private Poll getPoll(final Long pollId) throws EnMeNoResultsFoundException{
         return this.getPollDao().getPollById(pollId);
     }

     /**
      * Retrieve {@link Poll} by {@link UserAccount}
      * @param pollId
      * @param user
      * @return
      * @throws EnMeNoResultsFoundException
      */
     private Poll getPollbyUserAndId(final Long pollId, final UserAccount user) throws EnMeNoResultsFoundException{
         return this.getPollDao().getPollById(pollId, user);
     }

     /**
      * Remove Poll
      * @param pollId
      * @throws EnMeNoResultsFoundException
      */

    public void removePoll(final Long pollId)
            throws EnMeNoResultsFoundException {
        final Poll pollDomain = this.getPollbyUserAndId(pollId, getUserAccount(getUserPrincipalUsername()));

        final List<PollResult> pollResults;
        final List<QuestionAnswer> qAnswer;
        Question question = new Question();
        if (pollDomain != null) {
            // Retrieve Poll results, answers and question.
            pollResults = getPollDao().retrievePollResults(pollDomain);
            if (pollResults.size() > 0) {
                // Delete poll results.
                for (PollResult pollResult : pollResults) {
                    getPollDao().delete(pollResult);
                }
            }
            question = pollDomain.getQuestion();
            if (question != null) {
                // Retrieve answers by Question id.
                qAnswer = this.getQuestionAnswersbyQuestionId(question.getQid());
                if (qAnswer.size() > 0) {
                    for (QuestionAnswer questionAnswer : qAnswer) {
                        getQuestionDao().delete(questionAnswer);
                    }
                }
                // remove all social links and remove all of them
                final List<TweetPollSavedPublishedStatus> list = getTweetPollDao().getAllLinks(null, null, pollDomain, TypeSearchResult.POLL);
                for (TweetPollSavedPublishedStatus tweetPollSavedPublishedStatus : list) {
                     getQuestionDao().delete(tweetPollSavedPublishedStatus);
                }
                // remove all hits
                final List<Hit> hits = getFrontEndDao().getAllHitsByType(null, pollDomain, null);
                for (Hit hit : hits) {
                    getQuestionDao().delete(hit);
                }
                // Remove Poll
                getPollDao().delete(pollDomain);
                // TODO: Remove Hits and social network publications.
                // Remove Question
                getQuestionDao().delete(question);

            } else {
                throw new EnMeNoResultsFoundException("Question  not found");
            }

        } else {
            throw new EnMeNoResultsFoundException("Poll not found");
        }
    }

    /**
     * Search Polls by Question keyword.
     * @param keywordQuestion
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeException
     */

    public List<PollBean> searchPollByKeyword(final String keywordQuestion, final Integer maxResults,
        final Integer start) throws EnMeException{
        log.debug("search keyword Poll  "+keywordQuestion);
        List<Poll> polls = new ArrayList<Poll>();
        if (keywordQuestion == null) {
            throw new EnMeException("keyword is mandatory");
        } else {
            polls = getPollDao().getPollsByQuestionKeyword(
                    keywordQuestion,
                    getUserAccount(getUserPrincipalUsername()),
                    maxResults,
                    start);
        }
        log.debug("search keyword polls size "+polls.size());
        return ConvertDomainBean.convertListToPollBean(polls);
       }

    /**
     * Search Polls by Folder Id.
     * @param folderId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */

    public List<SearchBean> searchPollsByFolder(final Long folderId, final String username) throws EnMeNoResultsFoundException{
        final PollFolder pollFolder = getPollDao().getPollFolderById(folderId);
        List<Poll> polls = new ArrayList<Poll>();
        if (pollFolder != null){
            polls = getPollDao().getPollsByPollFolderId(getUserAccount(getUserPrincipalUsername()), pollFolder);
        }
        log.info("search polls by folder size "+polls.size());
        return ConvertDomainBean.convertPollListToSearchBean(polls);
    }


    /**
     * List Poll ByUser.
     * @param maxResults
     * @return unitPoll
     * @throws EnMeNoResultsFoundException
     */

    public List<PollBean> listPollByUser(final Integer maxResults, final Integer start) throws EnMeNoResultsFoundException{
        final List<PollBean> unitPoll = new ArrayList<PollBean>();
        final List<Poll> polls = getPollDao().findAllPollByEditorOwner(getUserAccount(getUserPrincipalUsername()), maxResults, start);
         for (Poll poll : polls) {
             unitPoll.add(ConvertDomainBean.convertPollDomainToBean(poll));
        }
        return unitPoll;
    }

   /*
    * (non-Javadoc)
    * @see org.encuestame.core.service.imp.IPollService#vote(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long)
    */
    public void vote(final Poll poll, final String slug,
            final String ipAddress,
            final Long answerId)
            throws EnMeNoResultsFoundException {
        log.trace("vote "+poll);
        log.trace("vote "+slug);
        log.trace("vote "+ipAddress);
        log.trace("vote "+answerId);

        final PollResult pr = new PollResult();
        pr.setAnswer(this.getQuestionAnswerById(answerId));
        //validate IP address.
        pr.setIpaddress(ipAddress);
        pr.setPoll(poll);
        pr.setVotationDate(Calendar.getInstance().getTime());
        getPollDao().saveOrUpdate(pr);
    }

    /**
     * Get Polls by Folder.
     * @param folder
     * @return
     * @throws EnMeNoResultsFoundException
     */

    public List<PollBean> getPollsByFolder(final FolderBean folder) throws EnMeNoResultsFoundException{
        final List<Poll> polls = getPollDao().getPollsByPollFolder(getUserAccount(getUserPrincipalUsername()), getPollFolder(folder.getId()));
        return ConvertDomainBean.convertSetToPollBean(polls);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#updateQuestionPoll(java.lang.Long, org.encuestame.persistence.domain.question.Question)
     */
    public PollBean updateQuestionPoll(final Long pollId, final Question question)
            throws EnMeException {
          final Poll poll = this.getPollById(pollId, getUserAccount(getUserPrincipalUsername()));
          if (poll == null) {
              throw new EnMeNoResultsFoundException("poll not found");
          }
          else{
              poll.setQuestion(question) ;
              getPollDao().saveOrUpdate(poll);
          }
           return ConvertDomainBean.convertPollDomainToBean(poll);
    }

    /**
     * Create url poll Access.
     * @param poll
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private String createUrlPollAccess(final Poll poll) {
        StringBuffer urlBuffer = new StringBuffer("/poll/");
        urlBuffer.append(poll.getPollHash());
        urlBuffer.append("/");
        urlBuffer.append(poll.getPollId());
        urlBuffer.append("/");
        urlBuffer.append(poll.getQuestion().getSlugQuestion());
        return urlBuffer.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#createPollNotification(org.encuestame.persistence.domain.survey.Poll)
     */
    public void createPollNotification(final Poll poll) throws EnMeNoResultsFoundException {
        createNotification(NotificationEnum.POLL_PUBLISHED,
                poll.getQuestion().getQuestion(),
                this.createUrlPollAccess(poll), false);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#publicPollByList(org.encuestame.persistence.domain.survey.Poll, org.encuestame.utils.web.UnitLists)
     */
    @SuppressWarnings("unused")
    public void publicPollByList(final Poll poll, final UnitLists emailList) {
        final List<Email> emailsList = getEmailListsDao().findEmailsByListId(emailList.getId());
        final String urlPoll = this.createUrlPollAccess(poll);
        if(emailList !=null){
                 for (Email emails : emailsList) {
                    if (EnMePlaceHolderConfigurer.getBooleanProperty("application.email.disabled")) {
                            try {
                                getMailService().send(emails.getEmail(),"New Poll", urlPoll);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                //e.printStackTrace();
                                log.error(e);
                            }
                    }
                 }
         }
         else{
             log.warn("Not Found Emails in your EmailList");
        }
    }

    /**
     * Retrieve Folder Poll.
     * @return List<PollFolder>
     * @throws EnMeNoResultsFoundException exception
     */
    public List<FolderBean> retrieveFolderPoll() throws EnMeNoResultsFoundException {
        final List<PollFolder> folders = getPollDao().getPollFolderByUserAccount(getUserAccount(getUserPrincipalUsername()));
        List<FolderBean> foldersBean = ConvertDomainBean.convertListPollFolderToBean(folders);
        for (FolderBean folderItem : foldersBean) {
            //FUTURE: ENCUESTAME-263 maybe is posible to improve this query
            final List<SearchBean> tweetPollsByFolder = this.searchPollsByFolder(folderItem.getId(), getUserPrincipalUsername());
            folderItem.setItems(Long.valueOf(tweetPollsByFolder.size()));
        }
        return foldersBean;
    }

    /**
     * Create Poll Folder.
     * @param folderName
     * @return
     * @throws EnMeNoResultsFoundException
     */

    public FolderBean createPollFolder(final String folderName) throws EnMeNoResultsFoundException {
        final PollFolder pollFolder = new PollFolder();
        pollFolder.setCreatedBy(getUserAccount(getUserPrincipalUsername()));
        pollFolder.setCreatedAt(Calendar.getInstance().getTime());
        pollFolder.setFolderName(folderName);
        pollFolder.setUsers(getUserAccount(getUserPrincipalUsername()).getAccount());
        this.getPollDao().saveOrUpdate(pollFolder);
        return ConvertDomainBean.convertFolderToBeanFolder(pollFolder);
    }

    /**
     * Update FolderName.
     * @param folderId folder id
     * @param newFolderName folder name
     * @param username username
     * @return {@link FolderBean}
     * @throws EnMeNoResultsFoundException exception
     */
    public FolderBean updateFolderName(final Long folderId,
            final String newFolderName,
            final String username) throws EnMeNoResultsFoundException{
        final PollFolder folder = this.getPollFolder(folderId);
        if(folder == null){
            throw new EnMeNoResultsFoundException("poll folder not found");
        } else {
            folder.setFolderName(newFolderName);
            getPollDao().saveOrUpdate(folder);
        }
        return ConvertDomainBean.convertFolderToBeanFolder(folder);
    }

    /**
     * Get Poll Folder.
     * @param id
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private PollFolder getPollFolder(final Long id) throws EnMeNoResultsFoundException{
        if(id == null){
             throw new EnMeNoResultsFoundException("poll folder id not found");
        }else {
        return this.getPollDao().getPollFolderById(id);
        }
    }

    /**
     * Remove PollFolder.
     * @param folderId
     * @throws EnMeNoResultsFoundException
     */
    public void removePollFolder(final Long folderId) throws EnMeNoResultsFoundException{
        final PollFolder folder = this.getPollFolder(folderId);
        if(folder != null){
            getPollDao().delete(folder);
        } else {
            throw new EnMeNoResultsFoundException("poll folder not found");
        }
    }

    public List<PollBean> listPollByUser(String currentUser)
            throws EnMeNoResultsFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Add poll to Folder.
     * @param folderId
     * @param pollId
     * @throws EnMeNoResultsFoundException
     */

    public void addPollToFolder(final Long folderId, final Long pollId)
                                throws EnMeNoResultsFoundException{
        final PollFolder pfolder = this.getPollFolderByFolderIdandUser(folderId, getUserAccount(getUserPrincipalUsername()));
        if (pfolder != null) {
            final Poll poll = getPollDao().getPollById(pollId, getUserAccount(getUserPrincipalUsername()));
            if (poll == null) {
                throw new EnMeNoResultsFoundException("Poll not found");
             }
            poll.setPollFolder(pfolder);
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnMeNoResultsFoundException("TweetPoll folder not found");
    }
}

    /**
     * Get Poll folder.
     * @param folderId
     * @return
     */
    public PollFolder getPollFolderByFolderIdandUser(final Long folderId, final UserAccount userAccount){
        return this.getPollDao().getPollFolderByIdandUser(folderId, userAccount);
    }

    /**
     * Get polls by creation date.
     * @param date
     * @param maxResults
     * @return
     * @throws EnMeNoResultsFoundException
     */

    public List<PollBean> getPollsbyDate(final Date date, final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        List<Poll> pollList = new ArrayList<Poll>();
        if (date !=null){
            pollList = getPollDao().getPollByUserIdDate(date, getUserAccount(getUserPrincipalUsername()), maxResults, start);
        }
        else{
            throw new EnMeNoResultsFoundException("Date not found");
        }
        return ConvertDomainBean.convertSetToPollBean(pollList);
    }

    /**
     * Get poll by id and user.
     * @param pollId
     * @param account
     * @return
     * @throws EnMeNoResultsFoundException
     */
     public Poll getPollById(final Long pollId, final UserAccount account) throws EnMeNoResultsFoundException{
        final Poll poll = getPollDao().getPollById(pollId, account);
        if (poll == null) {
            log.error("poll invalid with this id "+pollId+ " and username:{"+account);
            throw new EnMePollNotFoundException("poll invalid with this id "+pollId+ " and username:{"+account);
        }
        return poll;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IPollService#getPollByAnswerId(java.lang
     * .Long, java.lang.Long,
     * org.encuestame.persistence.domain.security.UserAccount)
     */
    public Poll getPollByAnswerId(final Long pollId, final Long answerId,
            final UserAccount account) throws EnMeNoResultsFoundException {
        final Poll poll = this.getPollById(pollId);
        QuestionAnswer qA = getQuestionDao().retrieveAnswersByQuestionId(poll.getQuestion(), answerId);
        if (qA == null) {
            throw new EnMeNoResultsFoundException("Answer not found");
        }
        return poll;
    }

    /**
     * Retrieve a {@link Poll} based on id.
     * @param pollId poll id.
     * @param account username account
     * @return {@link Poll}
     * @throws EnMeNoResultsFoundException
     */
    public Poll getPollById(final Long pollId, final String account) throws EnMeNoResultsFoundException {
         log.debug("getPollById pollId: "+pollId);
         log.debug("getPollById account: "+account);
         final Poll poll = this.getPollById(pollId, getUserAccount(account));
         if (poll == null) {
             log.error("poll invalid with this id "+pollId+ " and username:{"+account);
             throw new EnMePollNotFoundException("poll invalid with this id "+pollId+ " and username:{"+account);
         }
         return poll;
     }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getPollSlugById(java.lang.Long, java.lang.String)
     */
    public Poll getPollSlugById(final Long pollId, final String slug) throws EnMeNoResultsFoundException{
        final Poll poll = this.getPollDao().getPollById(pollId, slug, true);
        if (poll == null) {
            log.error("poll invalid with this id "+pollId+ " and slug:{"+slug);
            throw new EnMePollNotFoundException("poll invalid with this id "+pollId+ " and slug:{"+slug);
        }
        return poll;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.IPollService#getPollsByUserName(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<PollBean> getPollsByUserName(
            final String username,
            final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        log.debug("Poll username "+username);
        final List<Poll> polls = getPollDao().retrievePollsByUserId(getUserAccount(username), maxResults, start);
        final List<PollBean> pollBeans = new ArrayList<PollBean>();
        for (Poll poll : polls) {
            final List<PollBeanResult> results = getResultVotes(poll);
            final PollBean pollBean = ConvertDomainBean.convertPollDomainToBean(poll);
            pollBean.setResultsBean(results);
            pollBeans.add(pollBean);
        }
         log.info("Polls size "+ polls.size());
        return pollBeans;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getPolls(java.lang.Integer, java.lang.Integer, java.util.Date)
     */
    public List<Poll> getPollsByRange(
            final Integer maxResults,
            final Integer start,
            final SearchPeriods range) {
        final List<Poll> polls = getPollDao().getPolls(
                maxResults, start, range);
        return polls;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#changeStatusPoll(java.lang.Long, java.lang.String)
     */
    public void changeStatusPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setCloseAfterDate(!(poll.getCloseAfterDate() == null ? false
                    : poll.getCloseAfterDate()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#closeAfterQuotaPoll(java.lang.Long, java.lang.String)
     */
    public void closeAfterQuotaPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setCloseAfterquota(!(poll.getCloseAfterquota() == null ? false
                    : poll.getCloseAfterquota()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    public void hiddenPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setIsHidden(!(poll.getIsHidden() == null ? false
                    : poll.getIsHidden()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#ipProtectionPoll(java.lang.Long, java.lang.String)
     */
    public void ipProtectionPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setIpRestriction(!(poll.getIpRestriction() == null ? false
                    : poll.getIpRestriction()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#ipEnableNotificationsPoll(java.lang.Long, java.lang.String)
     */
    public void enableNotificationsPoll(final Long pollId,
            final String username) throws EnMeNoResultsFoundException,
            EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setNotifications(!(poll.getNotifications() == null ? false
                    : poll.getNotifications()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#setAdditionalInfoPoll(java.lang.Long, java.lang.String)
     */
    public void setAdditionalInfoPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setShowAdditionalInfo(!(poll.getShowAdditionalInfo() == null ? false
                    : poll.getShowAdditionalInfo()));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#setPasswordRestrictionsPoll(java.lang.Long, java.lang.String)
     */
    public void setPasswordRestrictionsPoll(final Long pollId,
            final String username) throws EnMeNoResultsFoundException,
            EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setPasswordRestrictions(!(poll.getPasswordRestrictions() == null ? false : poll.getPasswordRestrictions()));
            if(!poll.getPasswordRestrictions()) {
                //FIXME: for now, we don't clear the password
                //poll.setPassword("");
            }
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#setShowResultsPoll(java.lang.Long, java.lang.String)
     */
    public void setShowResultsPoll(final Long pollId, final String username)
            throws EnMeNoResultsFoundException, EnmeFailOperation {
        final Poll poll = getPollById(pollId, username);
        if (poll != null) {
            poll.setShowResults(EnumerationUtils.getEnumFromString(ShowResultsOptions.class, "ALL"));
            getPollDao().saveOrUpdate(poll);
        } else {
            throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getAnswersVotesByPoll(java.util.List)
     */
    public List<PollBean> getAnswersVotesByPoll(final List<Poll> pollsResults) {
        final List<PollBean> result = new ArrayList<PollBean>();
        for (Poll poll : pollsResults) {
                final List<PollBeanResult> pollBeanResult = this
                        .getResultVotes(poll);
                // Calculate votes percents
                final PollBean pollB = ConvertDomainBean.convertPollDomainToBean(poll);
                pollB.setResultsBean(pollBeanResult);
                result.add(pollB);
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getResultVotes(org.encuestame.persistence.domain.survey.Poll)
     */
    public List<PollBeanResult> getResultVotes(final Poll poll) {
         log.debug("poll getResultVotes " + poll);
         final List<PollBeanResult> results = new ArrayList<PollBeanResult>();
         /*
          * 1. Get all poll answers by question.
          * 2. Retrieve the votes - results for each response.
          */
         final List<QuestionAnswer> answerList = getQuestionDao().getAnswersByQuestionId(poll.getQuestion().getQid());
         for (QuestionAnswer questionAnswer : answerList) {
             final List<Object[]> myPoll = getPollDao().retrieveResultPollsbyAnswer(poll.getPollId(), questionAnswer.getQuestionAnswerId());
             log.debug(" Resultpolls by answer  --->  " + myPoll.size() + "\n");
             if (myPoll.size() == 0) {
                 final PollBeanResult result = ConvertDomainBean.convertPollResultToBean(questionAnswer.getQuestionAnswerId(),
                                                questionAnswer.getAnswer(), questionAnswer.getColor(), 0L, poll.getQuestion());
                results.add(result);
             } else {
                 for (Object[] objects2 : myPoll) {
                     final Long answerId = objects2[0] == null ? null : Long.valueOf(objects2[0].toString());
                     final String answerString = objects2[1] == null ? null : objects2[1].toString();
                     final String color = objects2[2] == null ? null : objects2[2].toString();
                     final Long votes = objects2[3] == null ? null : Long.valueOf(objects2[3].toString());
                     if (answerId != null) {
                         final PollBeanResult result = ConvertDomainBean.convertPollResultToBean(answerId, answerString, color, votes, poll.getQuestion());
                         results.add(result);
                     } else {
                         throw new IllegalArgumentException("answer id is empty");
                     }
                 }
             }
         }

        log.debug("poll PollBeanResult " + results.size());
        this.calculatePercents(results);
        return results;
    }

    /**
     * Calculate the percents.
     * @param beanResults
     */
    private void calculatePercents(final List<PollBeanResult> beanResults) {
        double totalVotes = 0;
        for (PollBeanResult pollBeanResult : beanResults) {
            totalVotes = totalVotes + pollBeanResult.getVotes();
        }
        for (PollBeanResult pollBeanResult : beanResults) {
            pollBeanResult.setPercent(EnMeUtils.calculatePercent(totalVotes, pollBeanResult.getVotes()));
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#getPollDetailInfo(java.lang.Long)
     */
    public PollDetailBean getPollDetailInfo(final Long pollId) throws EnMeNoResultsFoundException{
        final PollDetailBean detail = new PollDetailBean();
        final Poll poll = getPoll(pollId);
        detail.setPollBean(ConvertDomainBean.convertPollDomainToBean(poll));
        detail.setResults(this.getResultVotes(poll));
        //FIXME: Next line is repeated, please review
        this.calculatePercents(detail.getResults());
        //set the list of answers
        detail.setListAnswers(ConvertDomainBean
                .convertAnswersToQuestionAnswerBean(getQuestionDao()
                        .getAnswersByQuestionId(poll.getQuestion().getQid())));
        //set the comments.
        detail.getPollBean().setTotalComments(this.getTotalCommentsbyType(detail.getPollBean().getId(), TypeSearchResult.POLL));
        return detail;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#validatePollIP(java.lang.String, org.encuestame.persistence.domain.survey.Poll)
     */
    public Integer validatePollIP(final String ip, final Poll poll) {
        return getPollDao().validateVoteIP(ip, poll);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.IPollService#checkLimitVotesByIP(java.lang.String, org.encuestame.persistence.domain.survey.Poll)
     */
    public Boolean checkLimitVotesByIP(final String ip, final Poll poll) {
        final Integer totalIp = getPollDao().validateVoteIP(ip, poll);
        if ((poll.getAllowRepeatedVotes() != null && poll.getAllowRepeatedVotes()) && (poll.getClosedQuota() != null && poll.getClosedQuota() <= totalIp)) {
            log.info("checkLimitVotesByIP TRUE");
            return true;  // is not allowed to vote, the limit has been reached
        } else {
            log.info("checkLimitVotesByIP FALSE");
            return false;//that means is allowed and is allowed to vote more with that IP
        }
    }

    /**
     * Restrict Votes by Date.
     */
    public Boolean restrictVotesByDate(final Poll poll) {
        Boolean limitVoteByDate = Boolean.FALSE;
        if (poll.getCloseAfterDate() != null && poll.getCloseAfterDate()) {
            limitVoteByDate = DateUtil.compareToCurrentDate(poll
                    .getClosedDate());
        }
         return limitVoteByDate;
    }

    /**
     *
     * @param poll
     * @return
     */
    public Boolean restrictVotesByQuota(final Poll poll) {
        Boolean limitVote = Boolean.FALSE;
        if (poll.getLimitVotes() != null && poll.getLimitVotesEnabled()) {
            final Long totalVotes = getPollDao().getTotalVotesByPollIdAndDateRange(poll.getPollId(), null);
            log.info("restrictVotesByQuota totalVotes=>"+totalVotes);
            if (Long.valueOf(poll.getLimitVotes()) <= totalVotes) {
                limitVote = Boolean.TRUE;
            }
        }
        return limitVote;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.imp.IPollService#retrieveFoldersbyKeyword
     * (java.lang.String)
     */
    public List<PollFolder> retrieveFoldersbyKeyword(final String keyword)
            throws EnMeNoResultsFoundException {
        List<PollFolder> folders = new ArrayList<PollFolder>();
        if (keyword != null) {
            folders = getPollDao().getPollFolderByKeyword(keyword,
                    getUserAccount(getUserPrincipalUsername()));
        }
        return folders;
    }
}
