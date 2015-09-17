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

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.encuestame.persistence.domain.AbstractSurvey;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

/**
 * Poll Domain.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since March 15, 2010
 */
@Indexed(index="Poll")
@Entity
@Table(name = "poll",
       uniqueConstraints = {@UniqueConstraint(columnNames={"poll_hash"})})
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Poll extends AbstractSurvey {

    /**
     * Id.
     */
    private Long pollId;

    /**
     * Define if poll has been completed.
     */
    private Boolean pollCompleted;

    /**
     * Unique hash.
     */
    private String pollHash;

    /**
     * {@link Question} related with this poll.
     */
    private Question question;

    /**
     * Define if poll has been published.
     */
    private Boolean publish;

    /**
     * PollFolder
     */
    private PollFolder pollFolder;

    /**
     * Hash Tags.
     **/
    private Set<HashTag> hashTags = new HashSet<HashTag>();

    /**
     * @return the poll_id
     */
    @Id
    @DocumentId
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
    @Column(name = "poll_completed", nullable = false)
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
    @IndexedEmbedded
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
     * @return the hashTags
     */
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "poll_hashtags",
               joinColumns = {@JoinColumn(name = "poll_id")},
               inverseJoinColumns = {@JoinColumn(name = "hastag_id")})
    public Set<HashTag> getHashTags() {
        return hashTags;
    }

    /**
     * @param hashTags the hashTags to set
     */
    public void setHashTags(Set<HashTag> hashTags) {
        this.hashTags = hashTags;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Poll [pollId=" + pollId + ", pollCompleted=" + pollCompleted
                + ", pollHash=" + pollHash + ", question=" + question
                + ", publish=" + publish + ", pollFolder=" + pollFolder
                + ", hashTags=" + hashTags + ", getCreateDate()="
                + getCreateDate() + ", getCustomMessage()="
                + getCustomMessage() + ", getCustomStartMessages()="
                + getCustomStartMessages() + ", getOptionalTitle()="
                + getOptionalTitle() + ", getPasswordRestrictions()="
                + getPasswordRestrictions() + ", getIpRestriction()="
                + getIpRestriction() + ", getPassProtection()="
                + getPassProtection() + ", getIpProtection()="
                + getIpProtection() + ", getCloseAfterDate()="
                + getCloseAfterDate() + ", getClosedDate()=" + getClosedDate()
                + ", getCloseAfterquota()=" + getCloseAfterquota()
                + ", getClosedQuota()=" + getClosedQuota()
                + ", getCustomFinalMessage()=" + getCustomFinalMessage()
                + ", getMultipleResponse()=" + getMultipleResponse()
                + ", getShowResults()=" + getShowResults()
                + ", getShowComments()=" + getShowComments()
                + ", getShowAdditionalInfo()=" + getShowAdditionalInfo()
                + ", getAdditionalInfo()=" + getAdditionalInfo()
                + ", getNotifications()=" + getNotifications()
                + ", getNumbervotes()=" + getNumbervotes() + ", getHits()="
                + getHits() + ", getName()=" + getName()
                + ", getEditorOwner()=" + getEditorOwner()
                + ", getRelevance()=" + getRelevance() + ", getLikeVote()="
                + getLikeVote() + ", getDislikeVote()=" + getDislikeVote()
                + ", getFavourites()=" + getFavourites() + ", getEndDate()="
                + getEndDate() + ", getUpdatedDate()=" + getUpdatedDate()
                + ", getOwner()=" + getOwner() + ", getScheduled()="
                + getScheduled() + ", getScheduleDate()=" + getScheduleDate()
                + ", getLocationLatitude()=" + getLocationLatitude()
                + ", getLocationLongitude()=" + getLocationLongitude()
                + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }


}
