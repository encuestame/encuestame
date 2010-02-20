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
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.QuestionDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.dao.SurveyDaoImp;
import org.encuestame.core.persistence.dao.imp.ITweetPoll;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.web.beans.ConvertListDomainSelectBean;
import org.encuestame.web.beans.survey.UnitAnswersBean;
import org.encuestame.web.beans.survey.UnitPatternBean;
import org.encuestame.web.beans.survey.UnitQuestionBean;
import org.encuestame.web.beans.survey.tweetpoll.UnitTweetPoll;
import org.hibernate.HibernateException;

import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Survey Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
public class SurveyService extends Service implements ISurveyService {

    private MailServiceImpl serviceMail;
    private SurveyDaoImp surveyDaoImp;
    private QuestionDaoImp questionDaoImp;
    private SecUserDaoImp userDaoImp;
    private ITwitterService twitterService;
    private String answerPollPath;
    private String tweetPollResultsPath;
    private ITweetPoll tweetPollDao;
    /**
     * @return {@link MailServiceImpl}.
     */
    public MailServiceImpl getServiceMail() {
        return serviceMail;
    }

    /**
     * @param serviceMail {@link MailServiceImpl}.
     */
    public void setServiceMail(MailServiceImpl serviceMail) {
        this.serviceMail = serviceMail;
    }

    /**
     * @return the surveyDaoImp
     */
    public SurveyDaoImp getSurveyDaoImp() {
        return surveyDaoImp;
    }

    /**
     * @param surveyDaoImp
     *            the surveyDaoImp to set
     */
    public void setSurveyDaoImp(SurveyDaoImp surveyDaoImp) {
        this.surveyDaoImp = surveyDaoImp;
    }

    /**
     * @return the questionDaoImp
     */
    public QuestionDaoImp getQuestionDaoImp() {
        return questionDaoImp;
    }

    /**
     * @param questionDaoImp
     *            the questionDaoImp to set
     */
    public void setQuestionDaoImp(QuestionDaoImp questionDaoImp) {
        this.questionDaoImp = questionDaoImp;
    }

    /**
     * Create Question.
     * @param questionBean {@link UnitQuestionBean}.
     */
    public void createQuestion(final UnitQuestionBean questionBean){
            try{
                final Questions questions = new Questions();
                questions.setQuestion(questionBean.getQuestionName());
                questions.setSecUsersQuestion(getUserDaoImp().getUserById(questionBean.getUserId()));
                questions.setQidKey("12345");
                questions.setSharedQuestion(false);
                getQuestionDaoImp().saveOrUpdate(questions);
                questionBean.setId(questions.getQid());
                for (final UnitAnswersBean answerBean : questionBean.getListAnswers()) {
                     final QuestionsAnswers answer = new QuestionsAnswers();
                     answer.setQuestions(questions);
                     answer.setAnswer(answerBean.getAnswers());
                     answer.setUniqueAnserHash(answerBean.getAnswerHash());
                     getQuestionDaoImp().saveOrUpdate(answer);
                     answerBean.setAnswerId(answer.getQuestionAnswerId());
                }
            }
            catch (Exception e) {
                log.error(e);
                e.printStackTrace();
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
             tweetPollsBean.add(ConvertDomainBean.convertTweetPollToBean(tweetPoll));
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
            final Questions question = getQuestionDaoImp().retrieveQuestionById(tweetPollBean.getQuestionBean().getId());
            log.info("question found "+question);
            if(question == null){
                throw new EnMeExpcetion("question not found");
            }
            tweetPollDomain.setQuestion(question);
            tweetPollDomain.setCloseNotification(tweetPollBean.getCloseNotification());
            tweetPollDomain.setPublicationDateTweet(tweetPollBean.getPublicationDateTweet());
            tweetPollDomain.setEndDateTweet(tweetPollBean.getEndDateTweet());
            tweetPollDomain.setCompleted(false);
            tweetPollDomain.setTweetOwner(getUserDaoImp().getUserById(tweetPollBean.getUserId()));
            tweetPollDomain.setResultNotification(tweetPollBean.getResultNotification());
            tweetPollDomain.setPublishTweetPoll(tweetPollBean.getPublishPoll());
            tweetPollDomain.setStartDateTweet(tweetPollBean.getStartDateTweet());
            getTweetPollDao().saveOrUpdate(tweetPollDomain);
            tweetPollBean.setId(tweetPollDomain.getTweetPollId());
        }
        catch (Exception e) {
           throw new EnMeExpcetion(e);
        }
    }

    /**
     * Generate TweetPoll Text.
     * @param tweetPoll tweetPoll
     * @return tweet text
     * @throws EnMeExpcetion exception
     */
    public String generateTweetPollText(final UnitTweetPoll tweetPoll) throws EnMeExpcetion{
        String tweetQuestionText = "";
        try{
            final TweetPoll tweetPollDomain = getTweetPollDao().getTweetPollById(tweetPoll.getId());
            tweetQuestionText = tweetPollDomain.getQuestion().getQuestion();
            final List<QuestionsAnswers> answers = getQuestionDaoImp().getAnswersByQuestionId(tweetPollDomain.getQuestion().getQid());
            if(answers.size()==2){
                for (QuestionsAnswers questionsAnswers : answers) {
                    tweetQuestionText += " "+questionsAnswers.getAnswer()+" "+createUrlAnswer(questionsAnswers);
             }
        }
        }catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
        return tweetQuestionText;
    }

    /**
     *
     * @param anwer
     * @throws IOException
     * @throws HttpException
     */
    private String createUrlAnswer(final QuestionsAnswers answer) throws HttpException, IOException{
        final String urlAnswer = "http://"+getDomainUrl() + "/"+getAnswerPollPath()+answer.getUniqueAnserHash();
        return getTwitterService().getTinyUrl(urlAnswer);
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
            final  List<Questions> questionsList = getQuestionDaoImp()
                    .loadAllQuestions();
            if (questionsList.size() > 0) {

               for (Questions questions : questionsList) {
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
            System.out.println("--------- LOAD PATTERN-------->"+unitPatternBean.getId());
            final QuestionPattern questionPatternDomain = getQuestionDaoImp().loadPatternInfo(
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
        } else {
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
            final List<QuestionPattern> patronList = getQuestionDaoImp()
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
     * @return the userDaoImp
     */
    public SecUserDaoImp getUserDaoImp() {
        return userDaoImp;
    }

    /**
     * @param userDaoImp the userDaoImp to set
     */
    public void setUserDaoImp(final SecUserDaoImp userDaoImp) {
        this.userDaoImp = userDaoImp;
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
}
