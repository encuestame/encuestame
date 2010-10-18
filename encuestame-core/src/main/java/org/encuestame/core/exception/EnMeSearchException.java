/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.exception;

/**
 * Represent exception for a search.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 17, 2010 9:03:14 PM
 * @version $Id:$
 */
public class EnMeSearchException extends EnMeExpcetion {

    /**
     * Serial
     */
    private static final long serialVersionUID = -220610572233612939L;

    /**
     * Constructor.
     */
    public EnMeSearchException() {
        super();
    }

    /**
     * Exception Search.
     * @param message message
     * @param cause throwable cause
     */
    public EnMeSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception Search.
     * @param message message
     */
    public EnMeSearchException(String message) {
        super(message);
    }

    /**
     * Exception Search.
     * @param cause cause
     */
    public EnMeSearchException(Throwable cause) {
        super(cause);
    }
}
