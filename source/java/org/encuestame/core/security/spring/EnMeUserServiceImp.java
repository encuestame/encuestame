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
package org.encuestame.core.security.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
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
 * @version $Id$
 */
public class EnMeUserServiceImp implements EnMeUserService, UserDetailsService {

    protected ISecUserDao secUserDao;
    protected Boolean roleGroupAuth = true;
    protected Boolean roleUserAuth;
    private static Logger log = Logger.getLogger(EnMeUserServiceImp.class);

    /**
     * Setter.
     *
     * @param roleGroupAuth
     *            roleGroupAuth
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
        System.out.println("loading by username");
        System.out.println("username: " + username);
        final SecUserSecondary user = secUserDao.getUserByUsername(username);
        if (user == null) {
            log.error("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        System.out.println("found..." + user.getUserEmail());
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
     *
     * @param user
     * @return {@link UserDetails}
     */
    protected UserDetails convertToUserDetails(final SecUserSecondary user) {
        final List<String> listPermissions = new ArrayList<String>();
        if (user == null) {
            return null;
        }
        System.out.println("user is not null");
        // search if authorities if the group are activated
        System.out.println("check permission by group");
        if (this.roleGroupAuth == true) {
            // search groups of the user
            System.out.println("list group permissions");
            // sec groups
            final Set<SecGroups> groups = user.getSecGroups();
            for (SecGroups secGroups : groups) {
                final Set<SecPermission> groupPermissions = secGroups
                        .getSecPermissions();
                System.out.println("group permission "+groupPermissions.size());
                for (SecPermission secPermission : groupPermissions) {
                    if (listPermissions.indexOf(secPermission.getPermission()
                            .trim()) != -1) {
                        System.out.println("ignore permission"
                                + secPermission.getPermission());
                    } else {
                        listPermissions.add(secPermission.getPermission()
                                .trim());
                        System.out.println("permission added"
                                + secPermission.getPermission());
                    }
                }
            }
        }
        // sec permissions
        System.out.println("check permission by user");
        if (this.roleUserAuth == true) {
            final Set<SecPermission> permissions = user.getSecUserPermissions();
            System.out.println("group permission size"+permissions.size());
            for (SecPermission secPermission : permissions) {
                if (listPermissions.indexOf(secPermission.getPermission()
                        .trim()) != -1) {
                    System.out.println("permission ignored "
                            + secPermission.getPermission().trim());
                } else {
                    listPermissions.add(secPermission.getPermission().trim());
                    System.out.println("permission added "
                            + secPermission.getPermission().trim());
                }
            }
        }
         System.out.println("total permission " + listPermissions.size());
         final GrantedAuthority[] authorities = new
         GrantedAuthority[listPermissions
         .size()];
         System.out.println("authorities "+authorities);
         //convert list to array
         int i = 0;
         for (final String permission : listPermissions) {
         authorities[i++] = new GrantedAuthorityImpl(permission.trim());
         }
         //creating user details
         System.out.println("user detail");
         // System.out.println("user pass "+user.getPassword());
         System.out.println("user name "+user.getUsername());
         final User userDetails = new User(user.getUsername(),
         user.getPassword(),
         user.isUserStatus() == null ? false : user.isUserStatus(),
         true, // accoun not expired
         true, // cridentials not expired
         true, // account not locked
         authorities);
         System.out.println("userDetails " + userDetails);
         return userDetails;
    }
}
