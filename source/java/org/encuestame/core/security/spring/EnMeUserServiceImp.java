package org.encuestame.core.security.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecUserPermission;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

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
 *
 * Id: EnMeUserServiceImp.java Date: 07/05/2009 14:19:02
 *
 * @author juanpicado package: org.encuestame.core.security.spring
 * @version 1.0
 */
public class EnMeUserServiceImp implements EnMeUserService, UserDetailsService {

    protected SecUserDaoImp userDao;
    protected Boolean roleGroupAuth = true;
    protected Boolean roleUserAuth;
    private static Logger log = Logger.getLogger(EnMeUserServiceImp.class);

    public void setUserDao(SecUserDaoImp userDao) {
        this.userDao = userDao;
    }

    public void setRoleGroupAuth(Boolean roleGroupAuth) {
        this.roleGroupAuth = roleGroupAuth;
    }

    public void setRoleUserAuth(Boolean roleUserAuth) {
        this.roleUserAuth = roleUserAuth;
    }

    /**
     * Carga el Usuario por nombre de usuario
     */
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        log.info("cargando el usuario por nombre");
        SecUsers user = userDao.getUserByUsername(username);
        if (user == null) {
            log.info("no encontrado...");
            throw new UsernameNotFoundException("username");
        }
        log.info("encontrado..."+user.getEmail());
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
     * @return
     */
    protected UserDetails convertToUserDetails(SecUsers user){
        List<String> listPermissions = new ArrayList<String>();
        if (user == null) {
            return null;
        }
        // verificamos si esta activado las autoridades por usuario
        log.info("verificamos si esta activado las autoridades por usuario...");
        if (this.roleGroupAuth == true) {
            List<SecGroupPermission> listGroupPermissions = userDao
                    .getGroupPermission(userDao.getUserGroups(user));
            log.info("listGroupPermissions.."+listGroupPermissions.size());
            log.info("listGroupPermissions "+ listGroupPermissions);
            if (listGroupPermissions != null && listGroupPermissions.size() > 0) {
                Iterator<SecGroupPermission> i = listGroupPermissions.iterator();
                while (i.hasNext()) {
                    SecGroupPermission secPermission = (SecGroupPermission) i.next();
                    if (listPermissions.indexOf(secPermission.getSecPermission().getPermission()
                            .trim()) != -1) {
                        log.info("Rol Ignorado Group "+secPermission.getSecPermission().getPermission());
                        // se ignora porque el rol ya existe
                    } else {
                        listPermissions.add(secPermission.getSecPermission().getPermission()
                                .trim());
                        log.info("Rol Agregado Group "+secPermission.getSecPermission().getPermission());

                    }
                }
            }
        }
        // verificamos si esta activado las autoridades del usuario
        if (this.roleUserAuth == true) {
            log.info("verificando permisos para el usuario");
            List<SecUserPermission> listUserPermissions = userDao
                    .getUserPermission(user);
            log.info("listUserPermissions "+listUserPermissions);
            if (listUserPermissions != null && listUserPermissions.size() > 0) {
                Iterator<SecUserPermission> i = listUserPermissions.iterator();
                while (i.hasNext()) {
                    SecUserPermission secPermission = (SecUserPermission) i.next();
                    if (listPermissions.indexOf(secPermission.getSecPermission().getPermission()
                            .trim()) != -1) {
                        // se ignora porque el rol ya existe
                        log.info("Rol Ignorado User "+secPermission.getSecPermission().getPermission()
                                .trim());
                    } else {
                        listPermissions.add(secPermission.getSecPermission().getPermission()
                                .trim());
                        log.info("Rol Agregado User "+secPermission.getSecPermission().getPermission()
                                .trim());
                    }
                }
            }
        }
        log.info("listPermissions TOTALES "+listPermissions.size());
        // agrupamos todas las autoridades en una lista
        // crea las autoridades de spring
        GrantedAuthority[] authorities = new GrantedAuthority[listPermissions
                .size()];
        int i = 0;
        for (String permission : listPermissions) {
            authorities[i++] = new GrantedAuthorityImpl(permission.trim());
        }

        User userDetails = new User(user.getUsername(), user.getPassword(),
                user.isStatus() == null ? false : user.isStatus(), true, // account
                // not
                // expired
                true, // cridentials not expired
                true, // account not locked
                authorities);
        log.info("userDetails "+userDetails);
        return userDetails;
    }

}
