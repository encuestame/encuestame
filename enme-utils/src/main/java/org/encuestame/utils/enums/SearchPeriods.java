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
    TWENTYFOURHOURS("24"),
    /**
     * Represent 7 last days
     */
    SEVENDAYS("7"),
    /**
     * Represent Last 30 Days.
     */
    THIRTYDAYS("30"),
    /**
     * Represent All Time.
     */
    ALLTIME("all"),

    /**
     * Represent last 12 months.
     */
    ONEYEAR("365");

    /**
     *
     */
    private String periodAsString;

    /**
     *
     */
    private Integer periodAsNumOfDays;

    /**
     * Constructor.
     */
    SearchPeriods(final String optionAsString){
        this.periodAsString = optionAsString;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.periodAsString;
    }

    /**
     * Convert the {@link SearchPeriods} to {@link Integer}
     * @return
     */
    public Integer toNumber() {
        if(this ==ALLTIME )
        {
            return 1095;
        }
        else {
            return Integer.parseInt(this.periodAsString);
        }
    }

    /**
     * Return the period on days.
     * @return
     */
    public Integer toDays() {
        Integer days;
        if(this == TWENTYFOURHOURS){
            return days=1;
        }else if(this == ALLTIME){
            return days = 1095;
        } else{
    	    return this.periodAsNumOfDays;
        }
    }
}
