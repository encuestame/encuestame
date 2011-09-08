/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
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
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.imp.ISurveyService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.question.QuestionPattern;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.MD5Utils;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.json.QuestionPatternBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.SurveyBean;
import org.encuestame.utils.web.UnitSurveySection;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import twitter4j.TwitterException;
import twitter4j.http.RequestToken;

/**
 * Survey Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
@Service
public class SurveyService extends AbstractSurveyService implements ISurveyService {

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Random question key value.
     */
    private static Integer RANDOM_QUESTION_KEY = 500;

    /**
     * Create Question.
     * @param questionBean {@link QuestionBean}.
     * @throws EnMeExpcetion exception
     * @deprecated use the parent method.
     */
    @Deprecated
    public Question createQuestion(final QuestionBean questionBean) throws EnMeExpcetion{
            final Question question = new Question();
            try{
                question.setQuestion(questionBean.getQuestionName());
                question.setAccountQuestion(getAccountDao().getUserById(questionBean.getUserId()));
                question.setQidKey(MD5Utils.md5(RandomStringUtils.randomAlphanumeric(SurveyService.RANDOM_QUESTION_KEY)));
                question.setSlugQuestion(RestFullUtil.slugify(questionBean.getQuestionName()));
                //question.setQidKey("xxxxxxxxxxxxxx");
                question.setSharedQuestion(Boolean.TRUE);
                question.setHits(0L);
                question.setCreateDate(new Date());
                //save question
                getQuestionDao().saveOrUpdate(question);
                questionBean.setId(question.getQid());
                //save answers.
                for (final QuestionAnswerBean answerBean : questionBean.getListAnswers()) {
                    this.createQuestionAnswer(answerBean, question);
                }
            }
            catch (Exception e) {
                log.error("Error Creating Question "+e.getMessage());
                throw new EnMeExpcetion(e);
            }
            return question;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.service.AbstractSurveyService#createQuestionAnswer(org.encuestame.utils.web.QuestionAnswerBean, org.encuestame.persistence.domain.question.Question)
     */
    public QuestionAnswer createQuestionAnswer(final QuestionAnswerBean answerBean, final Question question){
           //return createQuestionAnswer(answerBean, question);
            return null; //TODO: fix java.lang.StackOverflowError
    }

    /**
     * Retrieve Answer By Question Id.
     * @param questionId question Id
     * @return List of Answers
     */
    public List<QuestionAnswerBean> retrieveAnswerByQuestionId(final Long questionId){
        final List<QuestionAnswer> answers = this.getQuestionDao().getAnswersByQuestionId(questionId);
        final List<QuestionAnswerBean> answersBean = new ArrayList<QuestionAnswerBean>();
        for (QuestionAnswer questionsAnswers : answers) {
            answersBean.add(ConvertDomainBean.convertAnswerToBean(questionsAnswers));
        }
        return answersBean;
    }



    /**
     * Update Answer Name by Answer Id.
     * @param answerId answer Id
     * @param nameUpdated new name for answer
     * @throws EnMeExpcetion exception
     */
    public void updateAnswerByAnswerId(final Long answerId, String nameUpdated) throws EnMeExpcetion{
            final QuestionAnswer answer = getQuestionDao().retrieveAnswerById(answerId);
            if(answer==null){
                throw new EnMeExpcetion("answer not found");
            }
            answer.setAnswer(nameUpdated);
            getQuestionDao().saveOrUpdate(answer);
    }


    /**
     * Get Twitter Token.
     * @param consumerKey consumer key
     * @param consumerSecret consumer secret
     * @return {@link RequestToken}
     * @throws TwitterException exception
     */
    public RequestToken getTwitterToken(final String consumerKey,  final String consumerSecret) throws TwitterException{
            log.debug("getTwitterToken");
            //return getTwitterService().getTwitterPing(consumerKey, consumerSecret);
            return null;
    }


    /**
     * Load all questions.
     * @return List of {@link QuestionBean}
     * @throws EnMeExpcetion exception
     */
    public List<QuestionBean> loadAllQuestions() throws EnMeExpcetion {
        final List<QuestionBean> listQuestionBean = new LinkedList<QuestionBean>();
        try {
            final  List<Question> questionsList = getQuestionDao()
                    .loadAllQuestions();
            if (questionsList.size() > 0) {

               for (Question questions : questionsList) {
                    final QuestionBean q = new QuestionBean();
                    q.setId(Long.valueOf(questions.getQid().toString()));
                    q.setQuestionName(questions.getQuestion());
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
     * @param unitPatternBean {@link QuestionPatternBean}
     * @return {@link QuestionPatternBean}
     * @throws EnMeExpcetion exception
     */
    public QuestionPatternBean loadPatternInfo(QuestionPatternBean unitPatternBean)
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
     * @return List of {@link QuestionPatternBean}
     * @throws EnMeExpcetion exception
     */
    public Collection<QuestionPatternBean> loadAllPatrons()
            throws EnMeExpcetion {
        final List<QuestionPatternBean> listPatronBean = new LinkedList<QuestionPatternBean>();
        try {
            final List<QuestionPattern> patronList = getQuestionDao()
                    .loadAllQuestionPattern();
            if (patronList.size() > 0) {
               for (QuestionPattern patron : patronList) {
                    QuestionPatternBean p = new QuestionPatternBean();
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
     * @param rANDOMQUESTIONKEY the rANDOM_QUESTION_KEY to set
     */
    public void setRandomQuestionKey(Integer rInteger){
        RANDOM_QUESTION_KEY = rInteger;
    }

    /**
     * Create Survey.
     * @param surveyBean
     * @throws EnMeExpcetion
     */
    public void createSurvey(final SurveyBean surveyBean) throws EnMeExpcetion{
        try {
            final Survey surveyDomain = new Survey();
            surveyDomain.setTicket(surveyBean.getTicket());
            surveyDomain.setStartDate(surveyBean.getStartDate());
            surveyDomain.setEndDate(surveyBean.getEndDate());
            surveyDomain.setDateInterview(surveyBean.getDateInterview());
            surveyDomain.setComplete(surveyBean.getComplete());
            surveyDomain.setCustomMessage(surveyBean.getCustomMessage());
            surveyDomain.setCustomStartMessages(surveyBean.getCustomStartMessages());
            surveyDomain.setShowProgressBar(surveyBean.getShowProgressBar());
            surveyDomain.setOptionalTitle(surveyBean.getOptionalTitle());
            surveyDomain.setPassProtection(surveyBean.getPassProtection());
            surveyDomain.setIpProtection(surveyBean.getIpProtection());
            surveyDomain.setIpRestriction(surveyBean.getIpRestriction());
            surveyDomain.setPasswordRestrictions(surveyBean.getPasswordRestrictions());
            surveyDomain.setCloseAfterDate(surveyBean.getCloseAfterDate());
            surveyDomain.setCloseAfterquota(surveyBean.getCloseAfterquota());
            surveyDomain.setCloseAfterquota(surveyBean.getCloseAfterquota());
            surveyDomain.setClosedQuota(surveyBean.getClosedQuota());
            surveyDomain.setShowResults(surveyBean.getShowResults());
            surveyDomain.setNumbervotes(surveyBean.getNumbervotes());
            surveyDomain.setHits(surveyBean.getHits());
            surveyDomain.setAdditionalInfo(surveyBean.getAdditionalInfo());
            surveyDomain.setShowAdditionalInfo(surveyBean.getShowAdditionalInfo());
            surveyDomain.setNotifications(surveyBean.getNotifications());
            surveyDomain.setName(surveyBean.getName());
            getSurveyDaoImp().saveOrUpdate(surveyDomain);
            surveyBean.setSid(surveyBean.getSid());
        } catch (Exception e) {
             log.error("Error Creating Survey "+e.getMessage());
             throw new EnMeExpcetion(e);
        }
    }


    /**
     * Create Survey Section.
     * @param surveySectionBean
     */
    public void createSurveySection(final UnitSurveySection surveySectionBean){
        try {
            final SurveySection surveySectionDomain = new SurveySection();
            surveySectionDomain.setDescSection(surveySectionBean.getName());
            for (final QuestionBean questionBean : surveySectionBean.getListQuestions()) {
                this.saveQuestions(questionBean);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    /**
     * Save Questions.
     * @param questionBean
     */
    public void saveQuestions(final QuestionBean questionBean){
        final Question question = new Question();
        question.setQuestion(questionBean.getQuestionName());
        //	question.setQidKey();
        question.setQuestionPattern(question.getQuestionPattern());
        question.setQuestionsAnswers(question.getQuestionsAnswers());
        question.setAccountQuestion(getAccountDao().getUserById(questionBean.getUserId()));
       // question.setSharedQuestion();
        this.getQuestionDao().saveOrUpdate(question);
        questionBean.setId(question.getQid());
}

    /**
     * Create Survey Folder.
     * @param folderName
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public FolderBean createSurveyFolder(final String folderName, final String username)
            throws EnMeNoResultsFoundException{
        final SurveyFolder surveyFolderDomain = new SurveyFolder();
        surveyFolderDomain.setUsers(getUserAccount(username).getAccount());
        surveyFolderDomain.setCreatedAt(new Date());
        surveyFolderDomain.setFolderName(folderName);
        this.getSurveyDaoImp().saveOrUpdate(surveyFolderDomain);
        return ConvertDomainBean.convertFolderToBeanFolder(surveyFolderDomain);
       }

    /**
     * Update Survey Folder.
     * @param folderId
     * @param folderName
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public FolderBean updateSurveyFolder(final Long folderId, final String folderName, final String username)
                throws EnMeNoResultsFoundException{
        final SurveyFolder surveyPollFolder = this.getSurveyFolder(folderId);
        if(surveyPollFolder == null) {
            throw new EnMeNoResultsFoundException("Survey Poll Folder not found");
        }
        else{
            surveyPollFolder.setFolderName(folderName);
            getSurveyDaoImp().saveOrUpdate(surveyPollFolder);
        }
         return ConvertDomainBean.convertFolderToBeanFolder(surveyPollFolder);
     }

    /**
     * Get Survey Folder.
     * @param folderId
     * @return
     */
    private SurveyFolder getSurveyFolder(final Long folderId){
        return this.getSurveyDaoImp().getSurveyFolderById(folderId);
    }

    /**
     * Delete Survey Folder.
     * @param folderId
     * @throws EnMeNoResultsFoundException
     */
    public void deleteSurveyFolder(final Long folderId) throws EnMeNoResultsFoundException{
        final SurveyFolder surveyFolder = this.getSurveyFolder(folderId);
        if(surveyFolder != null){
            getSurveyDaoImp().delete(surveyFolder);
        } else {
            throw new EnMeNoResultsFoundException("Survey folder not found");
        }
    }

    /**
     * Get Survey Folders by Folder Id and User.
     * @param folderId
     * @param userId
     * @return
     */

    private SurveyFolder getSurveysFolderByFolderIdandUser(final Long folderId, final Long userId){
        return this.getSurveyDaoImp().getSurveyFolderByIdandUser(folderId, userId);
   }

    /**
     * Add Survey to Folder.
     * @param folderId
     * @param username
     * @param surveyId
     * @throws EnMeNoResultsFoundException
     */
    public void addSurveyToFolder(final Long folderId, final String username, final Long surveyId) throws EnMeNoResultsFoundException{
        final SurveyFolder surveyFolder = this.getSurveysFolderByFolderIdandUser(folderId, getPrimaryUser(username));
        if(surveyFolder!=null) {
            final Survey survey = getSurveyDaoImp().getSurveyByIdandUserId(surveyId, getPrimaryUser(username));
            survey.setSurveysfolder(surveyFolder);
            getSurveyDaoImp().saveOrUpdate(survey);
            } else {
            throw new EnMeNoResultsFoundException("Survey folder not found");
        }
   }
}
