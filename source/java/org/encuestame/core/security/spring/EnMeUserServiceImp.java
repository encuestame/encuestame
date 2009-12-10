/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.security.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * Encuestame user service implementation.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 07/05/2009 14:19:02
 */
public class EnMeUserServiceImp implements EnMeUserService, UserDetailsService {

    protected SecUserDaoImp secUserDao;
    protected Boolean roleGroupAuth = true;
    protected Boolean roleUserAuth;
    private static Logger log = Logger.getLogger(EnMeUserServiceImp.class);

    /**
     * Setter.
     * @param roleGroupAuth roleGroupAuth
     */

    public void setRoleGroupAuth(final Boolean roleGroupAuth) {
        this.roleGroupAuth = roleGroupAuth;
    }

    /**
     * Setter.
     *
     * @param userDao
     *            the userDao to set
     */
    public void setUserDao(SecUserDaoImp userDao) {
        this.secUserDao = userDao;
    }

    /**
     * Setter.
     *
     * @param roleUserAuth roleUserAuth
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
        log.info("loading by username");
        log.info("username: "+username);
        final SecUserSecondary user = secUserDao.getUserByUsername(username);
        if (user == null) {
            log.warn("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        log.info("found..." + user.getUserEmail());
        return convertToUserDetails(user);
    }

    public SecUsers getSurveyUser(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSurveyUserPassword(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Convert Survey User to Spring Security UserDetails
     * @param user
     * @return {@link UserDetails}
     */
    protected UserDetails convertToUserDetails(final SecUserSecondary user) {
        final List<String> listPermissions = new ArrayList<String>();
        if (user == null) {
            return null;
        }
        // search if authorities if the group are activated
        log.info("verificamos si esta activado las autoridades por grupo...");
        if (this.roleGroupAuth == true) {
            //search groups of the user
            log.info("list group permissions");
            final List<SecGroupPermission> listGroupPermissions = secUserDao
                    .getGroupPermission(secUserDao.getUserGroups(user.getSecUser()));
                //iterator list of groups permissions
                final Iterator<SecGroupPermission> iterator = listGroupPermissions
                        .iterator();
                log.info("list group ite permissions");
                while (iterator.hasNext()) {
                    final SecGroupPermission secPermission = (SecGroupPermission) iterator
                            .next();
                    //search if permission exits in the list, if permission exists are ignored
                    if (listPermissions.indexOf(secPermission
                            .getSecPermission().getPermission().trim()) != -1) {
                        log.warn("ignore permission"
                                + secPermission.getSecPermission()
                                        .getPermission());
                    } else {
                        listPermissions.add(secPermission.getSecPermission()
                                .getPermission().trim());
                        log.info("permission added"
                                + secPermission.getSecPermission()
                                        .getPermission());
                    }
                }
            }
        // verify is user permission flag is activated
        log.info("verify is user permission flag is activated");
        if (this.roleUserAuth == true) {
            log.info("list user permissions");
           final List<SecUserPermission> listUserPermissions = secUserDao
                    .getUserPermission(user.getSecUser());
                Iterator<SecUserPermission> iteratorUser = listUserPermissions.iterator();
                while (iteratorUser.hasNext()) {
                    final SecUserPermission secPermission = (SecUserPermission) iteratorUser
                            .next();
                    if (listPermissions.indexOf(secPermission
                            .getSecPermission().getPermission().trim()) != -1) {
                        log.info("permission ignored "
                                + secPermission.getSecPermission()
                                        .getPermission().trim());
                    } else {
                        listPermissions.add(secPermission.getSecPermission()
                                .getPermission().trim());
                        log.info("permission added "
                                + secPermission.getSecPermission()
                                        .getPermission().trim());
                    }
                }
            }
        log.info("total permission " + listPermissions.size());
        final GrantedAuthority[] authorities = new GrantedAuthority[listPermissions
                .size()];
        //convert list to array
        int i = 0;
        for (final String permission : listPermissions) {
            authorities[i++] = new GrantedAuthorityImpl(permission.trim());
        }
        //creating user details
        log.info("user detail");
        log.info("user pass "+user.getPassword());
        log.info("user name "+user.getUsername());
        final User userDetails = new User(user.getUsername(), user.getPassword(),
                user.isUserStatus() == null ? false : user.isUserStatus(),
                true,  // accoun  not expired
                true, // cridentials not expired
                true, // account not locked
                authorities);
        log.info("userDetails " + userDetails);
        return userDetails;
    }
}
