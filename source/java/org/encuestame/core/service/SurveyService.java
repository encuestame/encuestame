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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.QuestionDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.dao.SurveyDaoImp;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.core.persistence.pojo.QuestionsAnswers;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.persistence.pojo.TweetPoll;
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
     * Create Tweet Poll.
     * @param tweetPollBean tweet poll bean.
     * @throws EnMeExpcetion exception
     */
    public void createTweetPoll(final UnitTweetPoll tweetPollBean) throws EnMeExpcetion {
        try{
            final TweetPoll tweetPollDomain = new TweetPoll();
            System.out.println("1question id "+tweetPollBean.getTweetPoll().getId());
            //TODO: save tweet poll
            final Questions question = getQuestionDaoImp().retrieveQuestionById(tweetPollBean.getTweetPoll().getId());
            if(question == null){
                throw new EnMeExpcetion("question not found");
            }
            System.out.println("2question id "+question.getQid());
            final List<QuestionsAnswers> answers = getQuestionDaoImp().getAnswersByQuestionId(question.getQid());
            System.out.println("answers id "+answers.size());
            String tweetQuestionText = question.getQuestion();
            if(answers.size()==2){
                for (QuestionsAnswers questionsAnswers : answers) {
                    tweetQuestionText += " "+questionsAnswers.getAnswer()+" "+createUrlAnswer(questionsAnswers);
                    System.out.println("url answwerr "+tweetQuestionText);
                }
            }
            if(tweetPollBean.getPublishTweet()){
                System.out.println("Public Tweet Poll witter Account Data "+tweetPollBean.getUserId() );
                System.out.println("Public Tweet Text "+tweetQuestionText );
                this.publicTweetPoll(tweetQuestionText, getUserDaoImp().getUserById(tweetPollBean.getUserId()));
            }
        }catch (Exception e) {
           throw new EnMeExpcetion(e);
        }
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
     * @param user user
     * @return status of tweet
     * @throws EnMeExpcetion exception
     */
    public Status publicTweetPoll(final String tweetText, final SecUsers user) throws EnMeExpcetion {
        try {
          System.out.println("Twitter Account Data");
          System.out.println(user.getTwitterAccount());
          System.out.println(user.getTwitterPassword());
          return getTwitterService().publicTweet(
                    user.getTwitterAccount(), user.getTwitterPassword(), tweetText);
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
                    q.setIdState(questions.getCatState().getIdState());
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

}
