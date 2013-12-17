package org.encuestame.social.api.support;

import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.utils.TweetPublishedMetadata;

public interface TumblrAPIOperations extends SocialAPIOperations{
	
    /**
     * 
     * @param status
     * @param socialAccount
     * @return
     * @throws Exception
     */
    TweetPublishedMetadata updateStatus(final String status, final SocialAccount socialAccount, final TweetPoll tweetPoll) throws Exception;	

}
