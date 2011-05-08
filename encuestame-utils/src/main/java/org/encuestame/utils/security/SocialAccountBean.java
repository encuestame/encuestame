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
package org.encuestame.utils.security;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.encuestame.utils.JsonDateDeserializer;

/**
 * Social Account Bean.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 26, 2010 5:15:12 PM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialAccountBean implements Serializable {

    /**
     * Serial.
     */
    public static final long serialVersionUID = 4250537795415299836L;

    @JsonProperty(value = "id")
    public Long accountId;

    @JsonProperty(value = "account")
    public String account;

    @JsonProperty(value = "type")
    public String type;

    @JsonProperty(value = "type_account")
    public String typeAccount;

    @JsonProperty(value = "description")
    public String  descriptionProfile;

    @JsonProperty(value = "email")
    public String  email;

    @JsonProperty(value = "default_selected")
    public Boolean  defaultSelected;

    @JsonProperty(value = "date_created")
    @JsonDeserialize(using = JsonDateDeserializer.class)
    public Date addedAccount;

    @JsonProperty(value = "picture_url")
    public String prictureUrl;

    @JsonProperty(value = "profile_picture_url")
    public String  profilePictureUrl;

    @JsonProperty(value = "profile_thumbnail_picture")
    public String  profileThumbnailPictureUrl;

    @JsonProperty(value = "real_name")
    public String realName;

    @JsonProperty(value = "social_username")
    public String socialAccountName;


    public SocialAccountBean() {
    }


    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    /**
     * @param account the account to set
     */
    public void setAccount(String account) {
        this.account = account;
    }


    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * @param typeAccount the typeAccount to set
     */
    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }


    /**
     * @param descriptionProfile the descriptionProfile to set
     */
    public void setDescriptionProfile(String descriptionProfile) {
        this.descriptionProfile = descriptionProfile;
    }


    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * @param defaultSelected the defaultSelected to set
     */
    public void setDefaultSelected(Boolean defaultSelected) {
        this.defaultSelected = defaultSelected;
    }


    /**
     * @param addedAccount the addedAccount to set
     */
    public void setAddedAccount(Date addedAccount) {
        this.addedAccount = addedAccount;
    }


    /**
     * @param prictureUrl the prictureUrl to set
     */
    public void setPrictureUrl(String prictureUrl) {
        this.prictureUrl = prictureUrl;
    }


    /**
     * @param profilePictureUrl the profilePictureUrl to set
     */
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }


    /**
     * @param profileThumbnailPictureUrl the profileThumbnailPictureUrl to set
     */
    public void setProfileThumbnailPictureUrl(String profileThumbnailPictureUrl) {
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
    public void setSocialAccountName(String socialAccountName) {
        this.socialAccountName = socialAccountName;
    }




}
