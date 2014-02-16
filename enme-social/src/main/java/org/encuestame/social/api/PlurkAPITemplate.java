package org.encuestame.social.api;

import java.util.Calendar;
import java.util.Map;

import org.encuestame.social.AbstractSocialAPISupport;
import org.encuestame.social.api.support.PlurkAPIOperations;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.social.SocialUserProfile;
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
		//{ List of available properties
		//verified_account=false, 
		//page_title=, 
		//plurks_count=5, 
		//full_name=encuestame, 
		//timezone=null, 
		//id=10210418, 
		//fans_count=0, 
		//relationship=not_saying,
		//avatar_small=http://www.plurk.com/static/default_small.gif,
		//friends_count=1, 
		//post_anonymous_plurk=false, 
		//date_of_birth=Thu, 09 Apr 1981 00:01:00 GMT, 
		//location=Vienna, Austria, 
		//avatar_medium=http://www.plurk.com/static/default_medium.jpg,
		//recruited=0, 
		//bday_privacy=2,
		//avatar=null, 
		//default_lang=en, 
		//setup_facebook_sync=false,
		//avatar_big=http://www.plurk.com/static/default_big.jpg,
		//dateformat=0, 
		//has_profile_image=0, 
		//response_count=0,
		//setup_weibo_sync=false,
		//about=,
		//nick_name=encuestame, 
		//gender=2,
		//setup_twitter_sync=false, 
		//karma=5.35}
		final SocialUserProfile socialUserProfile = new SocialUserProfile();
		socialUserProfile.setFollowersCount(0);
		socialUserProfile.setUsername(response.get("nick_name").toString());
		socialUserProfile.setId(response.get("id").toString());
		//get the primary blog
		socialUserProfile.setEmail(null);
		socialUserProfile.setCreatedAt(Calendar.getInstance().getTime());
		socialUserProfile.setName(response.get("full_name").toString());
		socialUserProfile.setUrl("http://www.plurk.com/" + socialUserProfile.getUsername());
		socialUserProfile.setProfileImageUrl("http://avatars.plurk.com/{user_id}-small.gif".replace("{user_id}", socialUserProfile.getId()));
		socialUserProfile.setScreenName(response.get("nick_name").toString());
		socialUserProfile.setLocation(response.get("location").toString());
		socialUserProfile.setDescription(response.get("about").toString());
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
        // plurk example response:
        // 		{
		//        "replurkers": [],
		//        "responses_seen": 0,
		//        "qualifier": "is",
		//        "replurkers_count": 0,
		//        "plurk_id": 1180244031,
		//        "response_count": 0,
		//        "anonymous": false,
		//        "replurkable": true,
		//        "limited_to": null,
		//        "favorite_count": 0,
		//        "is_unread": 0,
		//        "lang": "en",
		//        "favorers": [],
		//        "content_raw": "juan test",
		//        "user_id": 10210418,
		//        "plurk_type": 0,
		//        "replurked": false,
		//        "favorite": false,
		//        "no_comments": 0,
		//        "content": "juan test",
		//        "replurker_id": null,
		//        "posted": "Sun, 15 Dec 2013 12:04:00 GMT",
		//        "owner_id": 10210418
		//    }
        final TweetPublishedMetadata metadata = createStatus(status);
        //final Map response_body = (Map) body.get("plurk_id");
        metadata.setTweetId(body.get("plurk_id").toString());
		return metadata;
	}

}
