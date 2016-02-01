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
    TODAY(1),
    THIS_WEEK(7),
    THIS_MONTH(30),
    LAST_MONTH(180),
    FEW_MONTHS_AGO(360),
    LAST_YEAR(366),
    LONG_TIME_AGO(720);

    /** **/
    private Integer dateAsInteger;

    /**
     *
     * @param dateAsString
     */
    DateClasificatedEnum(final Integer dateAsString) {
        this.dateAsInteger = dateAsString;
    }

    /**
     *
     * @return
     */
    public Integer toNumber(){
        return this.dateAsInteger;
    }
}
