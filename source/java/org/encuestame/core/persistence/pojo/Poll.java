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
    private Long poll_id;
    private Boolean pollCompleted;
    private Date createdAt;
    private Questions question;
    private String codeResource;
    private String sendTweet;
    private String share;
    private SecUsers pollOwner;


    /**
     * @return the poll_id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "poll_id", unique = true, nullable = true)
    public Long getPoll_id() {
        return poll_id;
    }

    /**
     * @param pollId the poll_id to set
     */
    public void setPoll_id(Long pollId) {
        poll_id = pollId;
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
    public void setPollCompleted(Boolean pollCompleted) {
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
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
    public void setQuestion(Questions question) {
        this.question = question;
    }

    /**
     * @return the codeResource
     */
    @Column(name = "code_resource", nullable = false)
    public String getCodeResource() {
        return codeResource;
    }

    /**
     * @param codeResource the codeResource to set
     */
    public void setCodeResource(String codeResource) {
        this.codeResource = codeResource;
    }

    /**
     * @return the sendTweet
     */
    @Column(name = "sendTweet", nullable = false)
    public String getSendTweet() {
        return sendTweet;
    }

    /**
     * @param sendTweet the sendTweet to set
     */
    public void setSendTweet(String sendTweet) {
        this.sendTweet = sendTweet;
    }

    /**
     * @return the share
     */
    @Column(name = "share", nullable = false)
    public String getShare() {
        return share;
    }

    /**
     * @param share the share to set
     */
    public void setShare(String share) {
        this.share = share;
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
    public void setPollOwner(SecUsers pollOwner) {
        this.pollOwner = pollOwner;
    }


}
