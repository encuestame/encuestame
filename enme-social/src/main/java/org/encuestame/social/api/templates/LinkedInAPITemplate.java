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

import java.util.List;

import org.encuestame.social.AbstractSocialAPISupport;
import org.encuestame.social.api.operation.LinkedInAPIOperations;
import org.encuestame.social.api.operation.LinkedInConnections;
import org.encuestame.social.api.profile.LinkedInProfile;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.social.SocialUserProfile;

/**
 * LinkedIn API template.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public class LinkedInAPITemplate extends AbstractSocialAPISupport implements LinkedInAPIOperations {

    /**
    *
    */
    static final String GET_CURRENT_USER_INFO = "https://api.linkedin.com/v1/people/~:public";

    /**
    *
    */
    static final String PUT_STATUS = "http://api.linkedin.com/v1/people/~/current-status";

    /**
     *
     * @param apiKey
     * @param apiSecret
     * @param accessToken
     * @param accessTokenSecret
     */
    public LinkedInAPITemplate(
            String apiKey,
            String apiSecret,
            String accessToken, String accessTokenSecret) {
        setRestTemplate(org.encuestame.oauth1.support.ProtectedResourceClientFactory
                .create(apiKey, apiSecret, accessToken, accessTokenSecret));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#getProfileId()
     */
    public String getProfileId() {
        return getUserProfile().getId();
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.SocialAPIOperations#getProfileUrl()
     */
    public String getProfileUrl() {
        return getUserProfile().getPublicProfileUrl();
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.social.LinkedInAPIOperations#getUserProfile()
     */
    public LinkedInProfile getUserProfile() {
        return getRestTemplate().getForObject(GET_CURRENT_USER_INFO,
                LinkedInProfile.class);
    }

    /**
     *
     * @param status
     * @param twitter
     */
    public TweetPublishedMetadata updateStatus(final String status, final Boolean twitter) {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><current-status>"
                + status + "</current-status>";
        final StringBuffer url = new StringBuffer(PUT_STATUS);
        if (twitter) {
            url.append("?twitter-post=true");
        }
        getRestTemplate().put(url.toString(), xml);
        return createStatus(status);
    }

    /**
     * Update Status.
     */
    public TweetPublishedMetadata updateStatus(final String status) {
        return this.updateStatus(status, false);
    }

    /**
     *
     * @return
     */
    public List<LinkedInProfile> getConnections() {
        LinkedInConnections connections = getRestTemplate().getForObject(
                "https://api.linkedin.com/v1/people/~/connections",
                LinkedInConnections.class);
        return connections.getConnections();
    }

    /**
     *
     */
    @Override
    public SocialUserProfile getProfile() throws Exception {
        final SocialUserProfile profile = new SocialUserProfile();
        final LinkedInProfile inProfile = getUserProfile();
        profile.setFirstName(inProfile.getFirstName());
        profile.setId(inProfile.getId());
        profile.setHeadline(inProfile.getHeadline());
        profile.setIndustry(inProfile.getIndustry());
        profile.setLastName(inProfile.getLastName());
        profile.setProfileUrl(inProfile.getPublicProfileUrl());
        profile.setProfileImageUrl(inProfile.getPictureUrl());
        profile.setUrl(inProfile.getStandardProfileUrl());
        profile.setUsername(inProfile.getProfileUrl()); //TODO: linkedIn provide username?
        return profile;
    }
}