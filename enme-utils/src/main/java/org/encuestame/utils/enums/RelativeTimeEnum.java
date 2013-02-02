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
 * Relative Time Enumeration.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Dec 14, 2010 20:14:07 PM
 */
public enum RelativeTimeEnum {
    RIGTH_NOW, //##
    ONE_SECOND_AGO,
    SECONDS_AGO,
    A_MINUTE_AGO,
    MINUTES_AGO,
    AN_HOUR_AGO,
    HOURS_AGO,
    YESTERDAY, //##
    DAYS_AGO, //##
    ONE_MONTH_AGO,
    MONTHS_AGO,
    ONE_YEAR_AGO,
    YEARS_AGO,

    /**
     *
     */
    RelativeTimeEnum() {

    };

    /**
     * Relative time to number.
     * @return
     */
    public Integer toNumber() {
        Integer period = null;
        if (this == RIGTH_NOW) { period = 1; }
        else if (this == ONE_SECOND_AGO) { period = 15; }
        else if (this == SECONDS_AGO) { period = 70; }
        else if (this == A_MINUTE_AGO) { period = 1; }
        else if (this == MINUTES_AGO) { period = 80; }
        else if (this == AN_HOUR_AGO) { period = 1; }
        else if (this == HOURS_AGO) { period = 18; }
        else if (this == YESTERDAY) { period = 1; }
        else if (this == DAYS_AGO) { period = 4; }
        else if (this == ONE_MONTH_AGO) { period = 8; }
        else if (this == MONTHS_AGO) { period = 11; }
        else if (this == ONE_YEAR_AGO) { period = 1; }
        else if (this == YEARS_AGO) { period = 6; }
        return period;
    }
}


