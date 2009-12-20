/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.core.security.util;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Password Utils.
 * @author Picado, Juan juan@encuestame.org
 * @since 19/12/2009 22:37:32
 * @version $Id: $
 */
public class EnMePasswordUtils {

    /**
     * Encrypt the password.
     * @param password password
     * @return password encrypt
     */
    public static String encryptPassworD(final String password) {
        final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.encryptPassword(password);
    }


    /**
     * Check if password is correct.
     * @param inputPassword input password
     * @param encryptedPassword encrypted password
     * @return if correct true and if not false
     */
    public static Boolean checkPassword(final String inputPassword, final String encryptedPassword ){
        final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        if (passwordEncryptor.checkPassword(inputPassword, encryptedPassword)) {
            // correct
            return true;
          } else {
            // bad password
            return false;
          }
    }
}
