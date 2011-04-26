package org.encuestame.business.service.social.api;

import java.util.List;

import org.encuestame.business.service.social.AbstractSocialAPISupport;
import org.encuestame.core.social.LinkedInAPIOperations;
import org.encuestame.core.social.LinkedInConnections;
import org.encuestame.core.social.LinkedInProfile;
import org.encuestame.core.social.SocialUserProfile;

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
    public LinkedInAPITemplate(String apiKey, String apiSecret,
            String accessToken, String accessTokenSecret) {
        setRestTemplate(org.encuestame.core.social.oauth1.ProtectedResourceClientFactory
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
    public String updateStatus(final String status, final Boolean twitter) {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><current-status>"
                + status + "</current-status>";
        final StringBuffer url = new StringBuffer(PUT_STATUS);
        if (twitter) {
            url.append("?twitter-post=true");
        }
        getRestTemplate().put(url.toString(), xml);
        return "";
    }

    /**
     * Update Status.
     */
    public String updateStatus(final String status) {
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
        LinkedInProfile inProfile = new LinkedInProfile();
        profile.setFirstName(inProfile.getFirstName());
        profile.setId(inProfile.getId());
        profile.setHeadline(inProfile.getHeadline());
        profile.setIndustry(inProfile.getIndustry());
        profile.setLastName(inProfile.getLastName());
        profile.setProfileUrl(inProfile.getPublicProfileUrl());
        profile.setUrl(inProfile.getStandardProfileUrl());
        return profile;
    }
}