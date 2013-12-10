package org.encuestame.utils.json;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetPollScheduledBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4243987840986024952L;
	@JsonProperty(value = "id")
    private Long id;	
    @JsonProperty(value = "social_accounts")
    private List<SocialAccountBean> socialAccounts;
	/**
	 * @return the id
	 */
    @JsonIgnore
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the socialAccounts
	 */
	@JsonIgnore
	public List<SocialAccountBean> getSocialAccounts() {
		return socialAccounts;
	}
	/**
	 * @param socialAccounts the socialAccounts to set
	 */
	public void setSocialAccounts(List<SocialAccountBean> socialAccounts) {
		this.socialAccounts = socialAccounts;
	}
    
    
}
