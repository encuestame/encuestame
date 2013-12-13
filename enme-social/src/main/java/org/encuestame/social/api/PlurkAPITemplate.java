package org.encuestame.social.api;

import org.encuestame.social.AbstractSocialAPISupport;
import org.encuestame.social.api.support.PlurkAPIOperations;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.social.SocialUserProfile;

public class PlurkAPITemplate extends AbstractSocialAPISupport implements PlurkAPIOperations{

    /**
    *
    */
    static final String GET_CURRENT_USER_INFO = "https://api.linkedin.com/v1/people/~:public";

    /**
    *
    */
    static final String PUT_STATUS = "http://api.linkedin.com/v1/people/~/current-status";
	
	@Override
	public SocialUserProfile getProfile() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetPublishedMetadata updateStatus(String status) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
