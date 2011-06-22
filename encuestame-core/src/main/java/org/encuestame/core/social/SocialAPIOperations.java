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
package org.encuestame.core.social;

import org.encuestame.utils.TweetPublishedMetadata;



/**
 * Define basic social operations.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 20, 2011
 */
public interface SocialAPIOperations {

    SocialUserProfile getProfile() throws Exception;

    TweetPublishedMetadata updateStatus(final String status) throws Exception;

}
