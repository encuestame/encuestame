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
package org.encuestame.core.test.config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.encuestame.core.config.ConfigurationManager;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import junit.framework.TestCase;

/**
 * Test for {@link ConfigurationManager}.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 4, 2011
 */
public class XmlConfigurationText extends TestCase{

    /**
     * @throws ConfigurationException
     *
     */
    @Test
    public void testXmlFile(){
        //final ConfigurationManager configurationManager = new ConfigurationManager("encuestame-config.xml");
        //System.out.println(configurationManager.getIntProperty("encuestame.database.version"));
        XMLConfiguration xmlConfiguration = null;
        try {
            Resource  res = new ClassPathResource("properties-test/encuestame-config.xml");
            System.out.println(res.getFilename());
            System.out.println(res.getFile().getAbsolutePath());
            xmlConfiguration = new XMLConfiguration(res.getFile());
            System.out.println(xmlConfiguration.getString("encuestame.database.version"));
            System.out.println(xmlConfiguration.getString("database.version"));
            xmlConfiguration.setAutoSave(true);
            xmlConfiguration.addProperty("juan", "juan");
        } catch (ConfigurationException e) {

            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
