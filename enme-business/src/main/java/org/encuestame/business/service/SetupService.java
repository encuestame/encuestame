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
package org.encuestame.business.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.encuestame.business.setup.install.InstallDatabaseOperations;
import org.encuestame.business.setup.install.demo.CSVParser;
import org.encuestame.core.config.AdministratorProfile;
import org.encuestame.core.config.XMLConfigurationFileSupport;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.filter.RequestSessionMap;
import org.encuestame.core.service.AbstractBaseService;
import org.encuestame.core.service.SetupOperations;
import org.encuestame.core.service.imp.SecurityOperations;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.DateUtil;
import org.encuestame.utils.enums.TypeDatabase;
import org.encuestame.utils.social.SocialNetworkBean;
import org.encuestame.utils.web.UserAccountBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.Regexp;

/**
 * Define all setup operations.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 9, 2011
 */
@Service(value = "setupService")
public class SetupService extends AbstractBaseService implements SetupOperations {

    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Install database operations.
     */
    @Autowired
    private InstallDatabaseOperations install;

    /**
     *
     */
    @Autowired
    private CSVParser csvParser;

    /**
     *
     */
    @Autowired
    private SecurityOperations securityOperations;

    /**
     * Set {@link InstallDatabaseOperations}.
     *
     * @param install
     *            the install to set
     */
    public void setInstall(final InstallDatabaseOperations install) {
        this.install = install;
    }

    /**
     *
     * @return
     */
    @Deprecated
    private String getTypeDatabase() {
        final String typeDatabase = EnMePlaceHolderConfigurer
                .getConfigurationManager().getProperty("database.type");
        return typeDatabase;
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.SetupOperations#validateInstall()
     */
    public void validateInstall() {
        log.debug("validateInstall ------------");
        final XMLConfigurationFileSupport config = EnMePlaceHolderConfigurer.getConfigurationManager();
        log.debug("validateInstall ------------"+config.getXmlConfiguration().getBasePath());
        config.getXmlConfiguration().addProperty("install.date", DateUtil.getCurrentFormatedDate());
        config.getXmlConfiguration().addProperty("install.uuid", RandomStringUtils.randomAlphanumeric(50));
        log.debug("validateInstall ------------");
    }
    
    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.SetupOperations#finishInstall()
     */
    public void finishInstall(){
    	EnMePlaceHolderConfigurer.setSystemInstalled(Boolean.TRUE);
    }

    /**
     * @throws EnmeFailOperation
     * @throws IOException
     *
     */
    @Override
    public String installDatabase() {
        log.debug("installDatabase.....");
        try {
            this.install.initializeDatabase(TypeDatabase
                    .getTypeDatabaseByString(this.getTypeDatabase()));
        } catch (Exception e) {
            log.fatal(e);
            RequestSessionMap.setErrorMessage(e.getMessage());
            e.printStackTrace();
            return "fail";
        }
        return "ok"; //TODO: replace by enum in the future.
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.SetupOperations#checkDatabase()
     */
    public Boolean checkDatabase() {
        log.info("******** check database **********");
        final boolean check = this.install.checkDatabase();
        log.info("******** "+check+" **********");
        log.info("******** check database **********");
        return check;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.SetupOperations#upgradeDatabase()
     */
    @Override
    public void upgradeDatabase() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.SetupOperations#getSQLExecuted()
     */
    @Override
    public String getSQLExecuted() {
        log.debug("******************************************");
        log.debug("SQL " + this.install.getScriptLog());
        return this.install.getScriptLog();
    }

    /**
     * Check status version.
     * @return the status.
     * @throws EnMeExpcetion 
     */
    public String checkStatus() throws EnMeExpcetion {
        //TODO: replace by ENUMs
        log.debug("Check Version Status");
        String status = "install";
        final String currentVersion = EnMePlaceHolderConfigurer.getProperty("app.version");
        log.debug("Current Version : "+ currentVersion);
        final String installedVersion = EnMePlaceHolderConfigurer.getConfigurationManager().getInstalledVersion();
        log.debug("Installed Version : "+installedVersion);
        if (installedVersion != null) {
        	final int[] versionAsArrayCurrent = EnMeUtils.cleanVersion(currentVersion);
        	final int[] versionAsArrayInstalled = EnMeUtils.cleanVersion(installedVersion);            
            if (versionAsArrayCurrent[0] > versionAsArrayInstalled[0]) {
                status = "upgrade";
            } else if (versionAsArrayCurrent[0] == versionAsArrayInstalled[0]) {
            	 if (versionAsArrayCurrent[1] > versionAsArrayInstalled[1]) {
                 	status = "upgrade";
            	 } else if (versionAsArrayCurrent[1] == versionAsArrayInstalled[1]) {
            		 if (versionAsArrayCurrent[2] > versionAsArrayInstalled[2]) {
                      	status = "upgrade";
                      }
            	 }
            }
        }
        return status;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.core.service.SetupOperations#createAdmon(org.encuestame
     * .core.config.AdministratorProfile)
     */
    @Override
    public UserAccountBean createUserAdministration(
            AdministratorProfile administratorProfile) {
        log.debug("===============CREATE ADMON==============");
        final UserAccountBean account = this.securityOperations.createAdministrationUser(administratorProfile);
        return account;
    }

    /**
     * @return the securityOperations
     */
    public SecurityOperations getSecurityOperations() {
        return securityOperations;
    }

    /**
     * @param securityOperations
     *            the securityOperations to set
     */
    public void setSecurityOperations(
            final SecurityOperations securityOperations) {
        this.securityOperations = securityOperations;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.core.service.SetupOperations#removeTables()
     */
    @Override
    public Boolean removeTables() {
         try {
             this.install.dropAll();
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             log.fatal(e);
             RequestSessionMap.setErrorMessage(e.getMessage());
             return false;
         }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.SetupOperations#demoInstall()
     */
    @Override
    public void demoInstall() {
        try {
            this.csvParser.executeCSVDemoInstall(EnMePlaceHolderConfigurer
                    .getIntegerProperty("demo.votes.by.tppoll"),
                    EnMePlaceHolderConfigurer
                    .getIntegerProperty("demo.votes.by.poll"),
                    EnMePlaceHolderConfigurer
                    .getIntegerProperty("demo.votes.by.survey"));
        } catch (Exception e) {
            e.printStackTrace();
            log.fatal(e);
            RequestSessionMap.setErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.SetupOperations#loadInstallParameters()
     */
    @Override
    public List<String> loadInstallParameters() {
        final List<String> parameters = new ArrayList<String>();
        parameters.add(this.createParameter("database", EnMePlaceHolderConfigurer.getProperty("datasource.database")));
        parameters.add(this.createParameter("jdbc-url", EnMePlaceHolderConfigurer.getProperty("datasource.urldb")));
        parameters.add(this.createParameter("jdbc-driver", EnMePlaceHolderConfigurer.getProperty("datasource.classname")));
        parameters.add(this.createParameter("username", EnMePlaceHolderConfigurer.getProperty("datasource.userbd")));
        parameters.add(this.createParameter("password", EnMePlaceHolderConfigurer.getProperty("datasource.pass")));
        parameters.add(this.createParameter("dialect", EnMePlaceHolderConfigurer.getProperty("datasource.dialect")));
        return parameters;
    }

    /**
     *
     * @return
     */
    private String createParameter(final String value, final String paramValue){
        final StringBuffer param = new StringBuffer();
        param.append(value);
        param.append(" : ");
        param.append(paramValue);
        return param.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.SetupOperations#addNewSocialNetworkConfiguration()
     */
    @Override
    public void addNewSocialNetworkConfiguration() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.SetupOperations#removeSocialNetworkConfiguration()
     */
    @Override
    public void removeSocialNetworkConfiguration() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.core.service.SetupOperations#listAllNetworkConfigurationSocial()
     */
    @Override
    public List<SocialNetworkBean> listAllNetworkConfigurationSocial() {
    	
        return null;
    }

    /**
     * @return the csvParser
     */
    public CSVParser getCsvParser() {
        return csvParser;
    }

    /**
     * @param csvParser the csvParser to set
     */
    public void setCsvParser(CSVParser csvParser) {
        this.csvParser = csvParser;
    }

	@Override
	public void checkSocialNetworks() {
		// TODO Auto-generated method stub
		
	}
}
