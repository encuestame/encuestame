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
package org.encuestame.persistence.exception;
 /**
 * Encuestame Exception.
 * @author Picado, Juan juan@encuestame.org
 * @since May 07, 2009
 * @version $Id$
 */
public class EnMeExpcetion extends Exception{

    /** serial. */
    private static final long serialVersionUID = 7631058192250904935L;

    /**
     * Constructor.
     */
    public EnMeExpcetion() {
        super();

    }
    /**
     * Exception.
     * @param message message
     * @param cause cause
     */
    public EnMeExpcetion(final String message, final Throwable cause) {
        super(message, cause);

    }
    /**
     * Exception.
     * @param message message
     */
    public EnMeExpcetion(final String message) {
        super(message);

    }

    /**
     * Exception.
     * @param cause cause
     */
    public EnMeExpcetion(final Throwable cause) {
        super(cause);
    }
}
