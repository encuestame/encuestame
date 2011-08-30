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
package org.encuestame.core.security.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.encuestame.core.security.SecurityUtils;
import org.encuestame.core.security.util.HTMLInputFilter;
import org.encuestame.persistence.dao.IAccountDao;
import org.encuestame.persistence.dao.imp.AccountDaoImp;
import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * Encuestame user service implementation.
 * @author Picado, Juan juan@encuestame.org
 * @since 07/05/2009 14:19:02
 */
public class EnMeUserServiceImp implements UserDetailsService {

    /**
     * {@link AccountDaoImp}.
     */
    @Autowired
    private IAccountDao accountDao;

    /**
     * Define if group permissions should be added.
     */
    private Boolean roleGroupAuth = false;

    /**
     * Define if user permissions should be added, by default should be true.
     */
    private Boolean roleUserAuth = true;

    /**
     * Log.
     */
    private static Logger log = Logger.getLogger(EnMeUserServiceImp.class);

    /**
     * Setter.
     *
     * @param roleGroupAuth roleGroupAuth
     */
    public void setRoleGroupAuth(final Boolean roleGroupAuth) {
        this.roleGroupAuth = roleGroupAuth;
    }

    /**
     * Setter.
     *
     * @param userDao the userDao to set
     */
    public void setAccountDao(final IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Setter.
     *
     * @param roleUserAuth
     *            roleUserAuth
     */
    public void setRoleUserAuth(final Boolean roleUserAuth) {
        this.roleUserAuth = roleUserAuth;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    public UserDetails loadUserByUsername(String username){
        log.debug("loggin with username: {"+username+"}");
        log.debug("loggin with user dao instance: {"+this.accountDao+"}");
        //filter username.
        final HTMLInputFilter filter = new HTMLInputFilter(true);
        username = filter.filter(username);
        final UserAccount user = this.accountDao.getUserByUsername(username);
        log.debug("fetch username filtered: {"+user+"}");
        if (user == null) {
            log.error("user not found :{"+username);
            throw new UsernameNotFoundException("user not found");
        } else {
            log.debug("Logged with username: {"+user.getUsername()+" id: "+user.getUid()+"}");
            this.updateLoggedInfo(user);
            return SecurityUtils.convertUserAccountToUserDetails(user, this.roleUserAuth);
        }
    }

    /**
     * Update Logged Info.
     * @param userAccount {@link UserAccount}.
     */
    private void updateLoggedInfo(final UserAccount userAccount){
        final Calendar calendar = Calendar.getInstance();
        userAccount.setLastTimeLogged(calendar.getTime());
        log.debug("Updating logged time to:{"+calendar.getTime());
        accountDao.saveOrUpdate(userAccount);
    }
}
