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
package org.encuestame.social.api.templates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.oauth2.support.ProtectedResourceClientFactory;
import org.encuestame.social.api.AbstractSocialAPISupport;
import org.encuestame.core.social.profile.FacebookLink;
import org.encuestame.core.social.operation.FacebookAPIOperations;
import org.encuestame.core.social.profile.FacebookProfile;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.social.SocialUserProfile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Facebook API {@link RestTemplate} support.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 22, 2011
 */
public class FacebookAPITemplate extends AbstractSocialAPISupport implements FacebookAPIOperations {

    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(FacebookAPITemplate.class);

    private static final String OBJECT_URL = "https://graph.facebook.com/{objectId}";
    private static final String PICTURE_PROFILE_URL = "https://graph.facebook.com/{objectId}/picture";
    private static final String CONNECTION_URL = OBJECT_URL + "/{connection}";
    private static final String FRIENDS = "friends";
    private static final String FEED = "feed";
    private static final String CURRENT_USER_ID = "me";

    /**
     * Create a new instance of FacebookTemplate.
     * This constructor creates the FacebookTemplate using a given access token.
     * @param accessToken An access token given by Facebook after a successful OAuth 2 authentication
     */
    public FacebookAPITemplate(String accessToken) {
        setRestTemplate(ProtectedResourceClientFactory.draft10(accessToken));
        // facebook returns JSON data with text/javascript content type
        final MappingJackson2HttpMessageConverter json = new MappingJackson2HttpMessageConverter();
        json.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "javascript")));
        getRestTemplate().getMessageConverters().add(json);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#getProfileId()
     */
    public String getProfileId() {
        return Long.toString(getUserProfile().getId());
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#getProfileUrl()
     */
    public String getProfileUrl() {
        return "http://www.facebook.com/profile.php?id=" + getProfileId();
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.FacebookAPIOperations#getUserProfile()
     */
    public FacebookProfile getUserProfile() {
        return getUserProfile(CURRENT_USER_ID);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.FacebookAPIOperations#getUserProfile(java.lang.String)
     */
    public FacebookProfile getUserProfile(String facebookId) {
        @SuppressWarnings("unchecked")
        Map<String, ?> profileMap = getRestTemplate().getForObject(OBJECT_URL, Map.class,
                facebookId);
        /*
         *  Example of single profile.
         *  {
         *  id=xxxxxxxxxxxxxxx,
         *  name=name,
         *  first_name=xxxxxxx,
         *  last_name=yyyyyyy,
         *  link=http://www.facebook.com/profile.php?id=xxxxxxxxxx,
         *  hometown={id=ccccccccccc, name=Madrid, Spain},
         *  location={id=ccccccccccc, name=Madrid, Spain},
         *  gender=male,
         *  email=xxxxxxxxxccccc@gmail.com,
         *  timezone=-6,
         *  locale=es_LA,
         *  updated_time=2011-05-04T07:01:01+0000}
         */
        if (log.isDebugEnabled()) {
            log.debug("FacebookProfile:{ "+profileMap);
        }
        long id = Long.valueOf(String.valueOf(profileMap.get("id")));
        final String name = String.valueOf(profileMap.get("name"));
        final String firstName = String.valueOf(profileMap.get("first_name"));
        final String lastName = String.valueOf(profileMap.get("last_name"));
        Assert.notNull(profileMap.get("email"));
        final String email = String.valueOf(profileMap.get("email"));
        /*
         * sometimes the username is not configured on facebook profile (eg. new profiles) in this cases
         * our username social account is required, we use unique facebook id, this is completely valid to build
         * facebook picture.
         */
        final String username = String.valueOf(profileMap.get("username") == null ? profileMap.get("id") : profileMap.get("username"));
        return new FacebookProfile(id, name, firstName, lastName, email, username);
    }

    /**
     *
     * @param facebookId
     * @return
     */
    public String getPictureProfile(String facebookId) {
        @SuppressWarnings("unchecked")
        Map<String, ?> profileMap = getRestTemplate().getForObject(PICTURE_PROFILE_URL, Map.class,
                facebookId);
        log.debug("PROFILE URL "+profileMap);
        return "";
    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.FacebookAPIOperations#getFriendIds()
     */
    public List<String> getFriendIds() {
        final ResponseEntity<Map> response = getRestTemplate().getForEntity(CONNECTION_URL, Map.class,
              CURRENT_USER_ID, FRIENDS);
        final Map<String, List<Map<String, String>>> resultsMap = response.getBody();
        final List<Map<String, String>> friends = resultsMap.get("data");
        final List<String> friendIds = new ArrayList<String>();
        for (Map<String, String> friendData : friends) {
            friendIds.add(friendData.get("id"));
        }
        return friendIds;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#updateStatus(java.lang.String)
     */
    public TweetPublishedMetadata updateStatus(final String message) {
        log.debug("facebook message to publish: "+message);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.set("message", message);
        return this.publish(CURRENT_USER_ID, FEED, map);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.FacebookAPIOperations#updateStatus(java.lang.String, org.encuestame.core.social.FacebookLink)
     */
    //TODO un-used.
    public void updateStatus(String message, FacebookLink link) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.set("link", link.getLink());
        map.set("name", link.getName());
        map.set("caption", link.getCaption());
        map.set("description", link.getDescription());
        map.set("message", message);
        this.publish(CURRENT_USER_ID, FEED, map);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.FacebookAPIOperations#publish(java.lang.String, java.lang.String, org.springframework.util.MultiValueMap)
     */
    public TweetPublishedMetadata publish(String object, String connection, MultiValueMap<String, String> data) {
        final MultiValueMap<String, String> requestData = new LinkedMultiValueMap<String, String>(data);
        log.debug("before facebookResponse:{"+requestData);
        final Map facebookResponse = getRestTemplate().postForObject(CONNECTION_URL, requestData, Map.class, object, connection);
        log.debug("facebookResponse:{"+facebookResponse);
        final TweetPublishedMetadata status = createStatus(data.get("message").toString());
        status.setTweetId(facebookResponse.get("id").toString());
        return status;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#getProfile()
     */
    @Override
    public SocialUserProfile getProfile() {
        final FacebookProfile facebookProfile = this.getUserProfile();
        //Log.debug("Facebook PRofile "+facebookProfile.toString());
        final SocialUserProfile profile = new SocialUserProfile();
        profile.setEmail(facebookProfile.getEmail());
        profile.setFirstName(facebookProfile.getFirstName());
        profile.setLastName(facebookProfile.getLastName());
        profile.setId(String.valueOf(facebookProfile.getId()));
        profile.setProfileImageUrl(PICTURE_PROFILE_URL.replace("{objectId}", facebookProfile.getUsername()));
        profile.setName(facebookProfile.getName());
        profile.setUsername(facebookProfile.getUsername());
        return profile;
    }

}
