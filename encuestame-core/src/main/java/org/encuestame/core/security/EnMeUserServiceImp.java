/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.encuestame.persistence.domain.security.SecGroup;
import org.encuestame.persistence.domain.security.SecUserSecondary;
import org.encuestame.core.util.ConvertDomainsToSecurityContext;
import org.encuestame.persistence.dao.ISecUserDao;
import org.encuestame.persistence.dao.imp.SecUserDaoImp;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * Encuestame user service implementation.
 * @author Picado, Juan juan@encuestame.org
 * @since 07/05/2009 14:19:02
 * @version $Id$
 */
public class EnMeUserServiceImp implements EnMeUserService, UserDetailsService {

    /**
     * {@link SecUserDaoImp}.
     */
    private ISecUserDao secUserDao;

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
    public void setUserDao(final ISecUserDao userDao) {
        this.secUserDao = userDao;
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

    /**
     * Search user by username
     *
     * @param username
     *            username return {@link UserDetails}
     */
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException, DataAccessException {
        log.debug("username "+username);
        final SecUserSecondary user = secUserDao.getUserByUsername(username);
        if (user == null) {
            log.error("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        this.updateLoggedInfo(user);
        return null;
        //convertToUserDetails(user);
    }


    /**
     * Update Logged Info.
     * @param secUserSecondary
     */
    private void updateLoggedInfo(final SecUserSecondary secUserSecondary){
        final Calendar calendar = Calendar.getInstance();
        secUserSecondary.setLastTimeLogged(calendar.getTime());
        log.debug("Updating logged time "+calendar.getTime());
        secUserDao.saveOrUpdate(secUserSecondary);
    }

/*    *//**
     * Convert Survey User to Spring Security UserDetails
     *
     * @param user
     * @return {@link UserDetails}
     *//*
    protected UserDetails convertToUserDetails(final SecUserSecondary user) {
        log.debug("convertToUserDetails username "+user.getUsername());
        final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // search if authorities if the group are activated
        if (this.roleGroupAuth) {
            // search groups of the user
            final Set<SecGroup> groups = user.getSecGroups();
            for (final SecGroup secGroups : groups) {
                authorities.addAll(ConvertDomainsToSecurityContext.convertEnMePermission(secGroups.getSecPermissions()));
            }
        }
        // sec permissions
        if (this.roleUserAuth) {
            authorities.addAll(ConvertDomainsToSecurityContext.convertEnMePermission(user.getSecUserPermissions()));
        }

         //creating user details
         final EnMeUserDetails userDetails = new EnMeUserDetails(
         user.getUsername(),
         user.getPassword(),
         authorities,
         user.isUserStatus() == null ? false : user.isUserStatus(),
         true, // accoun not expired
         true, // cridentials not expired
         true, // account not locked
         user.getUserTwitterAccount() == null ? "" : user.getUserTwitterAccount(), //twitter account
         user.getCompleteName() == null ? "" : user.getCompleteName(), // complete name
         user.getUserEmail() // user email
         );
         userDetails.setAccountNonExpired(true);
         userDetails.setAccountNonLocked(true);
         log.debug("user details "+userDetails.getPassword());
         log.debug("user details "+userDetails.getPassword());
         log.debug("user details "+userDetails.getAuthorities());
         log.debug("user details "+userDetails.getUserEmail());
         return userDetails;
    }*/
}
