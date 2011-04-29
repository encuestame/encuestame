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
package org.encuestame.core.social;

import java.util.Date;

/**
 * Social User Profile.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 25, 2011
 */
public class SocialUserProfile {

    private String id;
    private String name;
    private String firstName;
    private String lastName;
    private String screenName;
    private String username;
    private String email;
    private String industry;
    private String headline;
    private String location;
    private String description;
    private boolean isContributorsEnabled;
    private String profileImageUrl;
    private String url;
    private String profileUrl;
    private boolean isProtected;
    private int followersCount;
    private String profileBackgroundColor;
    private String profileTextColor;
    private String profileLinkColor;
    private String profileSidebarFillColor;
    private String profileSidebarBorderColor;
    private boolean profileUseBackgroundImage;
    private boolean showAllInlineMedia;
    private int friendsCount;
    private Date createdAt;
    private int favouritesCount;
    private int utcOffset;
    private String timeZone;
    private String profileBackgroundImageUrl;
    private boolean profileBackgroundTiled;
    private String lang;
    private int statusesCount;
    private boolean isGeoEnabled;
    private boolean isVerified;
    private boolean translator;
    private int listedCount;
    private boolean isFollowRequestSent;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the screenName
     */
    public String getScreenName() {
        return screenName;
    }
    /**
     * @param screenName the screenName to set
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the industry
     */
    public String getIndustry() {
        return industry;
    }
    /**
     * @param industry the industry to set
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    /**
     * @return the headline
     */
    public String getHeadline() {
        return headline;
    }
    /**
     * @param headline the headline to set
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }
    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the isContributorsEnabled
     */
    public boolean isContributorsEnabled() {
        return isContributorsEnabled;
    }
    /**
     * @param isContributorsEnabled the isContributorsEnabled to set
     */
    public void setContributorsEnabled(boolean isContributorsEnabled) {
        this.isContributorsEnabled = isContributorsEnabled;
    }
    /**
     * @return the profileImageUrl
     */
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    /**
     * @param profileImageUrl the profileImageUrl to set
     */
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the isProtected
     */
    public boolean isProtected() {
        return isProtected;
    }
    /**
     * @param isProtected the isProtected to set
     */
    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }
    /**
     * @return the followersCount
     */
    public int getFollowersCount() {
        return followersCount;
    }
    /**
     * @param followersCount the followersCount to set
     */
    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }
    /**
     * @return the profileBackgroundColor
     */
    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }
    /**
     * @param profileBackgroundColor the profileBackgroundColor to set
     */
    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }
    /**
     * @return the profileTextColor
     */
    public String getProfileTextColor() {
        return profileTextColor;
    }
    /**
     * @param profileTextColor the profileTextColor to set
     */
    public void setProfileTextColor(String profileTextColor) {
        this.profileTextColor = profileTextColor;
    }
    /**
     * @return the profileLinkColor
     */
    public String getProfileLinkColor() {
        return profileLinkColor;
    }
    /**
     * @param profileLinkColor the profileLinkColor to set
     */
    public void setProfileLinkColor(String profileLinkColor) {
        this.profileLinkColor = profileLinkColor;
    }
    /**
     * @return the profileSidebarFillColor
     */
    public String getProfileSidebarFillColor() {
        return profileSidebarFillColor;
    }
    /**
     * @param profileSidebarFillColor the profileSidebarFillColor to set
     */
    public void setProfileSidebarFillColor(String profileSidebarFillColor) {
        this.profileSidebarFillColor = profileSidebarFillColor;
    }
    /**
     * @return the profileSidebarBorderColor
     */
    public String getProfileSidebarBorderColor() {
        return profileSidebarBorderColor;
    }
    /**
     * @param profileSidebarBorderColor the profileSidebarBorderColor to set
     */
    public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
        this.profileSidebarBorderColor = profileSidebarBorderColor;
    }
    /**
     * @return the profileUseBackgroundImage
     */
    public boolean isProfileUseBackgroundImage() {
        return profileUseBackgroundImage;
    }
    /**
     * @param profileUseBackgroundImage the profileUseBackgroundImage to set
     */
    public void setProfileUseBackgroundImage(boolean profileUseBackgroundImage) {
        this.profileUseBackgroundImage = profileUseBackgroundImage;
    }
    /**
     * @return the showAllInlineMedia
     */
    public boolean isShowAllInlineMedia() {
        return showAllInlineMedia;
    }
    /**
     * @param showAllInlineMedia the showAllInlineMedia to set
     */
    public void setShowAllInlineMedia(boolean showAllInlineMedia) {
        this.showAllInlineMedia = showAllInlineMedia;
    }
    /**
     * @return the friendsCount
     */
    public int getFriendsCount() {
        return friendsCount;
    }
    /**
     * @param friendsCount the friendsCount to set
     */
    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }
    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }
    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    /**
     * @return the favouritesCount
     */
    public int getFavouritesCount() {
        return favouritesCount;
    }
    /**
     * @param favouritesCount the favouritesCount to set
     */
    public void setFavouritesCount(int favouritesCount) {
        this.favouritesCount = favouritesCount;
    }
    /**
     * @return the utcOffset
     */
    public int getUtcOffset() {
        return utcOffset;
    }
    /**
     * @param utcOffset the utcOffset to set
     */
    public void setUtcOffset(int utcOffset) {
        this.utcOffset = utcOffset;
    }
    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }
    /**
     * @param timeZone the timeZone to set
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    /**
     * @return the profileBackgroundImageUrl
     */
    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }
    /**
     * @param profileBackgroundImageUrl the profileBackgroundImageUrl to set
     */
    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }
    /**
     * @return the profileBackgroundTiled
     */
    public boolean isProfileBackgroundTiled() {
        return profileBackgroundTiled;
    }
    /**
     * @param profileBackgroundTiled the profileBackgroundTiled to set
     */
    public void setProfileBackgroundTiled(boolean profileBackgroundTiled) {
        this.profileBackgroundTiled = profileBackgroundTiled;
    }
    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }
    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }
    /**
     * @return the statusesCount
     */
    public int getStatusesCount() {
        return statusesCount;
    }
    /**
     * @param statusesCount the statusesCount to set
     */
    public void setStatusesCount(int statusesCount) {
        this.statusesCount = statusesCount;
    }
    /**
     * @return the isGeoEnabled
     */
    public boolean isGeoEnabled() {
        return isGeoEnabled;
    }
    /**
     * @param isGeoEnabled the isGeoEnabled to set
     */
    public void setGeoEnabled(boolean isGeoEnabled) {
        this.isGeoEnabled = isGeoEnabled;
    }
    /**
     * @return the isVerified
     */
    public boolean isVerified() {
        return isVerified;
    }
    /**
     * @param isVerified the isVerified to set
     */
    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
    /**
     * @return the translator
     */
    public boolean isTranslator() {
        return translator;
    }
    /**
     * @param translator the translator to set
     */
    public void setTranslator(boolean translator) {
        this.translator = translator;
    }
    /**
     * @return the listedCount
     */
    public int getListedCount() {
        return listedCount;
    }
    /**
     * @param listedCount the listedCount to set
     */
    public void setListedCount(int listedCount) {
        this.listedCount = listedCount;
    }
    /**
     * @return the isFollowRequestSent
     */
    public boolean isFollowRequestSent() {
        return isFollowRequestSent;
    }
    /**
     * @param isFollowRequestSent the isFollowRequestSent to set
     */
    public void setFollowRequestSent(boolean isFollowRequestSent) {
        this.isFollowRequestSent = isFollowRequestSent;
    }
    /**
     * @return the profileUrl
     */
    public String getProfileUrl() {
        return profileUrl;
    }
    /**
     * @param profileUrl the profileUrl to set
     */
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }
}
