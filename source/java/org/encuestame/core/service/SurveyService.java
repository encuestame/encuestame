/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.mail.MailServiceImpl;
import org.encuestame.core.persistence.dao.QuestionDaoImp;
import org.encuestame.core.persistence.dao.SurveyDaoImp;
import org.encuestame.core.persistence.pojo.Questions;
import org.encuestame.core.persistence.pojo.QuestionPattern;
import org.encuestame.web.beans.survey.UnitPatternBean;
import org.encuestame.web.beans.survey.UnitQuestionBean;
import org.hibernate.HibernateException;

/**
 * Survey Service.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * File name: $HeadURL$
 * Revision: $Revision$
 * Last modified: $Date$
 * Last modified by: $Author$
 */
public class SurveyService extends Service implements ISurveyService {

    private MailServiceImpl serviceMail;
    private SurveyDaoImp surveyDaoImp;
    private QuestionDaoImp questionDaoImp;

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
                    q.setId(Integer.valueOf(questions.getQid().toString()));
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
        return listQuestionBean;
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
            final QuestionPattern questionPatternDomain = getQuestionDaoImp().loadPatternInfo(
                    unitPatternBean.getId());
            unitPatternBean.setId(questionPatternDomain.getIdPatron());
            unitPatternBean.setDescripcion(questionPatternDomain.getDesQid());
            unitPatternBean.setLabel(questionPatternDomain.getLabelQid());
            unitPatternBean.setPatronType(questionPatternDomain.getTypePatron());
            unitPatternBean.setTemplate(questionPatternDomain.getTemplatePatron());
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
                    p.setId(patron.getIdPatron());
                    p.setPatronType(patron.getTypePatron());
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

}
