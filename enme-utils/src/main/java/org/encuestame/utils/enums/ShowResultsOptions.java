package org.encuestame.utils.enums;

public enum ShowResultsOptions {
	
	/**
	 * Only display percents
	 */
	PERCENT("PERCENT"),
	
	/**
	 * Display all data
	 */
	ALL("ALL"),
	
	/**
	 * All results are restricted
	 */
	RESTRICTED("RESTRICTED");

	/** **/
	private String optionAsString;

	/**
	 *
	 * @param optionAsString
	 */
	ShowResultsOptions(final String optionAsString){
		this.optionAsString = optionAsString;
	}

}
