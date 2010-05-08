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
import org.encuestame.utils.web.UnitPermission;
import org.encuestame.utils.web.UnitUserBean;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 * EnMeSchemaExport.
 * @author Picado, Juan juan@encuestame.org
 * @since October 19, 2009
 * @version $Id$
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
        annotationSF.createDatabaseSchema();

        try {
            //security service
            final SecurityService securityService = (SecurityService) appContext
            .getBean("securityService");

            final UnitPermission defaultUserPermission = new UnitPermission();
            // create encuestame user permission.
            defaultUserPermission.setPermission("ENCUESTAME_USER");
            defaultUserPermission.setDescription("ENCUESTAME_USER");
            securityService.createPermission(defaultUserPermission);

            // create permission admin
            final UnitPermission permissionAdmin = new UnitPermission();
            permissionAdmin.setPermission("ENCUESTAME_ADMIN");
            permissionAdmin.setDescription("ENCUESTAME_ADMIN");
            securityService.createPermission(permissionAdmin);

            //TODO: lazy exception problem with this script.

            //create user admin
            final SecUsers userPrimary = new SecUsers();
            userPrimary.setTwitterAccount("testEncuesta");
            userPrimary.setTwitterPassword("testEncuesta123");

            final SecUserDaoImp secUserDao = (SecUserDaoImp) appContext.getBean("secUserDao");
            secUserDao.saveOrUpdate(userPrimary);
            final UnitUserBean user = new UnitUserBean();
            user.setDateNew(new Date());
            user.setPrimaryUserId(userPrimary.getUid());
            user.setEmail("admin@encuestame.org");
            user.setPassword("12345");
            user.setUsername("admin");
            user.setName("admin");
            user.setStatus(true);
            //securityService.createUser(user);

            //admin user permission
            securityService.assignPermission(user, permissionAdmin);
            securityService.assignPermission(user, defaultUserPermission);

            final SecPermissionDaoImp secPermissionDaoImp = (SecPermissionDaoImp) appContext.getBean("secPermissionDaoImp");
            SecPermission d = secPermissionDaoImp.getPermissionById(1L);
            SecPermission d2 = secPermissionDaoImp.getPermissionById(2L);
            SecUserSecondary secondary = secUserDao.getSecondaryUserById(1L);
            secondary.getSecUserPermissions().add(d);
            secondary.getSecUserPermissions().add(d2);
            secUserDao.saveOrUpdate(secondary);

            final CatStateDaoImp stateDao = (CatStateDaoImp) appContext.getBean("catStateDaoImp");
            final CatState activate = new CatState();
            activate.setDescState("activate");
            stateDao.saveOrUpdate(activate);
            final CatState inactive = new CatState();
            inactive.setDescState("inactive");
            stateDao.saveOrUpdate(inactive);
            stateDao.saveOrUpdate(inactive);

        } catch (EnMeExpcetion e) {
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
