package org.encuestame.utils.enums;

public enum Language {

	/**
	 * English language
	 */
	ENGLISH("ENGLISH");


	/** **/
	private String optionAsString;

	/**
	 *
	 * @param optionAsString
	 */
	Language(final String optionAsString) {
		this.optionAsString = optionAsString;
	}

	/**
	 *
	 * @return
	 */
	public String toString(){
		return this.optionAsString;
	}
}
