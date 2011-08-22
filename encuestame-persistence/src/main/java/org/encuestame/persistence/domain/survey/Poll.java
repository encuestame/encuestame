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
import javax.persistence.UniqueConstraint;

import org.encuestame.persistence.domain.AbstractSurvey;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Poll Domain.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since March 15, 2010
 * @version $Id: $
 */
@Entity
@Table(name = "poll",
    uniqueConstraints = {@UniqueConstraint(columnNames={"poll_hash"})})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Poll extends AbstractSurvey {
    private Long pollId;
    private Boolean pollCompleted;
    private Date createdAt;
    private String pollHash;
    private Question question;
    private UserAccount pollOwner;
    private Date endDate;
    private Boolean publish;
    private Boolean closeNotification;
    private Boolean showVotes;
    private PollFolder pollFolder;
    private Date updatedDate;

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
    public Question getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(final Question question) {
        this.question = question;
    }

    /**
     * @return the pollOwner
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public UserAccount getPollOwner() {
        return pollOwner;
    }

    /**
     * @param pollOwner the pollOwner to set
     */
    public void setPollOwner(final UserAccount pollOwner) {
        this.pollOwner = pollOwner;
    }

    /**
     *
     * @return endDate
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = true)
    public Date getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate Finish Date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return publish Publish Poll Indicator
     */

    @Column(name = "publish_poll", nullable = true)
    public Boolean getPublish() {
        return publish;
    }

    /**
     *
     * @param publish publish Indicator to set
     */
    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    /**
     *
     * @return closeNotification
     */
    @Column(name = "close_notification", nullable = true)
    public Boolean getCloseNotification() {
        return closeNotification;
    }

    /**
     *
     * @param closeNotification closeNotification Indicator to set
     */
    public void setCloseNotification(Boolean closeNotification) {
        this.closeNotification = closeNotification;
    }

    /**
     *
     * @return showVotes Show Results Indicator
     */
    @Column(name = "show_results", nullable = true)
    public Boolean getShowVotes() {
        return showVotes;
    }

    /**
     *
     * @param showVotes the showVotes option to set
     */
    public void setShowVotes(Boolean showVotes) {
        this.showVotes = showVotes;
    }

    /**
     * @return the pollFolder
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "poll_folder")
    public PollFolder getPollFolder() {
        return pollFolder;
    }

    /**
     * @param pollFolder the pollFolder to set
     */
    public void setPollFolder(PollFolder pollFolder) {
        this.pollFolder = pollFolder;
    }

    /**
    * @return the updatedDate
    */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
    * @param updatedDate the updatedDate to set
    */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
