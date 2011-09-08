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
package org.encuestame.utils.json;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.encuestame.utils.web.AbstractUnitSurvey;

/**
 * Home Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since September 06, 2011
 */
public class HomeBean extends AbstractUnitSurvey implements Serializable {

    /** Serial. **/
    private static final long serialVersionUID = 2543644253906482885L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "question")
    private QuestionBean questionBean = new QuestionBean();

    @JsonProperty(value = "userId")
    private Long userId;

    @JsonProperty(value = "ownerUsername")
    private String ownerUsername;

    @JsonProperty(value = "createDate")
    private String createDate;

    @JsonProperty(value = "relativeTime")
    private String relativeTime;

    @JsonProperty(value = "totalVotes")
    private Long totalVotes;

    @JsonProperty(value = "itemType")
    private String itemType;

    @JsonProperty(value = "createdDateAt")
    private Date createdDateAt;

    /**
     * @return the id
     */
    @JsonIgnore
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the questionBean
     */
    @JsonIgnore
    public QuestionBean getQuestionBean() {
        return questionBean;
    }

    /**
     * @param questionBean the questionBean to set
     */
    public void setQuestionBean(final QuestionBean questionBean) {
        this.questionBean = questionBean;
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
     * @return the createdDateAt
     */
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
