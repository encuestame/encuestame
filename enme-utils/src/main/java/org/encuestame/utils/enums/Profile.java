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
 * User Account profile.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 5, 2011 11:18:56 AM
 */
public enum Profile{
    EMAIL("EMAIL"),
    USERNAME("USERNAME"),
    LANGUAGE("LANGUAGE"),
    PICTURE("PICTURE"),
    PRIVATE("PRIVATE"),
    WELCOME("WELCOME"),
    PAGE_INFO("PAGE_INFO"),
    REAL_NAME("REAL_NAME");

    /** **/
    private String profileAsString;

    /**
     * Constructor
     * @param optionAsString
     */
    Profile(String optionAsString){
        this.profileAsString = optionAsString;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return this.profileAsString;
    }
}
