package org.encuestame.utils.social;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TubmlrUserProfile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4160553730163063784L;
	
	/**
	 * The user's tumblr short name
	 */
	private String name;
	/**
	 * The total count of the user's likes
	 */
	private Integer likes;
	/**
	 * The number of blogs the user is following
	 */
	private Integer following;
	/**
	 * The default posting format - html, markdown or raw
	 */
	private String defaultPostFormat;
	
	/**
	 * 
	 */
	private List<Blog> listBlogs = new ArrayList<Blog>();
	
	/**
	 * 
	 */
	public TubmlrUserProfile() {}
	

	/**
	 * @return the listBlogs
	 */
	public List<Blog> getListBlogs() {
		return listBlogs;
	}



	/**
	 * @param listBlogs the listBlogs to set
	 */
	public void setListBlogs(List<Blog> listBlogs) {
		this.listBlogs = listBlogs;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the likes
	 */
	public Integer getLikes() {
		return likes;
	}


	/**
	 * @param likes the likes to set
	 */
	public void setLikes(Integer likes) {
		this.likes = likes;
	}


	/**
	 * @return the following
	 */
	public Integer getFollowing() {
		return following;
	}


	/**
	 * @param following the following to set
	 */
	public void setFollowing(Integer following) {
		this.following = following;
	}


	/**
	 * @return the defaultPostFormat
	 */
	public String getDefaultPostFormat() {
		return defaultPostFormat;
	}


	/**
	 * @param defaultPostFormat the defaultPostFormat to set
	 */
	public void setDefaultPostFormat(String defaultPostFormat) {
		this.defaultPostFormat = defaultPostFormat;
	}
	
	public class Blog {
		/**
		 * string: the short name of the blog
		 */
		private String name;
		/**
		 *  string: the URL of the blog
		 */
		private String url;
		/**
		 * number: total count of followers for this blog
		 */
		private Integer followers;
		/**
		 * string: the title of the blog
		 */
		private String title;
		/**
		 * : indicates if this is the user's primary blog
		 */
		private Boolean primary;
		private String description;
		private Boolean isAdmin;
		private Date lastUpdated;
		private Integer post;
		private Integer messages;
		private Integer Queue;
		private Integer drafts;
		private Boolean shareLikes;
		private Boolean ask;
		/**
		 * number: indicate if posts are tweeted auto, Y, N
		 */
		private Boolean tweet;
		/**
		 *  indicate if posts are sent to facebook Y, N
		 */
		private Boolean facebook;
		private Boolean facebookOpenGraphEnabled;
		/**
		 * indicates whether a blog is public or private
		 */
		private String type;
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}
		/**
		 * @param url the url to set
		 */
		public void setUrl(String url) {
			this.url = url;
		}
		/**
		 * @return the followers
		 */
		public Integer getFollowers() {
			return followers;
		}
		/**
		 * @param followers the followers to set
		 */
		public void setFollowers(Integer followers) {
			this.followers = followers;
		}
		/**
		 * @return the title
		 */
		public String getTitle() {
			return title;
		}
		/**
		 * @param title the title to set
		 */
		public void setTitle(String title) {
			this.title = title;
		}
		/**
		 * @return the primary
		 */
		public Boolean getPrimary() {
			return primary;
		}
		/**
		 * @param primary the primary to set
		 */
		public void setPrimary(Boolean primary) {
			this.primary = primary;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		/**
		 * @return the isAdmin
		 */
		public Boolean getIsAdmin() {
			return isAdmin;
		}
		/**
		 * @param isAdmin the isAdmin to set
		 */
		public void setIsAdmin(Boolean isAdmin) {
			this.isAdmin = isAdmin;
		}
		/**
		 * @return the lastUpdated
		 */
		public Date getLastUpdated() {
			return lastUpdated;
		}
		/**
		 * @param lastUpdated the lastUpdated to set
		 */
		public void setLastUpdated(Date lastUpdated) {
			this.lastUpdated = lastUpdated;
		}
		/**
		 * @return the post
		 */
		public Integer getPost() {
			return post;
		}
		/**
		 * @param post the post to set
		 */
		public void setPost(Integer post) {
			this.post = post;
		}
		/**
		 * @return the messages
		 */
		public Integer getMessages() {
			return messages;
		}
		/**
		 * @param messages the messages to set
		 */
		public void setMessages(Integer messages) {
			this.messages = messages;
		}
		/**
		 * @return the queue
		 */
		public Integer getQueue() {
			return Queue;
		}
		/**
		 * @param queue the queue to set
		 */
		public void setQueue(Integer queue) {
			Queue = queue;
		}
		/**
		 * @return the drafts
		 */
		public Integer getDrafts() {
			return drafts;
		}
		/**
		 * @param drafts the drafts to set
		 */
		public void setDrafts(Integer drafts) {
			this.drafts = drafts;
		}
		/**
		 * @return the shareLikes
		 */
		public Boolean getShareLikes() {
			return shareLikes;
		}
		/**
		 * @param shareLikes the shareLikes to set
		 */
		public void setShareLikes(Boolean shareLikes) {
			this.shareLikes = shareLikes;
		}
		/**
		 * @return the ask
		 */
		public Boolean getAsk() {
			return ask;
		}
		/**
		 * @param ask the ask to set
		 */
		public void setAsk(Boolean ask) {
			this.ask = ask;
		}
		/**
		 * @return the tweet
		 */
		public Boolean getTweet() {
			return tweet;
		}
		/**
		 * @param tweet the tweet to set
		 */
		public void setTweet(Boolean tweet) {
			this.tweet = tweet;
		}
		/**
		 * @return the facebook
		 */
		public Boolean getFacebook() {
			return facebook;
		}
		/**
		 * @param facebook the facebook to set
		 */
		public void setFacebook(Boolean facebook) {
			this.facebook = facebook;
		}
		/**
		 * @return the facebookOpenGraphEnabled
		 */
		public Boolean getFacebookOpenGraphEnabled() {
			return facebookOpenGraphEnabled;
		}
		/**
		 * @param facebookOpenGraphEnabled the facebookOpenGraphEnabled to set
		 */
		public void setFacebookOpenGraphEnabled(Boolean facebookOpenGraphEnabled) {
			this.facebookOpenGraphEnabled = facebookOpenGraphEnabled;
		}
		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}
		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}
		
	}
}
