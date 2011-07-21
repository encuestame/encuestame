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
package org.encuestame.mvc.controller.syndication;

import java.util.List;

import org.encuestame.mvc.controller.AbstractBaseOperations;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.utils.json.TweetPollBean;

/**
 * Abstract Feed Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2010 3:29:57 AM
 * @version $Id:$
 */
public abstract class AbstractFeedController extends AbstractBaseOperations{

    /**
     * Get TweetPolls.
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<TweetPollBean> getTweetPolls(final String username) throws EnMeNoResultsFoundException{
        return getTweetPollService().getTweetsPollsByUserName(username, null, null);
    }

}
