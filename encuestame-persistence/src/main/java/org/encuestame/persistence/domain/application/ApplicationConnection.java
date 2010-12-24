/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.security.UserAccount;

/**
 * Details about application register by user.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 12:27:46 PM
 * @version $Id:$
 */
@Entity
@Table(name = "application_connection")
public class ApplicationConnection {

    private Long connectionId;

    private String accessToken;

    private String secret;

    private String apiKey;

    private Application application;

    private UserAccount account;


    /**
     * @return the connectionId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "connection_id", unique = true, nullable = false)
    public Long getConnectionId() {
        return connectionId;
    }

    /**
     * @param connectionId the connectionId to set
     */
    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * @return the accessToken
     */
    @Column(name = "access_token")
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the secret
     */
    @Column(name = "secret")
    public String getSecret() {
        return secret;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(final String secret) {
        this.secret = secret;
    }

    /**
     * @return the application
     */
    @ManyToOne()
    public Application getApplication() {
        return application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication(final Application application) {
        this.application = application;
    }

    /**
     * @return the account
     */
    @ManyToOne()
    public UserAccount getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(final UserAccount account) {
        this.account = account;
    }

    /**
     * @return the apiKey
     */
    @Column(name = "api_key")
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }
}
