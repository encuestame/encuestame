/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.integration.gateway;

import org.springframework.integration.annotation.Gateway;

/**
 * Spring Integration Message Gateway for sending twitter messages.
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 12, 2011 1:24:36 PM
 */
public interface TwitterGateway {

    void publishTweet(String message);
}
