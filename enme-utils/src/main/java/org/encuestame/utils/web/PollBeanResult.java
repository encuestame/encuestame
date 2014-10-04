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
package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


 /**
 * Unit Poll Result.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  April 01, 2010
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PollBeanResult extends AbstractResultBean implements Serializable {

    private static final long serialVersionUID = -8437551135621750551L;

    /**
     * Label of answer.
     */
    @JsonProperty(value = "answer")
    private QuestionAnswerBean answerBean;

    /**
     * Number of votes.
     */
    @JsonProperty(value = "answer_votes")
    private Long result;

    /**
     * Date of vote.
     */
    @JsonIgnore
    private Date votedDate;

    /**
     * Percent of result.
     */
    @JsonProperty(value = "percent")
    private String percent;

    /**
     * @return the answerBean
     */
    @JsonIgnore
    public QuestionAnswerBean getAnswerBean() {
        return answerBean;
    }

    /**
     * @param answerBean the answerBean to set
     */
    public void setAnswerBean(QuestionAnswerBean answerBean) {
        this.answerBean = answerBean;
    }

    /**
     * @return the result
     */
    @JsonIgnore
    public Long getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(final Long result) {
        this.result = result;
    }

    /**
     * @return the votedDate
     */
    @JsonIgnore
    public Date getVotedDate() {
        return votedDate;
    }

    /**
     * @param votedDate the votedDate to set
     */
    public void setVotedDate(final Date votedDate) {
        this.votedDate = votedDate;
    }


    /**
     * Get percent.
     * @return
     */
    public String getPercent() {
        return percent;
    }

    /**
     * Set percent.
     * @param percent
     */
    public void setPercent(String percent) {
        this.percent = percent;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PollBeanResult [answerBean=" + answerBean + ", result="
                + result + ", votedDate=" + votedDate + "]";
    }
}