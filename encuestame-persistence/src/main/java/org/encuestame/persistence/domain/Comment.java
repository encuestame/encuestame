/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain;

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
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Comments Domain.
 * @author Morales Diana, Paola paolaATencuestame.org
 * @since August 11, 2011
 */
@Entity
@Indexed(index="Comments")
@Table(name = "comments")
public class Comment{

    /** Comment id **/
    private Long commentId;

    /** Comment **/
    private String comment;

    /** Comment created at **/
    private Date createdAt;

    /** {@link UserAccount} **/
    private UserAccount user;

    /** Comment parent id.**/
    private Long parentId;

    /** Like option**/
    private Long likeVote;

    /** Unlike **/
    private Long dislikeVote;

    /** {@link TweetPoll} **/
    private TweetPoll tweetPoll;

    /** {@link Poll} **/
    private Poll poll;

    /** {@link Survey} **/
    private Survey survey;

    /**
     * @return the commentId
     */
    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentId", unique = true, nullable = false)
    public Long getCommentId() {
        return commentId;
    }

    /**
     * @param commentId the commentId to set
     */
    public void setCommentId(final Long commentId) {
        this.commentId = commentId;
    }

    /**
     * @return the comment
     */
    @Field(index=Index.TOKENIZED, store=Store.YES)
    @Column(name = "comment", nullable = false , length = 2000)
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }

    /**
     * @return the createdAt
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
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
     * @return the user
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public UserAccount getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(final UserAccount user) {
        this.user = user;
    }

    /**
     * @return the parentId
     */
    @Column(name = "parentId", nullable = true)
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the like
     */
    @Column(name = "likeVote", nullable = true)
    public Long getLikeVote() {
        return likeVote;
    }

    /**
     * @param like the like to set
     */
      public void setLikeVote(final Long likeVote) {
        this.likeVote = likeVote;
    }

    /**
     * @return the unlike
     */
    @Column(name = "dislikeVote", nullable = true)
    public Long getDislikeVote() {
        return dislikeVote;
    }

    /**
     * @param unlike the unlike to set
     */
    public void setDislikeVote(final Long dislikeVote) {
        this.dislikeVote = dislikeVote;
    }

    /**
     * @return the tweetPoll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tweetPollId", nullable = true)
    public TweetPoll getTweetPoll() {
        return tweetPoll;
    }

    /**
     * @param tweetPoll the tweetPoll to set
     */
    public void setTweetPoll(final TweetPoll tweetPoll) {
        this.tweetPoll = tweetPoll;
    }

    /**
     * @return the poll
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pollId", nullable = true)
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
     * @return the survey
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sid", nullable = true)
    public Survey getSurvey() {
        return survey;
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(final Survey survey) {
        this.survey = survey;
    }
}
