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
package org.encuestame.mvc.controller.social;

import org.encuestame.business.service.social.OAuth1RequestFlow;
import org.encuestame.business.service.social.OAuth2RequestFlow;
import org.encuestame.mvc.controller.AbstractBaseOperations;

/**
 * Define helper methods to social networks OAuth connect.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 25, 2010 4:47:05 PM
 */
public abstract class AbstractSocialController extends AbstractBaseOperations {

    /**
     * {@link OAuth1RequestFlow}
     */
    public OAuth1RequestFlow auth1RequestProvider;

    /**
     *
     */
    public OAuth2RequestFlow auth2RequestProvider;

}
