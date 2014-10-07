package org.encuestame.utils.web;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePollBean implements Serializable{

	/**
	 *
	 */
	private static  long serialVersionUID = -7561028976907252715L;

	/**
	 *
	 */
	public CreatePollBean() {}

	@JsonProperty(value = "questionName")
	public  String questionName;

	@JsonProperty(value = "listAnswers")
	public   String[] answers;

	@JsonProperty(value = "results")
	public   String results;

	@JsonProperty(value = "showComments")
	public   String showComments;

	@JsonProperty(value = "limit_votes")
	public Long limitVote;

	@JsonProperty(value = "close_date")
	public   Long close_date;

	@JsonProperty(value = "folder_name")
	public   String folder_name;

	@JsonProperty(value = "multiple")
	public   Boolean multiple;

	@JsonProperty(value = "hashtags")
	public   String[] hashtags;

	@JsonProperty(value="allow_add")
	public Boolean allowAdd;

	@JsonProperty(value="isHidden")
	public Boolean isHidden;

	@JsonProperty(value="isPasswordProtected")
	public Boolean isPasswordProtected;

	@JsonProperty(value="pollPassword")
	public String pollPassword;

	/**
	 * @return the questionName
	 */
	@JsonIgnore
	public String getQuestionName() {
		return questionName;
	}

	/**
	 * @param questionName the questionName to set
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	/**
	 * @return the answers
	 */
	@JsonIgnore
	public String[] getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	/**
	 * @return the results
	 */
	@JsonIgnore
	public String getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(String results) {
		this.results = results;
	}

	/**
	 * @return the showComments
	 */
	@JsonIgnore
	public String getShowComments() {
		return showComments;
	}

	/**
	 * @param showComments the showComments to set
	 */
	public void setShowComments(String showComments) {
		this.showComments = showComments;
	}

	/**
	 * @return the limitVote
	 */
	@JsonIgnore
	public Long getLimitVote() {
		return limitVote;
	}

	/**
	 * @param limitVote the limitVote to set
	 */
	public void setLimitVote(Long limitVote) {
		this.limitVote = limitVote;
	}

	/**
	 * @return the close_date
	 */
	@JsonIgnore
	public Long getCloseDate() {
		return close_date;
	}

	/**
	 * @param close_date the close_date to set
	 */
	public void setCloseDate(Long close_date) {
		this.close_date = close_date;
	}

	/**
	 * @return the folder_name
	 */
	@JsonIgnore
	public String getFolder_name() {
		return folder_name;
	}

	/**
	 * @param folder_name the folder_name to set
	 */
	public void setFolder_name(String folder_name) {
		this.folder_name = folder_name;
	}

	/**
	 * @return the multiple
	 */
	@JsonIgnore
	public Boolean getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return the hashtags
	 */
	@JsonIgnore
	public String[] getHashtags() {
		return hashtags;
	}

	/**
	 * @param hashtags the hashtags to set
	 */
	public void setHashtags(String[] hashtags) {
		this.hashtags = hashtags;
	}

	/**
	 * @return the allowAdd
	 */
	@JsonIgnore
	public Boolean getAllowAdd() {
		return allowAdd;
	}

	/**
	 * @param allowAdd the allowAdd to set
	 */
	public void setAllowAdd(Boolean allowAdd) {
		this.allowAdd = allowAdd;
	}

	/**
	 * @return the isHidden
	 */
	public Boolean getIsHidden() {
		return isHidden;
	}

	/**
	 * @param isHidden the isHidden to set
	 */
	public void setIsHidden(final Boolean isHidden) {
		this.isHidden = isHidden;
	}

	/**
	 * @return the isPasswordProtected
	 */
	public Boolean getIsPasswordProtected() {
		return isPasswordProtected;
	}

	/**
	 * @param isPasswordProtected the isPasswordProtected to set
	 */
	public void setIsPasswordProtected(final Boolean isPasswordProtected) {
		this.isPasswordProtected = isPasswordProtected;
	}

	/**
	 * @return the pollPassword
	 */
	public String getPollPassword() {
		return pollPassword;
	}

	/**
	 * @param pollPassword the pollPassword to set
	 */
	public void setPollPassword(final String pollPassword) {
		this.pollPassword = pollPassword;
	}

}
