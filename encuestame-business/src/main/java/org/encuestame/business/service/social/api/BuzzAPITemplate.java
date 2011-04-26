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

import java.util.Map;

import org.encuestame.business.service.social.AbstractSocialAPISupport;
import org.encuestame.core.social.BuzzAPIOperations;
import org.encuestame.core.social.Data;
import org.encuestame.core.social.oauth2.ProtectedResourceClientFactory;
import org.encuestame.persistence.domain.security.SocialAccount;

/**
 * Google Buzz
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 19, 2011
 */
public class BuzzAPITemplate extends AbstractSocialAPISupport implements BuzzAPIOperations{

    /**
     * Google Key.
     */
    private String GOOGLE_KEY;
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
          this.GOOGLE_KEY = googleKey;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#getProfile()
     */
    public String getProfile(){
        //TOOD: conver to BuzzProfile.
        Object profileMap = getRestTemplate().getForObject(this.GOOGLE_REST_PROFILE, Object.class);
        System.out.println(profileMap);
        return "";
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#updateStatus(java.lang.String)
     */
    public String updateStatus(final String status) {
        final Data jsonData = new Data();
        jsonData.getData().getObject().setType("note");
        jsonData.getData().getObject().setComment(status);
        @SuppressWarnings("rawtypes")
        final Map response = getRestTemplate().postForObject(
                this.GOOGLE_REST_UPDATE, jsonData, Map.class, this.GOOGLE_KEY);
        return response.toString();
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
        System.out.println(f);
        return "google";
    }

    @Override
    public String getProfileId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProfileUrl() {
        // TODO Auto-generated method stub
        return null;
    }
}
