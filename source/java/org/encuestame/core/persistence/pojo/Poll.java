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
package org.encuestame.core.persistence.pojo;

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

/**
 * Poll Domain.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since March 15, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "poll")
public class Poll {
    private Long pollId;
    private Boolean pollCompleted;
    private Date createdAt;
    private String pollHash;
    private Questions question;
    private SecUsers pollOwner;


    /**
     * @return the poll_id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "poll_id", unique = true, nullable = true)
    public Long getPollId() {
        return pollId;
    }

    /**
     * @param pollId the pollId to set
     */
    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }


    /**
     * @return the pollCompleted
     */
    @Column(name = "completed", nullable = false)
    public Boolean getPollCompleted() {
        return pollCompleted;
    }

    /**
     * @param pollCompleted the pollCompleted to set
     */
    public void setPollCompleted(final Boolean pollCompleted) {
        this.pollCompleted = pollCompleted;
    }

    /**
     * @return the createdAt
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the pollHash
     */
    @Column(name = "poll_hash", nullable = false)
    public String getPollHash() {
        return pollHash;
    }

    /**
     * @param pollHash the pollHash to set
     */
    public void setPollHash(String pollHash) {
        this.pollHash = pollHash;
    }

    /**
     * @return the question
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "qid", nullable = false)
    public Questions getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(final Questions question) {
        this.question = question;
    }

    /**
     * @return the pollOwner
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public SecUsers getPollOwner() {
        return pollOwner;
    }

    /**
     * @param pollOwner the pollOwner to set
     */
    public void setPollOwner(final SecUsers pollOwner) {
        this.pollOwner = pollOwner;
    }


}
