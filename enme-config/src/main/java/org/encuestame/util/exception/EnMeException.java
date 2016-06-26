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
package org.encuestame.util.exception;

 /**
 * Encuestame Exception.
 * @author Picado, Juan juanATencuestame.org
 * @since May 07, 2009
 */
public class EnMeException extends EnMeGenericException {

    /** serial. */
    private static final long serialVersionUID = 7631058192250904935L;

    /**
     * Constructor.
     */
    public EnMeException() {
        super();

    }
    /**
     * Exception.
     * @param message message
     * @param cause cause
     */
    public EnMeException(final String message, final Throwable cause) {
        super(message, cause);

    }
    /**
     * Exception.
     * @param message message
     */
    public EnMeException(final String message) {
        super(message);

    }

    /**
     * Exception.
     * @param cause cause
     */
    public EnMeException(final Throwable cause) {
        super(cause);
    }
}
