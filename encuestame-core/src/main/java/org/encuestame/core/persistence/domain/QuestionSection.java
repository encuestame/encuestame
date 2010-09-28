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
package org.encuestame.core.persistence.domain;

/**
 * Questions Dependencies.
 *
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since August 19, 2010
 * @version $Id: $
 */
public class QuestionSection {

    /****/
    private Long questionSectionId;

    /****/
    private Question question;

    /****/
    private SurveySection surveySection;

    /**
     * @return the questionSectionId
     */
    public Long getQuestionSectionId() {
        return questionSectionId;
    }

    /**
     * @param questionSectionId the questionSectionId to set
     */
    public void setQuestionSectionId(Long questionSectionId) {
        this.questionSectionId = questionSectionId;
    }

    /**
     * @return the question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * @return the surveySection
     */
    public SurveySection getSurveySection() {
        return surveySection;
    }

    /**
     * @param surveySection the surveySection to set
     */
    public void setSurveySection(SurveySection surveySection) {
        this.surveySection = surveySection;
    }
}
