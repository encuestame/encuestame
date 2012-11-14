package org.encuestame.utils.web;

import java.io.Serializable;

public class AdvancedSearchBean  implements Serializable {


	/** **/
	private static final long serialVersionUID = 2164671051410597590L;

	/** **/
	private String keyword;

	/** **/
	private Integer period;

	/** **/
	private Boolean isPublished = Boolean.TRUE;

	/** **/
	private Boolean isComplete = Boolean.FALSE;

	/** **/
	private Boolean isFavourite = Boolean.FALSE;

	/** **/
	private Boolean isScheduled = Boolean.FALSE;

	/** **/
	private Integer start = 0;

	/** **/
	private Integer max = 20;

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the period
	 */
	public Integer getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(final Integer period) {
		this.period = period;
	}

	/**
	 * @return the isPublished
	 */
	public Boolean getIsPublished() {
		return isPublished;
	}

	/**
	 * @param isPublished the isPublished to set
	 */
	public void setIsPublished(final Boolean isPublished) {
		this.isPublished = isPublished;
	}

	/**
	 * @return the isComplete
	 */
	public Boolean getIsComplete() {
		return isComplete;
	}

	/**
	 * @param isComplete the isComplete to set
	 */
	public void setIsComplete(final Boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * @return the isFavourite
	 */
	public Boolean getIsFavourite() {
		return isFavourite;
	}

	/**
	 * @param isFavourite the isFavourite to set
	 */
	public void setIsFavourite(final Boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	/**
	 * @return the isScheduled
	 */
	public Boolean getIsScheduled() {
		return isScheduled;
	}

	/**
	 * @param isScheduled the isScheduled to set
	 */
	public void setIsScheduled(final Boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	/**
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(final Integer start) {
		this.start = start;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(final Integer max) {
		this.max = max;
	}
}
