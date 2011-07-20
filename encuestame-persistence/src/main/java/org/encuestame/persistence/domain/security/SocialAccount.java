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
package org.encuestame.persistence.domain.security;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Social Account.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 19, 2010 7:15:32 AM
 */

//@TypeDef(name="encryptedString", typeClass= EncryptedStringType.class,
//        parameters= {
///            @Parameter(name="encryptorRegisteredName",  value="strongHibernateStringEncryptor")
//        }
//    )

@Entity
@Table(name = "social_account")
@Indexed(index="SocialAccount")
public class SocialAccount extends AbstractSocial {

    /**
     * Id.
     */
    private Long id;

    /**
     * {@link Account}.
     */
    private Account account;

    /**
     * Reference to User Account.
     */
    private UserAccount userOwner;

    /**
     * Social Account Name.
     */
    private String socialAccountName;

    /**
     * Default Selected.
     */
    private Boolean defaultSelected = false;

    /**
     * Url profile account.
     */
    private String profileUrl;

    /**
     * Real Name Profile.
     */
    private String realName;

    /**
     * Added account.
     */
    private Date addedAccount;

    /**
     * Last date system upgrade credentials.
     */
    private Date upgradedCredentials;

    /**
     * Description Profile.
     */
    private String descriptionProfile;

    /**
     * Social account email associated.
     */
    private String email;

    /**
     * Profile for thumbnail url.
     */
    private String profileThumbnailPictureUrl;

    /**
     * Url for profile picture.
     */
    private String prictureUrl;

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
    @Column (name="social_account_id", unique=true)
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
    @Column (name="social_account_name", nullable = false, unique=true)
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
    public void setDefaultSelected(final Boolean defaultSelected) {
        this.defaultSelected = defaultSelected;
    }

    /**
     * @return the profileUrl
     */
    @Column(name = "profile_url")
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * @param profileUrl the profileUrl to set
     */
    public void setProfileUrl(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     * @return the realName
     */
    @Column(name = "real_name")
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName the realName to set
     */
    public void setRealName(final String realName) {
        this.realName = realName;
    }

    /**
     * @return the addedAccount
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "added_account_date", nullable = false)
    public Date getAddedAccount() {
        return addedAccount;
    }

    /**
     * @param addedAccount the addedAccount to set
     */
    public void setAddedAccount(final Date addedAccount) {
        this.addedAccount = addedAccount;
    }

    /**
     * @return the descriptionProfile
     */
    @Column(name = "description_profile")
    public String getDescriptionProfile() {
        return descriptionProfile;
    }

    /**
     * @param descriptionProfile the descriptionProfile to set
     */
    public void setDescriptionProfile(final String descriptionProfile) {
        this.descriptionProfile = descriptionProfile;
    }

    /**
     * @return the email
     */
    @Column(name = "social_account_email")
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the upgradedCredentials
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upgraded_credentials_last_date", nullable = false)
    public Date getUpgradedCredentials() {
        return upgradedCredentials;
    }

    /**
     * @param upgradedCredentials the upgradedCredentials to set
     */
    public void setUpgradedCredentials(final Date upgradedCredentials) {
        this.upgradedCredentials = upgradedCredentials;
    }

    /**
     * @return the profileThumbailPictureUrl
     */
    @Column(name = "picture_thumbnail_url")
    public String getProfileThumbnailPictureUrl() {
        return profileThumbnailPictureUrl;
    }

    /**
     * @param profileThumbailPictureUrl the profileThumbailPictureUrl to set
     */
    public void setProfileThumbnailPictureUrl(final String profileThumbnailPictureUrl) {
        this.profileThumbnailPictureUrl = profileThumbnailPictureUrl;
    }

    /**
     * @return the prictureUrl
     */
    @Column(name = "picture_url")
    public String getPrictureUrl() {
        return prictureUrl;
    }

    /**
     * @param prictureUrl the prictureUrl to set
     */
    public void setPrictureUrl(final String prictureUrl) {
        this.prictureUrl = prictureUrl;
    }

    /**
     * @return the userAccout
     */
    @ManyToOne()
    public UserAccount getUserOwner() {
        return userOwner;
    }

    /**
     * @param userAccout the userAccout to set
     */
    public void setUserOwner(final UserAccount userOwnser) {
        this.userOwner = userOwnser;
    }
}