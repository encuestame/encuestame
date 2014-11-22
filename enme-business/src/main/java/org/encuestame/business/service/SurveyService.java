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

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.imp.ISurveyService;
import org.encuestame.core.util.ConvertDomainBean; 
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyResult;
import org.encuestame.persistence.domain.survey.SurveySection; 
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeSurveyNotFoundException;
import org.encuestame.utils.MD5Utils;
import org.encuestame.utils.RestFullUtil;
import org.encuestame.utils.enums.QuestionPattern;
import org.encuestame.utils.enums.TypeSearch;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.web.QuestionAnswerBean;
import org.encuestame.utils.web.SurveyBean;
import org.encuestame.utils.web.UnitSurveySection;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Survey Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
@Service
@Transactional
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
     * @param rANDOMQUESTIONKEY the rANDOM_QUESTION_KEY to set
     */
    public void setRandomQuestionKey(Integer rInteger){
        RANDOM_QUESTION_KEY = rInteger;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#createSurvey(org.encuestame.utils.web.SurveyBean)
     */
	public Survey createSurvey(final SurveyBean surveyBean, final HttpServletRequest request) throws EnMeNoResultsFoundException
		  { 
		final String sectionNameDefault = getMessage("survey.section.name.default",
				request, null);
		final String sectionDescDefault = getMessage(
				"survey.section.name.description.default", request, null);

		// 1- Create survey with possible options
		final Survey surveyDomain = this.newSurvey(surveyBean);
		if (surveyDomain != null) {
			final UnitSurveySection sectionBean = this
					.createDefaultSurveySection(sectionNameDefault, sectionDescDefault, surveyDomain);
			this.newSurveySection(sectionBean, surveyDomain);
			// 2- Create a section by default
		}
		if (surveyBean.getHashTags().size() > 0) {
			surveyDomain.getHashTags().addAll(
					retrieveListOfHashTags(surveyBean.getHashTags()));
			log.debug("Update Hash Tag");
			getSurveyDaoImp().saveOrUpdate(surveyDomain);
		}
		return surveyDomain;
	}

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#newSurvey(org.encuestame.utils.web.SurveyBean)
     */
    private Survey newSurvey(final SurveyBean surveyBean){
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
            //surveyDomain.setShowResults(surveyBean.getShowResults());
            surveyDomain.setNumbervotes(surveyBean.getNumbervotes());
            surveyDomain.setHits(surveyBean.getHits());
            surveyDomain.setAdditionalInfo(surveyBean.getAdditionalInfo());
            surveyDomain.setShowAdditionalInfo(surveyBean.getShowAdditionalInfo());
            surveyDomain.setNotifications(surveyBean.getNotifications());
            surveyDomain.setName(surveyBean.getName());
            surveyDomain.setCreateDate(surveyBean.getCreatedAt());
            surveyDomain.setFavourites(surveyBean.getFavorites());
            try {
                surveyDomain.setOwner(getAccount(surveyBean.getOwnerUsername()));
                surveyDomain.setEditorOwner(getUserAccount(surveyBean.getOwnerUsername()));
            } catch (EnMeNoResultsFoundException e) {
                log.debug("Survey user not found");
            }

            getSurveyDaoImp().saveOrUpdate(surveyDomain);
            surveyBean.setSid(surveyBean.getSid());
        return surveyDomain;
    }


    /**
     * Create Survey Section.
     * @param surveySectionBean
     */
	public SurveySection createSurveySection(
			final UnitSurveySection surveySectionBean, final Survey survey) {
		SurveySection surveySectionDomain = new SurveySection();
		 
			surveySectionDomain = this
					.newSurveySection(surveySectionBean, survey); 
		return surveySectionDomain;
	}
    
    /**
     * New survey section.
     * @param sectionBean
     * @param survey
     * @return
     */
    private SurveySection newSurveySection(final UnitSurveySection sectionBean, final Survey survey){
    	final SurveySection surveySection = new SurveySection();
		surveySection.setDescSection(sectionBean.getDescription());
		surveySection.setSectionName(sectionBean.getName());
		surveySection.setSurvey(survey);
		getSurveyDaoImp().saveOrUpdate(surveySection);
    	return surveySection;
    	
    }
    
    /**
     * 
     * @param name
     * @param description
     * @param survey
     * @return
     */
	private UnitSurveySection createDefaultSurveySection(final String name,
			final String description, final Survey survey) {
		final UnitSurveySection sectionBean = new UnitSurveySection();
		sectionBean.setDescription(description);
		sectionBean.setName(name);
		return sectionBean;
	}
	 
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.encuestame.core.service.imp.ISurveyService#retrieveSectionsBySurvey
	 * (org.encuestame.persistence.domain.survey.Survey)
	 */
	public List<UnitSurveySection> retrieveSectionsBySurvey(final Survey survey) {

		final List<SurveySection> sections = getSurveyDaoImp()
				.getSurveySection(survey);
		List<UnitSurveySection> sectionsBean = ConvertDomainBean
				.convertSurveySectionListToSurveySectionBean(sections);

		return sectionsBean;

	} 
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.encuestame.core.service.imp.ISurveyService#addQuestionToSurveySection
	 * (java.lang.String,
	 * org.encuestame.persistence.domain.security.UserAccount,
	 * org.encuestame.persistence.domain.survey.SurveySection,
	 * org.encuestame.utils.enums.QuestionPattern, java.lang.String[])
	 */
	public Question addQuestionToSurveySection(final String questionName,
			final UserAccount user, final SurveySection section,
			final QuestionPattern questionPattern, final String[] answers)
			throws EnMeExpcetion, NoSuchAlgorithmException,
			UnsupportedEncodingException { 
			Question question = new Question();
			if ((questionName == null) || (questionName.isEmpty())) {
				log.error("Question is required");
			} else {
				// Create question
				question = createQuestion(questionName, user);
				// Add section to question
				question.setSection(section);
				// Add answers to question
				question.setQuestionPattern(questionPattern);
				this.getQuestionDao().saveOrUpdate(question); 
				// 
			/* if(answers.length == 0) {
				throw new EnMeNoResultsFoundException(
				"answers are required");
			}
				this.createQuestionAnswers(answers, question);*/
			} 
			return question;
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

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#retrieveSurveyByFolder(java.lang.Long, java.lang.Long)
     */
    public List<Survey> retrieveSurveyByFolder(final Long accountId,
            final Long folderId) throws EnMeNoResultsFoundException {
        final SurveyFolder surveyFolder = this.getSurveyFolder(folderId);
        List<Survey> surveysByFolder = new ArrayList<Survey>();
        if (surveyFolder != null) {
            surveysByFolder = getSurveyDaoImp().retrieveSurveyByFolder(
                    accountId, folderId);
        } else {
            throw new EnMeNoResultsFoundException("Survey folder not found");
        }

        return surveysByFolder;
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
        final SurveyFolder surveyFolder = this.getSurveysFolderByFolderIdandUser(folderId, getUserAccountId(username));
        if(surveyFolder!=null) {
            final Survey survey = getSurveyDaoImp().getSurveyByIdandUserId(surveyId, getUserAccountId(username));
            survey.setSurveysfolder(surveyFolder);
            getSurveyDaoImp().saveOrUpdate(survey);
            } else {
            throw new EnMeNoResultsFoundException("Survey folder not found");
        }
   }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#getFolders()
     */
    public List<FolderBean> getFolders() throws EnMeNoResultsFoundException {
        final List<SurveyFolder> surveyFolders = getSurveyDaoImp()
                .retrieveSurveyFolderByUserAccount(
                        getUserAccount(getUserPrincipalUsername()));
        log.debug("List of Folders :" + surveyFolders.size());
        return ConvertDomainBean.convertListSurveyFolderToBean(surveyFolders);
    }

    /**
     * Get survey by id and user.
     * @param surveyId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public Survey getSurvey(final Long surveyId, final String username) throws EnMeNoResultsFoundException {
        Survey survey = new Survey();
 
        if (username != null) {
            survey = getSurveyDaoImp()
                    .getSurveyByIdandUserId(surveyId, getUserAccountonSecurityContext().getAccount().getUid());
        } else { 
            survey = getSurveyDaoImp().getSurveyById(surveyId); 
        }
        if (survey == null) {
            log.error("survey invalid with this id "+surveyId);
            throw new EnMeSurveyNotFoundException("tweet poll invalid with this id "+surveyId);
        }
        return survey;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#getSurveyById(java.lang.Long)
     */
	public Survey getSurveyById(final Long surveyId)
			throws EnMeNoResultsFoundException {  
		return this.getSurvey(surveyId, null);
	}

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#filterSurveyItemsByType(org.encuestame.utils.enums.TypeSearch, java.lang.String, java.lang.Integer, java.lang.Integer, org.encuestame.utils.enums.TypeSearchResult)
     */
    public List<SurveyBean> filterSurveyItemsByType(final TypeSearch typeSearch,
            String keyword, Integer max, Integer start)
            throws EnMeNoResultsFoundException, EnMeExpcetion {
        final List<SurveyBean> list = new ArrayList<SurveyBean>();
        if (TypeSearch.KEYWORD.equals(typeSearch)) {
            list.addAll(this.searchSurveysbyKeywordName(getUserPrincipalUsername(), keyword, max, start));
        } else if (TypeSearch.BYOWNER.name().equals(typeSearch)) {
            list.addAll(this.getSurveysByUserName(getUserPrincipalUsername(), max, start));
        } else if (TypeSearch.ALL.equals(typeSearch)) {
            list.addAll(this.getSurveysByAccount(max, start));
        } else if (TypeSearch.LASTDAY.equals(typeSearch)) {
            list.addAll(this.searchSurveysToday(getUserPrincipalUsername(),
                    max, start));
        } else if (TypeSearch.LASTWEEK.equals(typeSearch)) {
            list.addAll(this.searchSurveysLastWeek(
                    getUserPrincipalUsername(), max, start));
        } else if (TypeSearch.FAVOURITES.equals(typeSearch)) {
            list.addAll(this.searchSurveysFavourites(
                    getUserPrincipalUsername(), max, start));
        } else {
            list.addAll(this.getSurveysByUserName(
                    getUserPrincipalUsername(), max, start));
        }
        return list;
    }

    /**
     * Add to {@link Survey} domain item to {@link SurveyBean} list.
     * @param surveyList
     * @return
     */
    private List<SurveyBean> addSurveyDomainItemToSurveyBeanList(
            final List<Survey> surveyList) {
        final List<SurveyBean> surveyBean = new ArrayList<SurveyBean>();
        for (Survey survey : surveyList) {
            surveyBean.add(ConvertDomainBean.convertSurveyDomaintoBean(survey));
        }
        return surveyBean;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#searchSurveysByKeyWord(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<SurveyBean> searchSurveysByKeyWord(final String username,
            final String keyword, final Integer maxResults, final Integer start)
            throws EnMeExpcetion {
        log.info("search keyword survey  " + keyword);
        List<Survey> surveys = new ArrayList<Survey>();
        if (keyword == null) {
            throw new EnMeExpcetion("keyword is missing");
        } else {
            // TODO: migrate search to Hibernate Search.
            surveys = getSurveyDaoImp().retrieveSurveybyName(keyword,
                    getUserAccountId(username), maxResults, start);
        }
        log.info("search keyword surveys size " + surveys.size());
        return this.addSurveyDomainItemToSurveyBeanList(surveys);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#searchSurveysbyKeywordName(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<SurveyBean> searchSurveysbyKeywordName(final String keyWord,
            final String username, final Integer maxResults, final Integer start)
            throws EnMeExpcetion {
        final List<Survey> surveyList = getSurveyDaoImp().retrieveSurveybyName(
                keyWord, getUserAccount(getUserPrincipalUsername()).getUid(), maxResults, start);
        return this.addSurveyDomainItemToSurveyBeanList(surveyList);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#searchSurveysToday(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<SurveyBean> searchSurveysToday(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion {
        final List<Survey> surveyList = getSurveyDaoImp().retrieveSurveyToday(
                getAccount(username), maxResults, start);
        return this.addSurveyDomainItemToSurveyBeanList(surveyList);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#searchSurveysLastWeek(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<SurveyBean> searchSurveysLastWeek(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion {
        final List<Survey> surveyList = getSurveyDaoImp()
                .retrieveSurveyLastWeek(getAccount(username), maxResults, start);
        return this.addSurveyDomainItemToSurveyBeanList(surveyList);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#searchSurveysFavourites(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<SurveyBean> searchSurveysFavourites(final String username,
            final Integer maxResults, final Integer start) throws EnMeExpcetion {
        final List<Survey> surveyList = getSurveyDaoImp()
                .retrieveFavoritesSurvey(getUserAccount(username), maxResults,
                        start);
        return this.addSurveyDomainItemToSurveyBeanList(surveyList);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#getSurveysByUserName(java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    public List<SurveyBean> getSurveysByUserName(final String username,
            final Integer maxResults, final Integer start)
            throws EnMeNoResultsFoundException {
        final List<Survey> surveys = getSurveyDaoImp().retrieveSurveyByUserId(
                getUserAccount(getUserPrincipalUsername()).getUid(), maxResults, start);
        log.info("Size surveys by Username : " + surveys.size());
        return this.addSurveyDomainItemToSurveyBeanList(surveys);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.encuestame.core.service.imp.ISurveyService#getSurveysByAccount(java
	 * .lang.Integer, java.lang.Integer)
	 */
    public List<SurveyBean> getSurveysByAccount(
            final Integer maxResults, final Integer start)
            throws EnMeNoResultsFoundException {
        final List<Survey> surveys = getSurveyDaoImp().retrieveSurveyByAccount(
                getUserAccountId(getUserPrincipalUsername()), maxResults, start);
        log.info("Size surveys by account : " + surveys.size());
        return this.addSurveyDomainItemToSurveyBeanList(surveys);
    }
    
    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.imp.ISurveyService#getSurveyFolderbyId(java.lang.Long)
     */
	public SurveyFolder getSurveyFolderbyId(final Long folderId) { 
		return this.getSurveyDaoImp().getSurveyFolderByIdandUser(folderId,
				getUserAccountonSecurityContext().getUid());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.encuestame.core.service.imp.ISurveyService#saveSurveyResult(org.
	 * encuestame.persistence.domain.question.QuestionAnswer,
	 * org.encuestame.persistence.domain.survey.Survey,
	 * org.encuestame.persistence.domain.question.Question, java.lang.String)
	 */
	public SurveyResult saveSurveyResult(final QuestionAnswer answer, final Survey survey, final Question question, final String response){
		final SurveyResult result = new SurveyResult();
		result.setAnswer(answer);
		result.setSurvey(survey);
		result.setQuestion(question);
		result.setTxtResponse(response == " " ? null : response);
		getSurveyDaoImp().saveOrUpdate(result);
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.encuestame.core.service.imp.ISurveyService#retrieveSurveySectionById(java.lang.Long)
	 */
	public SurveySection retrieveSurveySectionById(final Long sectionId)
			throws EnMeSurveyNotFoundException {
		final SurveySection section = getSurveyDaoImp()
				.retrieveSurveySectionById(sectionId);
		if (section == null) {
			throw new EnMeSurveyNotFoundException(
					"Section not found with this id: " + sectionId);
		} else {
			return section;
		}

	}
}
