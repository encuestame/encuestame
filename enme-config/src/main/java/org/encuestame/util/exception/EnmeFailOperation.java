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
 * EnMe Fail Operation.
 *
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since December 8, 2010
 */
public class EnmeFailOperation extends EnMeException {
    /**
     * Serial.
     */
    private static final long serialVersionUID = -120650589333637899L;

    public EnmeFailOperation() {
        super();
    }

    public EnmeFailOperation(String message, Throwable cause) {
        super(message, cause);
    }

    public EnmeFailOperation(String message) {
        super(message);
    }

    public EnmeFailOperation(Throwable cause) {
        super(cause);
    }
}
