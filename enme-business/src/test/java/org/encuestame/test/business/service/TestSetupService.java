/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.encuestame.test.business.service;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.business.setup.StartupProcess;
import org.encuestame.business.setup.install.InstallDatabaseOperations;
import org.encuestame.core.admin.AdministratorProfile;
import org.encuestame.core.service.SetupOperations;
import org.encuestame.core.service.startup.DirectorySetupOperations;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.XMLConfigurationFileSupport;
import org.encuestame.persistence.dao.jdbc.InstallerDao;
import org.encuestame.persistence.dao.jdbc.InstallerOperations;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.TypeDatabase;
import org.encuestame.utils.web.UserAccountBean;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by jpicado on 27/12/14.
 */
@Category(DefaultTest.class)
public class TestSetupService extends AbstractServiceBase {

    @Autowired
    public SetupOperations setupOperations;

    @Resource(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate;

    @Resource(name = "installDaoTest")
    private InstallerOperations installerOperations;

    @Resource(name = "databaseInstallTest")
    private InstallDatabaseOperations installDatabaseOperations;

    @Autowired
    private StartupProcess startupProcess;

    /**
     * Own implementation for testing.
     */
    XMLConfigurationFileSupport xmlConfigurationFileSupport;

    @Before
    public void setupInit() throws Exception{
        try {
            DirectorySetupOperations.createConfileFile();

        this.xmlConfigurationFileSupport = new XMLConfigurationFileSupport();
        } catch (Exception e){
            Assert.assertTrue(false);
        }
        this.xmlConfigurationFileSupport.createConfigurationFile();
        EnMePlaceHolderConfigurer.setConfigurationManager(this.xmlConfigurationFileSupport);
        //XMLConfigurationFileSupport.getIntProperty("database.version");
        //FIXME: the application should be off due http://stackoverflow.com/questions/2039522/getting-a-no-thread-bound-request-found-error-from-spring-in-my-web-app
        // No thread-bound request found: Are you referring to request attributes outside of an actual web request, or processing a request outside of the originally receiving thread?
        // If you are actually operating within a web request and still receive this message, your code is probably running outside of DispatcherServlet/DispatcherPortlet: In this case, use RequestContextListener or RequestContextFilter to expose the current request.
        EnMePlaceHolderConfigurer.setProperty("application.offline.mode", "true");
        EnMePlaceHolderConfigurer.setProperty("short.default", "yourls");
        EnMePlaceHolderConfigurer.setProperty("short.yourls.key", "755bf7092e");
        EnMePlaceHolderConfigurer.setProperty("short.yourls.path", "http://jota.mobi/s/yourls-api.php?action=shorturl");
        EnMePlaceHolderConfigurer.setProperty("app.version", "1.2.0");
        this.startupProcess.startProcess();
    }
    @After
    public void setupEnd(){
        EnMePlaceHolderConfigurer.setProperty("application.offline.mode", "true");
    }

    /*
     * test to execute database SQL installation (HSQLDB) by default
     */
    @Test
    public void testinstallerOperations() throws Exception {
        // check if the data exist (see method info)
        boolean check = this.installDatabaseOperations.checkRequiredDataExist();
        Assert.assertNotNull(check);
        // drop all tables
        this.installDatabaseOperations.dropAll();
        // create database
        this.setupOperations.installDatabase();
        // create database -
        this.installDatabaseOperations.initializeDatabase(TypeDatabase.HSQLDB);
        this.setupOperations.demoInstall();
    }

    @Test
    public void testjdbcTemplate(){
       Assert.assertNotNull(this.jdbcTemplate);
    }

    @Test
    public void testpreCheckSetup(){
        String status = this.setupOperations.preCheckSetup();
        Assert.assertEquals(status, "no");
    }

    @Test
    public void testvalidateInstall() throws Exception{
       String uuid1  = this.xmlConfigurationFileSupport.getProperty("install.uuid");
       Assert.assertNull(uuid1);
       this.setupOperations.validateInstall();
       this.xmlConfigurationFileSupport.reloadConfigFile();
       EnMePlaceHolderConfigurer.getConfigurationManager().reloadConfigFile();
       String uuid2  = this.xmlConfigurationFileSupport.getProperty("install.uuid");
       Assert.assertNotNull(uuid2);
    }

    //@Test
    public void testcheckStatus() throws Exception{
        String status = this.setupOperations.checkStatus();
        Assert.assertEquals(status, "install");
        EnMePlaceHolderConfigurer.setProperty("app.version", "1.9.5");
        EnMePlaceHolderConfigurer.getConfigurationManager().getXmlConfiguration().addProperty("app.version", "1.5.0");
        String status2 = this.setupOperations.checkStatus();
        Assert.assertEquals(status2, "upgrade");
        EnMePlaceHolderConfigurer.setProperty("app.version", "1.5.1");
        String status3 = this.setupOperations.checkStatus();
        Assert.assertEquals(status3, "upgrade");
        EnMePlaceHolderConfigurer.setProperty("app.version", "1.5.0");
        String status4 = this.setupOperations.checkStatus();
        Assert.assertEquals(status4, "install");
    }

    @Test
    public void testloadInstallParameters() throws Exception{
        List list = this.setupOperations.loadInstallParameters();
        Assert.assertEquals(list.size(), 6);
    }

    @Test
    public void testcheckDatabase() throws Exception{
        Boolean status = this.setupOperations.checkDatabase();
        Assert.assertTrue(status);
    }

    @Test
    public void testcreateUserAdministration() throws Exception{
        final AdministratorProfile administratorProfile = new AdministratorProfile();
        administratorProfile.setPassword("12345");
        administratorProfile.setEmail("demo@demo.org");
        administratorProfile.setConfirmEmail("demo@demo.org");
        administratorProfile.setUsername("demoAdmon");
        administratorProfile.setConfirmPassword("12345");
        UserAccountBean userBean = this.setupOperations.createUserAdministration(administratorProfile);
        Assert.assertNotNull(userBean);
    }

}

