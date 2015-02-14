package org.encuestame.social.api.templates;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.social.AbstractSocialAPISupport;
import org.encuestame.social.api.operation.TumblrAPIOperations;
import org.encuestame.utils.TweetPublishedMetadata;
import org.encuestame.utils.social.SocialUserProfile;
import org.encuestame.utils.social.TubmlrUserProfile;
import org.encuestame.utils.social.TumblrBlog;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


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
    static final String PUT_STATUS = "http://api.tumblr.com/v2/blog/{username}.tumblr.com/post";
        
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
    	   List<LinkedHashMap<String, Object>> t =   (List<LinkedHashMap<String, Object>>) user.get("blogs");
    	   for (LinkedHashMap<String, Object> linkedHashMap : t) {
    		   final TumblrBlog blog = new TumblrBlog();
    		   blog.setAsk(Boolean.valueOf(linkedHashMap.get("ask").toString()));
    		   blog.setDescription(linkedHashMap.get("description").toString());
    		   blog.setFollowers(Integer.valueOf(linkedHashMap.get("followers").toString()));
    		   blog.setUrl(linkedHashMap.get("url").toString());
    		   blog.setName(linkedHashMap.get("name").toString());
    		   blog.setPrimary(Boolean.valueOf(linkedHashMap.get("primary").toString()));
    		   //blog.setLastUpdated(new Date(linkedHashMap.get("updated").toString()));
    		   blog.setLastUpdated(Calendar.getInstance().getTime());
    		   profile.getListBlogs().add(blog);
    	   }
       } catch(Exception ex) {
    	   ex.printStackTrace();
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
		//TODO: finish properties mapping
		final SocialUserProfile socialUserProfile = new SocialUserProfile();
		final TubmlrUserProfile profile = this.getUserProfile();
		socialUserProfile.setFollowersCount(0);
		socialUserProfile.setUsername(profile.getName());
		socialUserProfile.setId(profile.getName());
		//get the primary blog
		socialUserProfile.setEmail(null);
		socialUserProfile.setCreatedAt(Calendar.getInstance().getTime());
		socialUserProfile.setName(profile.getName());
		socialUserProfile.setUrl(profile.getListBlogs().get(0).getUrl());
		socialUserProfile.setProfileImageUrl("http://api.tumblr.com/v2/blog/{username}.tumblr.com/avatar/24".replace("{username}", profile.getName()));
		socialUserProfile.setScreenName(profile.getName());
		socialUserProfile.setLocation("");
		socialUserProfile.setDescription(profile.getName());
		return socialUserProfile;
	}

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.social.api.support.TumblrAPIOperations#updateStatus(java.lang.String, org.encuestame.persistence.domain.security.SocialAccount)
	 */
	@Override
	public TweetPublishedMetadata updateStatus(String status, final SocialAccount account, final Set<HashTag> hashtags) throws Exception {
		final MultiValueMap<String, Object> tweetParams = new LinkedMultiValueMap<String, Object>();
		String hashStrign = "";
		if (hashtags.size() > 0) {
			Iterator<HashTag> iterator = hashtags.iterator();
		    while(iterator.hasNext()) {
		    	HashTag setElement = iterator.next();
		        hashStrign  = hashStrign + setElement.getHashTag() + ",";
		    }
		    tweetParams.add("tags",  hashStrign.substring(0,hashStrign.length()-1));
		}	   
        tweetParams.add("type","text");
        tweetParams.add("state","published");        
        tweetParams.add("body",status);
        final ResponseEntity<Map> response = getRestTemplate().postForEntity(PUT_STATUS.replace("{username}", account.getSocialAccountName()), tweetParams, Map.class);
        final Map body = response.getBody();
        //Responses table: http://www.tumblr.com/docs/en/api/v2#common-fields
        final TweetPublishedMetadata metadata = createStatus(status);
        final Map response_body = (Map) body.get("response");
        metadata.setTweetId(response_body.get("id").toString());
		return metadata;
	}

	/*
	 * (non-Javadoc)
	 * @see org.encuestame.social.api.support.SocialAPIOperations#updateStatus(java.lang.String)
	 */
	@Override
	public TweetPublishedMetadata updateStatus(String status) throws Exception {	
		return null; //not necesary
	}
}
