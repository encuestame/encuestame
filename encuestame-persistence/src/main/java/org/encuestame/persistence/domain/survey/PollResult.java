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
package org.encuestame.persistence.domain.survey;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.encuestame.persistence.domain.question.QuestionAnswer;

/**
 * PollResult Domain
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since March 15, 2009
 */
@Entity
@Table(name = "poll_result")
public class PollResult {

    private Long pollResultId;
    private QuestionAnswer answer;
    private Poll poll;
    private Date votationDate;
    private String ipAddress;


    /**
     * @return the pollResultId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "poll_resultId", unique = true, nullable = false)
    public Long getPollResultId() {
        return pollResultId;
    }

    /**
     * @param pollResultId the pollResultId to set
     */

    public void setPollResultId(final Long pollResultId) {
        this.pollResultId = pollResultId;
    }


    /**
     * @return the answer
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "q_answer_id", nullable = false)
    public QuestionAnswer getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(final QuestionAnswer answer) {
        this.answer = answer;
    }

    /**
     * @return the poll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "poll_id", nullable = false)
    public Poll getPoll() {
        return poll;
    }

    /**
     * @param poll the poll to set
     */
    public void setPoll(final Poll poll) {
        this.poll = poll;
    }

    /**
     * @return the votationDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "votation_date", nullable = false)
    public Date getVotationDate() {
        return votationDate;
    }
    /**
     * @param votationDate the votationDate to set
     */
    public void setVotationDate(final Date votationDate) {
        this.votationDate = votationDate;
    }

    /**
     * @return the ipaddress
     */
    @Column(name = "ip_address", nullable = false)
    public String getIpaddress() {
        return ipAddress;
    }
    /**
     * @param ipaddress the ipaddress to set
     */
    public void setIpaddress(final String ipaddress) {
        this.ipAddress = ipaddress;
    }


}
