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
package org.encuestame.persistence.exception;

/**
 * EnMe Domain not found Exception.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 9, 2010 1:27:09 PM
 * @version $Id:$
 */
public class EnMeOAuthSecurityException extends Exception{

    /**
     * Serial.
     */
    private static final long serialVersionUID = -120650572833612949L;

    public EnMeOAuthSecurityException() {
        super();
    }

    public EnMeOAuthSecurityException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public EnMeOAuthSecurityException(String message) {
        super(message);
    }

    public EnMeOAuthSecurityException(Throwable cause) {
        super(cause);
    }
}
