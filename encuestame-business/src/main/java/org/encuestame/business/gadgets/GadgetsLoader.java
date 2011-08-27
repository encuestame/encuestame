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
package org.encuestame.business.gadgets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * Gadgets Loader.
 * @author Picado, Juan juanATencuestame.org
 * @since 04/08/2011
 */
public class GadgetsLoader {

    /**
     *
     */
    final static List<Properties> directory = new ArrayList<Properties>();

    /**
     *
     */
    final static HashMap<String, Properties> mapDirectory = new HashMap<String, Properties>();

    /**
     *
     */
    private Resource[] gadgets;

    /**
     * Log.
     */
    private static final Log log = LogFactory.getLog(GadgetsLoader.class);

    /**
     * Init the load of gadgets.
     */
    public void load(){
        if (this.gadgets != null) {
            for (int i = 0; i < this.gadgets.length; i++) {
                Resource location = this.gadgets[i];
                log.info("Initializing Gadget from: " + location + "");
                try {
                   final InputStream is = location.getInputStream();
                    try {
                        Properties properties = loadPropertyFile(is);
                        directory.add(properties);
                        log.debug("adding to map getDirectoy "+properties.getProperty("name"));
                        mapDirectory.put(properties.getProperty("name"), properties);
                    }
                    finally {
                        is.close();
                    }
                } catch (IOException ex) {
                    String msg = "Could not load gadget from " + location;
                    log.warn(msg + ": " + ex.getMessage());
                } catch (Exception ex) {
                     log.error(" " + ex.getMessage());
                }
            }
        }
    }

    /**
     *
     * @return
     * @throws IOException
     */
    private Properties loadPropertyFile(final InputStream is) throws IOException{
        Properties prop = new Properties();
        prop.load(is);
        log.info("Gadget :"+prop.get("widget") +" Desc: "+prop.get("description"));
        return prop;
    }

    /**
     *
     * @return
     */
    public static List<Properties> getDirectoy(){
        return directory;
    }

    /**
    *
    * @return
    */
   public static Properties getDirectoy(final String key){
       log.debug("getDirectoy "+mapDirectory.size());
       log.debug("getDirectoy "+mapDirectory);
       return mapDirectory.get(key);
   }

    /**
     * @param gadgets the gadgets to set
     */
    public void setGadgets(Resource[] gadgets) {
        this.gadgets = gadgets;
    }
}
