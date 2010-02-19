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
package org.encuestame.web.beans.survey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.web.beans.MasterBean;
/**
 * Build Survey Controller Bean.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 02/06/2009 19:36:44
 * @version $Id$
 */
public class BuildSurveyControllerBean extends MasterBean {

    private String questionSearch;

    private Boolean showQuestionForm = false;
    private Boolean showSectionForm = false;


    private List<UnitQuestionBean> questionsList;
    private List<UnitSurveySection> sectionList;


    private Integer idCounterSection = 1;
    private Integer idCounterQuestion = 1;
    private Integer sectionSelected;
    private Integer patternSelected;

    private UnitQuestionBean unitQuestionBean;
    private UnitPatternBean unitPatterBean;
    private UnitSurveySection unitSurveySection;

    private Log log = LogFactory.getLog(this.getClass());


    /**
     *
     */
    public BuildSurveyControllerBean() {
        log.info("seccion add's");
    }

    /**
     * create new section
     * @author juanpicado
     */

    public void createSecction() {
        if (getUnitSurveySection() != null) {
            if (sectionList == null) {
                sectionList = new ArrayList<UnitSurveySection>();
            }
            UnitSurveySection sec;
            try {
                sec = new UnitSurveySection(getNewIdCounter(1),
                        getUnitSurveySection().getName(), 2);
                sectionList.add(sec);
                addInfoMessage("Seccion Creada", "");
                cleanSecctionBean();
            } catch (EnMeExpcetion e) {
                addErrorMessage("error->" + e.getMessage(), "");
            }
        } else {
            addErrorMessage("No se pudo Crear Seccion", "");
        }
    }

    /**
     * create a question
     * @author juanpicado
     */
    public void createQuestion() {
        if (getUnitQuestionBean() != null) {
            if (questionsList == null) {
                questionsList = new ArrayList<UnitQuestionBean>();
            }
            UnitQuestionBean question;
            try {
                int d = getNewIdCounter(2);
                question = new UnitQuestionBean();
                question.setId(Long.valueOf(d));
                if (getPatternSelected() != null) {
                    // getUnitPatterBean().setId(getPatternSelected());
                    UnitPatternBean patternN = new UnitPatternBean();
                    patternN.setId(Long.valueOf(getPatternSelected()));
                    patternN = getServicemanager().getApplicationServices().getSecurityService().getSurveyService()
                            .loadPatternInfo(patternN);
                    //cleanPatterBean(getUnitPatterBean());
                    question.setPattern(patternN);
                    question.setQuestionName(getUnitQuestionBean()
                            .getQuestionName());
                    question.setVersion("1");
                    question.setStateId(2L);
                    addInfoMessage("Pregunta Creada", "");
                    questionsList.add(question);
                } else {
                    addErrorMessage("error message", "");
                    new EnMeExpcetion("patron nulo");
                }
                // cleanSecctionBean();
            } catch (Exception e) {
                addErrorMessage("error->" + e.getMessage(), "");
            }
        }
    }

    /**
     * clean pattern bean
     * @param bean
     */
    private void cleanPatterBean(UnitPatternBean bean) {
        bean.setId(null);
        bean.setLabel(null);
        bean.setTemplate(null);
        bean.setDescripcion(null);
    }

    /**
     *
     * @return
     * @throws EnMeExpcetion
     */
    private Integer getNewIdCounter(Integer op) throws EnMeExpcetion {
        Integer idRe = null;
        switch (op) {
        case 1:
            if (idCounterSection != null) {
                idRe = idCounterSection;
                idCounterSection = idCounterSection + 1;
            } else {
                idRe = idCounterSection = 1;
            }
            break;
        case 2:
            if (idCounterQuestion != null) {
                idRe = idCounterQuestion;
                idCounterQuestion = idCounterQuestion + 1;
            } else {
                idRe = idCounterQuestion = 1;
            }
            break;
        default:
            throw new EnMeExpcetion("counter error");
        }
        return idRe;
    }

    /**
     * clean section bean
     */
    private void cleanSecctionBean() {
        getUnitSurveySection().setName(null);
        getUnitSurveySection().setId(null);
        getUnitSurveySection().setStateId(null);
    }

    /**
     * search questions in bd
     */
    public void searchQuestions() {
        try {
            questionsList = getServicemanager().getApplicationServices()
            .getSecurityService()
            .getSurveyService()
                    .loadAllQuestions();
        } catch (HibernateException e) {
            addErrorMessage("Error->" + e, "");
        } catch (EnMeExpcetion e) {
            addErrorMessage("Error->" + e, "");
        } catch (Exception e) {
            addErrorMessage("Error->" + e, "");
        }

    }

    /**
     * Mueve una pregunta a una sección
     * @author juanpicado
     * @param fm fm
     * @param family family
     */
    public void moveQuestion(Object fm, Object family) {
        ArrayList target = null;
        UnitQuestionBean dd = (UnitQuestionBean) fm;
        dd.getPattern().setTemplate("pattern/url.xhtml");
        // log.info("Object Move fm->" + fm);
        // log.info("Object family->" + family);
        addInfoMessage("Pregunta ASignada a la Seccion ->" + family
                + "La pregunta->" + dd.getQuestionName(), "");
        int ind = sectionList.indexOf(family);
        UnitSurveySection d = sectionList.get(ind);
        d.getQuestions().add((UnitQuestionBean) fm);
        // log.info("Posicion Encontrada->" + ind);
        // log.info("Posicion sectionList->" + sectionList);
        UnitSurveySection ddedd = sectionList.get(ind);
        // log.info("Posicion Preguntas Totales->" + ddedd.getQuestions());
    }

    /**
     * @return the questionSearch
     */
    public String getQuestionSearch() {
        return questionSearch;
    }

    /**
     * @param questionSearch
     *            the questionSearch to set
     */
    public void setQuestionSearch(String questionSearch) {
        this.questionSearch = questionSearch;
    }

    /**
     * @return the questionsList
     */
    public List<UnitQuestionBean> getQuestionsList() {
        // searchQuestions();
        return questionsList;
    }

    /**
     * @param questionsList
     *            the questionsList to set
     */
    public void setQuestionsList(List<UnitQuestionBean> questionsList) {
        this.questionsList = questionsList;
    }

    /**
     * @return the sectionList
     */
    public List<UnitSurveySection> getSectionList() {
        return sectionList;
    }

    /**
     * @param sectionList
     *            the sectionList to set
     */
    public void setSectionList(List<UnitSurveySection> sectionList) {
        this.sectionList = sectionList;
    }

    /**
     * @return the unitQuestionBean
     */
    public UnitQuestionBean getUnitQuestionBean() {
        return unitQuestionBean;
    }

    /**
     * @param unitQuestionBean
     *            the unitQuestionBean to set
     */
    public void setUnitQuestionBean(UnitQuestionBean unitQuestionBean) {
        this.unitQuestionBean = unitQuestionBean;
    }

    /**
     * @return the unitSurveySection
     */
    public UnitSurveySection getUnitSurveySection() {
        return unitSurveySection;
    }

    /**
     * @param unitSurveySection
     *            the unitSurveySection to set
     */
    public void setUnitSurveySection(UnitSurveySection unitSurveySection) {
        this.unitSurveySection = unitSurveySection;
    }

    /**
     * @return the showQuestionForm
     */
    public Boolean getShowQuestionForm() {
        return showQuestionForm;
    }

    /**
     * @param showQuestionForm
     *            the showQuestionForm to set
     */
    public void setShowQuestionForm(Boolean showQuestionForm) {
        this.showQuestionForm = showQuestionForm;
    }

    /**
     * @return the showSectionForm
     */
    public Boolean getShowSectionForm() {
        return showSectionForm;
    }

    /**
     * @param showSectionForm
     *            the showSectionForm to set
     */
    public void setShowSectionForm(Boolean showSectionForm) {
        this.showSectionForm = showSectionForm;
    }

    /**
     * @return the sectionSelected
     */
    public Integer getSectionSelected() {
        return sectionSelected;
    }

    /**
     * change rendered section
     *
     * @param selected selected
     */
    public void changeRenderedSection() {
        if (getSectionSelected() != null) {
            UnitSurveySection ind = sectionList.get(getSectionSelected()
                    .intValue() - 1);
            changeShowedSection(ind);
        } else {
            addErrorMessage("getSectionSelected() is null", "");
        }
    }

    /**
     *
     * @param sec
     */
    private void changeShowedSection(UnitSurveySection sec) {

        try {
            Iterator<UnitSurveySection> i = sectionList.iterator();
            while (i.hasNext()) {
                UnitSurveySection unitSurveySection = (UnitSurveySection) i
                        .next();
                unitSurveySection.setShowPanel(false);
            }
            if (sec != null) {
                sec.setShowPanel(true);

            } else {
                addErrorMessage("No se pudo cambiar la secci�n", "");
            }
        } catch (Exception e) {
            addErrorMessage("No se pudo cambiar la secci�n", "");
        }
    }

    /**
     * @param sectionSelected
     *            the sectionSelected to set
     */
    public void setSectionSelected(Integer sectionSelected) {
        this.sectionSelected = sectionSelected;
    }

    /**
     * @return
     */
    public UnitPatternBean getUnitPatterBean() {
        return unitPatterBean;
    }

    /**
     * @param unitPatterBean
     */
    public void setUnitPatterBean(UnitPatternBean unitPatterBean) {
        this.unitPatterBean = unitPatterBean;
    }

    /**
     * @return
     */
    public Integer getPatternSelected() {
        return patternSelected;
    }

    /**
     * @param patternSelected
     */
    public void setPatternSelected(Integer patternSelected) {
        this.patternSelected = patternSelected;
    }

}
