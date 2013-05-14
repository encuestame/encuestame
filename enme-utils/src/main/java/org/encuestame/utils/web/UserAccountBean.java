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
package org.encuestame.utils.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Unit Use rBean.
 * @author Picado, Juan juanATencuestame.org
 * @since 27/04/2009
 */
public class UserAccountBean implements Serializable {

    /** **/
    private static final long serialVersionUID = -4738463192806706221L;
    private Long id;
    private String email;
    private String name;
    private String username;
    private Boolean status;
    private Boolean activated;
    @JsonIgnore
    private String password;
    private String inviteCode;
    private Date dateNew;
    private String relateTimeEnjoy;
    private Long primaryUserId;
    @Deprecated
    private Collection<UnitGroupBean> listGroups;
    private Collection<UnitPermission> listPermission;
    private UnitGroupBean groupBean;
    private Long survey;
    private Long tweetPoll;
    private Long poll;
    private Long followers;
    private Date lastTimeLogged;
    private String ipLastLogged;
    private Long groupId;
    private String pictureSource;

    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public final void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the email
     */
    public final String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public final void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }
    /**
     * @return the username
     */
    public final String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public final void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the status
     */
    public final Boolean getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public final void setStatus(Boolean status) {
        this.status = status;
    }
    /**
     * @return the password
     */
    @JsonIgnore
    public final String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public final void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the inviteCode
     */
    public final String getInviteCode() {
        return inviteCode;
    }
    /**
     * @param inviteCode the inviteCode to set
     */
    public final void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
    /**
     * @return the dateNew
     */
    public final Date getDateNew() {
        return dateNew;
    }
    /**
     * @param dateNew the dateNew to set
     */
    public final void setDateNew(Date dateNew) {
        this.dateNew = dateNew;
    }

    /**
     * @return the listGroups
     */
    public final Collection<UnitGroupBean> getListGroups() {
        return listGroups;
    }
    /**
     * @param listGroups the listGroups to set
     */
    public final void setListGroups(Collection<UnitGroupBean> listGroups) {
        this.listGroups = listGroups;
    }
    /**
     * @return the listPermission
     */
    public final Collection<UnitPermission> getListPermission() {
        return listPermission;
    }
    /**
     * @param listPermission the listPermission to set
     */
    public final void setListPermission(Collection<UnitPermission> listPermission) {
        this.listPermission = listPermission;
    }
    /**
     * @return the primaryUserId
     */
    public final Long getPrimaryUserId() {
        return primaryUserId;
    }
    /**
     * @param primaryUserId the primaryUserId to set
     */
    public final void setPrimaryUserId(Long primaryUserId) {
        this.primaryUserId = primaryUserId;
    }
    /**
     * @return the groupBean
     */
    public UnitGroupBean getGroupBean() {
        return groupBean;
    }
    /**
     * @param groupBean the groupBean to set
     */
    public void setGroupBean(UnitGroupBean groupBean) {
        this.groupBean = groupBean;
    }
    /**
     * @return the survey
     */
    public Long getSurvey() {
        return survey;
    }
    /**
     * @param survey the survey to set
     */
    public void setSurvey(Long survey) {
        this.survey = survey;
    }
    /**
     * @return the tweetPoll
     */
    public Long getTweetPoll() {
        return tweetPoll;
    }
    /**
     * @param tweetPoll the tweetPoll to set
     */
    public void setTweetPoll(Long tweetPoll) {
        this.tweetPoll = tweetPoll;
    }
    /**
     * @return the poll
     */
    public Long getPoll() {
        return poll;
    }
    /**
     * @param poll the poll to set
     */
    public void setPoll(Long poll) {
        this.poll = poll;
    }
    /**
     * @return the followers
     */
    public Long getFollowers() {
        return followers;
    }
    /**
     * @param followers the followers to set
     */
    public void setFollowers(Long followers) {
        this.followers = followers;
    }
    /**
     * @return the lastTimeLogged
     */
    public Date getLastTimeLogged() {
        return lastTimeLogged;
    }
    /**
     * @param lastTimeLogged the lastTimeLogged to set
     */
    public void setLastTimeLogged(Date lastTimeLogged) {
        this.lastTimeLogged = lastTimeLogged;
    }
    /**
     * @return the ipLastLogged
     */
    public String getIpLastLogged() {
        return ipLastLogged;
    }
    /**
     * @param ipLastLogged the ipLastLogged to set
     */
    public void setIpLastLogged(String ipLastLogged) {
        this.ipLastLogged = ipLastLogged;
    }

    /**
     * @return the groupId
     */
    public Long getGroupId() {
        return groupId;
    }
    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    /**
     * @return the relateTimeEnjoy
     */
    public String getRelateTimeEnjoy() {
        return relateTimeEnjoy;
    }
    /**
     * @param relateTimeEnjoy the relateTimeEnjoy to set
     */
    public void setRelateTimeEnjoy(final String relateTimeEnjoy) {
        this.relateTimeEnjoy = relateTimeEnjoy;
    }

    /**
     * @return the pictureSource
     */
    public String getPictureSource() {
        return pictureSource;
    }
    /**
     * @param pictureSource the pictureSource to set
     */
    public void setPictureSource(final String pictureSource) {
        this.pictureSource = pictureSource;
    }

    /**
     * @return the activated
     */
    public Boolean getActivated() {
        return activated;
    }
    /**
     * @param activated the activated to set
     */
    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UnitUserBean [id=").append(id).append(", email=")
                .append(email).append(", name=").append(name)
                .append(", username=").append(username).append(", status=")
                .append(status).append(", dateNew=").append(dateNew)
                .append(", relateTimeEnjoy=").append(relateTimeEnjoy)
                .append(", primaryUserId=").append(primaryUserId)
                .append(", survey=").append(survey).append(", tweetPoll=")
                .append(tweetPoll).append(", poll=").append(poll)
                .append(", followers=").append(followers)
                .append(", lastTimeLogged=").append(lastTimeLogged)
                .append(", ipLastLogged=").append(ipLastLogged)
                .append(", groupId=").append(groupId).append("]");
        return builder.toString();
    }
}
