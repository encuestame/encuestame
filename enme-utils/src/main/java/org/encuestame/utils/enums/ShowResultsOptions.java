package org.encuestame.utils.enums;

public enum ShowResultsOptions {
	
	/**
	 * Only display percents
	 */
	PERCENT,
	
	/**
	 * Display all data
	 */
	ALL,
	
	/**
	 * All results are restricted
	 */
	RESTRICTED,
	
	ShowResultsOptions(){};
	
	/**
	 * 
	 */
	 public String toString() {
	        String comment = "";
	        if (this == PERCENT) { comment = "PERCENT"; }
	        else if (this == ALL) { comment = "ALL"; }
	        else if (this == RESTRICTED) { comment = "RESTRICTED"; }
			return comment;
	 }
	
	 /**
	  * 
	  * @param option
	  * @return
	  */
	 public static ShowResultsOptions getShowResults(final String option) {
		 if (null == option) { return null; }
	        else if (option.equalsIgnoreCase("PERCENT")) { return PERCENT; }
	        else if (option.equalsIgnoreCase("ALL")) { return ALL; }
	        else if (option.equalsIgnoreCase("RESTRICTED")) { return RESTRICTED; }
	        else return null;		 
	 }
}
