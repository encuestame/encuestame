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
package org.encuestame.core.security.service;

import org.apache.log4j.Logger;
import org.encuestame.core.exception.EnMeNoSuchAccountConnectionException;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.security.SocialAccountConnectionException;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Check
 * @author Picado, Juan juanATencuestame.org
 * @since May 2, 2011
 */
public class EnMeSocialAccountUserService implements SocialUserService {

    @Autowired
    private IAccountDao accountDao;

    /*
     * Log.
     */
    private Logger log = Logger.getLogger(EnMeSocialAccountUserService.class);

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.security.service.SocialUserService#loadAccountConnection(java.lang.String, org.encuestame.persistence.domain.social.SocialProvider)
     */
    @Override
    public UserDetails loadAccountConnection(
            final String profileId,
            final SocialProvider provider)
            throws EnMeNoSuchAccountConnectionException {
        UserAccount accountConnection = null;
        log.debug("EnMeSocialAccountUserService "+profileId);
        log.debug("EnMeSocialAccountUserService "+provider);
        try {
           accountConnection = this.accountDao.findAccountByConnection(provider, profileId);
        } catch (EnMeNoResultsFoundException e) {
            throw new SocialAccountConnectionException("connection invalid", e);
        }
        return  SecurityUtils.convertUserAccountToUserDetails(accountConnection, false);
    }

    /**
     * @return the accountDao
     */
    public IAccountDao getAccountDao() {
        return accountDao;
    }

    /**
     * @param accountDao the accountDao to set
     */
    public void setAccountDao(final IAccountDao accountDao) {
        this.accountDao = accountDao;
    }


}
