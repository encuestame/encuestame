/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2012
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */ 
package org.encuestame.utils.enums;

/**
 * Hashtag rate Enumeration
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 7:14:20 PM
 */
public enum HashTagRate {
	
	/** **/
	LBL_USAGE(""),
	
	/** **/
	LBL_SOCIAL_NETWORK(""),
	
	/** **/
	LBL_HITS(""),
	
	/** **/
	LBL_VOTES(""),
	
	/** **/
	SUB_LBL_TIMES(""),
	
	/** **/
	SUB_LBL_TWEETS(""),
	
	/** **/
	SUB_LBL_VOTES(""),
	
	/** **/
	JANUARY("1"), FEBRUARY("2"), MARCH("3"), APRIL("4"), MAY("5"), JUNE("6"), JULY("7"), AUGUST("8"), SEPTEMBER("9"), OCTOBER("10"), NOVEMBER("11"), DECEMBER("12"),
	
	/** **/
	MONDAY("1"), TUESDAY("2"), WEDNESDAY("3"), THURSDAY("4"), FRIDAY("5"), SATURDAY("6"), SUNDAY("7");

	private String optionAsString;

	private HashTagRate(final String optionAsString){
		this.optionAsString = optionAsString;
	}
	
	/**
	 * Return the month of the year.
	 * @param type
	 * @return
	 */
	public static HashTagRate getHashTagMonthLabel(final String type) {
		if (null == type) {
			return null;
		} else if (type.equalsIgnoreCase("1")) {
			return JANUARY;
		} else if (type.equalsIgnoreCase("2")) {
			return FEBRUARY;
		} else if (type.equalsIgnoreCase("3")) {
			return MARCH;
		} else if (type.equalsIgnoreCase("4")) {
			return APRIL;
		} else if (type.equalsIgnoreCase("5")) {
			return MAY;
		} else if (type.equalsIgnoreCase("6")) {
			return JUNE;
		} else if (type.equalsIgnoreCase("7")) {
			return JULY;
		} else if (type.equalsIgnoreCase("8")) {
			return AUGUST;
		} else if (type.equalsIgnoreCase("9")) {
			return SEPTEMBER;
		} else if (type.equalsIgnoreCase("10")) {
			return OCTOBER;
		} else if (type.equalsIgnoreCase("11")) {
			return NOVEMBER;
		} else if (type.equalsIgnoreCase("12")) {
			return DECEMBER;
		} else
			return null;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static HashTagRate getHashTagWeekDayLabel(final String type) {
		if (null == type) {
			return null;
		} else if (type.equalsIgnoreCase("1")) {
			return MONDAY;
		} else if (type.equalsIgnoreCase("2")) {
			return TUESDAY;
		} else if (type.equalsIgnoreCase("3")) {
			return WEDNESDAY;
		} else if (type.equalsIgnoreCase("4")) {
			return THURSDAY;
		} else if (type.equalsIgnoreCase("5")) {
			return FRIDAY;
		} else if (type.equalsIgnoreCase("6")) {
			return SATURDAY;
		} else if (type.equalsIgnoreCase("7")) {
			return SUNDAY;
		} else
			return null;
	}
}
