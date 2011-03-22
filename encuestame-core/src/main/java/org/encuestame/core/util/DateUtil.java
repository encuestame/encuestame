/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;


/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 11:04:07 PM
 * @version Id:
 */
public class DateUtil {

    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";

    public static final String DEFAULT_FORMAT_TIME = "hh:mm:ss";

    public static final String DEFAULT_FORMAT_ALL = "yyyy-MM-dd hh:mm:ss";

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


    public static String getFormatDate(final Date date, final String format){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
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
        System.out.println("days = " + days);
     */

    /**
     * Get Relative Time.
     * @param startDate date in the time.
     * @return
     */
    public static HashMap<Integer, RelativeTimeEnum> getRelativeTime(final Date startDate){
        int SECOND = 1;
        int MINUTE = 60 * SECOND;
        int HOUR = 60 * MINUTE;
        int DAY = 24 * HOUR;
        int MONTH = 30 * DAY;
        final HashMap<Integer, RelativeTimeEnum> numbers = new HashMap<Integer, RelativeTimeEnum>();
        final Integer seconds = DateUtil.getSecondsBetweenDates(startDate);
        //System.out.println("seconds ago  "+seconds);
        final Integer minutes = DateUtil.getMinutesBetweenDates(startDate);
        //System.out.println("minutes ago  "+minutes);
        final Integer hour = DateUtil.getHoursBetweenDates(startDate);
        //System.out.println("hour ago  "+hour);
        final Integer days = DateUtil.getDaysBetweenDates(startDate);
        //System.out.println("days ago  "+days);
        //System.out.println("start date "+startDate);
        if (seconds < 0) {
            // TODO: no yet
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
            numbers.put(days, RelativeTimeEnum.DAYS_AGO);
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
}
