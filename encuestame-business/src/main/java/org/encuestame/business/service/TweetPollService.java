/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.imp.ITweetPollService;
import org.encuestame.core.exception.EnMeFailSendSocialTweetException;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollFolder;
import org.encuestame.persistence.domain.tweetpoll.TweetPollResult;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.security.SocialAccountBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;

import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Tweet Poll Service Interface.
 * @author Morales, Diana Paola paola AT encuestame.org
 * @since  April 02, 2010
 */
public class TweetPollService extends AbstractSurveyService implements ITweetPollService{


    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Answer Poll Path.
     */
    private String answerPollPath;

    /**
     * TweetPoll Result Path.
     */
    private String tweetPollResultsPath;

    /**
     * Tweet Path.
     **/
    private String tweetPath;

    /**
     * Twitter Domain.
     */
    private String twitterDomain;

    private final Long TOTALVOTE = 0L ;

    /**
     * Get Tweet Polls by User Id.
     * @param username username.
     * @return list of Tweet polls bean
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitTweetPoll> getTweetsPollsByUserName(final String username,
            final Integer maxResults,
            final Integer start) throws EnMeNoResultsFoundException{
        final List<TweetPoll> tweetPolls = getTweetPollDao().retrieveTweetsByUserId(getPrimaryUser(username), maxResults, start);
         log.info("tweetPoll size "+tweetPolls.size());
        return this.setTweetPollListAnswers(tweetPolls);
    }

    /**
     * Set List Answer.
     * @param listTweetPolls List of {@link TweetPoll}
     * @return
     * @throws EnMeExpcetion
     */
    private List<UnitTweetPoll> setTweetPollListAnswers(final List<TweetPoll> listTweetPolls){
        final List<UnitTweetPoll> tweetPollsBean = new ArrayList<UnitTweetPoll>();
        for (TweetPoll tweetPoll : listTweetPolls) {
            final UnitTweetPoll unitTweetPoll = ConvertDomainBean.convertTweetPollToBean(tweetPoll);
             unitTweetPoll.getQuestionBean().setListAnswers(this.retrieveAnswerByQuestionId(unitTweetPoll.getQuestionBean().getId()));
             if (unitTweetPoll.getId() != null) {
                 unitTweetPoll.setTotalVotes(getTweetPollDao().getTotalVotesByTweetPollId(unitTweetPoll.getId()));
             } else {
                 unitTweetPoll.setTotalVotes(this.TOTALVOTE);
             }
             tweetPollsBean.add(unitTweetPoll);
        }
        return tweetPollsBean;
    }

    /**
     * Search {@link TweetPoll} by Keyword.
     * @param username username session
     * @param keyword keyword.
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollsByKeyWord(final String username,
                               final String keyword,
                               final Integer maxResults, final Integer start) throws EnMeExpcetion{
        log.info("search keyword tweetPoll  "+keyword);
        List<TweetPoll> tweetPolls  = new ArrayList<TweetPoll>();
        if(keyword == null){
           throw new EnMeExpcetion("keyword is missing");
        } else {
            tweetPolls = getTweetPollDao().retrieveTweetsByQuestionName(keyword, getPrimaryUser(username), maxResults, start);
        }
        log.info("search keyword tweetPoll size "+tweetPolls.size());
        return this.setTweetPollListAnswers(tweetPolls);
    }

    /**
     * Search Tweet Polls Today.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollsToday(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion{
        return this.setTweetPollListAnswers(getTweetPollDao().retrieveTweetPollToday(
                getPrimaryUser(username), maxResults, start));
    }

    /**
     * Search Tweet Polls Last Week.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollsLastWeek(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion{
        return this.setTweetPollListAnswers(getTweetPollDao().retrieveTweetPollLastWeek(
                getPrimaryUser(username), maxResults, start));
    }

    /**
     * Search Favourites TweetPolls.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollFavourites(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion{
        return this.setTweetPollListAnswers(getTweetPollDao().retrieveFavouritesTweetPoll(
                getPrimaryUser(username), maxResults, start));
    }

    /**
     * Search Scheduled TweetsPoll.
     * @param username
     * @param keyword
     * @param maxResults
     * @param start
     * @return
     * @throws EnMeExpcetion
     */
    public List<UnitTweetPoll> searchTweetsPollScheduled(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion{
        return this.setTweetPollListAnswers(getTweetPollDao().retrieveScheduledTweetPoll(
                getPrimaryUser(username), maxResults, start));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.ITweetPollService#createTweetPoll(org.encuestame.utils.web.UnitTweetPoll, org.encuestame.persistence.domain.question.Question)
     */
    public void saveTweetPoll(final UnitTweetPoll tweetPollBean, final Question question) throws EnMeExpcetion {
        try{
            final TweetPoll tweetPollDomain = new TweetPoll();
            log.debug("question found "+question);
            if(question == null){
                throw new EnMeExpcetion("question not found");
            }
            tweetPollDomain.setQuestion(question);
            tweetPollDomain.setCloseNotification(tweetPollBean.getCloseNotification());
            tweetPollDomain.setCompleted(Boolean.FALSE);
            tweetPollDomain.setCaptcha(tweetPollBean.getCaptcha());
            tweetPollDomain.setAllowLiveResults(tweetPollBean.getAllowLiveResults());
            tweetPollDomain.setLimitVotes(tweetPollBean.getLimitVotes());
            tweetPollDomain.setTweetOwner(getAccountDao().getUserById(tweetPollBean.getUserId()));
            tweetPollDomain.setResultNotification(tweetPollBean.getResultNotification());
            tweetPollDomain.setPublishTweetPoll(tweetPollBean.getPublishPoll());
            tweetPollDomain.setCreateDate(new Date());
            tweetPollDomain.setAllowLiveResults(tweetPollBean.getAllowLiveResults());
            tweetPollDomain.setScheduleTweetPoll(tweetPollBean.getSchedule());
            tweetPollDomain.setScheduleDate(tweetPollBean.getScheduleDate());
            this.getTweetPollDao().saveOrUpdate(tweetPollDomain);
            final List<QuestionAnswer> answers = this.getQuestionDao().getAnswersByQuestionId(question.getQid());
            for (QuestionAnswer questionsAnswers : answers) {
                final TweetPollSwitch tPollSwitch = new TweetPollSwitch();
                tPollSwitch.setAnswers(questionsAnswers);
                tPollSwitch.setTweetPoll(tweetPollDomain);
                tPollSwitch.setCodeTweet(questionsAnswers.getUniqueAnserHash());
                getTweetPollDao().saveOrUpdate(tPollSwitch);
                createNotification(NotificationEnum.TWEETPOL_CREATED, question.getQuestion() , question.getAccountQuestion());
            }
            //Save Hash Tags for this tweetPoll.
            log.debug("HashTag Size"+tweetPollBean.getHashTags().size());
            for (HashTagBean unitHashTag : tweetPollBean.getHashTags()) {
                HashTag hashTag = getHashTagDao().getHashTagByName(unitHashTag.getHashTagName().toLowerCase());
                //If is null, create new hashTag.
                if(hashTag == null){
                    log.debug("Create HashTag "+unitHashTag.getHashTagName().toLowerCase());
                    hashTag = createHashTag(unitHashTag.getHashTagName().toLowerCase());
                }
                tweetPollDomain.getHashTags().add(hashTag);
            }
            //Update TweetPoll.
            if( tweetPollBean.getHashTags().size() > 0){
                log.debug("Update Hash Tag");
                getTweetPollDao().saveOrUpdate(tweetPollDomain);
            }
            tweetPollBean.setId(tweetPollDomain.getTweetPollId());
        }
        catch (Exception e) {
            log.error("Error creating TweetlPoll "+e);
            e.printStackTrace();
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * Generate TweetPoll Text.
     * @param tweetPoll tweetPoll
     * @param url url
     * @return tweet text
     * @throws EnMeExpcetion exception
     */
    public String generateTweetPollText(final UnitTweetPoll tweetPoll, final String url) throws EnMeExpcetion{
        String tweetQuestionText = "";
        try{
            final TweetPoll tweetPollDomain = getTweetPollDao().getTweetPollById(tweetPoll.getId());
            log.debug("generateTweetPollText");
            log.debug("TweetPoll ID: "+tweetPollDomain.getTweetPollId());
            tweetQuestionText = tweetPollDomain.getQuestion().getQuestion();
            log.debug("Question text: "+tweetQuestionText);
            //Build Answers.
            final List<QuestionAnswer> answers = getQuestionDao().getAnswersByQuestionId(tweetPollDomain.getQuestion().getQid());
            for (final QuestionAnswer questionsAnswers : answers) {
                 log.debug("Answer ID: "+questionsAnswers.getQuestionAnswerId());
                 log.debug("Answer Question: "+questionsAnswers.getAnswer());
                 tweetQuestionText += " "+questionsAnswers.getAnswer()+" "+buildUrlAnswer(questionsAnswers, url);
            }
            //Build Hash Tag.
            for (final HashTag tag : tweetPollDomain.getHashTags()) {
                 log.debug("Hash Tag ID: "+tag.getHashTagId());
                 log.debug("Tag Name "+tag.getHashTag());
                 final StringBuffer buffer = new StringBuffer(tweetQuestionText);
                 buffer.append(" ");
                 buffer.append("#");
                 buffer.append(tag.getHashTag());
                 tweetQuestionText = buffer.toString();
            }
        }
        catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
        log.debug("Question Generated: "+tweetQuestionText);
        return tweetQuestionText;
    }

    /**
     * Build Url Answer.
     * @param anwer answer
     * @throws IOException exception
     * @throws HttpException exception
     */
    private String buildUrlAnswer(final QuestionAnswer answer, final String domain) throws HttpException, IOException{
        final StringBuffer stringBuffer = new StringBuffer(domain);
        stringBuffer.append(this.getTweetPath());
        stringBuffer.append(answer.getUniqueAnserHash());
        return getTwitterService().getTinyUrl(stringBuffer.toString());
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.ITweetPollService#publicMultiplesTweetAccounts(java.util.List, java.lang.Long, java.lang.String)
     */
    public List<TweetPollSavedPublishedStatus> publicMultiplesTweetAccounts(
            final List<SocialAccountBean> twitterAccounts,
            final Long tweetPollId,
            final String tweetText){
            log.debug("publicMultiplesTweetAccounts:{"+twitterAccounts.size());
            final List<TweetPollSavedPublishedStatus> results = new ArrayList<TweetPollSavedPublishedStatus>();
            for (SocialAccountBean unitTwitterAccountBean : twitterAccounts) {
                log.debug("publicMultiplesTweetAccounts unitTwitterAccountBean "+unitTwitterAccountBean.toString());
                final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
                results.add(this.publishTweetPoll(unitTwitterAccountBean.getAccountId(), tweetPoll,
                            SocialProvider.TWITTER));
            }
            return results;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.imp.ITweetPollService#publishTweetPoll(java.lang.Long, org.encuestame.persistence.domain.tweetpoll.TweetPoll, org.encuestame.persistence.domain.social.SocialProvider)
     */
    public TweetPollSavedPublishedStatus publishTweetPoll(final Long accountId, final TweetPoll tweetPoll, final SocialProvider provider){
         log.debug("publicMultiplesTweetAccounts tweetPoll"+tweetPoll);
         final SocialAccount socialTwitterAccounts = getAccountDao().getTwitterAccount(accountId);
         log.debug("publishTweetPoll socialTwitterAccounts: {"+socialTwitterAccounts);
         final TweetPollSavedPublishedStatus publishedStatus = new TweetPollSavedPublishedStatus();
         publishedStatus.setApiType(provider);
         publishedStatus.setTweetPoll(tweetPoll);
         if (socialTwitterAccounts != null && tweetPoll != null) {
             log.debug("secUserTwitterAccounts Account:{"+socialTwitterAccounts.getSocialAccountName());
             publishedStatus.setTwitterAccount(socialTwitterAccounts);
             try {
                 log.debug("publishTweetPoll Publishing...");
                 final Status status = publicTweetPoll("", socialTwitterAccounts);
                 //store original tweet id.
                 publishedStatus.setTweetId(status.getId());
                 //store original publication date.
                 publishedStatus.setPublicationDateTweet(status.getCreatedAt());
                 //success publish state..
                 publishedStatus.setStatus(TweetPollSavedPublishedStatus.Status.SUCCESS);
                 //store original tweet content.
                 publishedStatus.setTweetContent(status.getText());
                 //create notification
                 createNotification(NotificationEnum.TWEETPOLL_PUBLISHED,
                         buildTwitterItemView(socialTwitterAccounts.getSocialAccountName(), String.valueOf(status.getId())),
                         socialTwitterAccounts.getSecUsers());
             } catch (Exception e) {
                 log.error("Error publish tweet:{"+e.getMessage());
                 //change status to failed
                 publishedStatus.setStatus(TweetPollSavedPublishedStatus.Status.FAILED);
                 //store error descrition
                 publishedStatus.setDescriptionStatus(e.getMessage().substring(254));
                 //save original tweet content.
                 publishedStatus.setTweetContent("");
             }
         } else {
             log.warn("Twitter Account Not Found [Id:"+accountId+"]");
             publishedStatus.setStatus(TweetPollSavedPublishedStatus.Status.FAILED);
             //throw new EnMeFailSendSocialTweetException("Twitter Account Not Found [Id:"+accountId+"]");
             tweetPoll.setPublishTweetPoll(Boolean.FALSE);
             getTweetPollDao().saveOrUpdate(tweetPoll);
         }
         getTweetPollDao().saveOrUpdate(publishedStatus);
         return publishedStatus;
    }

    /**
     * Build Twitter Url.
     * @param username
     * @param id
     * @return
     */
    public String buildTwitterItemView(final String username, final String id){
        final StringBuilder builder = new  StringBuilder(getTwitterDomain());
        builder.append(username);
        builder.append("/status/");
        builder.append(id);
        return builder.toString();
    }

    /**
     * Vote on TweetPoll.
     * @param pollSwitch {@link TweetPollSwitch}
     * @param ip ip
     */
    public void tweetPollVote(final TweetPollSwitch pollSwitch, final String ip){
        final TweetPollResult pollResult = new TweetPollResult();
        pollResult.setIpVote(ip.trim());
        pollResult.setTweetPollSwitch(pollSwitch);
        pollResult.setTweetResponseDate(new Date());
        getTweetPollDao().saveOrUpdate(pollResult);
    }

    /**
     * Get Results By {@link TweetPoll}.
     * @param tweetPollId tweetPoll Id
     * @return list of {@link UnitTweetPollResult}
     * @throws EnMeNoResultsFoundException
     */
    //FIXME: this service don' retrieve data if answer never was voted.
    public List<UnitTweetPollResult> getResultsByTweetPollId(final Long tweetPollId) throws EnMeNoResultsFoundException{
        log.debug("getResultsByTweetPollId "+tweetPollId);
        final List<UnitTweetPollResult> pollResults = new ArrayList<UnitTweetPollResult>();
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
        log.debug("tweetPoll "+tweetPoll);
        if(tweetPoll == null){
            throw new EnMeNoResultsFoundException("tweetPoll not found");
        } else {
            for (QuestionAnswer questionsAnswers : getQuestionDao().getAnswersByQuestionId(tweetPoll.getQuestion().getQid())) {
                  log.debug("Question Name "+tweetPoll.getQuestion().getQuestion());
                  log.debug("Answers Size "+tweetPoll.getQuestion().getQuestionsAnswers().size());
                  final List<Object[]> result = getTweetPollDao().getResultsByTweetPoll(tweetPoll, questionsAnswers);
                  log.debug("result getResultsByTweetPollId- "+result.size());
                  for (Object[] objects : result) {
                      log.debug("objects 1- "+objects[0]);
                      log.debug("objects 2- "+objects[1]);
                      final UnitTweetPollResult tweetPollResult = new UnitTweetPollResult();
                      tweetPollResult.setResults(Long.valueOf(objects[1].toString()));
                      tweetPollResult.setAnswersBean(ConvertDomainBean.convertAnswerToBean(questionsAnswers));
                      pollResults.add(tweetPollResult);
                  }
            }
        }
        return pollResults;
    }

    /**
     * Validate TweetPoll IP.
     * @param ipVote  ipVote
     * @param tweetPoll tweetPoll
     * @return {@link TweetPollResult}
     */
    public TweetPollResult validateTweetPollIP(final String ipVote, final TweetPoll tweetPoll){
        return getTweetPollDao().validateVoteIP(ipVote, tweetPoll);
    }

    /**
     * Validate User Twitter Account.
     * @param username username logged.
     * @return if user have twitter account
     * @throws EnMeNoResultsFoundException
     * @throws TwitterException
     */
    @Deprecated
    public Boolean validateUserTwitterAccount(final String username) throws EnMeNoResultsFoundException{
        final Account users = getUserAccount(username).getAccount();
        Boolean validate = false;
     // TODO: Removed by ENCUESTAME-43
    /*    log.info(users.getTwitterAccount());
        if(!users.getTwitterAccount().isEmpty() && !users.getTwitterPassword().isEmpty()){
            log.info(users.getTwitterPassword());
            try{
                final User user = getTwitterService().verifyCredentials(users.getTwitterAccount(), users.getTwitterPassword());
                log.info(user);
                validate = Integer.valueOf(user.getId()) != null ? true : false;
            }
            catch (Exception e) {
                log.error("Error Validate Twitter Account "+e.getMessage());
            }
        }*/
        log.info(validate);
        return validate;
    }

    /**
     * Create TweetPoll Folder.
     * @param folderName
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UnitFolder createTweetPollFolder(final String folderName, final String username) throws EnMeNoResultsFoundException{
        final TweetPollFolder tweetPollFolderDomain = new TweetPollFolder();
        tweetPollFolderDomain.setUsers(getUserAccount(username).getAccount());
        tweetPollFolderDomain.setCreatedAt(new Date());

        tweetPollFolderDomain.setFolderName(folderName);
        this.getTweetPollDao().saveOrUpdate(tweetPollFolderDomain);
        return ConvertDomainBean.convertFolderToBeanFolder(tweetPollFolderDomain);

    }

    /**
     * Update Tweet Poll Folder.
     * @throws EnMeNoResultsFoundException
     */
    public UnitFolder updateTweetPollFolder(final Long folderId, final String folderName, final String username) throws EnMeNoResultsFoundException{
        final TweetPollFolder tweetPollFolder = this.getTweetPollFolder(folderId);
        if(tweetPollFolder == null) {
            throw new EnMeNoResultsFoundException("Tweet Poll Folder not found");
        }
        else{
            tweetPollFolder.setFolderName(folderName);
            getTweetPollDao().saveOrUpdate(tweetPollFolder);
        }
         return ConvertDomainBean.convertFolderToBeanFolder(tweetPollFolder);
     }

     /**
     * Remove TweetPoll Folder.
     * @param TweetPoll folderId
     * @throws EnMeNoResultsFoundException
     */
    public void deleteTweetPollFolder(final Long folderId) throws EnMeNoResultsFoundException{
        final TweetPollFolder tweetPollfolder = this.getTweetPollFolder(folderId);
        if(tweetPollfolder != null){
            getTweetPollDao().delete(tweetPollfolder);
        } else {
            throw new EnMeNoResultsFoundException("TweetPoll folder not found");
        }
    }

    /**
     * Get Tweet Poll Folder.
     * @param id
     * @return
     */
    private TweetPollFolder getTweetPollFolder(final Long folderId){
        return this.getTweetPollDao().getTweetPollFolderById(folderId);
    }

    /**
     * Get Tweet Poll Folder by User and FolderId.
     * @param id
     * @return
     */
    private TweetPollFolder getTweetPollFolderByFolderIdandUser(final Long folderId, final Long userId){
        return this.getTweetPollDao().getTweetPollFolderByIdandUser(folderId, userId);
    }

    /**
     * Add {@link TweetPoll} to Folder.
     * @param folderId
     * @throws EnMeNoResultsFoundException
     */
    public void addTweetPollToFolder(final Long folderId, final String username, final Long tweetPollId) throws EnMeNoResultsFoundException{
        final TweetPollFolder tpfolder = this.getTweetPollFolderByFolderIdandUser(folderId, getPrimaryUser(username));
         if(tpfolder!=null) {
             final TweetPoll tpoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
             tpoll.setTweetPollFolder(tpfolder);
             getTweetPollDao().saveOrUpdate(tpoll);
         } else {
             throw new EnMeNoResultsFoundException("TweetPoll folder not found");
         }
    }

    /**
     * Change Status {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     */
    public void changeStatusTweetPoll(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
            tweetPoll.setCloseNotification(Boolean.TRUE);
            getTweetPollDao().saveOrUpdate(tweetPoll);
        } else {
               throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }

    /**
     * Set Favourite TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    public void setFavouriteTweetPoll(final Long tweetPollId, final String username) throws
           EnMeNoResultsFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
            tweetPoll.setFavourites(tweetPoll.getFavourites() == null ? false : !tweetPoll.getFavourites());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        } else {
               throw new EnmeFailOperation("Fail Change Status Operation");
        }
    }


    /**
     * Change Allow Live Results {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     */
    public void changeAllowLiveResultsTweetPoll(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
            tweetPoll.setAllowLiveResults(tweetPoll.getAllowLiveResults() == null ? false : !tweetPoll.getAllowLiveResults());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }
        else {
            throw new EnmeFailOperation("Fail Change Allow Live Results Operation");
        }
    }

    /**
     * Change Allow Live Results {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     */
    public void changeAllowCaptchaTweetPoll(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
             tweetPoll.setCaptcha(tweetPoll.getCaptcha() == null ? false : !tweetPoll.getCaptcha());
             getTweetPollDao().saveOrUpdate(tweetPoll);
        }
        else {
            throw new EnmeFailOperation("Fail Change Allow Captcha Operation");
        }
    }

    /**
     * Change Resume Live Results {@link TweetPoll}.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     */
    public void changeResumeLiveResultsTweetPoll(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
        if (tweetPoll != null){
            tweetPoll.setResumeLiveResults(tweetPoll.getResumeLiveResults() == null ? false : !tweetPoll.getResumeLiveResults());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }
        else {
            throw new EnmeFailOperation("Fail Change Resume Live Results Operation");
        }
    }

    /**
     * Get TweetPoll.
     * @param tweetPollId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private TweetPoll getTweetPoll(final Long tweetPollId, final String username) throws EnMeNoResultsFoundException{
        return getTweetPollDao().getTweetPollByIdandUserId(tweetPollId, getPrimaryUser(username));
    }

    /**
     * Change Allow Repeated TweetPoll.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    public void changeAllowRepeatedTweetPoll(final Long tweetPollId, final String username)
                throws EnMeNoResultsFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = this.getTweetPoll(tweetPollId, username);
        if (tweetPoll != null){
            tweetPoll.setAllowRepatedVotes(tweetPoll.getAllowRepatedVotes() == null ? false : !tweetPoll.getAllowRepatedVotes());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        } else {
            throw new EnmeFailOperation("Fail Change Allow Repeated Operation");
        }
    }

    /**
     * Change Close Notification.
     * @param tweetPollId
     * @param username
     * @throws EnMeNoResultsFoundException
     * @throws EnmeFailOperation
     */
    public void changeCloseNotificationTweetPoll(final Long tweetPollId, final String username)
           throws EnMeNoResultsFoundException, EnmeFailOperation{
        final TweetPoll tweetPoll = this.getTweetPoll(tweetPollId, username);
        if (tweetPoll != null){
            tweetPoll.setCloseNotification(tweetPoll.getCloseNotification() == null
                      ? false : !tweetPoll.getCloseNotification());
            getTweetPollDao().saveOrUpdate(tweetPoll);
        } else {
            throw new EnmeFailOperation("Fail Change Allow Repeated Operation");
        }
    }

    /**
     * @return the answerPollPath
     */
    public String getAnswerPollPath() {
        return answerPollPath;
    }

    /**
     * @param answerPollPath the answerPollPath to set
     */
    public void setAnswerPollPath(final String answerPollPath) {
        this.answerPollPath = answerPollPath;
    }

    /**
     * @return the tweetPollResultsPath
     */
    public String getTweetPollResultsPath() {
        return tweetPollResultsPath;
    }

    /**
     * @param tweetPollRgetTweetPollResultsPathesultsPath the tweetPollResultsPath to set
     */
    public void setTweetPollResultsPath(final String tweetPollResultsPath) {
        this.tweetPollResultsPath = tweetPollResultsPath;
    }

    /**
     * @return the tweetPath
     */
    public String getTweetPath() {
        return tweetPath;
    }

    /**
     * @param tweetPath the tweetPath to set
     */
    public void setTweetPath(final String tweetPath) {
        this.tweetPath = tweetPath;
    }

    /**
     * @return the twitterDomain
     */
    public String getTwitterDomain() {
        return twitterDomain;
    }

    /**
     * @param twitterDomain the twitterDomain to set
     */
    public void setTwitterDomain(String twitterDomain) {
        this.twitterDomain = twitterDomain;
    }
}
