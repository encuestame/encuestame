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
package org.encuestame.persistence.domain;

import java.util.Calendar;
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
 * Attachment.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 28, 2011
 */
@Entity
@Table(name = "attachment")
public class Attachment {

    /** Attachment id**/
    private Long attachmentId;

    /** Filename**/
    private String filename;

    /** Attachment upload date. **/
    private Date uploadDate = Calendar.getInstance().getTime();

    /** Project. **/
   // private Project projectAttachment ;

    /**
    * @return the attachmentId.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attachment_id", unique = true, nullable = false)
    public Long getAttachmentId() {
        return attachmentId;
    }

    /**
    * @param attachmentId the attachmentId to set.
    */
    public void setAttachmentId(final Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    /**
     * @return the filename.
     */
    @Column(name = "filename", nullable = false)
    public String getFilename() {
        return filename;
    }

    /**
    * @param filename the filename to set.
    */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
    * @return the uploadDate
    */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploadDate", nullable = true)
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
    * @param uploadDate the uploadDate to set.
    */
    public void setUploadDate(final Date uploadDate) {
        this.uploadDate = uploadDate;
    }

//    /**
//    * @return the projectAttachment
//    */
//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "project_id", nullable = false)
//    public Project getProjectAttachment() {
//        return projectAttachment;
//    }
//
//    /**
//    * @param projectAttachment the projectAttachment to set
//    */
//    public void setProjectAttachment(final Project projectAttachment) {
//        this.projectAttachment = projectAttachment;
//    }
 }