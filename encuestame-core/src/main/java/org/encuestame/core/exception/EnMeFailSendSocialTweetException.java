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
package org.encuestame.core.exception;

import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;

/**
 * Indicates no connection exists between a {@link UserAccount} and a service provider with the submitted access token.
 * @author Picado, Juan juanATencuestame.org
 * @since April 04, 2011
 */
@SuppressWarnings("serial")
public final class EnMeFailSendSocialTweetException extends EnMeExpcetion {

    /**
     * Constructor.
     */
    public EnMeFailSendSocialTweetException() {
        super();

    }
    /**
     * Exception.
     * @param message message
     * @param cause cause
     */
    public EnMeFailSendSocialTweetException(final String message, final Throwable cause) {
        super(message, cause);

    }
    /**
     * Exception.
     * @param message message
     */
    public EnMeFailSendSocialTweetException(final String message) {
        super(message);

    }

    /**
     * Exception.
     * @param cause cause
     */
    public EnMeFailSendSocialTweetException(final Throwable cause) {
        super(cause);
    }
}