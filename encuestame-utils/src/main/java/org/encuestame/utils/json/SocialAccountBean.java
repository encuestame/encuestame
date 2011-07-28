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
package org.encuestame.utils.json;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * Social Account Bean.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 26, 2010 5:15:12 PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialAccountBean extends GlobalStats implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = 4250537795415299836L;

    @JsonProperty(value = "id")
    private Long accountId;

    @JsonProperty(value = "account")
    private String account;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "type_account")
    private String typeAccount;

    @JsonProperty(value = "description")
    private String  descriptionProfile;

    @JsonProperty(value = "email")
    private String  email;

    @JsonProperty(value = "default_selected")
    private Boolean  defaultSelected;

    @JsonProperty(value = "date_created")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date addedAccount;

    @JsonProperty(value = "picture_url")
    private String prictureUrl;

    @JsonProperty(value = "profile_picture_url")
    private String  profilePictureUrl;

    @JsonProperty(value = "profile_thumbnail_picture")
    private String  profileThumbnailPictureUrl;

    @JsonProperty(value = "real_name")
    private String realName;

    @JsonProperty(value = "social_username")
    private String socialAccountName;

    @JsonProperty(value = "url")
    private String socialProfileUrl;

    /**
     * Constructor.
     */
    public SocialAccountBean() {
    }


    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }


    /**
     * @param account the account to set
     */
    public void setAccount(final String account) {
        this.account = account;
    }


    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }


    /**
     * @param typeAccount the typeAccount to set
     */
    public void setTypeAccount(final String typeAccount) {
        this.typeAccount = typeAccount;
    }


    /**
     * @param descriptionProfile the descriptionProfile to set
     */
    public void setDescriptionProfile(final String descriptionProfile) {
        this.descriptionProfile = descriptionProfile;
    }


    /**
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }


    /**
     * @param defaultSelected the defaultSelected to set
     */
    public void setDefaultSelected(final Boolean defaultSelected) {
        this.defaultSelected = defaultSelected;
    }


    /**
     * @param addedAccount the addedAccount to set
     */
    public void setAddedAccount(final Date addedAccount) {
        this.addedAccount = addedAccount;
    }


    /**
     * @param prictureUrl the prictureUrl to set
     */
    public void setPrictureUrl(final String prictureUrl) {
        this.prictureUrl = prictureUrl;
    }


    /**
     * @param profilePictureUrl the profilePictureUrl to set
     */
    public void setProfilePictureUrl(final String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }


    /**
     * @param profileThumbnailPictureUrl the profileThumbnailPictureUrl to set
     */
    public void setProfileThumbnailPictureUrl(final String profileThumbnailPictureUrl) {
        this.profileThumbnailPictureUrl = profileThumbnailPictureUrl;
    }


    /**
     * @param realName the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }


    /**
     * @param socialAccountName the socialAccountName to set
     */
    public void setSocialAccountName(final String socialAccountName) {
        this.socialAccountName = socialAccountName;
    }


    /**
     * @return the accountId
     */
    @JsonIgnore
    public Long getAccountId() {
        return accountId;
    }


    /**
     * @return the account
     */
    @JsonIgnore
    public String getAccount() {
        return account;
    }


    /**
     * @return the type
     */
    @JsonIgnore
    public String getType() {
        return type;
    }


    /**
     * @return the typeAccount
     */
    @JsonIgnore
    public String getTypeAccount() {
        return typeAccount;
    }


    /**
     * @return the descriptionProfile
     */
     @JsonIgnore
    public String getDescriptionProfile() {
        return descriptionProfile;
    }


    /**
     * @return the email
     */
    @JsonIgnore
    public String getEmail() {
        return email;
    }


    /**
     * @return the defaultSelected
     */
    @JsonIgnore
    public Boolean getDefaultSelected() {
        return defaultSelected;
    }


    /**
     * @return the addedAccount
     */
    @JsonIgnore
    public Date getAddedAccount() {
        return addedAccount;
    }


    /**
     * @return the prictureUrl
     */
    @JsonIgnore
    public String getPrictureUrl() {
        return prictureUrl;
    }


    /**
     * @return the profilePictureUrl
     */
    @JsonIgnore
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }


    /**
     * @return the profileThumbnailPictureUrl
     */
    @JsonIgnore
    public String getProfileThumbnailPictureUrl() {
        return profileThumbnailPictureUrl;
    }


    /**
     * @return the realName
     */
    @JsonIgnore
    public String getRealName() {
        return realName;
    }


    /**
     * @return the socialAccountName
     */
    @JsonIgnore
    public String getSocialAccountName() {
        return socialAccountName;
    }


    /**
     * @return the socialProfileUrl
     */
    public String getSocialProfileUrl() {
        return socialProfileUrl;
    }


    /**
     * @param socialProfileUrl the socialProfileUrl to set
     */
    public void setSocialProfileUrl(final String socialProfileUrl) {
        this.socialProfileUrl = socialProfileUrl;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SocialAccountBean [accountId=" + accountId + ", account="
                + account + ", type=" + type + ", typeAccount=" + typeAccount
                + ", descriptionProfile=" + descriptionProfile + ", email="
                + email + ", defaultSelected=" + defaultSelected
                + ", addedAccount=" + addedAccount + ", prictureUrl="
                + prictureUrl + ", profilePictureUrl=" + profilePictureUrl
                + ", profileThumbnailPictureUrl=" + profileThumbnailPictureUrl
                + ", realName=" + realName + ", socialAccountName="
                + socialAccountName + ", socialProfileUrl=" + socialProfileUrl
                + "]";
    }
}
