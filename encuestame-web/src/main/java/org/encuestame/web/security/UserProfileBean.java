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
package org.encuestame.web.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.encuestame.core.security.spring.EnMeUserDetails;
import org.encuestame.web.beans.MasterBean;

/**
 * User Profile Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 20, 2010 2:48:24 PM
 * @version $Id:$
 */
public class UserProfileBean extends MasterBean {

    private List<String> groups = new ArrayList<String>();

    private String username;

    private String email;

    private EnMeUserDetails details;

    private String gravatarImageUrl;

    private Date lastDateLogged;

    public UserProfileBean() {
        this.details = getSecurityDetails();
    }

    /**
     * @return the groups
     */
    public List<String> getGroups() {
        return groups;
    }

    /**
     * @param groups
     *            the groups to set
     */
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the lastDateLogged
     */
    public Date getLastDateLogged() {
        return lastDateLogged;
    }

    /**
     * @param lastDateLogged
     *            the lastDateLogged to set
     */
    public void setLastDateLogged(Date lastDateLogged) {
        this.lastDateLogged = lastDateLogged;
    }

    /**
     * @return the details
     */
    public EnMeUserDetails getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(final EnMeUserDetails details) {
        this.details = details;
    }

    /**
     * @return the gravatarImageUrl
     */
    public String getGravatarImageUrl() {
        if(gravatarImageUrl == null){
            this.gravatarImageUrl = getGravatar(getDetails().getUserEmail(), MasterBean.GRAVATAR_SIZE);
        }
        return gravatarImageUrl;
    }

    /**
     * @param gravatarImageUrl the gravatarImageUrl to set
     */
    public void setGravatarImageUrl(final String gravatarImageUrl) {
        this.gravatarImageUrl = gravatarImageUrl;
    }
}
