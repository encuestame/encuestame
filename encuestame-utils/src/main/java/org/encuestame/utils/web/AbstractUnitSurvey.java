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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Abstract Unit Survey.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 31, 2010 8:19:51 AM
 * @version $Id:$
 */
public abstract class AbstractUnitSurvey implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = -2162917977567543044L;


    /** List of HashTags. **/
    private List<HashTagBean> hashTags = new ArrayList<HashTagBean>();

    /** **/
    @JsonProperty(value = "userId")
    private Long userId;

    /** **/
    @JsonProperty(value = "ownerUsername")
    private String ownerUsername;

    /** **/
    @JsonProperty(value = "likeVote")
    private String relativeTime;

    /** **/
    @JsonProperty(value = "totalVotes")
    private Long totalVotes;

    /** **/
    @JsonProperty(value = "itemType")
    private String itemType;

    /** Relevance. **/
    @JsonProperty(value = "relevance")
    private Long relevance;

    /** **/
    @JsonProperty(value = "likeVote")
    private Long likeVote;

    /** **/
    @JsonProperty(value = "dislike_vote")
    private Long dislikeVote;

    @JsonProperty(value = "createDate")
    private String createDate;

    @JsonProperty(value = "createdDateAt")
    private Date createdDateAt;

    /**
     * @return the hashTags
     */
    public final List<HashTagBean> getHashTags() {
        return hashTags;
    }

    /**
     * @param hashTags the hashTags to set
     */
    public final void setHashTags(List<HashTagBean> hashTags) {
        this.hashTags = hashTags;
    }

    /**
     * @return the userId
     */
    @JsonIgnore
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the ownerUsername
     */
    @JsonIgnore
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * @param ownerUsername the ownerUsername to set
     */
    public void setOwnerUsername(final String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * @return the relativeTime
     */
    @JsonIgnore
    public String getRelativeTime() {
        return relativeTime;
    }

    /**
     * @param relativeTime the relativeTime to set
     */
    public void setRelativeTime(final String relativeTime) {
        this.relativeTime = relativeTime;
    }

    /**
     * @return the totalVotes
     */
    @JsonIgnore
    public Long getTotalVotes() {
        return totalVotes;
    }

    /**
     * @param totalVotes the totalVotes to set
     */
    public void setTotalVotes(final Long totalVotes) {
        this.totalVotes = totalVotes;
    }

    /**
     * @return the itemType
     */
    @JsonIgnore
    public String getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(final String itemType) {
        this.itemType = itemType;
    }

    /**
     * @return the relevance
     */
    @JsonIgnore
    public Long getRelevance() {
        return relevance;
    }

    /**
     * @param relevance the relevance to set
     */
    public void setRelevance(final Long relevance) {
        this.relevance = relevance;
    }

    /**
     * @return the likeVote
     */
    @JsonIgnore
    public Long getLikeVote() {
        return likeVote;
    }

    /**
     * @param likeVote the likeVote to set
     */
    public void setLikeVote(final Long likeVote) {
        this.likeVote = likeVote;
    }

    /**
     * @return the dislikeVote
     */
    @JsonIgnore
    public Long getDislikeVote() {
        return dislikeVote;
    }

    /**
     * @param dislikeVote the dislikeVote to set
     */
    public void setDislikeVote(final Long dislikeVote) {
        this.dislikeVote = dislikeVote;
    }

    /**
     * @return the createDate
     */
    @JsonIgnore
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(final String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the createdDateAt
     */
    @JsonIgnore
    public Date getCreatedDateAt() {
        return createdDateAt;
    }

    /**
     * @param createdDateAt the createdDateAt to set
     */
    public void setCreatedDateAt(final Date createdDateAt) {
        this.createdDateAt = createdDateAt;
    }
}
