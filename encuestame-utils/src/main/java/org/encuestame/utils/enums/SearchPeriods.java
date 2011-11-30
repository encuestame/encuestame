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
package org.encuestame.utils.enums;

/**
 * Enumeration for Search Periods.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 9:08:06 PM
 * @version $Id:$
 */
public enum SearchPeriods {

    /**
     * Represent last 24 hours.
     */
    TWENTYFOURHOURS,
    /**
     * Represent 7 last days
     */
    SEVENDAYS,
    /**
     * Represent Last 30 Days.
     */
    THIRTYDAYS,
    /**
     * Represent All Time.
     */
    ALLTIME,

    /**
     * Constructor.
     */
    SearchPeriods() {
        //Empty constructor.
    };

    /**
     * To String.
     */
    public String toString() {
        String period = "24";
        //If last 24 hours
        if (this == TWENTYFOURHOURS) { period = "24"; }
        //If last 7 days
        else if (this == SEVENDAYS) { period = "7"; }
        //If last 30 days
        else if (this == THIRTYDAYS) { period = "30"; }
        //If select all time.
        else if (this == ALLTIME) { period = "all"; }
        return period;
    }

    /**
     * Get Period by String.
     * @param period period
     * @return
     */
    public static SearchPeriods getPeriodString(final String period) {
        if (null == period) { return TWENTYFOURHOURS; }
        else if (period.equalsIgnoreCase("24")) { return TWENTYFOURHOURS; }
        else if (period.equalsIgnoreCase("7")) { return SEVENDAYS; }
        else if (period.equalsIgnoreCase("30")) { return THIRTYDAYS; }
        else if (period.equalsIgnoreCase("all")) { return ALLTIME; }
        else return TWENTYFOURHOURS;
    }
}
