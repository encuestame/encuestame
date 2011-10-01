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
package org.encuestame.oauth.security;

import org.encuestame.persistence.dao.imp.ApplicationDao;
import org.encuestame.persistence.domain.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth.common.OAuthException;
import org.springframework.security.oauth.provider.ConsumerDetails;
import org.springframework.security.oauth.provider.ConsumerDetailsService;

/**
 * Describe Consumer Details Services, implementes OAuth Security {@link ConsumerDetailsService}.
 * @author Picado, Juan juanATencuestame.org
 * @since Dec 23, 2010 11:06:54 AM
 * @version $Id:$
 */
public class EnMeConsumerDetailsService implements ConsumerDetailsService {

    /** {@link ApplicationDao}. **/
    @Autowired
    private ApplicationDao applicationDao;

    /* (non-Javadoc)
     * @see org.springframework.security.oauth.provider.ConsumerDetailsService#loadConsumerByConsumerKey(java.lang.String)
     */
    public ConsumerDetails loadConsumerByConsumerKey(String consumerKey)
            throws OAuthException {
       try {
           return consumerDetailsFor(this.applicationDao.getApplicationByKey(consumerKey));
       } catch (Exception e) {
           throw new OAuthException("Invalid OAuth consumer key " + consumerKey, e);
       }
    }

    /**
     * Create new Application Consumer Details.
     * @param application applications.
     */
    private ConsumerDetails consumerDetailsFor(Application application) {
        return new ApplicationConsumerDetails(application);
    }

    /**
     * @return the applicationDao
     */
    public ApplicationDao getApplicationDao() {
        return applicationDao;
    }

    /**
     * @param applicationDao the applicationDao to set
     */
    public void setApplicationDao(final ApplicationDao applicationDao) {
        this.applicationDao = applicationDao;
    }
}