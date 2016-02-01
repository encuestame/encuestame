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
    KEYWORD("KEYWORD"),
    LASTDAY("LASTDAY"),
    LASTWEEK("LASTWEEK"),
    THISMONTH("THISMONTH"),
    LAST30DAYS("LAST30DAYS"),
    LAST365DAYS("LAST365DAYS"),
    FAVOURITES("FAVOURITES"),
    SCHEDULED("FAVOURITES"),
    ALL("ALL"),
    BYOWNER("BYOWNER"),
    FOLDER("FOLDER"),
    DATE("DATE");

    /**
     *
     */
    private String typeSearchAsString;

    /**
     *
     * @param optionAsString
     */
    TypeSearch(String optionAsString){
        this.typeSearchAsString = optionAsString;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return this.typeSearchAsString;
    }
}
