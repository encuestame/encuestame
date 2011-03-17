
package org.encuestame.business.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 * Encuestame config file.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 14, 2011
 */
public class EncuestameConfig {

    private static String encuestameConfigFileName = "/org/encuestame/config/encuestame-config.properties";
    private static String encuestameOverwriteConfigFileName = "/encuestame-config-overwrite.properties";
    private static String overwriteJvmParam = "encuestame.overwrite.config";
    private static File customEncuestameFile = null;

    private static Properties configHolderProperty;

    private static Log log = LogFactory.getLog(EncuestameConfig.class);

    /*
     * Static block run once at class loading
     *
     * We load the default properties and any custom properties we find
     */
    static {
        configHolderProperty = new Properties();

        try {
            // we'll need this to get at our properties files in the classpath
            Class configClass = Class.forName("org.encuestame.business.config.EncuestameConfig");

            // first, lets load our default properties
            InputStream is = configClass.getResourceAsStream(encuestameConfigFileName);
            configHolderProperty.load(is);

            // now, see if we can find our custom config
            is = configClass.getResourceAsStream(encuestameOverwriteConfigFileName);
            if(is != null) {
                configHolderProperty.load(is);
                log.info("Encuestame : Loading custom properties.");
            } else {
                log.error("Encuestame : Loading default properties, custom configuration properties not found.");
            }
            // finally, check for an external config file
            final String enviromentPropertyFile = System.getProperty(overwriteJvmParam);
            if(enviromentPropertyFile != null && enviromentPropertyFile.length() > 0) {
                customEncuestameFile = new File(enviromentPropertyFile);
                // make sure the file exists, then try and load it
                if(customEncuestameFile != null && customEncuestameFile.exists()) {
                    is = new FileInputStream(customEncuestameFile);
                    configHolderProperty.load(is);
                    System.out.println("EncuestameConfig file: Successfully loaded custom properties from "+
                            customEncuestameFile.getAbsolutePath());
                } else {
                    System.out.println("EncuestameConfig file: Failed to load custom properties from "+
                            customEncuestameFile.getAbsolutePath());
                }

            }

            // Now expand system properties for properties in the config.expandedProperties list,
            // replacing them by their expanded values.
            /*String expandedPropertiesDef = (String) configHolderProperty.get("config.expandedProperties");
            if (expandedPropertiesDef != null) {
                String[] expandedProperties = expandedPropertiesDef.split(",");
                for (int i = 0; i < expandedProperties.length; i++) {
                    String propName = expandedProperties[i].trim();
                    String initialValue = (String) configHolderProperty.get(propName);
                    if (initialValue != null) {
                        String expandedValue = PropertyExpander.expandSystemProperties(initialValue);
                        configHolderProperty.put(propName,expandedValue);
                    }
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
            log.fatal("Encuestame Config: "+e);
        }
    }

    /**
     * Get properties starting with a specified string.
     */
    public static Properties getPropertiesStartingWith(String startingWith) {
        Properties props = new Properties();
        for (Enumeration it = configHolderProperty.keys(); it.hasMoreElements();) {
            String key = (String)it.nextElement();
            props.put(key, configHolderProperty.get(key));
        }
        return props;
    }
}
