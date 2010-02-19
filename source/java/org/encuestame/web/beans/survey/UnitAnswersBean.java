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

/**
 * Unit Answers Bean.
 *
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since 01/06/2009 15:24:16
 * @version $Id$
 */

public class UnitAnswersBean {

    /** Answer Id. **/
    public Long answerId;

    /** Answer. **/
    public String answers;

    /** Answer Hash. **/
    public String answerHash;

    /** Question Id. **/
    public Long questionId;

    /**
     * @return the answerId
     */
    public Long getAnswerId() {
        return answerId;
    }

    /**
     * @param answerId
     *            the answerId to set
     */
    public void setAnswerId(final Long answerId) {
        this.answerId = answerId;
    }

    /**
     * @return the answers
     */
    public String getAnswers() {
        return answers;
    }

    /**
     * @param answers
     *            the answers to set
     */
    public void setAnswers(final String answers) {
        this.answers = answers;
    }

    /**
     * @return the answerHash
     */
    public String getAnswerHash() {
        return answerHash;
    }

    /**
     * @param answerHash
     *            the answerHash to set
     */
    public void setAnswerHash(final String answerHash) {
        this.answerHash = answerHash;
    }

    /**
     * @return the questionId
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId
     *            the questionId to set
     */
    public void setQuestionId(final Long questionId) {
        this.questionId = questionId;
    }
}
