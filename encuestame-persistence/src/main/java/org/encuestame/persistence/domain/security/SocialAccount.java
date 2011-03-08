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
package org.encuestame.persistence.domain.security;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.social.SocialProvider;

/**
 * SecUser Twitter Acounts.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 19, 2010 7:15:32 AM
 * @version  $Id:$
 */

//@TypeDef(name="encryptedString", typeClass= EncryptedStringType.class,
//        parameters= {
///            @Parameter(name="encryptorRegisteredName",  value="strongHibernateStringEncryptor")
//        }
//    )

@Entity
@Table(name = "social_account")
public class SocialAccount {

    /**
     * Id.
     */
    private Long id;

    /**
     * {@link Account}.
     */
    private Account secUsers;

    /**
     * Social Account Name.
     */
    private String socialAccountName;

    /**
     * Social User Id.
     */
    private Long socialUserId;

    /**
     * Tokeb
     */
    private String token;

    /**
     * Secret Token.
     */
    private String secretToken;

    /**
     * Default Selected.
     */
    private Boolean defaultSelected = false;

    /**
     * Type.
     */
    private TypeAuth type = TypeAuth.OAUTH; //Twitter only accept OAuth.

    /**
     * Type Accountd.
     */
    private SocialProvider accounType = SocialProvider.TWITTER;


    /** Verfied. **/
    private Boolean verfied = false;

    /**
     */
    public enum TypeAuth {

    /**
     * OAuth.
     */
    OAUTH,
    /**
     * Password.
     * Twitter as deprecated password login from 31/10/10.
     */
    @Deprecated
    PASSWORD};

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name="sec_user_twitter_id", unique=true)
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
     * @return the secUsers
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Account getSecUsers() {
        return secUsers;
    }

    /**
     * @param secUsers the secUsers to set
     */
    public void setSecUsers(final Account secUsers) {
        this.secUsers = secUsers;
    }

    /**
     * @return the twitterAccount
     */
    @Column (name="social_account_name", nullable = false, unique = true)
    public String getSocialAccountName() {
        return socialAccountName;
    }

    /**
     * @param socialAccountName the social account name to set
     */
    public void setSocialAccountName(final String socialAccountName) {
        this.socialAccountName = socialAccountName;
    }

    /**
     * @return the socialUserId
     */
    @Column (name="social_account_id", nullable = false, unique = true)
    public Long getSocialUserId() {
        return socialUserId;
    }

    /**
     * @param socialUserId the socialUserId to set
     */
    public void setSocialUserId(Long socialUserId) {
        this.socialUserId = socialUserId;
    }

    /**
     * @return the type
     */
    @Column(name="type_auth")
    @Enumerated(EnumType.STRING)
    public TypeAuth getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final TypeAuth type) {
        this.type = type;
    }



    /**
     * @return the accounType
     */
    @Column(name="type_account")
    @Enumerated(EnumType.STRING)
    public SocialProvider getAccounType() {
        return accounType;
    }

    /**
     * @param accounType the accounType to set
     */
    public void setAccounType(final SocialProvider accounType) {
        this.accounType = accounType;
    }

    /**
     * @return the verfied
     */
    @Column (name="account_verified", nullable = true)
    public Boolean getVerfied() {
        return verfied;
    }

    /**
     * @param verfied the verfied to set
     */
    public void setVerfied(Boolean verfied) {
        this.verfied = verfied;
    }

    @Column(name = "oauth_token", nullable = true)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name = "oauth_secret_token", nullable = true)
    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    /**
     * @return the defaultSelected
     */
    @Column(name = "default_selected", nullable = true)
    public Boolean getDefaultSelected() {
        return defaultSelected;
    }

    /**
     * @param defaultSelected the defaultSelected to set
     */
    public void setDefaultSelected(Boolean defaultSelected) {
        this.defaultSelected = defaultSelected;
    }
}
