package org.encuestame.social.api;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.social.AbstractSocialAPISupport;
import org.encuestame.social.api.support.TumblrAPIOperations;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.social.TubmlrUserProfile;


public class TumblrAPITemplate extends AbstractSocialAPISupport implements TumblrAPIOperations{
	
	/**
     * Log.
     */
    private Log log = LogFactory.getLog(this.getClass());
	
    /**
    *
    */
    static final String GET_CURRENT_USER_INFO = "https://api.tumblr.com/v2/user/info";

    /**
    *
    */
    static final String PUT_STATUS = "http://api.tumblr.com/v2/blog/citriccomics.tumblr.com/posts/text";
    
    
    /**
    *
    * @param apiKey
    * @param apiSecret
    * @param accessToken
    * @param accessTokenSecret
    */
   public TumblrAPITemplate(
           String apiKey,
           String apiSecret,
           String accessToken, String accessTokenSecret) {
       setRestTemplate(org.encuestame.oauth1.support.ProtectedResourceClientFactory
               .create(apiKey, apiSecret, accessToken, accessTokenSecret));
   }
   
   /**
    * Get the Tumblr user profile.
    * eg: of possible response. 
    * {
		  "meta": {
		    "status": 200,
		    "msg": "OK"
		  },
		  "response": {
		    "user": {
		      "name": "jota22",
		      "likes": 0,
		      "following": 1,
		      "default_post_format": "html",
		      "blogs": [
		        {
		          "name": "jota22",
		          "url": "http://jota22.tumblr.com/",
		          "followers": 0,
		          "primary": true,
		          "title": "helloWord",
		          "description": "",
		          "admin": true,
		          "updated": 1386830027,
		          "posts": 2,
		          "messages": 0,
		          "queue": 0,
		          "drafts": 0,
		          "share_likes": true,
		          "ask": false,
		          "tweet": "N",
		          "facebook": "N",
		          "facebook_opengraph_enabled": "N",
		          "type": "public"
		        }
		      ]
		    }
		  }
		}
    * @return
    */
   private TubmlrUserProfile getUserProfile() {
       Map<?, ?> response = getRestTemplate().getForObject(GET_CURRENT_USER_INFO, Map.class);
       Map<?, ?> response2 = (Map<?, ?>) response.get("response");
       Map<?, ?> user = (Map<?, ?>) response2.get("user");
       final TubmlrUserProfile profile = new TubmlrUserProfile();
       profile.setName(user.get("name").toString());
       profile.setFollowing(Integer.valueOf(user.get("following").toString()));
       profile.setLikes(Integer.valueOf(user.get("likes").toString()));
       profile.setDefaultPostFormat(user.get("default_post_format").toString());
       try {
    	   Object t =  user.get("blogs");
    	   log.debug(t);
       }catch(Exception ex){
    	   log.error(ex);    	
       }
       return profile;
   }
   
   /*
    * (non-Javadoc)
    * @see org.encuestame.social.api.support.SocialAPIOperations#getProfile()
    */
	@Override
	public SocialUserProfile getProfile() throws Exception {
		final SocialUserProfile socialUserProfile = new SocialUserProfile();
		final TubmlrUserProfile profile = this.getUserProfile();
		socialUserProfile.setFollowersCount(0);
		socialUserProfile.setUsername(profile.getName());
		socialUserProfile.setId(profile.getName());
		//get the primary blog
		socialUserProfile.setEmail(null);
		socialUserProfile.setCreatedAt(Calendar.getInstance().getTime());
		socialUserProfile.setName(profile.getName());
		socialUserProfile.setUrl("profile url");
		socialUserProfile.setProfileImageUrl("url image");
		socialUserProfile.setScreenName(profile.getName());
		socialUserProfile.setLocation("");
		socialUserProfile.setDescription(profile.getName());
		return socialUserProfile;
	}

	@Override
	public TweetPublishedMetadata updateStatus(String status) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
