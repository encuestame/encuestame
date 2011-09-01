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

import java.io.File;
import java.util.Date;

/**
 * Unit attachment Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 7, 2011
 */
public class UnitAttachment {

    /** Attachment Id. **/
    private Long attachmentId;

    /** Attachment file name. **/
    private String filename;

    /** Attachment file. **/
    private File file;

    /** Attachment upload date. **/
    private Date uploadDate;

    /** {@link UnitProjectBean} **/
    private UnitProjectBean projectBean;

    /**
    * @return the attachmentId
    */
    public Long getAttachmentId() {
        return attachmentId;
    }

    /**
    * @param attachmentId the attachmentId to set
    */
    public void setAttachmentId(final Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    /**
    * @return the filename
    */
    public String getFilename() {
        return filename;
    }

    /**
    * @param filename the filename to set
    */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
    * @return the file
    */
    public File getFile() {
        return file;
    }

    /**
    * @param file the file to set
    */
    public void setFile(final File file) {
        this.file = file;
    }

    /**
    * @return the uploadDate
    */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
    * @param uploadDate the uploadDate to set
    */
    public void setUploadDate(final Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
    * @return the projectBean
    */
    public UnitProjectBean getProjectBean() {
        return projectBean;
    }

    /**
    * @param projectBean the projectBean to set
    */
    public void setProjectBean(final UnitProjectBean projectBean) {
        this.projectBean = projectBean;
    }
}
