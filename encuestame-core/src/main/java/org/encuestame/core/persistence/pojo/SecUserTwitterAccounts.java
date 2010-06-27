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
package org.encuestame.core.persistence.pojo;

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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Parameter;
import org.jasypt.hibernate.type.EncryptedStringType;

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
@Table(name = "sec_user_twitter_account")
public class SecUserTwitterAccounts {

    /**
     * Id.
     */
    private Long id;

    /**
     * {@link SecUsers}.
     */
    private SecUsers secUsers;

    /**
     * Twitter Account.
     */
    private String twitterAccount;

    /**
     * Twitter Password.
     */
    private String twitterPassword;

    /**
     *  Consumer Key.
     **/
    private String consumerKey;

    /**
     * Consumer Secret.
     **/
    private String consumerSecret;

    /** Twitter Pin.
     *
     **/
    private Integer twitterPin;

    /**
     * Type.
     */
    private TypeAuth type = TypeAuth.PASSWORD;

    /**
     */
    public enum TypeAuth {

    /**
     * OAuth.
     */
    OAUTH,
    /**
     * Password.
     */
    PASSWORD}

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
    public SecUsers getSecUsers() {
        return secUsers;
    }

    /**
     * @param secUsers the secUsers to set
     */
    public void setSecUsers(final SecUsers secUsers) {
        this.secUsers = secUsers;
    }

    /**
     * @return the twitterAccount
     */
    @Column (name="twitter_account", nullable = false)
    public String getTwitterAccount() {
        return twitterAccount;
    }

    /**
     * @param twitterAccount the twitterAccount to set
     */
    public void setTwitterAccount(final String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    /**
     * @return the twitterPassword
     */
    //@Type(type="encryptedString")
    @Column (name="twitter_password", nullable = false)
    public String getTwitterPassword() {
        return twitterPassword;
    }

    /**
     * @param twitterPassword the twitterPassword to set
     */
    public void setTwitterPassword(final String twitterPassword) {
        this.twitterPassword = twitterPassword;
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
     * @return the consumerKey
     */
    @Column(name = "twitter_consumer_key", nullable = true)
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * @param consumerKey the consumerKey to set
     */
    public void setConsumerKey(final String consumerKey) {
        this.consumerKey = consumerKey;
    }

    /**
     * @return the consumerSecret
     */
    @Column(name = "twitter_consumer_secret", nullable = true)
    public String getConsumerSecret() {
        return consumerSecret;
    }

    /**
     * @param consumerSecret the consumerSecret to set
     */
    public void setConsumerSecret(final String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    /**
     * @return the twitterPin
     */
    @Column(name = "twitter_pin", nullable = true)
    public Integer getTwitterPin() {
        return twitterPin;
    }

    /**
     * @param twitterPin the twitterPin to set
     */
    public void setTwitterPin(Integer twitterPin) {
        this.twitterPin = twitterPin;
    }
}
