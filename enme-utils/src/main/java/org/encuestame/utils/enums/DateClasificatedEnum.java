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
 * Clasificated past dates.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 27, 2011
 * TODO: similar to {@link RelativeTimeEnum}
 */
public enum DateClasificatedEnum {
    TODAY,
    THIS_WEEK,
    THIS_MONTH,
    LAST_MONTH,
    FEW_MONTHS_AGO,
    LAST_YEAR,
    LONG_TIME_AGO,

    /**
     *
     */
    DateClasificatedEnum() {

    };

    /**
     * Relative time to number.
     * @return
     */
    public Integer toNumber() {
        Integer period = null;
        if (this == TODAY) { period = 1; }
        else if (this == THIS_WEEK) { period = 7; }
        else if (this == THIS_MONTH) { period = 30; }
        else if (this == LAST_MONTH) { period = 180; }
        else if (this == FEW_MONTHS_AGO) { period = 360; }
        else if (this == LAST_YEAR) { period = 366; }
        else if (this == LONG_TIME_AGO) { period = 720; }
        return period;
    }
}
