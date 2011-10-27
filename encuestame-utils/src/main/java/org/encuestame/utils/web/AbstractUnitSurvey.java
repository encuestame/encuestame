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
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Abstract Unit Survey.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 31, 2010 8:19:51 AM
 */
public abstract class AbstractUnitSurvey implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -2162917977567543044L;

    /** List of HashTags. **/
    @JsonProperty(value = "hashtags")
    private List<HashTagBean> hashTags = new ArrayList<HashTagBean>();

    /** **/
    @JsonProperty(value = "owner_username")
    private String ownerUsername;

    /** **/
    @JsonProperty(value = "relative_time")
    private String relativeTime;

    /** **/
    @JsonProperty(value = "total_votes")
    private Long totalVotes;

    /** Hits. **/
    @JsonProperty(value = "hits")
    private Long hits;

    /** **/
    @JsonProperty(value = "item_type")
    private String itemType;

    /** **/
    @JsonProperty(value = "like_votes")
    private Long likeVote;

    /** **/
    @JsonProperty(value = "dislike_votes")
    private Long dislikeVote;

    @JsonProperty(value = "create_date")
    private String createDate;

    /** Relevance. **/
    @JsonProperty(value = "relevance")
    private Long relevance;

    /** Favorite. **/
    @JsonProperty(value = "favorite")
    private Boolean favorite;

    /** Latitud. **/
    @JsonProperty(value = "latitude")
    private Float latitude;

    /** Longitude. **/
    @JsonProperty(value = "longitude")
    private Float longitude;

    /** Question additional info. **/
    @JsonProperty(value = "additional_info")
    private String additionalInfo;

    /** Show comments. **/
    @JsonProperty(value = "show_comments")
    private String showComments;

    /** Show results. **/
    @JsonProperty(value = "is_show_results")
    private Boolean isShowResults;

    /** Poll Folder. **/
    @JsonProperty(value = "folder_id")
    private Long folderId;

    /** Show Additional Info. **/
    @JsonProperty(value = "is_show_additional_info")
    private Boolean isShowAdditionalInfo;

    /** Closing Option. **/
    @JsonProperty(value = "is_close_after_date")
    private Boolean isCloseAfterDate;

    /** Closing date**/
    @JsonProperty(value = "close_date")
    private String closedDate;

    /** Close after quota.**/
    @JsonProperty(value = "is_close_after_quota")
    private Boolean isCloseAfterQuota;

    /** Closing quota .**/
    @JsonProperty(value = "close_quota")
    private Integer closedQuota;

    /** Is ip restricted. **/
    @JsonProperty(value = "is_ip_restricted")
    private Boolean isIpRestricted;

    /** Ip restricted. **/
    @JsonProperty(value = "ip_restricted")
    private String ipRestricted;

    /** Multiple response option. **/
    @JsonProperty(value = "multiple_response")
    private String multipleResponse;

    /**
     * @return the hashTags
     */
    @JsonIgnore
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
     * @return the favorite
     */
    @JsonIgnore
    public Boolean getFavorite() {
        return favorite;
    }

    /**
     * @param favorite the favorite to set
     */
    public void setFavorite(final Boolean favorite) {
        this.favorite = favorite;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public Long getHits() {
        return hits;
    }

    /**
     *
     * @param hits
     */
    public void setHits(final Long hits) {
        this.hits = hits;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(final Float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(final Float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the additionalInfo
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * @param additionalInfo the additionalInfo to set
     */
    public void setAdditionalInfo(final String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * @return the showComments
     */
    public String getShowComments() {
        return showComments;
    }

    /**
     * @param showComments the showComments to set
     */
    public void setShowComments(final String showComments) {
        this.showComments = showComments;
    }

    /**
     * @return the showResults
     */
    public Boolean getShowResults() {
        return isShowResults;
    }

    /**
     * @param showResults the showResults to set
     */
    public void setIsShowResults(final Boolean isShowResults) {
        this.isShowResults = isShowResults;
    }

    /**
     * @return the folderId
     */
    public Long getFolderId() {
        return folderId;
    }

    /**
     * @param folderId the folderId to set
     */
    public void setFolderId(final Long folderId) {
        this.folderId = folderId;
    }

    /**
     * @return the showAdditionalInfo
     */
    public Boolean getIsShowAdditionalInfo() {
        return isShowAdditionalInfo;
    }

    /**
     * @param showAdditionalInfo the showAdditionalInfo to set
     */
    public void setIsShowAdditionalInfo(final Boolean isShowAdditionalInfo) {
        this.isShowAdditionalInfo = isShowAdditionalInfo;
    }

    /**
     * @return the closeAfterDate
     */
    public Boolean getIsCloseAfterDate() {
        return isCloseAfterDate;
    }

    /**
     * @param closeAfterDate the closeAfterDate to set
     */
    public void setIsCloseAfterDate(final Boolean isCloseAfterDate) {
        this.isCloseAfterDate = isCloseAfterDate;
    }

    /**
     * @return the closedDate
     */
    public String getClosedDate() {
        return closedDate;
    }

    /**
     * @param closedDate the closedDate to set
     */
    public void setClosedDate(final String closedDate) {
        this.closedDate = closedDate;
    }

    /**
     * @return the isCloseAfterQuota
     */
    public Boolean getIsCloseAfterQuota() {
        return isCloseAfterQuota;
    }

    /**
     * @param isCloseAfterQuota the isCloseAfterQuota to set
     */
    public void setIsCloseAfterQuota(final Boolean isCloseAfterQuota) {
        this.isCloseAfterQuota = isCloseAfterQuota;
    }

    /**
     * @return the closedQuota
     */
    public Integer getClosedQuota() {
        return closedQuota;
    }

    /**
     * @param closedQuota the closedQuota to set
     */
    public void setClosedQuota(final Integer closedQuota) {
        this.closedQuota = closedQuota;
    }

    /**
     * @return the isIpRestricted
     */
    public Boolean getIsIpRestricted() {
        return isIpRestricted;
    }

    /**
     * @param isIpRestricted the isIpRestricted to set
     */
    public void setIsIpRestricted(final Boolean isIpRestricted) {
        this.isIpRestricted = isIpRestricted;
    }

    /**
     * @return the ipRestricted
     */
    public String getIpRestricted() {
        return ipRestricted;
    }

    /**
     * @param ipRestricted the ipRestricted to set
     */
    public void setIpRestricted(final String ipRestricted) {
        this.ipRestricted = ipRestricted;
    }

    /**
     * @return the multipleResponse
     */
    public String getMultipleResponse() {
        return multipleResponse;
    }

    /**
     * @param multipleResponse the multipleResponse to set
     */
    public void setMultipleResponse(final String multipleResponse) {
        this.multipleResponse = multipleResponse;
    }

    /**
     * @return the isShowResults
     */
    public Boolean getIsShowResults() {
        return isShowResults;
    }
}
