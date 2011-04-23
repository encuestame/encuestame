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
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

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
@Indexed(index="SocialAccount")
public class SocialAccount extends AbstractSocial{

    /**
     * Id.
     */
    private Long id;

    /**
     * {@link Account}.
     */
    private Account account;

    /**
     * Social Account Name.
     */
    private String socialAccountName;

    /**
     * Default Selected.
     */
    private Boolean defaultSelected = false;

    /**
     * Type.
     */
    private TypeAuth type = TypeAuth.OAUTH1;


    /** Verfied. **/
    private Boolean verfied = false;

    /**
     */
    public enum TypeAuth {

    /**
     * OAuth1 protocol.
     */
    OAUTH1,

    /**
     * OAuth2 protocol
     */
    OAUTH2

    };

    /**
     * @return the id
     */
    @Id
    @DocumentId
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
     * @return the account
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    public Account getAccount() {
        return account;
    }

    /**
     * @param secUsers the account to set
     */
    public void setAccount(final Account account) {
        this.account = account;
    }

    /**
     * @return the twitterAccount
     */
    @Field(index = Index.TOKENIZED, store = Store.YES)
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
