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
package org.encuestame.business.search;

import java.util.Date;

/**
 * Attachment Index Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Apr 5, 2011
 */
public class AttachmentIndex {

    /** Attachment Id.**/
    private Long documentId;

    /** File Text Content**/
    private String content;

    /**File name**/
    private String filename;

    /**File path**/
    private String filepath;

    /** Upload Date Attachment. **/
    private Date uploadDate;

    /** Title attachment **/
    private String Title;

    /** Author attachment file. **/
    private String author;

    /** Modification attachment date. **/
    private Date modificationDate;

    /** Producer attachment. **/
    private String producer;

    /** Document type**/
    private String documentType;

    /****/
    private String subject;

    /**
    * @return the documentId
    */
    public Long getDocumentId() {
        return documentId;
    }

    /**
    * @param documentId the documentId to set
    */
    public void setDocumentId(final Long documentId) {
        this.documentId = documentId;
    }

    /**
    * @return the content
    */
    public String getContent() {
        return content;
    }

    /**
    * @param content the content to set
    */
    public void setContent(final String content) {
        this.content = content;
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
    * @return the filepath
    */
    public String getFilepath() {
        return filepath;
    }

    /**
    * @param filepath the filepath to set
    */
    public void setFilepath(final String filepath) {
        this.filepath = filepath;
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
    * @return the title
    */
    public String getTitle() {
        return Title;
    }

    /**
    * @param title the title to set
    */
    public void setTitle(final String title) {
        Title = title;
    }

    /**
    * @return the author
    */
    public String getAuthor() {
        return author;
    }

    /**
    * @param author the author to set
    */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
    * @return the modificationDate
    */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
    * @param modificationDate the modificationDate to set
    */
    public void setModificationDate(final Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
    * @return the producer
    */
    public String getProducer() {
        return producer;
    }

    /**
    * @param producer the producer to set
    */
    public void setProducer(final String producer) {
        this.producer = producer;
    }

    /**
    * @return the documentType
    */
    public String getDocumentType() {
        return documentType;
    }

    /**
    * @param documentType the documentType to set
    */
    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }

    /**
    * @return the subject
    */
    public String getSubject() {
        return subject;
    }

    /**
    * @param subject the subject to set
    */
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
