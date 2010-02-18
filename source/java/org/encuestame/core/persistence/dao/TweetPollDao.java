/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.core.persistence.dao;

import org.encuestame.core.persistence.dao.imp.ITweetPoll;
import org.encuestame.core.persistence.pojo.TweetPoll;
import org.hibernate.HibernateException;

/**
 * TweetPoll Dao Implementation.
 * @author Picado, Juan juan@encuestame.org
 * @since Feb 17, 2010 8:26:57 PM
 * @version $Id:$
 */
public class TweetPollDao extends AbstractHibernateDaoSupport implements ITweetPoll{

    /**
     * Get TweetPoll by Id.
     * @param tweetPollId tweetPollId
     * @return {@link TweetPoll}
     * @throws HibernateException exception
     */
    public TweetPoll getTweetPollById(final Long tweetPollId) throws HibernateException {
        return (TweetPoll) getHibernateTemplate().get(TweetPoll.class, tweetPollId);
}
}
