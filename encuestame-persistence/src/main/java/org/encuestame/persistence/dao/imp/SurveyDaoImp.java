/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.dao.imp;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.encuestame.persistence.dao.ISurvey;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.survey.SurveyFolder;
import org.encuestame.persistence.domain.survey.SurveyFormat;
import org.encuestame.persistence.domain.survey.SurveyPagination;
import org.encuestame.persistence.domain.survey.SurveyResult;
import org.encuestame.persistence.domain.survey.SurveySection;
import org.encuestame.utils.enums.TypeSearchResult;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * Survey Dao.
 * @author Picado, Juan juanATencuestame.org
 * @since June 01, 2009
 * @version $Id$
 */
@Repository("surveyDaoImp")
public class SurveyDaoImp extends AbstractHibernateDaoSupport implements ISurvey {

    /**
     *
     * @param sessionFactory
     */
    @Autowired
    public SurveyDaoImp(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Search survey by name.
     * @param searchString search string
     * @return {@link Collection} of {@link SurveyFormat}
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<Survey> searchSurveyByUserId(String searchString, final Long userId)
            throws HibernateException {
         final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
         criteria.add(Restrictions.like("name", searchString, MatchMode.ANYWHERE));
         criteria.add(Restrictions.eq("owner.uid", userId));
        return getHibernateTemplate().findByCriteria(criteria);

    }

    /**
     * Retrieve Survey Folders By UserId
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SurveyFolder> retrieveFolderByUserId(final Long userId){
        return getHibernateTemplate().findByNamedParam("FROM SurveyFolder where users.uid=:userId","userId", userId);
    }

    @SuppressWarnings("unchecked")
    public List<SurveyFolder> retrieveSurveyFolderByUserAccount(final UserAccount account){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SurveyFolder.class);
        criteria.add(Restrictions.eq("createdBy", account));
        criteria.add(Restrictions.eq("status", org.encuestame.utils.enums.Status.ACTIVE));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Retrieve Surveys by Folder
     * @param userId
     * @param folderId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Survey> retrieveSurveyByFolder(final Long userId, final Long folderId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
        criteria.add(Restrictions.eq("owner.uid", userId));
        criteria.add(Restrictions.eq("surveysfolder.id", folderId));
        return getHibernateTemplate().findByCriteria(criteria);
   }

    /**
     * Retrieve All Folders
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SurveyFolder> retrieveAllFolders(final Long userId){
        return getHibernateTemplate().find("FROM SurveyFolder");
    }

    /**
     * Retrieve Sections By Page.
     * @param surveyId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<SurveyPagination> retrieveSectionsByPage(final Long surveyId){
        return getHibernateTemplate().findByNamedParam("FROM SurveyFolder where surveyFolderId:folderId","folderId", surveyId);
    }

    /**
     * Retrieve Survey Section by Id.
     * @param sectionId
     * @return
     */
    public SurveySection retrieveSurveySectionById(Long sectionId){
          return getHibernateTemplate().get(SurveySection.class, sectionId);
        //return (SurveySection) getHibernateTemplate().findByNamedParam("FROM SurveySection where ssid=:sectionId","sectionId", sectionId);

    }

    /**
     * Retrieve Questions by Survey Section.
     */

   /* public List<SurveySection> retrieveQuestionsBySurveySection(final Long secId){
        final SurveySection ssection = this.retrieveSurveySectionById(secId);
        List questionsSection = new ArrayList(ssection.get());
         //final List  quest = ssection.getQuestionSection();
        return questionsSection;
    }*/

    /**
     * Retrieve Sections by Page in Survey.
     */
    @SuppressWarnings("unchecked")
    public List<SurveyPagination> retrieveSectionByPagination(final Integer pagId) {
        final DetachedCriteria criteria = DetachedCriteria	.forClass(SurveyPagination.class);
        criteria.add(Restrictions.eq("pageNumber", 1));
        return getHibernateTemplate().findByCriteria(criteria);
        // return getHibernateTemplate().findByNamedParam("FROM SurveyPagination where pageNumber=:pagId","pagId", pagId);
    }

    /**
     * Get Survey Folder by Id.
     * @param folderId
     * @return
     */
    public SurveyFolder getSurveyFolderById(final Long folderId){
        return getHibernateTemplate().get(SurveyFolder.class,folderId);
    }

    @SuppressWarnings("unchecked")
    public SurveyFolder getSurveyFolderByIdandUser(final Long FolderId, final Long userId){
         final DetachedCriteria criteria = DetachedCriteria.forClass(SurveyFolder.class);
         criteria.add(Restrictions.eq("users.id", userId));
         criteria.add(Restrictions.eq("id", FolderId));
         return (SurveyFolder) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get Surveys by Id and User.
     * @param surveyId
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Survey getSurveyByIdandUserId(final Long surveyId, final Long userId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
         criteria.add(Restrictions.eq("owner.uid", userId));
         criteria.add(Restrictions.eq("sid", surveyId));
         return (Survey) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get Total of TweetPoll By User Editor.
     * @param userSecondary
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Long> getTotalSurveyByOwner(final Long userId){ //editorOwner
        return (List<Long>) getHibernateTemplate().findByNamedParam("select count(sid) "
               +" from Survey where editorOwner.id = :editorOwner", "editorOwner", userId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#getSurveyById(java.lang.Long)
     */
    public Survey getSurveyById(final Long surveyId) throws HibernateException {
        return (Survey) getHibernateTemplate().get(Survey.class, surveyId);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveSurveybyQuestionName(java.lang.String, java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    //TODO: migrate search to Hibernate Search.
    public List<Survey> retrieveSurveybyName(final String keyWord, final Long userId,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
        criteria.createAlias("editorOwner","editorOwner");
        criteria.add(Restrictions.like("name", keyWord, MatchMode.ANYWHERE));
        criteria.add(Restrictions.eq("editorOwner.uid", userId));
        return (List<Survey>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveSurveyByDate(org.encuestame.persistence.domain.security.Account, java.util.Date, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Survey> retrieveSurveyByDate(
            final Account account,
            final Date initDate,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
         criteria.createAlias("owner","owner");
         criteria.add(Restrictions.between("createdAt", initDate, getNextDayMidnightDate()));
         criteria.add(Restrictions.eq("owner", account));
         return (List<Survey>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveSurveyToday(org.encuestame.persistence.domain.security.Account, java.lang.Integer, java.lang.Integer)
     */
    public List<Survey> retrieveSurveyToday(
            final Account account,
            final Integer maxResults,
            final Integer start){
       final Calendar cal = Calendar.getInstance();
       cal.set(Calendar.HOUR_OF_DAY, 0);
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND, 0);
       cal.set(Calendar.MILLISECOND,0);
       return retrieveSurveyByDate(account, cal.getTime(), maxResults, start);
   }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveSurveyLastWeek(org.encuestame.persistence.domain.security.Account, java.lang.Integer, java.lang.Integer)
     */
    public List<Survey> retrieveSurveyLastWeek(
            final Account account,
            final Integer maxResults,
            final Integer start){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return retrieveSurveyByDate(account, cal.getTime(), maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveSurveyLastYear(org.encuestame.persistence.domain.security.Account, java.lang.Integer, java.lang.Integer)
     */
    public List<Survey> retrieveSurveyLastYear(
            final Account account,
            final Integer maxResults,
            final Integer start){
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return retrieveSurveyByDate(account, cal.getTime(), maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveSurveyLastDay(org.encuestame.persistence.domain.security.Account, java.lang.Integer, java.lang.Integer)
     */
    public List<Survey> retrieveSurveyLastDay(
            final Account account,
            final Integer maxResults,
            final Integer start){
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return retrieveSurveyByDate(account, cal.getTime(), maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveFavoritesSurvey(org.encuestame.persistence.domain.security.UserAccount, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Survey> retrieveFavoritesSurvey(
            final UserAccount userAccount,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
        criteria.createAlias("editorOwner","editorOwner");
        criteria.add(Restrictions.eq("editorOwner", userAccount));
        criteria.add(Restrictions.eq("favorites", Boolean.TRUE));
        return (List<Survey>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveScheduledSurvey(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Survey> retrieveScheduledSurvey(
            final Long userId,
            final Integer maxResults,
            final Integer start){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
        criteria.createAlias("editorOwner","editorOwner");
        criteria.add(Restrictions.eq("scheduleSurvey", Boolean.TRUE));
        criteria.add(Restrictions.isNotNull("scheduleDate"));
        criteria.add(Restrictions.eq("editorOwner.uid", userId));
        return (List<Survey>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveSurveyByUserId(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Survey> retrieveSurveyByUserId(
            final Long userId,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
         criteria.createAlias("editorOwner","editorOwner");
         criteria.add(Restrictions.eq("editorOwner.uid", userId));
         criteria.addOrder(Order.desc("createdAt"));
         return (List<Survey>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#retrieveSurveyByAccount(java.lang.Long, java.lang.Integer, java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public List<Survey> retrieveSurveyByAccount(
            final Long userId,
            final Integer maxResults,
            final Integer start){
         final DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
         criteria.createAlias("owner","owner");
         criteria.add(Restrictions.eq("owner.uid", userId));
         criteria.addOrder(Order.desc("createdAt"));
         return (List<Survey>) filterByMaxorStart(criteria, maxResults, start);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#getSurveyResponseBySurvey(org.encuestame.persistence.domain.survey.Survey)
     */
    @SuppressWarnings("unchecked")
    public List<SurveyResult> getSurveyResponseBySurvey(final Survey survey, final Question question) {
        final DetachedCriteria criteria = DetachedCriteria
                .forClass(SurveyResult.class);
        if (question != null) {
            criteria.add(Restrictions.eq("question", question));
            criteria.add(Restrictions.eq("survey", survey));
        } else {
            criteria.add(Restrictions.eq("survey", survey));
        }
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.ISurvey#getSurveySection(org.encuestame.persistence.domain.survey.Survey)
     */
    @SuppressWarnings("unchecked")
    public List<SurveySection> getSurveySection(final Survey survey){
        final DetachedCriteria criteria = DetachedCriteria.forClass(SurveySection.class);
        criteria.add(Restrictions.eq("survey", survey));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    public List<Survey> getSurveysByHashTagName(final String tagName, final Integer startResults,
            final Integer limitResults, final TypeSearchResult filterby) {
        final DetachedCriteria detached = DetachedCriteria
                .forClass(Survey.class)
                .createAlias("hashTags", "hashTags")
                .setProjection(Projections.id())
                .add(Subqueries.propertyIn(
                        "hashTags.hashTagId",
                        DetachedCriteria
                                .forClass(HashTag.class, "hash")
                                .setProjection(Projections.id())
                                .add(Restrictions.in("hash.hashTag",
                                        new String[] { tagName }))));
        final DetachedCriteria criteria = DetachedCriteria.forClass(
                Survey.class, "survey");
        criteria.add(Subqueries.propertyIn("survey.sid", detached));
        if (filterby.equals(TypeSearchResult.HASHTAG)) {
            criteria.addOrder(Order.desc("survey.createdAt"));
        } else if (filterby.equals(TypeSearchResult.HASHTAGRATED)) {
              criteria.addOrder(Order.desc("numbervotes"));
        }
        return (List<Survey>) filterByMaxorStart(criteria, limitResults, startResults);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.encuestame.persistence.dao.ISurvey#getSurveysbyHashTagNameAndDateRange
	 * (java.lang.String, java.lang.Integer)
	 */
    @SuppressWarnings("unchecked")
       public List<Survey> getSurveysbyHashTagNameAndDateRange(
               final String tagName, final Integer period) {
           Date startDate = null;
           Date endDate = null;
           if (period != null) {
               final Calendar hi = Calendar.getInstance();
               hi.add(Calendar.DAY_OF_YEAR, -period);
               startDate = hi.getTime();
               endDate = Calendar.getInstance().getTime();

           }
           final DetachedCriteria detached = DetachedCriteria
                   .forClass(Survey.class)
                   .createAlias("hashTags", "hashTags")
                   .setProjection(Projections.id())
                   .add(Subqueries.propertyIn(
                           "hashTags.hashTagId",
                           DetachedCriteria
                                   .forClass(HashTag.class, "hash")
                                   .setProjection(Projections.id())
                                   .add(Restrictions.in("hash.hashTag",
                                           new String[] { tagName }))));
           final DetachedCriteria criteria = DetachedCriteria.forClass(
                   Survey.class, "survey");
           criteria.add(Subqueries.propertyIn("survey.sid", detached));
           criteria.addOrder(Order.desc("survey.createdAt"));
           criteria.add(Restrictions.between("createdAt", startDate, endDate));
           return getHibernateTemplate().findByCriteria(criteria);
       }


}