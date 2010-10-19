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
package org.encuestame.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.domain.HashTag;
import org.encuestame.core.persistence.domain.Question;
import org.encuestame.core.persistence.domain.security.SecUser;
import org.encuestame.core.persistence.domain.security.SecUserTwitterAccounts;
import org.encuestame.core.persistence.domain.survey.QuestionsAnswers;
import org.encuestame.core.persistence.domain.survey.TweetPoll;
import org.encuestame.core.persistence.domain.survey.TweetPollResult;
import org.encuestame.core.persistence.domain.survey.TweetPollSavedPublishedStatus;
import org.encuestame.core.persistence.domain.survey.TweetPollSwitch;
import org.encuestame.core.persistence.domain.survey.TweetPollSavedPublishedStatus.Type;
import org.encuestame.core.service.imp.ITweetPollService;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.utils.security.UnitTwitterAccountBean;
import org.encuestame.utils.web.UnitHashTag;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Tweet Poll Service Interface.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  April 02, 2010
 * @version $Id: $
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

    /**
     * Get Tweet Polls by User Id.
     * @param username username.
     * @return list of Tweet polls bean
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitTweetPoll> getTweetsPollsByUserName(final String username) throws EnMeDomainNotFoundException{
        final List<TweetPoll> tweetPolls = getTweetPollDao().retrieveTweetsByUserId(getPrimaryUser(username));
        log.info("tweetPoll size "+tweetPolls.size());
        return this.setListAnswers(tweetPolls);
    }

    /**
     * Set List Answer.
     * @param listTweetPolls List of {@link TweetPoll}
     * @return
     */
    private List<UnitTweetPoll> setListAnswers(final List<TweetPoll> listTweetPolls){
        final List<UnitTweetPoll> tweetPollsBean = new ArrayList<UnitTweetPoll>();
        for (TweetPoll tweetPoll : listTweetPolls) {
            final UnitTweetPoll unitTweetPoll = ConvertDomainBean.convertTweetPollToBean(tweetPoll);
             unitTweetPoll.getQuestionBean().setListAnswers(this.retrieveAnswerByQuestionId(unitTweetPoll.getQuestionBean().getId()));
             tweetPollsBean.add(unitTweetPoll);
        }
        return tweetPollsBean;
    }

    /**
     * Search {@link TweetPoll} by Keyword.
     * @param username username session
     * @param keyword keyword.
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitTweetPoll> searchTweetsPollsByKeyWord(final String username, final String keyword) throws EnMeDomainNotFoundException{
        log.info("search keyword tweetPoll  "+keyword);
        List<TweetPoll> tweetPolls  = new ArrayList<TweetPoll>();
        if(keyword == null || keyword.trim().isEmpty()){
            tweetPolls = getTweetPollDao().retrieveTweetsByUserId(getPrimaryUser(username));
        } else {
            tweetPolls = getTweetPollDao().retrieveTweetsByQuestionName(keyword, getPrimaryUser(username));
        }
        log.info("search keyword tweetPoll size "+tweetPolls.size());
        return this.setListAnswers(tweetPolls);
    }

    /**
     * Disabled TweetPoll.
     * @param tweetPollId tweetPoll.
     * @throws EnMeExpcetion
     */
    public void disableTweetPoll(final Long tweetPollId) throws EnMeExpcetion{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
        if(tweetPoll == null){
            throw new EnMeExpcetion("tweetPoll.notfound");
        }
        else{
            tweetPoll.setEnabled(Boolean.FALSE);
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }
    }

    /**
     * Create Tweet Poll.
     * @param tweetPollBean tweet poll bean.
     * @throws EnMeExpcetion exception
     */
    public void createTweetPoll(final UnitTweetPoll tweetPollBean) throws EnMeExpcetion {
        try{
            final TweetPoll tweetPollDomain = new TweetPoll();
            final Question question = getQuestionDao().retrieveQuestionById(tweetPollBean.getQuestionBean().getId());
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
            tweetPollDomain.setTweetOwner(getSecUserDao().getUserById(tweetPollBean.getUserId()));
            tweetPollDomain.setResultNotification(tweetPollBean.getResultNotification());
            tweetPollDomain.setPublishTweetPoll(tweetPollBean.getPublishPoll());
            tweetPollDomain.setCreateDate(new Date());
            tweetPollDomain.setAllowLiveResults(tweetPollBean.getAllowLiveResults());
            tweetPollDomain.setScheduleTweetPoll(tweetPollBean.getSchedule());
            tweetPollDomain.setScheduleDate(tweetPollBean.getScheduleDate());
            this.getTweetPollDao().saveOrUpdate(tweetPollDomain);
            final List<QuestionsAnswers> answers = this.getQuestionDao().getAnswersByQuestionId(question.getQid());
            for (QuestionsAnswers questionsAnswers : answers) {
                final TweetPollSwitch tPollSwitch = new TweetPollSwitch();
                tPollSwitch.setAnswers(questionsAnswers);
                tPollSwitch.setTweetPoll(tweetPollDomain);
                tPollSwitch.setCodeTweet(questionsAnswers.getUniqueAnserHash());
                getTweetPollDao().saveOrUpdate(tPollSwitch);
                createNotification(NotificationEnum.TWEETPOL_CREATED, question.getQuestion() , question.getSecUsersQuestion());
            }
            //Save Hash Tags for this tweetPoll.
            log.debug("HashTag Size"+tweetPollBean.getHashTags().size());
            for (UnitHashTag unitHashTag : tweetPollBean.getHashTags()) {
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
            log.error("Error creating TweetlPoll "+e.getMessage());
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
            final List<QuestionsAnswers> answers = getQuestionDao().getAnswersByQuestionId(tweetPollDomain.getQuestion().getQid());
            for (final QuestionsAnswers questionsAnswers : answers) {
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
    private String buildUrlAnswer(final QuestionsAnswers answer, final String domain) throws HttpException, IOException{
        StringBuffer stringBuffer = new StringBuffer(domain);
        stringBuffer.append(this.getTweetPath());
        stringBuffer.append(answer.getUniqueAnserHash());
        return getTwitterService().getTinyUrl(stringBuffer.toString());
    }

    /**
     * Public Multiples Tweet Accounts.
     * @param twitterAccounts List of {@link SecUserTwitterAccounts}.
     * @param tweetPoll {@link TweetPoll}.
     * @param tweetText tweet text.
     */
    public void publicMultiplesTweetAccounts(
            final List<UnitTwitterAccountBean> twitterAccounts,
            final Long tweetPollId,
            final String tweetText){
            log.debug("publicMultiplesTweetAccounts "+twitterAccounts.size());
            for (UnitTwitterAccountBean unitTwitterAccountBean : twitterAccounts) {
                final TweetPollSavedPublishedStatus publishedStatus = new TweetPollSavedPublishedStatus();
                final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
                final SecUserTwitterAccounts secUserTwitterAccounts = getSecUserDao().getTwitterAccount(unitTwitterAccountBean.getAccountId());
                publishedStatus.setApiType(Type.TWITTER);
                if(secUserTwitterAccounts != null && tweetPoll != null){
                    log.debug("secUserTwitterAccounts Account"+secUserTwitterAccounts.getTwitterAccount());
                    publishedStatus.setTweetPoll(tweetPoll);
                    publishedStatus.setTwitterAccount(secUserTwitterAccounts);
                    try {
                        log.debug("Publishing...");
                        final Status status = this.publicTweetPoll(tweetText, secUserTwitterAccounts);
                        publishedStatus.setTweetId(status.getId());
                        publishedStatus.setPublicationDateTweet(status.getCreatedAt());
                        publishedStatus.setStatus(org.encuestame.core.persistence.domain.survey.TweetPollSavedPublishedStatus.Status.SUCCESS);
                        createNotification(NotificationEnum.TWEETPOLL_PUBLISHED,
                                buildTwitterItemView(secUserTwitterAccounts.getTwitterAccount(), String.valueOf(status.getId())),
                                secUserTwitterAccounts.getSecUsers());
                    } catch (Exception e) {
                        log.error("Error publish tweet "+e.getMessage());
                        publishedStatus.setStatus(org.encuestame.core.persistence.domain.survey.TweetPollSavedPublishedStatus.Status.FAILED);
                        publishedStatus.setDescriptionStatus(e.getMessage());
                    }
                    getTweetPollDao().saveOrUpdate(publishedStatus);
                }
                else{
                    log.warn("Twitter Account Not Found [Id:"+unitTwitterAccountBean.getAccountId()+"]");
                }
            }
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
     */
    //FIXME: this service don' retrieve data if answer never was voted.
    public List<UnitTweetPollResult> getResultsByTweetPollId(final Long tweetPollId){
        final List<UnitTweetPollResult> pollResults = new ArrayList<UnitTweetPollResult>();
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
        for (QuestionsAnswers questionsAnswers : getQuestionDao().getAnswersByQuestionId(tweetPoll.getQuestion().getQid())) {
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
     * @throws EnMeDomainNotFoundException
     * @throws TwitterException
     */
    @Deprecated
    public Boolean validateUserTwitterAccount(final String username) throws EnMeDomainNotFoundException{
        final SecUser users = getUser(username).getSecUser();
        Boolean validate = false;
        log.info(users.getTwitterAccount());
        log.info(users.getTwitterPassword());
        if(!users.getTwitterAccount().isEmpty() && !users.getTwitterPassword().isEmpty()){
            try{
                final User user = getTwitterService().verifyCredentials(users.getTwitterAccount(), users.getTwitterPassword());
                log.info(user);
                validate = Integer.valueOf(user.getId()) != null ? true : false;
            }
            catch (Exception e) {
                log.error("Error Validate Twitter Account "+e.getMessage());
            }
        }
        log.info(validate);
        return validate;
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
