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
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.imp.IHashTagDao;
import org.encuestame.core.persistence.dao.imp.ITweetPoll;
import org.encuestame.core.persistence.pojo.HashTag;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.core.persistence.pojo.Question;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.encuestame.core.persistence.pojo.SecUserTwitterAccounts;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.persistence.pojo.TweetPollResult;
import org.encuestame.core.persistence.pojo.TweetPollSwitch;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.core.service.util.MD5Utils;
import org.encuestame.utils.web.UnitAnswersBean;
import org.encuestame.utils.web.UnitHashTag;
import org.encuestame.utils.web.UnitPatternBean;
import org.encuestame.utils.web.UnitQuestionBean;
import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.utils.web.UnitTweetPollResult;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

/**
 * Survey Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
@Service
public class AbstractSurveyService extends AbstractChartService {

    /** Twitter Service. **/
    private ITwitterService twitterService;

    /** AnswerPoll Path. **/
    private String answerPollPath;

    /** TweetPoll Results Path. **/
    private String tweetPollResultsPath;

    /** TweetPoll Dao. **/
    @Resource
    private ITweetPoll tweetPollDao;

    /** Hash Tag Dao. **/
    @Resource
    private IHashTagDao hashTagDao;

    private Log log = LogFactory.getLog(this.getClass());

    /** Tweet Path, **/
    private String tweetPath;

    /**
     * Create Question.
     * @param questionBean {@link UnitQuestionBean}.
     * @throws EnMeExpcetion exception
     */
    public void createQuestion(final UnitQuestionBean questionBean) throws EnMeExpcetion{
            try{
                final Question question = new Question();
                question.setQuestion(questionBean.getQuestionName());
                question.setSecUsersQuestion(getSecUserDao().getUserById(questionBean.getUserId()));
                question.setQidKey(MD5Utils.md5(RandomStringUtils.randomAlphanumeric(500)));
                question.setSharedQuestion(false);
                getQuestionDao().saveOrUpdate(question);
                questionBean.setId(question.getQid());
                for (final UnitAnswersBean answerBean : questionBean.getListAnswers()) {
                    this.saveAnswer(answerBean);
                }
            }
            catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
    }

    /**
     * Update Question Name.
     * @param questionId
     * @param questionName
     */
    public void updateQuestionName(final Long questionId, final String questionName){
            final Question question = getQuestionDao().retrieveQuestionById(questionId);
            if(question != null){
                question.setQuestion(questionName);
                getQuestionDao().saveOrUpdate(question);
            }
    }

    /**
     * Create Hash Tag.
     * @param unitHashTag new tag
     * @return
     * @throws EnMeExpcetion exception.
     */
    public HashTag createHashTag(final UnitHashTag unitHashTag) throws EnMeExpcetion{
        try{
            final HashTag tag = new HashTag();
            tag.setHashTag(unitHashTag.getHashTagName());
            getHashTagDao().saveOrUpdate(tag);
            log.debug("Hash Tag Saved");
            return tag;
        }
        catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * Save Question Answer.
     * @param answerBean answer
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void saveAnswer(final UnitAnswersBean answerBean) throws EnMeExpcetion{
            final QuestionsAnswers answer = new QuestionsAnswers();
            if(answerBean.getQuestionId()!= null){
                final Question question = getQuestionDao().retrieveQuestionById(answerBean.getQuestionId());
                answer.setQuestions(question);
                answer.setAnswer(answerBean.getAnswers());
                answer.setUniqueAnserHash(answerBean.getAnswerHash());
                this.getQuestionDao().saveOrUpdate(answer);
                answerBean.setAnswerId(answer.getQuestionAnswerId());
            }
            else{
                  throw new EnMeExpcetion("questionId not found");
            }
    }

    /**
     * Get Tweet Polls by User Id.
     * @param userId user Id.
     * @return list of Tweet polls bean
     */
    public List<UnitTweetPoll> getTweetsPollsByUserId(final Long userId){
        final List<TweetPoll> tweetPolls = getTweetPollDao().retrieveTweetsByUserId(userId);
        final List<UnitTweetPoll> tweetPollsBean = new ArrayList<UnitTweetPoll>();
        for (TweetPoll tweetPoll : tweetPolls) {
            final UnitTweetPoll unitTweetPoll = ConvertDomainBean.convertTweetPollToBean(tweetPoll);
             unitTweetPoll.getQuestionBean().setListAnswers(this.retrieveAnswerByQuestionId(unitTweetPoll.getQuestionBean().getId()));
             tweetPollsBean.add(unitTweetPoll);
        }
        return tweetPollsBean;
    }

    /**
     * Retrieve Answer By Question Id.
     * @param questionId question Id
     * @return List of Answers
     */
    public List<UnitAnswersBean> retrieveAnswerByQuestionId(final Long questionId){
        final List<QuestionsAnswers> answers = this.getQuestionDao().getAnswersByQuestionId(questionId);
        log.debug("answers by question id ["+questionId+"] answers size "+answers.size());
        final List<UnitAnswersBean> answersBean = new ArrayList<UnitAnswersBean>();
        for (QuestionsAnswers questionsAnswers : answers) {
            answersBean.add(ConvertDomainBean.convertAnswerToBean(questionsAnswers));
        }
        return answersBean;
    }

    /**
     * Save Tweet Id.
     * @param tweetPollBean {@link UnitTweetPoll}
     * @throws EnMeExpcetion exception
     */
    public void saveTweetId(final UnitTweetPoll tweetPollBean) throws EnMeExpcetion{
        final TweetPoll tweetPoll = getTweetPollDao().getTweetPollById(tweetPollBean.getId());
        if(tweetPoll != null){
            //tweetPoll.setTweetId(tweetPollBean.getTweetId());
            //tweetPoll.setPublicationDateTweet(tweetPollBean.getPublicationDateTweet());
            tweetPoll.setPublishTweetPoll(Boolean.TRUE);
            getTweetPollDao().saveOrUpdate(tweetPoll);
        }else{
            throw new EnMeExpcetion("tweet poll not found");
        }
    }

    /**
     * Update Answer Name by Answer Id.
     * @param answerId answer Id
     * @param nameUpdated new name for answer
     * @throws EnMeExpcetion exception
     */
    public void updateAnswerByAnswerId(final Long answerId, String nameUpdated) throws EnMeExpcetion{
            final QuestionsAnswers answer = getQuestionDao().retrieveAnswerById(answerId);
            if(answer==null){
                throw new EnMeExpcetion("answer not found");
            }
            answer.setAnswer(nameUpdated);
            getQuestionDao().saveOrUpdate(answer);
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
            //tweetPollDomain.setPublicationDateTweet(tweetPollBean.getPublicationDateTweet());
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
     * Get Twitter Token.
     * @param consumerKey consumer key
     * @param consumerSecret consumer secret
     * @return {@link RequestToken}
     * @throws TwitterException exception
     */
    public RequestToken getTwitterToken(final String consumerKey,  final String consumerSecret) throws TwitterException{
            return getTwitterService().getTwitterPing(consumerKey, consumerSecret);
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
    @Deprecated
    public Status publicTweetPoll(final String tweetText, final String username, final String password) throws EnMeExpcetion {
        try {
          log.debug("publicTweetPoll");
          return getTwitterService().publicTweet(username, password, tweetText);
        } catch (TwitterException e) {
            log.error(e);
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * Public Tweet Poll (OAuth method).
     * @param tweetText tweet text
     * @return status of tweet
     * @throws EnMeExpcetion exception
     */
    public Status publicTweetPoll(final String tweetText, final SecUserTwitterAccounts account) throws EnMeExpcetion {
        try {
           return getTwitterService().publicTweet(account, tweetText);
        } catch (TwitterException e) {
            log.error(e);
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * Load all questions.
     * @return List of {@link UnitQuestionBean}
     * @throws EnMeExpcetion exception
     */
    public List<UnitQuestionBean> loadAllQuestions() throws EnMeExpcetion {
        final List<UnitQuestionBean> listQuestionBean = new LinkedList<UnitQuestionBean>();
        try {
            final  List<Question> questionsList = getQuestionDao()
                    .loadAllQuestions();
            if (questionsList.size() > 0) {

               for (Question questions : questionsList) {
                    final UnitQuestionBean q = new UnitQuestionBean();
                    q.setId(Long.valueOf(questions.getQid().toString()));
                    q.setQuestionName(questions.getQuestion());
                    q.setStateId(questions.getCatState().getIdState());
                    listQuestionBean.add(q);
                }
            }
        } catch (HibernateException e) {
            throw new EnMeExpcetion(e);
        } catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
        return  listQuestionBean;
    }

    /**
     * Load pattern info.
     * @param unitPatternBean {@link UnitPatternBean}
     * @return {@link UnitPatternBean}
     * @throws EnMeExpcetion exception
     */
    public UnitPatternBean loadPatternInfo(UnitPatternBean unitPatternBean)
            throws EnMeExpcetion {
        if (unitPatternBean != null && unitPatternBean.getId() != null) {
            final QuestionPattern questionPatternDomain = getQuestionDao().loadPatternInfo(
                    unitPatternBean.getId());

            unitPatternBean.setId(questionPatternDomain.getPatternId());

            unitPatternBean.setDescripcion(questionPatternDomain.getDesQid());
            unitPatternBean.setLabel(questionPatternDomain.getLabelQid());
            unitPatternBean.setPatronType(questionPatternDomain.getPatternType());
            unitPatternBean.setTemplate(questionPatternDomain.getPatternTemplate());
            unitPatternBean.setClasspattern("classpatthern");
            unitPatternBean.setLevelpattern("2");
            unitPatternBean.setFinallity("save");
            //TODO : need more properties.
            return unitPatternBean;
        }
        else {
            throw new EnMeExpcetion("unit patter bean is null");
        }
    }

    /**
     * Load all Patrons.
     * @return List of {@link UnitPatternBean}
     * @throws EnMeExpcetion exception
     */
    public Collection<UnitPatternBean> loadAllPatrons()
            throws EnMeExpcetion {
        final List<UnitPatternBean> listPatronBean = new LinkedList<UnitPatternBean>();
        try {
            final List<QuestionPattern> patronList = getQuestionDao()
                    .loadAllQuestionPattern();
            if (patronList.size() > 0) {
               for (QuestionPattern patron : patronList) {
                    UnitPatternBean p = new UnitPatternBean();
                    p.setId(patron.getPatternId());
                    p.setPatronType(patron.getPatternType());
                    listPatronBean.add(p);
                }
            }
        } catch (HibernateException e) {
            throw new EnMeExpcetion(e);
        } catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
        return listPatronBean;
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
     * Get List Suggestion Question.
     * @param questionKeyword question keyword
     * @return unitQuestionBean
     */
    public List<UnitQuestionBean> listSuggestQuestion(final String questionKeyword, final String username){
        final List<UnitQuestionBean> unitQuestionBean = new ArrayList<UnitQuestionBean>();
        final List<Question> questionsList = getQuestionDao().retrieveIndexQuestionsByKeyword(questionKeyword, getPrimaryUser(username));
        log.info("listSuggestQuestion "+questionsList.size());
        for (Question question : questionsList) {
            unitQuestionBean.add(ConvertDomainBean.convertQuestionsToBean(question));
        }
        return unitQuestionBean;
    }

    /**
     * List Suggested Hash Tags.
     * @param hashTagKeyWord
     * @param maxResults
     * @return
     */
    public List<UnitHashTag> listSuggestHashTags(final String hashTagKeyWord, final Integer maxResults){
        final List<HashTag> tags = getHashTagDao().getListHashTagsByKeyword(hashTagKeyWord, maxResults);
        log.debug("Hash Tag Suggested size "+tags.size());
        return ConvertDomainBean.convertListHashTagsToBean(tags);
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
     * Update Question.
     * @param unitQuestionPoll
     * @throws EnMeExpcetion  Exception
     */
     public void updateQuestion(final UnitQuestionBean unitQuestionPoll) throws EnMeExpcetion{
         final Question question = getQuestionDao().retrieveQuestionById(unitQuestionPoll.getId());
         if (question == null){
             throw new EnMeExpcetion("question not found");
         }
         else{
             question.setQuestion(unitQuestionPoll.getQuestionName());
             getQuestionDao().saveOrUpdate(question);
         }
     }

    /**
     * @return the twitterService
     */
    public ITwitterService getTwitterService() {
        return twitterService;
    }

    /**
     * @param twitterService the twitterService to set
     */
    public void setTwitterService(final ITwitterService twitterService) {
        this.twitterService = twitterService;
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
     * @return the tweetPollDao
     */
    public ITweetPoll getTweetPollDao() {
        return tweetPollDao;
    }

    /**
     * @param tweetPollDao the tweetPollDao to set
     */
    public void setTweetPollDao(final ITweetPoll tweetPollDao) {
        this.tweetPollDao = tweetPollDao;
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
     * @return the hashTagDao
     */
    public final IHashTagDao getHashTagDao() {
        return hashTagDao;
    }

    /**
     * @param hashTagDao the hashTagDao to set
     */
    public final void setHashTagDao(IHashTagDao hashTagDao) {
        this.hashTagDao = hashTagDao;
    }


}
