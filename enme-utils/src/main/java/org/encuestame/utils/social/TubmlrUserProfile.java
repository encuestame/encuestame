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
	private List<TumblrBlog> listBlogs = new ArrayList<TumblrBlog>();
	
	/**
	 * 
	 */
	public TubmlrUserProfile() {}
	

	/**
	 * @return the listBlogs
	 */
	public List<TumblrBlog> getListBlogs() {
		return listBlogs;
	}



	/**
	 * @param listBlogs the listBlogs to set
	 */
	public void setListBlogs(List<TumblrBlog> listBlogs) {
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
}
