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
package org.encuestame.core.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.files.PathUtil;
import org.encuestame.core.service.startup.DirectorySetupOperations;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.encuestame.utils.social.SocialNetworkBean;

/**
 * Provide support to modify default xml config file.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 4, 2011
 */
public class XMLConfigurationFileSupport {

    /**
     * Log.
     */
    private static Log log = LogFactory
            .getLog(XMLConfigurationFileSupport.class);

    /**
     * configuration store file.
     */
    private static XMLConfiguration xmlConfiguration = null;

    /**
     * Constructor.
     *
     * @throws IOException
     * @throws ConfigurationException
     */
    public XMLConfigurationFileSupport() throws IOException,
            ConfigurationException {
        this.createConfigurationFile();
    }

    /**
     *
     * @throws ConfigurationException
     */
    public void createConfigurationFile(){
            try {
                final File file = new File(buildConfigFilePath());
                this.reloadConfiguration(file);
            } catch (ConfigurationException e) {
                log.fatal(e);
            } catch (EnmeFailOperation e) {
                 log.fatal(e);
                //e.printStackTrace();
            }
    }

    /**
     *
     * @return
     * @throws EnmeFailOperation
     */
    private String buildConfigFilePath() throws EnmeFailOperation {
        final StringBuffer stringBuffer = new StringBuffer(
                DirectorySetupOperations.getHomeDirectory());
        stringBuffer.append(PathUtil.configFileName);
        return stringBuffer.toString();
    }

    /**
     *
     * @param file
     * @throws ConfigurationException
     */
    private void reloadConfiguration(final File file) throws ConfigurationException {
        log.debug("createConfiguration " + file.exists());
        log.debug("createConfiguration.... " + file.getAbsolutePath());
        XMLConfigurationFileSupport.xmlConfiguration = new XMLConfiguration(file);
        XMLConfigurationFileSupport.xmlConfiguration.setAutoSave(true);
        XMLConfigurationFileSupport.xmlConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    /**
     * @throws ConfigurationException
     *
     */
    public void reloadConfigFile() {
        log.debug("reloadConfigFile");
        try {
            final File file = new File(buildConfigFilePath());
            this.reloadConfiguration(file);
        } catch (ConfigurationException e) {
            //e.printStackTrace();
            log.fatal(e);
            log.fatal("not able to reload configuration manager");
        } catch (EnmeFailOperation e) {
             //e.printStackTrace();
             log.fatal(e);
             log.fatal("not able to reload configuration manager");
        }
    }

    /**
     *
     * @param property
     */
    public String getProperty(final String property) {
        if (XMLConfigurationFileSupport.xmlConfiguration == null) {
            createConfigurationFile();
        }
        return XMLConfigurationFileSupport.xmlConfiguration.getString(property);
    }

    /**
     *
     * @param property
     */
    public static Integer getIntProperty(final String String) {
        return xmlConfiguration.getInt(String);
    }

    /**
     * @param xmlConfiguration
     *            the xmlConfiguration to set
     */
    public void setXmlConfiguration(final XMLConfiguration xmlConfiguration) {
        XMLConfigurationFileSupport.xmlConfiguration = xmlConfiguration;
    }

    /**
     *
     * @param networkBean
     */
    public void addNewSocialNetworkConfiguration(final SocialNetworkBean networkBean) {
          //XMLConfigurationFileSupport.xmlConfiguration
    }

    /**
     *
     * @param socialNetworkName
     */
    public void removeSocialNetworkConfiguration(final String socialNetworkName) {
        // TODO Auto-generated method stub
    }

    /**
     *
     * @return
     */
    public List<SocialNetworkBean> listAllNetworkConfigurationSocial() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *
     * @return
     */
    public Integer getDatabaseVersion() {
        return xmlConfiguration.getInt("database.version");
    }

    /**
     *
     * @return
     */
    public String getInstalledVersion() {
        return xmlConfiguration.getString("version");
    }

    /**
     * @return the xmlConfiguration
     */
    public XMLConfiguration getXmlConfiguration() {
        return xmlConfiguration;
    }
}