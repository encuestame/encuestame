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
package org.encuestame.business.service.social.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.encuestame.business.service.social.AbstractSocialAPISupport;
import org.encuestame.core.social.FacebookAPIOperations;
import org.encuestame.core.social.FacebookLink;
import org.encuestame.core.social.FacebookProfile;
import org.encuestame.core.social.SocialUserProfile;
import org.encuestame.core.social.oauth2.ProtectedResourceClientFactory;
import org.jfree.util.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Facebook API {@link RestTemplate} support.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 22, 2011
 */
public class FacebookAPITemplate extends AbstractSocialAPISupport implements FacebookAPIOperations {

        private static final String OBJECT_URL = "https://graph.facebook.com/{objectId}";
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
        final MappingJacksonHttpMessageConverter json = new MappingJacksonHttpMessageConverter();
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

        long id = Long.valueOf(String.valueOf(profileMap.get("id")));
        String name = String.valueOf(profileMap.get("name"));
        String firstName = String.valueOf(profileMap.get("first_name"));
        String lastName = String.valueOf(profileMap.get("last_name"));
        String email = String.valueOf(profileMap.get("email"));
        String username = String.valueOf(profileMap.get("username"));
        return new FacebookProfile(id, name, firstName, lastName, email, username);
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
    public String updateStatus(String message) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.set("message", message);
        publish(CURRENT_USER_ID, FEED, map);
        return "";
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.FacebookAPIOperations#updateStatus(java.lang.String, org.encuestame.core.social.FacebookLink)
     */
    public void updateStatus(String message, FacebookLink link) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.set("link", link.getLink());
        map.set("name", link.getName());
        map.set("caption", link.getCaption());
        map.set("description", link.getDescription());
        map.set("message", message);
        publish(CURRENT_USER_ID, FEED, map);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.FacebookAPIOperations#publish(java.lang.String, java.lang.String, org.springframework.util.MultiValueMap)
     */
    public void publish(String object, String connection, MultiValueMap<String, String> data) {
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<String, String>(data);
        getRestTemplate().postForLocation(CONNECTION_URL, requestData, object, connection);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#getProfile()
     */
    @Override
    public SocialUserProfile getProfile() {
        final FacebookProfile facebookProfile = this.getUserProfile();
        Log.debug("Facebook PRofile "+facebookProfile.toString());
        final SocialUserProfile profile = new SocialUserProfile();
        profile.setEmail(facebookProfile.getEmail());
        profile.setFirstName(facebookProfile.getFirstName());
        profile.setLastName(facebookProfile.getLastName());
        profile.setId(String.valueOf(facebookProfile.getId()));
        profile.setName(facebookProfile.getName());
        profile.setUsername(facebookProfile.getUsername());
        return profile;
    }

}
