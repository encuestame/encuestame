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
package org.encuestame.core.config;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Configuration Manager.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 4, 2011
 */
public class ConfigurationManager {

    /**
     * Log.
     */
    private static Log log = LogFactory.getLog(ConfigurationManager.class);

    /**
     *
     */
    private static String defaultPath = "encuestame-config.xml";

    /**
     * configuration store file.
     */
    private static XMLConfiguration xmlConfiguration = null;

    /**
     *
     * @param path
     * @throws ConfigurationException
     * @throws IOException
     */
    public ConfigurationManager(final String path) throws ConfigurationException, IOException {
         Resource res = new ClassPathResource(path);
         ConfigurationManager.xmlConfiguration = new XMLConfiguration(res.getFile());
         xmlConfiguration.setAutoSave(true);
         xmlConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    /**
     * Constructor.
     *
     * @throws IOException
     * @throws ConfigurationException
     */
    public ConfigurationManager() throws IOException, ConfigurationException {
        Resource res = new ClassPathResource(ConfigurationManager.defaultPath);
        this.setXmlConfiguration(new XMLConfiguration(res.getFile()));
        xmlConfiguration.setAutoSave(true);
        xmlConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

    /**
     *
     * @param property
     */
    public static String getProperty(final String property) {
        return xmlConfiguration.getString(property);
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
    public void setXmlConfiguration(XMLConfiguration xmlConfiguration) {
        this.xmlConfiguration = xmlConfiguration;
    }

    /**
     *
     * @return
     */
    public static Float getDatabaseVersion(){
        return xmlConfiguration.getFloat("database.version");
    }

    /**
     * @return the xmlConfiguration
     */
    public XMLConfiguration getXmlConfiguration() {
        return xmlConfiguration;
    }
}
