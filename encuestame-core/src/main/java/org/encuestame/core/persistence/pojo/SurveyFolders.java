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
 * Survey Folders.
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since August 10, 2010
 * @version $Id: $
 */
@Entity
@Table(name = "survey_folders")
public class SurveyFolders {

    /****/
    private Long surveyFolderId;

    /****/
    private Surveys survey;

    /****/
    private String folderName;

    /****/
    private SecUser users;

    /****/
    private Date createdAt;

    /**
     * @return the surveyFolderId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "survey_folderId", unique = true, nullable = false)
    public Long getSurveyFolderId() {
        return surveyFolderId;
    }

    /**
     * @param surveyFolderId the surveyFolderId to set
     */
    public void setSurveyFolderId(Long surveyFolderId) {
        this.surveyFolderId = surveyFolderId;
    }

    /**
     * @return the survey
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sid", nullable = false)
    public Surveys getSurvey() {
        return survey;
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(Surveys survey) {
        this.survey = survey;
    }

    /**
     * @return the folderName
     */
    @Column(name = "folderName", unique = true, nullable = false)
    public String getFolderName() {
        return folderName;
    }

    /**
     * @param folderName the folderName to set
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    /**
     * @return the users
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "uid", nullable = false)
    public SecUser getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(SecUser users) {
        this.users = users;
    }

    /**
     * @return the createdAt
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", length = 0)
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


}
