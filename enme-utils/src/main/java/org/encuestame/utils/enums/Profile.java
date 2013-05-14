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
    EMAIL,
    USERNAME,
    LANGUAGE,
    PICTURE,
    PRIVATE,
    REAL_NAME;

    Profile(){
    }

    /**
     * Find Profile.
     * @param value
     * @return
     */
    public static Profile findProfile(final String value) {
        Profile result = null;
        if (null != value) {
           if ("EMAIL".equalsIgnoreCase(value)) { result = EMAIL; }
           if ("USERNAME".equalsIgnoreCase(value)) { result = USERNAME; }
           if ("PICTURE".equalsIgnoreCase(value)) { result = PICTURE; }
           if ("LANGUAGE".equalsIgnoreCase(value)) { result = LANGUAGE; }
           if ("PRIVATE".equalsIgnoreCase(value)) { result = PRIVATE; }
           if ("REAL_NAME".equalsIgnoreCase(value)) { result = REAL_NAME; }
        }
        return result;
    }



    /**
     * To String.
     */
    public String toString() {
        String type = "";
        if (this == EMAIL) { type = "email"; }
        else if (this == USERNAME) { type = "username"; }
        else if (this == REAL_NAME) { type = "completeName"; }
        else if (this == PICTURE) { type = "picture"; }
        else if (this == LANGUAGE) { type = "language"; }
        else if (this == PRIVATE) { type = "private"; }
        return type;
    }
}
