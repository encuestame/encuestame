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
package org.encuestame.business.security.util;
/**
 * Password Generator.
 * @author Picado, Juan juan@encuestame.org
 * @since 21/05/2009 13:41:09
 * @version $Id$
 **/
public class PasswordGenerator {

    /** Numbers. **/
    public final static String numbers = "0123456789";
    /** Capitals. **/
    public final static String capitals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /** LowerCase. **/
    public final static String lowercase = "abcdefghijklmnopqrstuvwxyz";
    /** Symbols. **/
    public final static String specials = "��%&@%=[]?�{}#~,.*+$!";

    /**
     * Pin Number.
     * @return
     */
    public final static String getPinNumber() {
        return getPassword(numbers, 4);
    }

    /**
     * Especial Password.
     * @return
     */
    public final static String getEspecialPassword() {
        return getPassword(numbers + capitals + lowercase + specials, 12);
    }

    /**
     * Especial Password by Integer.
     * @param e password lenght.
     * @return
     */
    public final static String getEspecialPassword(Integer e) {
        return getPassword(numbers + capitals + lowercase + specials, e);
    }

    /**
     * Get Default Password. 8 Characters
     * @return
     */
    public final static String getPassword() {
        return getPassword(8);
    }

    /**
     * Get Password by length.
     * @param length length
     * @return
     */
    public final static String getPassword(int length) {
        return getPassword(numbers + capitals + capitals, length);
    }

    /**
     * Get Password by key and length.
     * @param key special key
     * @param length length.
     * @return
     */
    public final static String getPassword(String key, int length) {
        String pswd = "";

        for (int i = 0; i < length; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }

        return pswd;
    }

    /**
     * Get Numeric Password.
     * @param lenght lenght
     * @return
     */
    public final static String getNumericPassword(int lenght) {
        return getPassword(numbers, lenght);
    }
}
