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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Client Domain.
 * @author Picado, Juan juanATencuestame.org
 * @since January 24, 2010
 * @version $Id$
 */

@Entity
@Table(name = "client")
public class Client {

    private Long clientId;
    private String clientName;
    private String clientTelephone;
    private String clientEmail;
    private String clientFax;
    private String clientUrl;
    private String clientTwitter;
    private String clientFacebook;
    private String clientDescription; 

    /**
     * @return the clientId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id", unique = true, nullable = false)
    public Long getClientId() {
        return clientId;
    }

    /**
     * @return the clientName
     */
    @Column(name = "name", nullable = false)
    public String getClientName() {
        return clientName;
    }

    /**
     * @return the clientTelephone
     */
    @Column(name = "telephone", nullable = true)
    public String getClientTelephone() {
        return clientTelephone;
    }

    /**
     * @return the clientEmail
     */
    @Column(name = "email", nullable = true)
    public String getClientEmail() {
        return clientEmail;
    }

    /**
     * @return the clientFax
     */
    @Column(name = "fax", nullable = true)
    public String getClientFax() {
        return clientFax;
    }

    /**
     * @return the clientUrl
     */
    @Column(name = "url", nullable = true)
    public String getClientUrl() {
        return clientUrl;
    }

    /**
     * @return the clientTwitter
     */
    @Column(name = "twitter", nullable = true)
    public String getClientTwitter() {
        return clientTwitter;
    }

    /**
     * @return the clientFacebook
     */
    @Column(name = "facebook", nullable = true)
    public String getClientFacebook() {
        return clientFacebook;
    }

    /**
     * @return the clientDescription
     */
    @Column(name = "description", nullable = true)
    public String getClientDescription() {
        return clientDescription;
    }

    /**
     * @param clientId
     *            the clientId to set
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * @param clientName
     *            the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @param clientTelephone
     *            the clientTelephone to set
     */
    public void setClientTelephone(String clientTelephone) {
        this.clientTelephone = clientTelephone;
    }

    /**
     * @param clientEmail
     *            the clientEmail to set
     */
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    /**
     * @param clientFax
     *            the clientFax to set
     */
    public void setClientFax(String clientFax) {
        this.clientFax = clientFax;
    }

    /**
     * @param clientUrl
     *            the clientUrl to set
     */
    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    /**
     * @param clientTwitter
     *            the clientTwitter to set
     */
    public void setClientTwitter(String clientTwitter) {
        this.clientTwitter = clientTwitter;
    }

    /**
     * @param clientFacebook
     *            the clientFacebook to set
     */
    public void setClientFacebook(String clientFacebook) {
        this.clientFacebook = clientFacebook;
    }

    /**
     * @param clientDescription
     *            the clientDescription to set
     */
    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    } 
}
