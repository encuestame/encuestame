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
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.persistence.pojo.TweetPollResult;
import org.encuestame.core.persistence.pojo.TweetPollSwitch;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;

import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Tweet Poll Service Interface.
 * @author Morales, Diana Paola paola AT encuestame.org
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
     * Get Tweet Polls by User Id.
     * @param username username.
     * @return list of Tweet polls bean
     */
    public List<UnitTweetPoll> getTweetsPollsByUserName(final String username){
        final List<TweetPoll> tweetPolls = getTweetPollDao().retrieveTweetsByUserId(getPrimaryUser(username));
        log.info("tweetPoll size "+tweetPolls.size());
        final List<UnitTweetPoll> tweetPollsBean = new ArrayList<UnitTweetPoll>();
        for (TweetPoll tweetPoll : tweetPolls) {
            final UnitTweetPoll unitTweetPoll = ConvertDomainBean.convertTweetPollToBean(tweetPoll);
             unitTweetPoll.getQuestionBean().setListAnswers(this.retrieveAnswerByQuestionId(unitTweetPoll.getQuestionBean().getId()));
             tweetPollsBean.add(unitTweetPoll);
        }
        return tweetPollsBean;
    }

    /**
     * Save Tweet Id.
     * @param tweetPollBean {@link UnitTweetPoll}
     * @throws EnMeExpcetion exception
     */
    public void saveTweetId(final UnitTweetPoll tweetPollBean) throws EnMeExpcetion{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollBean.getId());
        if(tweetPoll != null){
            tweetPoll.setTweetId(tweetPollBean.getTweetId());
            tweetPoll.setPublicationDateTweet(tweetPollBean.getPublicationDateTweet());
            tweetPoll.setPublishTweetPoll(Boolean.TRUE);
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }else{
            throw new EnMeExpcetion("tweet poll not found");
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
            final Questions question = getQuestionDao().retrieveQuestionById(tweetPollBean.getQuestionBean().getId());
            log.debug("question found "+question);
            if(question == null){
                throw new EnMeExpcetion("question not found");
            }
            tweetPollDomain.setQuestion(question);
            tweetPollDomain.setCloseNotification(tweetPollBean.getCloseNotification());
            tweetPollDomain.setPublicationDateTweet(tweetPollBean.getPublicationDateTweet());
            tweetPollDomain.setCompleted(Boolean.FALSE);
            tweetPollDomain.setTweetOwner(getSecUserDao().getUserById(tweetPollBean.getUserId()));
            tweetPollDomain.setResultNotification(tweetPollBean.getResultNotification());
            tweetPollDomain.setPublishTweetPoll(tweetPollBean.getPublishPoll());
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
            }
            tweetPollBean.setId(tweetPollDomain.getTweetPollId());
        }
        catch (Exception e) {
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
            log.info("/**//**//**////");
            log.info(tweetPollDomain.getTweetPollId());
            tweetQuestionText = tweetPollDomain.getQuestion().getQuestion();
            final List<QuestionsAnswers> answers = getQuestionDao().getAnswersByQuestionId(tweetPollDomain.getQuestion().getQid());
            if(answers.size()==2){
                for (final QuestionsAnswers questionsAnswers : answers) {
                    tweetQuestionText += " "+questionsAnswers.getAnswer()+" "+buildUrlAnswer(questionsAnswers, url);
                }
            }
        }
        catch (Exception e) {

            throw new EnMeExpcetion(e);
        }
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
        stringBuffer.append(getTweetPath());
        stringBuffer.append(answer.getUniqueAnserHash());
        return getTwitterService().getTinyUrl(stringBuffer.toString());
    }

    /**
     * Public Tweet Poll.
     * @param tweetText tweet text
     * @param username username
     * @param password  password
     * @return status of tweet
     * @throws EnMeExpcetion exception
     */
    public Status publicTweetPoll(final String tweetText, final String username, final String password) throws EnMeExpcetion {
        try {
          return getTwitterService().publicTweet(username, password, tweetText);
        } catch (TwitterException e) {
            log.error(e);
            throw new EnMeExpcetion(e);
        }
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
    public List<UnitTweetPollResult> getResultsByTweetPollId(final Long tweetPollId){
        final List<UnitTweetPollResult> pollResults = new ArrayList<UnitTweetPollResult>();
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollId);
        for (QuestionsAnswers questionsAnswers : getQuestionDao().getAnswersByQuestionId(tweetPoll.getQuestion().getQid())) {
              final List<Object[]> result = getTweetPollDao().getResultsByTweetPoll(tweetPoll, questionsAnswers);
              final UnitTweetPollResult tweetPollResult = new UnitTweetPollResult();
              tweetPollResult.setResults(Long.valueOf(result.get(0)[1].toString()));
              tweetPollResult.setAnswersBean(ConvertDomainBean.convertAnswerToBean(questionsAnswers));
              pollResults.add(tweetPollResult);
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
     * @param tweetPollResultsPath the tweetPollResultsPath to set
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
}
