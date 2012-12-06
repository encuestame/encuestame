/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;

/**
 * Collection of helper for dates.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 11:04:07 PM
 */
public class DateUtil {

    /**
     * yyyy-MM-dd
     */
    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";

    /**
     * hh:mm:ss
     */
    public static final String DEFAULT_FORMAT_TIME = "hh:mm:ss";

    /**
     * 5/25/11 hh:mm:ss
     */
    public static final String COMPLETE_FORMAT_TIME = "dd/MM/yy hh:mm:ss";

    /**
     * DOJO_FORMAT MMMMM d, yyyy hh:mm:ss
     */
    public static final String DOJO_FORMAT = "MMMMM d, yyyy hh:mm:ss";

    /**
     * Dojo date object format.
     */
    public static final SimpleDateFormat DOJO_DATE_FORMAT = new SimpleDateFormat(DOJO_FORMAT);

    /**
     * yyyy-MM-dd hh:mm:ss
     */
    public static final String DEFAULT_FORMAT_ALL = "yyyy-MM-dd hh:mm:ss";

    /**
     * Simple date format.
     */
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);

    /**
     * Simple time format.
     */
    public static final SimpleDateFormat SIMPLE_TIME_FORMAT = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_TIME);

    /**
     * Get Format Date.
     * @param date
     * @return
     */
    public static String getFormatDate(final Date date){
       return DateUtil.getFormatDate(date, DEFAULT_FORMAT_DATE);
    }

    /**
     * Return current formate date, minutes and date.
     * @return current formate date.
     */
    public static String getCurrentFormatedDate(){
        return DateUtil.getFormatDate(new Date(), DateUtil.DEFAULT_FORMAT_ALL);
     }

    /**
     * Get format date.
     * @param date date
     * @param format format
     * @return
     */
    public static String getFormatDate(final Date date, final String format){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
     }

    /**
     * Parse date.
     * @param date date on string
     * @param format format of date.
     * @return
     * @throws ParseException error on parse..
     */
    public static Date parseDate(final String date, final String format) throws ParseException{
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }

    /**
     * Parse string date with dojo format to Date.
     * @param date dojo string date format.
     * @return {@link Date}
     * @throws ParseException
     */
    public static Date parseFromDojo(final String date) throws ParseException {
        return DateUtil.parseDate(date, DateUtil.DOJO_FORMAT);
    }

    /**
     *
     * @param startDate
     * @return
     */
    public static Date decreaseDateAsWeek(final Date startDate){
        final DateTime midnightDate = new DateTime(startDate);
        midnightDate.minusWeeks(1);
        return midnightDate.toDate();
    }

    /**
     * Retrieve next day mid night date.
     * @return {@link Date}
     */
    public static Date getNextDayMidnightDate(){
        DateTime midNight = new DateTime();
        midNight = midNight.plusDays(1);
        final DateMidnight midnightDate  = midNight.toDateMidnight();
        return midnightDate.toDate();
    }

    /**
     * Increase a day your date.
     * @param startDate
     * @return
     */
    public static Date decreaseDateADay(final Date startDate){
        final DateTime endtDate = new DateTime(startDate);
        endtDate.minusDays(1);
        return endtDate.toDate();
    }
    
    /**
     * 
     * @param days
     * @return
     */
    public static Date minusDaysToCurrentDate(final Integer days, final Date date) {
        final DateTime dateTime = date == null ? new DateTime() : new DateTime(date);           
        return  dateTime.minusDays(days).toDate();
    }

    /**
     * Get Seconds Between Dates.
     * @param initDate
     * @return
     */
    public static Integer getSecondsBetweenDates(final Date startDate){
        final DateTime currentDate = new DateTime();
        final DateTime storedDate = new DateTime(startDate);
        final Seconds secondsBetween = Seconds.secondsBetween(storedDate, currentDate);
        return secondsBetween.getSeconds();
    }

    /**
     * Get Minutes Between Dates.
     * @param initDate
     * @return
     */
    public static Integer getMinutesBetweenDates(final Date startDate){
        final DateTime currentDate = new DateTime();
        final DateTime storedDate = new DateTime(startDate);
        final Minutes minutesBetween = Minutes.minutesBetween(storedDate, currentDate);
        return minutesBetween.getMinutes();
    }

    /**
     * Get Minutes Between Dates.
     * @param initDate
     * @return
     */
    public static Integer getHoursBetweenDates(final Date startDate){
        final DateTime currentDate = new DateTime();
        final DateTime storedDate = new DateTime(startDate);
        final Hours hoursBetween = Hours.hoursBetween(storedDate, currentDate);
        return hoursBetween.getHours();
    }

   /**
    * Get Days Between Dates.
    * @param startDate
    * @return
    */
    public static Integer getDaysBetweenDates(final Date startDate){
        final DateTime currentDate = new DateTime();
        final DateTime storedDate = new DateTime(startDate);
        final Days daysBetween = Days.daysBetween(storedDate, currentDate);
        return daysBetween.getDays();
    }

    /**
     * Get Months Between Dates.
     * @param startDate
     * @return
     */
    public static Integer getMothsBetweenDates(final Date startDate){
        final DateTime currentDate = new DateTime();
        final DateTime storedDate = new DateTime(startDate);
        final Months monthsBetween = Months.monthsBetween(storedDate, currentDate);
        return monthsBetween.getMonths();
    }

    /**
     * Get Years Between Dates.
     * @param startDate
     * @return
     */
    public static Integer getYearsBetweenDates(final Date startDate){
        final DateTime currentDate = new DateTime();
        final DateTime storedDate = new DateTime(startDate);
        final Years yearsBetween = Years.yearsBetween(storedDate, currentDate);
        return yearsBetween.getYears();
    }

    /**
     *  DateTime start = new DateTime(); //Devuelve la fecha actual al estilo Date
        DateTime end = new DateTime(); //Devuelve la fecha actual al estilo Date
        //Buscar la diferencia
        int days = Days.daysBetween(start, end).getDays();
     */

    /**
     *
     */
    public static DateClasificatedEnum getClasificated(final Date date){
        return DateClasificatedEnum.TODAY;
    }

    /**
     * Get Relative Time.
     * @param startDate date in the time.
     * @return
     */
    public static HashMap<Integer, RelativeTimeEnum> getRelativeTime(final Date startDate) {
        int SECOND = 1;
        int MINUTE = 60 * SECOND;
        int HOUR = 60 * MINUTE;
        int DAY = 24 * HOUR;
        int MONTH = 30 * DAY;
        final HashMap<Integer, RelativeTimeEnum> numbers = new HashMap<Integer, RelativeTimeEnum>();
        final Integer seconds = DateUtil.getSecondsBetweenDates(startDate);
        final Integer minutes = DateUtil.getMinutesBetweenDates(startDate);
        final Integer hour = DateUtil.getHoursBetweenDates(startDate);
        final Integer days = DateUtil.getDaysBetweenDates(startDate);
        if (seconds < 0) {
            numbers.put(seconds, RelativeTimeEnum.RIGTH_NOW);
        } else if (seconds < 1 * MINUTE) {
            numbers.put(seconds,
                    (seconds == 1 ? RelativeTimeEnum.ONE_SECOND_AGO
                            : RelativeTimeEnum.SECONDS_AGO));
        } else if (seconds < 2 * MINUTE) {
            numbers.put(seconds, RelativeTimeEnum.A_MINUTE_AGO);
        } else if (seconds < 45 * MINUTE) {
            numbers.put(minutes, RelativeTimeEnum.MINUTES_AGO);
        } else if (seconds < 90 * MINUTE) {
            numbers.put(hour, RelativeTimeEnum.AN_HOUR_AGO);
        } else if (seconds < 24 * HOUR) {
            numbers.put(hour, RelativeTimeEnum.HOURS_AGO);
        } else if (seconds < 48 * HOUR) {
            numbers.put(hour, RelativeTimeEnum.YESTERDAY);
        } else if (seconds < 30 * DAY) {
            numbers.put(days, RelativeTimeEnum.DAYS_AGO); //TODO: maybe it's better WEEKS_AGO
        } else if (seconds < 12 * MONTH) {
            Integer months = DateUtil.getMothsBetweenDates(startDate);
            numbers.put(months, (months <= 1 ? RelativeTimeEnum.ONE_MONTH_AGO
                    : RelativeTimeEnum.MONTHS_AGO));
        } else {
            Integer years = DateUtil.getYearsBetweenDates(startDate);
            numbers.put(years, (years <= 1 ? RelativeTimeEnum.ONE_YEAR_AGO
                    : RelativeTimeEnum.YEARS_AGO));
        }
        return numbers;
    }
    
    /**
     * Retrieve start Date to perform searches for a period of time(Month, Year, Hours, etc).
     * @param period
     * @return
     */
	public static Date retrieveStartDateByPeriod(final Integer period) {
		final Calendar currentDate = Calendar.getInstance();
		Date startDate = null; 
		currentDate.add(Calendar.DAY_OF_YEAR, -period);
		startDate = currentDate.getTime(); 
		return startDate;

	}
	
	/**
	 * Get current Date by Calendar instance.
	 * @return
	 */
	public static Date getCurrentCalendarDate() {
		final Date endDate = Calendar.getInstance().getTime();
		return endDate;
	}
	
	 /**
	  * Get value of the current month.
	  * @param currentDate
	  * @return
	  */
	public static Integer getValueCurrentMonthOfTheYear(final Date currentDate) {
		 Integer monthValue;
		 monthValue = new DateTime(currentDate).getMonthOfYear();  
		 return monthValue;
	}
	
	/**
	 * Get value of the current day of the month.
	 * @param currentDate
	 * @return
	 */
	public static Integer getValueCurrentDateOfTheMonths(final Date currentDate) { 
		 return new DateTime(currentDate).getDayOfMonth();
	}
	
	/**
	 * Get the current year.
	 * @param currentDate {@link Date}/
	 * @return
	 */
	public static Integer getValueCurrentYear(final Date currentDate) {
		 return new DateTime(currentDate).getYear(); 
	}	
	
	/**
	 * Get value of the day of the week.
	 * @param currentDate
	 * @return
	 */
	public static Integer getValueCurrentDayOfTheWeek(final Date currentDate) {
		return new DateTime(currentDate).getDayOfWeek();
	}

	/**
	 * Get value hour of the day.
	 * @param currentDate
	 * @return
	 */
	public static Integer getValueHourOfTheDay(final Date currentDate) {
		return new DateTime(currentDate).getHourOfDay();
	}
	
	/**
	 * Check if date is within allowed range.
	 * @param period
	 * @param itemDate
	 * @return
	 */
	public static Boolean checkDatedWithinAllowableRange(final Integer period, final Date itemDate){
		Boolean allowedRange = Boolean.FALSE;
		Date endDate = DateUtil.getCurrentCalendarDate();
		Date startDate = DateUtil.retrieveStartDateByPeriod(period);   
		// Check if date is between starDate by period and currentDate(itemDate is > startDate, itemDate < endDate)
		if ((itemDate.after(startDate)) && (itemDate.before(endDate))) { 
			allowedRange = Boolean.TRUE;
		} 
		return allowedRange;
	}
}
