package org.encuestame.core.social.operation;

import java.util.Set;

import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.utils.TweetPublishedMetadata;

public interface TumblrAPIOperations extends SocialAPIOperations{
	
    /**
     * 
     * @param status
     * @param socialAccount
     * @return
     * @throws Exception
     */
    TweetPublishedMetadata updateStatus(final String status, final SocialAccount socialAccount,  final Set<HashTag> hashtags) throws Exception;	

}
