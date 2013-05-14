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
package org.encuestame.utils.exception;

/**
 * EnMe generic exception.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2011
 */
public class EnMeGenericException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 4773692936156441054L;

    /**
     * Constructor.
     */
    public EnMeGenericException() {
        super();
    }

    /**
    * Exception.
    * @param message message
    * @param cause cause
    */
   public EnMeGenericException(final String message, final Throwable cause) {
       super(message, cause);

   }
   /**
    * Exception.
    * @param message message
    */
   public EnMeGenericException(final String message) {
       super(message);

   }

   /**
    * Exception.
    * @param cause cause
    */
   public EnMeGenericException(final Throwable cause) {
       super(cause);
   }
}
