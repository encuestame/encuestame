package org.encuestame.social.api;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.encuestame.persistence.domain.HashTag;
import org.encuestame.social.AbstractSocialAPISupport;
import org.encuestame.social.api.support.PlurkAPIOperations;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.social.TubmlrUserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class PlurkAPITemplate extends AbstractSocialAPISupport implements PlurkAPIOperations{

    /**
    *
    */
    static final String GET_CURRENT_USER_INFO = "http://www.plurk.com/APP/Users/me";

    /**
    *
    */
    static final String PUT_STATUS = "http://www.plurk.com/APP/Timeline/plurkAdd";
	
    /**
    *
    * @param apiKey
    * @param apiSecret
    * @param accessToken
    * @param accessTokenSecret
    */
   public PlurkAPITemplate(
           String apiKey,
           String apiSecret,
           String accessToken, String accessTokenSecret) {
       setRestTemplate(org.encuestame.oauth1.support.ProtectedResourceClientFactory
               .create(apiKey, apiSecret, accessToken, accessTokenSecret));
   }    
    
   /*
    * (non-Javadoc)
    * @see org.encuestame.social.api.support.SocialAPIOperations#getProfile()
    */
	@Override
	public SocialUserProfile getProfile() throws Exception {
		Map<?, ?> response = getRestTemplate().getForObject(GET_CURRENT_USER_INFO, Map.class);
		//{"display_name": "amix3", "gender": 0, "nick_name": "amix", "has_profile_image": 1, "id": 1, "avatar": null}
		final SocialUserProfile socialUserProfile = new SocialUserProfile();
		socialUserProfile.setFollowersCount(0);
		socialUserProfile.setUsername(response.get("nick_name").toString());
		socialUserProfile.setId(response.get("id").toString());
		//get the primary blog
		socialUserProfile.setEmail(null);
		socialUserProfile.setCreatedAt(Calendar.getInstance().getTime());
		socialUserProfile.setName(response.get("display_name").toString());
		socialUserProfile.setUrl("http://www.plurk.com/" + socialUserProfile.getUsername());
		socialUserProfile.setProfileImageUrl("http://avatars.plurk.com/{user_id}-small.gif".replace("{user_id}", socialUserProfile.getId()));
		socialUserProfile.setScreenName(response.get("display_name").toString());
		socialUserProfile.setLocation("");
		socialUserProfile.setDescription("");
		return socialUserProfile;
	}

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.social.api.support.SocialAPIOperations#updateStatus(java.lang.String)
	 */
	@Override
	public TweetPublishedMetadata updateStatus(String status) throws Exception {
		final MultiValueMap<String, Object> tweetParams = new LinkedMultiValueMap<String, Object>();			  
        tweetParams.add("content",status);
        tweetParams.add("qualifier", "is");
        final ResponseEntity<Map> response = getRestTemplate().postForEntity(PUT_STATUS, tweetParams, Map.class);
        final Map body = response.getBody();
        // {"plurk_id": 3, "content": "Test", "qualifier_translated": "says", "qualifier": "says", "lang": "en" ...}
        final TweetPublishedMetadata metadata = createStatus(status);
        //final Map response_body = (Map) body.get("plurk_id");
        metadata.setTweetId(body.get("plurk_id").toString());
		return metadata;
	}

}
