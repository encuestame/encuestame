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
    RIGTH_NOW(1), //##
    ONE_SECOND_AGO(15),
    SECONDS_AGO(70),
    A_MINUTE_AGO(1),
    MINUTES_AGO(80),
    AN_HOUR_AGO(1),
    HOURS_AGO(18),
    YESTERDAY(1), //##
    DAYS_AGO(4), //##
    ONE_MONTH_AGO(8),
    MONTHS_AGO(11),
    ONE_YEAR_AGO(1),
    YEARS_AGO(6);

    /** **/
    private Integer relativeTimeAsString;

    /**
     *
     * @param optionAsString
     */
    RelativeTimeEnum(Integer optionAsString){
        this.relativeTimeAsString = optionAsString;
    }
}


