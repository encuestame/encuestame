/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email Utils.
 * @author Picado, Juan juanATencuestame.org
 * @since 21/05/2009 13:41:09
 * @version $Id$
 */
public class EmailUtils {

    /**
     * Validate Email.
     * @param email email
     * @return boolean
     */
    public static final boolean validateEmail(String email) {
        // Set the email pattern string
        final Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        // Match the given string with the pattern
        final Matcher m = p.matcher(email);
        // check whether match is found
        boolean matchFound = m.matches();
        if (matchFound){
            return true;
        }
        else{
            return false;
        }
    }

}
