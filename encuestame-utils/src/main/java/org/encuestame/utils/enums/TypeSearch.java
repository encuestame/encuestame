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
 * Type Survey Search.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 5, 2010 10:32:22 PM
 * @version $Id:$
 */
public enum TypeSearch {
    KEYWORD,
    LASTDAY,
    LASTWEEK,
    THISMONTH,
    LAST30DAYS,
    LAST365DAYS,
    FAVOURITES,
    SCHEDULED,
    ALL,
    BYOWNER,
    FOLDER,
    TypeSearch(){
        //Constructor.
    };

    /**
     * To String.
     */
    public String toString() {
        String permission = "LASTDAY";
        if (this == LASTDAY) { permission = "LASTDAY"; }
        else if (this == KEYWORD) { permission = "KEYWORD"; }
        else if (this == BYOWNER) { permission = "BYOWNER"; }
        else if (this == FOLDER) { permission = "FOLDER"; }
        else if (this == LASTWEEK) { permission = "LASTWEEK"; }
        else if (this == FAVOURITES) { permission = "FAVOURITES"; }
        else if (this == SCHEDULED) { permission = "SCHEDULED"; }
        else if (this == THISMONTH) { permission = "THISMONTH"; }
        else if (this == LAST365DAYS) { permission = "LAST365DAYS"; }
        else if (this == LAST30DAYS) { permission = "LAST30DAYS"; }
        else if (this == ALL) { permission = "ALL"; }
        return permission;
    }

    /**
     * Get Type Search by String.
     * @param search period
     * @return
     */
    public static TypeSearch getSearchString(final String search) {
        if (null == search) { return null; }
        else if (search.equalsIgnoreCase("LASTDAY")) { return LASTDAY; }
        else if (search.equalsIgnoreCase("KEYWORD")) { return KEYWORD; }
        else if (search.equalsIgnoreCase("BYOWNER")) { return BYOWNER; }
        else if (search.equalsIgnoreCase("LASTWEEK")) { return LASTWEEK; }
        else if (search.equalsIgnoreCase("FOLDER")) { return FOLDER; }
        else if (search.equalsIgnoreCase("FAVOURITES")) { return FAVOURITES; }
        else if (search.equalsIgnoreCase("SCHEDULED")) { return SCHEDULED; }
        else if (search.equalsIgnoreCase("THISMONTH")) { return THISMONTH; }
        else if (search.equalsIgnoreCase("LAST365DAYS")) { return LAST365DAYS; }
        else if (search.equalsIgnoreCase("LAST30DAYS")) { return LAST30DAYS; }
        else if (search.equalsIgnoreCase("ALL")) { return ALL; }
        else return null;
    }
}
