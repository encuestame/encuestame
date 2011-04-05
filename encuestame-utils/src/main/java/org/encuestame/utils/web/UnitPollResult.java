/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
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

import java.util.Date;


 /**
 * Unit Poll Result.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since  April 01, 2010
 * @version $Id: $
 */

public class UnitPollResult {

    private QuestionAnswerBean answerBean;
    private Long result;
    private Date votedDate;
    private UnitPoll poll;
    /**
     * @return the answerBean
     */
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
    public Long getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(Long result) {
        this.result = result;
    }
    /**
     * @return the votedDate
     */
    public Date getVotedDate() {
        return votedDate;
    }
    /**
     * @param votedDate the votedDate to set
     */
    public void setVotedDate(Date votedDate) {
        this.votedDate = votedDate;
    }
    /**
     * @return the poll
     */
    public UnitPoll getPoll() {
        return poll;
    }
    /**
     * @param poll the poll to set
     */
    public void setPoll(UnitPoll poll) {
        this.poll = poll;
    }



}
