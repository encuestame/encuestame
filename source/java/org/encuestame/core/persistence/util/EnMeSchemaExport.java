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
package org.encuestame.core.persistence.util;

import java.util.Date;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.CatStateDaoImp;
import org.encuestame.core.persistence.dao.SecPermissionDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.service.SecurityService;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.admon.UnitUserBean;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 * EnMeSchemaExport.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 19, 2009
 */
public class EnMeSchemaExport {

    /** spring config files. **/
    private static final String[] SPRING_CONFIG_FILES = new String[] {
            "source/config/spring/encuestame-hibernate-context.xml",
            "source/config/spring/encuestame-dao-context.xml",
            "source/config/spring/encuestame-param-context.xml",
            "source/config/spring/encuestame-service-context.xml",
            "source/config/spring/encuestame-email-context.xml" };

    /** spring config files. **/
    private static final String[] SPRING_CONFIG_TEST_FILES = new String[] {
        "source/config/spring/encuestame-hibernate-context.xml",
        "source/config/spring/encuestame-dao-context.xml",
        "source/config/spring/encuestame-param-test-context.xml",
        "source/config/spring/encuestame-service-context.xml",
        "source/config/spring/encuestame-email-context.xml" };

    /**
     * Drop schema and create schema.
     */
    public void create() {
        final FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext(
                SPRING_CONFIG_FILES);
        final AnnotationSessionFactoryBean annotationSF = (AnnotationSessionFactoryBean) appContext
                .getBean("&sessionFactory");
        annotationSF.dropDatabaseSchema();
        final SecurityService securityService = (SecurityService) appContext
                .getBean("securityService");

        annotationSF.createDatabaseSchema();
        try {
            final UnitPermission permissionBean = new UnitPermission();
            // create encuestame user permission.
            permissionBean.setPermission("ENCUESTAME_USER");
            permissionBean.setDescription("ENCUESTAME_USER");
            securityService.createPermission(permissionBean);
            // create permission admin
            final UnitPermission permissionAdmin = new UnitPermission();
            permissionAdmin.setPermission("ENCUESTAME_ADMIN");
            permissionAdmin.setDescription("ENCUESTAME_ADMIN");
            securityService.createPermission(permissionAdmin);

            //create user admin
            final SecUsers userPrimary = new SecUsers();
            final SecUserDaoImp secUserDao = (SecUserDaoImp) appContext.getBean("secUserDao");
            secUserDao.saveOrUpdate(userPrimary);
            final UnitUserBean user = new UnitUserBean();
            user.setDateNew(new Date());
            user.setPrimaryUserId(userPrimary.getUid());
            user.setEmail("juan@encuestame.org");
            user.setPassword("12345");
            user.setUsername("admin");
            user.setName("admin");
            user.setStatus(true);
            securityService.createUser(user);
            //admin user permission
            //securityService.assignPermission(user, permissionAdmin);

            final SecPermissionDaoImp secPermissionDaoImp = (SecPermissionDaoImp) appContext.getBean("secPermissionDaoImp");
            SecPermission d = secPermissionDaoImp.getPermissionById(1L);
            SecPermission d2 = secPermissionDaoImp.getPermissionById(2L);
            SecUserSecondary s = secUserDao.getSecondaryUserById(1L);
            s.getSecUserPermissions().add(d);
            s.getSecUserPermissions().add(d2);
            secUserDao.saveOrUpdate(s);


            System.out.println("permisos total -> "+secUserDao.getSecondaryUserById(1L).getSecUserPermissions().size());

          /*  final CatStateDaoImp stateDao = (CatStateDaoImp) appContext.getBean("catStateDaoImp");
            final CatState activate = new CatState();
            activate.setDescState("activate");
            stateDao.saveOrUpdate(activate);
            final CatState inactive = new CatState();
            inactive.setDescState("inactive");
            stateDao.saveOrUpdate(inactive);
            stateDao.saveOrUpdate(inactive);*/

        } catch (EnMeExpcetion e) {
            System.out.println("Error create data " + e.getMessage());
        }
        //Create test database

        final FileSystemXmlApplicationContext appContextTest = new FileSystemXmlApplicationContext(
                SPRING_CONFIG_TEST_FILES);
        final AnnotationSessionFactoryBean annotationSFTest = (AnnotationSessionFactoryBean) appContextTest
                .getBean("&sessionFactory");
        annotationSFTest.dropDatabaseSchema();
        annotationSFTest.createDatabaseSchema();
    }

    /**
     * @param args args
     */
    public static void main(String[] args) {
        new EnMeSchemaExport().create();
    }

}
