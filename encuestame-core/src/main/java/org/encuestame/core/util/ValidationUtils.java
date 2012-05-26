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
package org.encuestame.core.util;

/**
 * Validations Utils.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 13, 2010 7:06:15 PM
 */
public final class ValidationUtils {

    public static final String EMAIL_REGEXP = "^[a-z0-9]+([_\\.-][a-z0-9]+)*@([a-z0-9]+([\\.-][a-z0-9]+)*)+\\.[a-z]{2,}$";

    public static final String NAME_PATTERN = "[A-Za-z0-9]*";

    public static final String CHARACTERS_PATTERN = "[^a-zA-Z0-9]";

    /**
     * Remove non-alphanumeric characters.
     * @param word
     * @return
     */
    public static String removeNonAlphanumericCharacters(String word) {
        word = word.replaceAll(ValidationUtils.CHARACTERS_PATTERN, "");
        return word;
    }

}
