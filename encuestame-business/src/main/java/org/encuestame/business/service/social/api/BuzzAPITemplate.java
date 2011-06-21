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
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.encuestame.business.service.social.AbstractSocialAPISupport;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.social.BuzzAPIOperations;
import org.encuestame.core.social.BuzzProfile;
import org.encuestame.core.social.SocialUserProfile;
import org.encuestame.core.social.oauth2.ProtectedResourceClientFactory;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.utils.TweetPublishedMetadata;

/**
 * Google Buzz
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 19, 2011
 */
public class BuzzAPITemplate extends AbstractSocialAPISupport implements BuzzAPIOperations{

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());


    /**
     * Google Key.
     */
    private String GOOGLE_KEY = EnMePlaceHolderConfigurer.getProperty("google.api.key");
    private String GOOGLE_REST_UPDATE = "https://www.googleapis.com/buzz/v1/activities/@me/@self?key={key}&alt=json";
    private String GOOGLE_REST_PROFILE = "https://www.googleapis.com/buzz/v1/people/@me/@self?alt=json";
    private String GOOGLE_REST_LIKE = "https://www.googleapis.com/buzz/v1/activities/@me/@liked/{activityId}?key={key}";
    private String GOOGLE_ACTIVITIES = "https://www.googleapis.com/buzz/v1/activities/@me/@public?alt=json";


    /**
     *
     * @param socialAccount
     */
    public BuzzAPITemplate(final SocialAccount socialAccount){
        this(socialAccount.getAccessToken(), socialAccount.getApplicationKey().toString());
    }

    /**
     * Constructor.
     * @param accessToken
     */
    public BuzzAPITemplate(final String accessToken, final String googleKey) {
          setRestTemplate(ProtectedResourceClientFactory.draft10(accessToken));
          //this.GOOGLE_KEY = googleKey;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#getProfile()
     * {"data":{
     * "kind":"buzz#person","id":"xxxxxxxxxxxxxxx",
     * "displayName":"My name",
     * "aboutMe":"xxxxxxxxxxxxxxxxxxxxxxxxx",
     * "profileUrl":"https://profiles.google.com/xxxxxxxxxxxxxxx",
     * "thumbnailUrl":"https://lh5.googleusercontent.com/xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx/photo.jpg",
     * "emails":[{"value":"xxxxxxxxxx9@gmail.com","type":"","primary":true}],
     *   "urls":[{"value":"http://picasaweb.google.com/xxxxxxxxxx"},
     *           {"value":"https://profiles.google.com/xxxxxxxxxxxxx","type":"profile"},
     *           {"value":"https://www.googleapis.com/buzz/v1/people/110583664879406693886/@self?alt\u003djson","type":"json"}
     *          ],
     * "photos":[
     *           {"value":"https://lh5.googleusercontent.com/-dxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx/photo.jpg","type":"thumbnail","width":24,"height":24},
     *           {"value":"https://lh5.googleusercontent.com/-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx/photo.jpg","type":"thumbnail","width":50,"height":50}
     *          ],
     *"organizations":[
     *          {"name":"xxxxxxxxx, Managua Nicaragua","title":"Computer xxxxxxxxx","type":"school"},
     *          {"name":"xxxxxxxxxxxxxxxxxxxxxxxxxxxx","title":"Leader xxxxxxxxxxx","type":"work"}]}}"
     */
    public SocialUserProfile getProfile(){
        //TOOD: conver to BuzzProfile.
        //{data={id=null, displayName=null, kind=kined, aboutMe=abbout me, profileUrl=null, emails=[], url=[], photos=null, organizations=[]}}
        Map profileMap = getRestTemplate().getForObject(this.GOOGLE_REST_PROFILE, Map.class);
        final SocialUserProfile profile = new SocialUserProfile();
        Map data = (Map) profileMap.get("data");
        profile.setId(data.get("id").toString());
        profile.setName(data.get("displayName") == null ? "" : data.get("displayName").toString());
        profile.setProfileUrl(data.get("profileUrl") == null ? "" : data.get("profileUrl").toString());
        profile.setProfileImageUrl(data.get("thumbnailUrl") == null ? "" : data.get("thumbnailUrl").toString());
        profile.setDescription(data.get("aboutMe") == null ? "" : data.get("aboutMe").toString());
        //get list of emails.
        List emails = (ArrayList) data.get("emails");
        log.debug("email list "+emails.size());
        if (emails.size() == 0) {
            log.error("email list is emtpy");
        }
        final Map email = (Map) emails.get(0);
        log.debug("email  "+email);
        profile.setEmail(email.get("value") == null ? "" : email.get("value").toString());
        //split username.
        final String[] tokens = email.get("value").toString().split("@");
        profile.setScreenName(tokens[0]);
        profile.setUsername(tokens[0]);
        return profile;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#updateStatus(java.lang.String)
     */
    public TweetPublishedMetadata updateStatus(final String status) {
        final BuzzProfile jsonData = new BuzzProfile();
        //jsonData.getData().getObject().setType("note");
        //jsonData.getData().getObject().setComment(status);
        @SuppressWarnings("rawtypes")
        final Map response = getRestTemplate().postForObject(
                this.GOOGLE_REST_UPDATE, jsonData, Map.class, this.GOOGLE_KEY);
        return createStatus(status);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.BuzzAPIOperations#likeActivity(java.lang.String)
     */
    public void likeActivity(String id){
         getRestTemplate().put(GOOGLE_REST_LIKE, Map.class, id, this.GOOGLE_KEY);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.BuzzAPIOperations#getActivities()
     */
    public String getActivities(){
        Object f = getRestTemplate().getForObject(this.GOOGLE_ACTIVITIES, Object.class);
        log.debug(f);
        return "google";
    }
}
